package com.example.demo.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoryResponseDto {
    private UUID id;
    private String title;
    private List<PageResponseDto> pages;
}