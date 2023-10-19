package com.search.service.impl;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.SegToken;
import com.search.dao.DataDao;
import com.search.dao.SegmentDao;
import com.search.entity.Data;
import com.search.entity.Result;
import com.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private DataDao dataDao;

    @Autowired
    private SegmentDao segmentDao;

    // 搜索业务
    @Override
    public List<Result> getDataByKeyword(String keyword, int pageSize, int pageNum) {

        int offset = pageSize * (pageNum - 1);
        StringBuilder sb = new StringBuilder();

        // 对输入的关键字进行分词
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<SegToken> segTokens = segmenter.process(keyword, JiebaSegmenter.SegMode.SEARCH);

        boolean flag = true;
        for (SegToken segToken : segTokens) {
            // 获取关键词的 segment
            if (segmentDao.getOneSeg(segToken.word) == null) continue;

            // segment 为空 跳过
            if ("".equals(segToken.word.trim())) continue;

            // 获取segId
            int segId = segmentDao.getOneSeg(segToken.word).getId();

            // 通过segId找到去哪张表查找
            int idx = segId % 100;

            // 组合出一个sql语句：用于取各个关键词查出来的data_segment，union的方式去重
            if (flag) {
                sb.append("select * from data_seg_relation_").append(idx).append(" where seg_id = ").append(segId).append('\n');
                flag = false;
            } else {
                sb.append("union").append('\n');
                sb.append("select * from data_seg_relation_").append(idx).append(" where seg_id = ").append(segId).append('\n');
            }

        }
        String sql = sb.toString();
        System.out.println(sql);

        if ("".equals(sql)) return null;

        // 通过sql去获取所有的Data，详细见DataMapper.xml
        // offset 是第几页搜索结果的意思
        return dataDao.getDataBySplit(sql, pageSize, offset);
    }

}
