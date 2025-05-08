package com.example.demo.controller;

import com.example.demo.dto.PageResponseDto;
import com.example.demo.dto.StoryResponseDto;
import com.example.demo.generic.ResponseDto;
import com.example.demo.model.enums.Reaction;
import com.example.demo.service.page.PageService;
import com.example.demo.service.story.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;
    private final PageService pageService;

    @GetMapping
    public ResponseEntity<ResponseDto<List<StoryResponseDto>>> getAllStories() {
        return ResponseEntity.ok(
                new ResponseDto(ResponseDto.States.SUCCESS,HttpStatus.OK, HttpStatus.OK.value(), "Stories retrieved", storyService.getAllStories())
        );
    }


    @GetMapping("/{storyId}/pages")
    public ResponseEntity<ResponseDto<List<PageResponseDto>>> getPagesByStoryId(@PathVariable UUID storyId) {
        return ResponseEntity.ok(
        new ResponseDto(ResponseDto.States.SUCCESS,HttpStatus.OK, HttpStatus.OK.value(), "pages retrieved", pageService.getPagesByStoryId(storyId))
        );
    }

    @PutMapping("/pages/{pageId}/reaction")
    public ResponseEntity<ResponseDto<PageResponseDto>> reactToPage(
            @PathVariable UUID pageId,
            @RequestParam Reaction reaction) {

        return ResponseEntity.ok(
                new ResponseDto(ResponseDto.States.SUCCESS,HttpStatus.OK, HttpStatus.OK.value(), "Stories retrieved", pageService.reactToPage(pageId, reaction))
        );
    }

}
