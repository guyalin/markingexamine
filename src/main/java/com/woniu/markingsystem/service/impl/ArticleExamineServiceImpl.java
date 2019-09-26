package com.woniu.markingsystem.service.impl;

import com.woniu.markingsystem.bean.Article;
import com.woniu.markingsystem.bean.Book;
import com.woniu.markingsystem.bean.Reviewer;
import com.woniu.markingsystem.dao.ArticleExamineDao;
import com.woniu.markingsystem.dto.ArticleDistributeDTO;
import com.woniu.markingsystem.dto.ReviewerDistributeDTO;
import com.woniu.markingsystem.service.ArticleExamineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ArticleExamineServiceImpl implements ArticleExamineService {

    private static final int NEED_COUNT = 5;

    private int current_index = 0;

    @Autowired
    private ArticleExamineDao articleExamineDao;

    public List<ReviewerDistributeDTO> distributeFunction(){
        List<ReviewerDistributeDTO> distributeResultDTOS = new ArrayList<>();
        List<ArticleDistributeDTO> distributeArticleDTOS = new ArrayList<>();
        List<Book> books = articleExamineDao.queryBooksForAutoExamine();
        for (Book book : books) {
            List<Article> articleList = articleExamineDao.queryArticleByType(book.getBookId());
            List<Reviewer> reviewers = articleExamineDao.queryReviewerByType(book.getBookId()); //查出的人员信息已经随机排序
            if (articleList == null || articleList.size() ==0){
                log.info("类型为{}的文章不存在！",book.getBookId());
                continue;
            }
            List<ArticleDistributeDTO> articleDtos = AutoDistribute(articleList, reviewers);
            List<ReviewerDistributeDTO> reviewerDTOS = convertToReviewerDTO(articleDtos, reviewers);
            if (articleDtos != null && articleDtos.size() > 0){
                distributeArticleDTOS.addAll(articleDtos);
            }
            if (reviewerDTOS != null && reviewerDTOS.size() > 0){
                distributeResultDTOS.addAll(reviewerDTOS);
            }
        }
        return distributeResultDTOS;
    }


    /**
     * 对需要自动分配评阅文章类型的书目进行匹配
     * @return
     */
    private List<ArticleDistributeDTO> AutoDistribute(List<Article> articleList, List<Reviewer> reviewers) {

        current_index = 0;
        List<ArticleDistributeDTO> articleDistributeDTOS = new ArrayList<>();
        for (Article article: articleList) {
            ArticleDistributeDTO distributeDTO;
            distributeDTO = distributeToReviewers(article, reviewers);
            articleDistributeDTOS.add(distributeDTO);
        }
        return articleDistributeDTOS;
    }

    private ArticleDistributeDTO distributeToReviewers(Article article, List<Reviewer> reviewers){
        ArticleDistributeDTO articleDTO = new ArticleDistributeDTO();
        int count = article.getExamineCount(); //文章分发次数
        List<Integer> reviewerList = articleDTO.getReviewerList(); //分发人员列表
        int fullReadCount = reviewers.size();
        while (count < NEED_COUNT && fullReadCount > 0){
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
            fullReadCount--; //剩余分发人数
        }


        article.setExamineCount(count);
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
            reviewerDTO.setBookId(reviewer.getExamineBook());
            for (ArticleDistributeDTO articleDTOS : articleDistributeDTOS) {
                if (articleDTOS.getReviewerList().contains(reviewer.getReviewerId())){
                    reviewerDTO.getArticleList().add(articleDTOS.getArticleId());
                }
            }
            reviewerDistributeDTOS.add(reviewerDTO);
        }
        return reviewerDistributeDTOS;
    }

}
