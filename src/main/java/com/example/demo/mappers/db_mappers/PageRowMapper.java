package com.example.demo.mappers.db_mappers;

import com.example.demo.model.enums.Reaction;
import com.example.demo.model.page.Page;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PageRowMapper implements RowMapper<Page> {
    @Override
    public Page mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Page.builder()
                .id(UUID.fromString(rs.getString("id")))
                .content(rs.getString("content"))
                .reaction(Reaction.valueOf(rs.getString("reaction")))
                .storyId(UUID.fromString(rs.getString("story_id")))
                .build();
    }
}
