package com.example.spring.telegram.wrapper.domain.user.step;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
@MappedSuperclass
public abstract class UserStep {

     @Id
     private Long id;

     @Setter
     private String url;

     @Setter
     private Update message;
}
