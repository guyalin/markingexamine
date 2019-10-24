package com.woniu.markingsystem.service;

import java.util.Map;

public interface ArticleExamineService {

    Map<String, Object> distributeFunction();

    void saveDistributeResult(Map<String, Object> resultMap) throws Exception;

}
