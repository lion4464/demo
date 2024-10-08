package com.example.demo.generic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageableRequest {
    private int perPage = 10;
    private int page = 0;

    private Sort sort = new Sort();

    private List<SearchCriteria> search;
}
