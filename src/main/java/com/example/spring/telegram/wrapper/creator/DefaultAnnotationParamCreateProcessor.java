package com.example.spring.telegram.wrapper.creator;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class DefaultAnnotationParamCreateProcessor implements AnnotationParamCreateProcessor {

    private final List<BotParamCreatorChain> botParamCreatorChains;
    private final Map<Class<? extends Annotation>, BotParamCreatorChain> createHandlerStrategyMap;

    public DefaultAnnotationParamCreateProcessor(Map<Class<? extends Annotation>, BotParamCreatorChain> createHandlerStrategyMap) {
        this.botParamCreatorChains = new ArrayList<>();
        this.createHandlerStrategyMap = createHandlerStrategyMap;
    }

    @Override
    public AnnotationParamCreateProcessor addCreator(BotParamCreatorChain paramCreatorChain) {
        botParamCreatorChains.add(paramCreatorChain);
        return this;
    }

    @Override
    public Object[] process(Update message, Method method) {
        Map<Parameter, List<Annotation>> suitableParamAnnotationMap = getSuitableParamAnnotationMap(method);
        List<Object> params = new ArrayList<>();

        for (Entry<Parameter, List<Annotation>> parameterEntry : suitableParamAnnotationMap.entrySet()) {
            Object[] array = suitableParamAnnotationMap.get(parameterEntry.getKey())
                    .stream()
                    .map(Annotation::annotationType)
                    .map(createHandlerStrategyMap::get)
                    .map(botParamCreatorChains ->
                            botParamCreatorChains.create(message, parameterEntry.getKey()))
                    .toArray();
            params.addAll(Arrays.asList(array));
        }

        return params.toArray();
    }

    private Map<Parameter, List<Annotation>> getSuitableParamAnnotationMap(Method method) {
        Parameter[] parameters = method.getParameters();
        return Arrays.stream(parameters)
                .collect(Collectors.toMap(
                        parameter -> parameter,
                        parameter -> Arrays.asList(parameter.getAnnotations())
                ));
    }
}
