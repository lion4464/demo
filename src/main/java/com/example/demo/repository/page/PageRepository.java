package com.example.demo.repository.page;

import com.example.demo.model.page.Page;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PageRepository {
    void save(Page page);
    List<Page> findByStoryId(UUID storyId);

    Optional<Page> findById(UUID pageId);

    Page update(Page page);
}