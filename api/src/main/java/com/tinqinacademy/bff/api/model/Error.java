package com.tinqinacademy.bff.api.model;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Error {
    private String field;
    private String message;
}
