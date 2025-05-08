package com.example.demo.model.story;

import com.example.demo.model.page.Page;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Story {
    private UUID id;

    private String title;

    private List<Page> pages = new ArrayList<>();
}
