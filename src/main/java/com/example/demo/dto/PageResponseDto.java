package com.example.demo.dto;

import com.example.demo.model.enums.Reaction;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDto {
    private UUID id;
    private String content;
    private Reaction reaction;
}