package com.example.demo.mappers.dto_mappers;
import com.example.demo.model.page.Page;
import com.example.demo.dto.PageResponseDto;
import org.springframework.stereotype.Component;


public class PageMapper {
    public static PageResponseDto toDto(Page page) {
        return PageResponseDto.builder()
                .id(page.getId())
                .content(page.getContent())
                .reaction(page.getReaction())
                .build();
    }
}
