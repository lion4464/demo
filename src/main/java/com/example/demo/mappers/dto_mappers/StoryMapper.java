package com.example.demo.mappers.dto_mappers;

import com.example.demo.dto.StoryResponseDto;
import com.example.demo.model.story.Story;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

public class StoryMapper {
    public static StoryResponseDto toDto(Story story) {
        return StoryResponseDto.builder()
                .id(story.getId())
                .title(story.getTitle())
                .pages(story.getPages().stream()
                        .map(PageMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
