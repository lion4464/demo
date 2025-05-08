package com.example.demo.model.page;

import com.example.demo.model.enums.Reaction;
import com.example.demo.model.story.Story;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Page {
    private  UUID id;
    private  String content;
    private  Reaction reaction;
    private  UUID storyId;
}
