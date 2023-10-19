package com.search.service;

import com.search.entity.Data;
import com.search.entity.Result;

import java.util.List;
import java.util.Map;


public interface SearchService {
    // 通过
    List<Result> getDataByKeyword(String keyword, int pageSize, int pageNum);
}