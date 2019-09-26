package com.woniu.markingsystem.dao;

import com.woniu.markingsystem.bean.Article;
import com.woniu.markingsystem.bean.Book;
import com.woniu.markingsystem.bean.Reviewer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleExamineDao {

    List<Article> queryArticleByType(int type);

    List<Reviewer> queryReviewerByType(int type);

    void updateArticleMarking(int articleId, float score);

    List<Book> queryBooksForAutoExamine();
}
