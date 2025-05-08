package com.example.demo.service.story;

import com.example.demo.dto.StoryResponseDto;
import com.example.demo.mappers.dto_mappers.StoryMapper;
import com.example.demo.model.story.Story;
import com.example.demo.repository.story.StoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;

    public List<StoryResponseDto> getAllStories() {
        return storyRepository.findAllWithPages().stream().map(StoryMapper::toDto).toList();
    }

    public boolean existsById(UUID storyId) {
      return storyRepository.existsById(storyId);
    }
}
