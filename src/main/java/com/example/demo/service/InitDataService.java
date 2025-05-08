package com.example.demo.service;
import com.example.demo.model.enums.Reaction;
import com.example.demo.model.page.Page;
import com.example.demo.model.story.Story;
import com.example.demo.repository.page.PageRepository;
import com.example.demo.repository.story.StoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class InitDataService {

    private final List<Story> stories = new ArrayList<>();
    private final StoryRepository storyRepository;
    private final PageRepository pageRepository;


    @PostConstruct
    public void initData() {
            UUID storyId = UUID.randomUUID();
        Story story = Story.builder()
                .id(storyId)
                .title("A Short Tale")
                .build();

        Page page1 = Page.builder()
                .id(UUID.randomUUID())
                .content("Once upon a time...")
                .reaction(Reaction.LIKED)
                .storyId(storyId)
                .build();

        Page page2 = Page.builder()
                .id(UUID.randomUUID())
                .content("The end.")
                .reaction(Reaction.VIEWED)
                .storyId(storyId)
                .build();


        stories.add(story);
            storyRepository.save(story);
            pageRepository.save(page1);
            pageRepository.save(page2);
        System.out.println("Initialized story data: " + stories);
    }

    public List<Story> getStories() {
        return stories;
    }
}
