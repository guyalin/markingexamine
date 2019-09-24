package com.woniu.markingsystem.service.impl;

import com.woniu.markingsystem.bean.Article;
import com.woniu.markingsystem.bean.Reviewer;
import com.woniu.markingsystem.dao.ArticleExamineDao;
import com.woniu.markingsystem.dto.ArticleDistributeDTO;
import com.woniu.markingsystem.dto.ReviewerDistributeDTO;
import com.woniu.markingsystem.service.ArticleExamineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ArticleExamineServiceImpl implements ArticleExamineService {

    private static final int NEED_COUNT = 5;

    private int current_index = 0;

    @Autowired
    private ArticleExamineDao articleExamineDao;

    @Override
    public List<ReviewerDistributeDTO> distributeFunction() {
        List<Article> articleList = articleExamineDao.queryArticleByType(2);
        List<Reviewer> reviewers = articleExamineDao.queryReviewerByType(2);
        current_index = 0;
        List<ArticleDistributeDTO> articleDistributeDTOS = new ArrayList<>();
        for (Article article: articleList) {
            ArticleDistributeDTO distributeDTO;
            distributeDTO = distributeToReviewers(article, reviewers);
            articleDistributeDTOS.add(distributeDTO);
        }

        List<ReviewerDistributeDTO> reviewerDistributeDTOS = convertToReviewerDTO(articleDistributeDTOS, reviewers);

        return reviewerDistributeDTOS;
    }

    private ArticleDistributeDTO distributeToReviewers(Article article, List<Reviewer> reviewers){
        ArticleDistributeDTO articleDTO = new ArticleDistributeDTO();
        int count = article.getExamineCount(); //文章分发次数
        List<Integer> reviewerList = articleDTO.getReviewerList(); //分发人员列表
        while (count < NEED_COUNT){
            Reviewer reviewer = reviewers.get(current_index);
            int reviewerId = reviewer.getReviewerId();
            if (article.getAuthor().equals(reviewer.getName())){
                current_index = (current_index+1)%reviewers.size();
            } else if ( !article.getAuthor().equals(reviewer.getName())
                    && !reviewerList.contains(reviewerId)){
                reviewerList.add(reviewerId);
                current_index = (current_index+1)%reviewers.size();
                count++;
            }

        }
        articleDTO.setArticleId(article.getArticleId());
        articleDTO.setArticleName(article.getArticleName());
        articleDTO.setReviewerList(reviewerList);
        articleDTO.setBookId(article.getBookId());
        return articleDTO;
    }

    private List<ReviewerDistributeDTO> convertToReviewerDTO(List<ArticleDistributeDTO> articleDistributeDTOS, List<Reviewer> reviewers){
        List<ReviewerDistributeDTO> reviewerDistributeDTOS = new ArrayList<>();
        for (Reviewer reviewer: reviewers) {
            ReviewerDistributeDTO reviewerDTO = new ReviewerDistributeDTO();
            reviewerDTO.setReviewerId(reviewer.getReviewerId());
            reviewerDTO.setReviewerName(reviewer.getName());
            for (ArticleDistributeDTO articleDTOS : articleDistributeDTOS) {
                if (articleDTOS.getReviewerList().contains(reviewer.getReviewerId())){
                    reviewerDTO.getArticleList().add(articleDTOS.getArticleId());
                }
            }

            reviewerDistributeDTOS.add(reviewerDTO);
        }
        return reviewerDistributeDTOS;
    }


    @Override
    public List<Article> queryArticleByType(int type) {
        return null;
    }

    @Override
    public List<Reviewer> queryReviewerByType(int type) {
        return null;
    }
}
