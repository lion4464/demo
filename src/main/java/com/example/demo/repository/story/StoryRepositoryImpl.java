package com.example.demo.repository.story;

import com.example.demo.model.enums.Reaction;
import com.example.demo.model.story.Story;
import com.example.demo.model.page.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class StoryRepositoryImpl implements StoryRepository {
    private final JdbcTemplate jdbcTemplate;

        @Override
        public List<Story> findAllWithPages() {
            String sql = """
            SELECT s.id AS story_id, s.title AS story_title,
                   p.id AS page_id, p.content, p.reaction, p.story_id
            FROM story s
            LEFT JOIN page p ON s.id = p.story_id
        """;

            Map<UUID, Story> storyMap = new LinkedHashMap<>();

            jdbcTemplate.query(sql, rs -> {
                UUID storyId = UUID.fromString(rs.getString("story_id"));
                String title = rs.getString("story_title");

                Story story = storyMap.computeIfAbsent(storyId, id ->
                        new Story(id, title, new ArrayList<>())
                );

                UUID pageId = rs.getObject("page_id", UUID.class);
                if (pageId != null) {
                    Page page = new Page(
                            pageId,
                            rs.getString("content"),
                            Reaction.valueOf(rs.getString("reaction")),
                            storyId
                    );
                    story.getPages().add(page);
                }
            });

            return new ArrayList<>(storyMap.values());
        }
@Override
    public void save(Story story) {
        String sql = "INSERT INTO story (id, title) VALUES (?, ?)";
        jdbcTemplate.update(sql, story.getId(), story.getTitle());
    }

    @Override
    public boolean existsById(UUID id) {
        String sql = "SELECT COUNT(*) FROM story WHERE id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

}
