package com.search.controller;

import com.search.entity.Data;
import com.search.entity.Result;
import com.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class SearchController {
    // 一页的所有搜索结果数量
    private final int resultNumInOnePage = 300;

    @Autowired
    private SearchService searchService;

    // 通过分词的方式去搜索
    @GetMapping("/search")
    public List<Result> searchBySegment(@RequestParam("keyword") String keyword, @RequestParam("pageNum") int pageNum) {
        return searchService.getDataByKeyword(keyword, resultNumInOnePage, pageNum);
    }
}
