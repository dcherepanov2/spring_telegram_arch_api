package com.example.spring.telegram.wrapper.compile.processor;


import com.example.spring.telegram.wrapper.annotations.BotHandler;
import com.example.spring.telegram.wrapper.annotations.BotRequestMapping;
import com.example.spring.telegram.wrapper.exceptions.BotHandlerCompilerException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeMirror;
import java.util.Set;
import java.util.stream.Stream;

@SupportedAnnotationTypes("com.example.aop_spring_telegram.annotations.BotHandler.java")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class BotHandlerProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return roundEnv.getElementsAnnotatedWith(BotHandler.class)
                .stream()
                .filter(this::isBotHandlerClass)
                .map(this::castTypeElement)
                .flatMap(this::getEnclosedElements)
                .filter(this::isSuitableMethod)
                .map(this::castExecutableMethod)
                .peek(this::process)
                .findFirst()
                .isPresent();
    }

    private void process(ExecutableElement methodElement) {
        if (!methodElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new BotHandlerCompilerException("Method " + methodElement.getSimpleName() + " must be public.");
        }
        if (!isSubtypeOfBotApiMethod(methodElement.getReturnType())) {
            throw new BotHandlerCompilerException("Method " + methodElement.getSimpleName() + " in "
                    + " must return a subtype of BotApiMethod<?>.");
        }
    }

    private Stream<? extends Element> getEnclosedElements(TypeElement typeElement) {
        return typeElement.getEnclosedElements().stream();
    }

    private ExecutableElement castExecutableMethod(Element element) {
        return (ExecutableElement) element;
    }

    private TypeElement castTypeElement(Element element) {
        return (TypeElement) element;
    }

    private boolean isSuitableMethod(Element enclosed) {
        return enclosed.getKind() == ElementKind.METHOD && enclosed.getAnnotation(BotRequestMapping.class) != null;
    }

    private boolean isBotHandlerClass(Element element) {
        return element.getKind() == ElementKind.CLASS;
    }

    private boolean isSubtypeOfBotApiMethod(TypeMirror typeMirror) {
        return processingEnv.getTypeUtils().isSubtype(typeMirror,
                processingEnv.getElementUtils().getTypeElement("com.example.BotApiMethod").asType());
    }
}
