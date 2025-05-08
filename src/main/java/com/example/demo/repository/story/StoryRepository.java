package com.example.demo.repository.story;

import com.example.demo.model.story.Story;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

public interface StoryRepository {
    List<Story> findAllWithPages();
    void save(Story story);
    boolean existsById(UUID storyId);
}