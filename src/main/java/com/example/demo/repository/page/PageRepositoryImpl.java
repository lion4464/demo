package com.example.demo.repository.page;

import com.example.demo.mappers.db_mappers.PageRowMapper;
import com.example.demo.model.page.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PageRepositoryImpl implements PageRepository{
    private final JdbcTemplate jdbcTemplate;

    public void save(Page page) {
        String sql = "INSERT INTO page (id, content, reaction, story_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                page.getId(),
                page.getContent(),
                page.getReaction().name(),
                page.getStoryId());
    }

        @Override
        public List<Page> findByStoryId(UUID storyId) {
            String sql = "SELECT * FROM page WHERE story_id = ?";
            return jdbcTemplate.query(sql, new Object[]{storyId}, new PageRowMapper());
        }


    @Override
    public Optional<Page> findById(UUID id) {
        String sql = "SELECT * FROM page WHERE id = ?";
        List<Page> pages = jdbcTemplate.query(sql, new Object[]{id}, new PageRowMapper());
        return pages.stream().findFirst();
    }

    @Override
    public Page  update(Page page) {
            String sql = "UPDATE page SET reaction = ? WHERE id = ? ";
        jdbcTemplate.update(sql,
                page.getReaction().name(),
                page.getId());

        String selectSql = "SELECT * FROM page WHERE id = ?";
        return jdbcTemplate.queryForObject(selectSql,
                new Object[]{page.getId()},
                new PageRowMapper());
    }


}
