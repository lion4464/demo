package com.example.demo.generic;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    @Pattern(regexp="^(^(!=)?|^(<=)?|^(>=)?|^(=)?|^(<)?|^(>)?)$")
    private String operation;
    private Object value;
}
