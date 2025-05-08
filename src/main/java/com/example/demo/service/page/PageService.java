package com.example.demo.service.page;

import com.example.demo.dto.PageResponseDto;
import com.example.demo.exception.RecordNotFoundException;
import com.example.demo.mappers.dto_mappers.PageMapper;
import com.example.demo.model.enums.Reaction;
import com.example.demo.model.page.Page;
import com.example.demo.repository.page.PageRepository;
import com.example.demo.service.story.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PageService {
    private final PageRepository pageRepository;
    private final StoryService storyService;

    public Page getPageById(UUID pageId){
        return pageRepository.findById(pageId)
                .orElseThrow(() -> new RecordNotFoundException("Page not found"));
    }

    public List<PageResponseDto> getPagesByStoryId(UUID storyId) {
        if(!storyService.existsById(storyId)){
            throw new RecordNotFoundException("Story not found");
        }
         return pageRepository.findByStoryId(storyId).stream().map(PageMapper::toDto).toList();

    }
    @Transactional
    public PageResponseDto reactToPage(UUID pageId, Reaction reaction) {
        Page page = getPageById(pageId);
        page.setReaction(reaction);
        return PageMapper.toDto(pageRepository.update(page));
    }

}
