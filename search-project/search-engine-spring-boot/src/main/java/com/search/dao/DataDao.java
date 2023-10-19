package com.search.dao;

import com.search.entity.Data;
import com.search.entity.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public interface DataDao {
    // 通过 limit & offset 获取一些Data
    List<Data> getSomeDatas(@Param("limit") int limit, @Param("offset") int offset);
    List<Result> getDataBySplit(@Param("sql")String sql, @Param("pageSize")int pageSize, @Param("offset")int offset);
}
