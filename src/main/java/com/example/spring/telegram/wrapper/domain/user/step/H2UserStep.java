package com.example.spring.telegram.wrapper.domain.user.step;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_step")
@Getter
@Setter
public class H2UserStep extends UserStep {

}
