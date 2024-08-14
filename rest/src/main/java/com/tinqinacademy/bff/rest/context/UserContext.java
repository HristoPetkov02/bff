package com.tinqinacademy.bff.rest.context;

import lombok.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class UserContext {
    private String userId;
}
