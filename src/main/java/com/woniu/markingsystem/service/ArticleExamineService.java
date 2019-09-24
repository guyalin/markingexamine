package com.woniu.markingsystem.service;

import com.woniu.markingsystem.bean.Article;
import com.woniu.markingsystem.bean.Book;
import com.woniu.markingsystem.bean.Reviewer;
import com.woniu.markingsystem.dto.ArticleDistributeDTO;
import com.woniu.markingsystem.dto.ReviewerDistributeDTO;

import java.util.List;

public interface ArticleExamineService {

    List<ReviewerDistributeDTO> distributeFunction();

    List<Article> queryArticleByType(int type);

    List<Reviewer> queryReviewerByType(int type);

}
