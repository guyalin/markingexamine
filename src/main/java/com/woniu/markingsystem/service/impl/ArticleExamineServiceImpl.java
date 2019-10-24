package com.woniu.markingsystem.service.impl;

import com.woniu.markingsystem.bean.Article;
import com.woniu.markingsystem.bean.Book;
import com.woniu.markingsystem.bean.Reviewer;
import com.woniu.markingsystem.dao.ArticleExamineDao;
import com.woniu.markingsystem.dto.ArticleDistributeDTO;
import com.woniu.markingsystem.dto.ReviewerDistributeDTO;
import com.woniu.markingsystem.po.ArticleDistributePo;
import com.woniu.markingsystem.po.ReviewerDistributePo;
import com.woniu.markingsystem.service.ArticleExamineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArticleExamineServiceImpl implements ArticleExamineService {

    private static final int NEED_COUNT = 5;

    //private int current_index = 0;

    @Autowired
    private ArticleExamineDao articleExamineDao;

    @Override
    public Map<String, Object> distributeFunction(){
        Map<String, Object> listMap = new HashMap<>();
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
            if (articleList == null || articleList.size() ==0){
                log.info("没有为{}类型文章的评阅人存在！",book.getBookId());
                continue;
            }
            //将某一类型的所有文章进行分发给评阅人，列表是以文章为主键的情况
            List<ArticleDistributeDTO> articleDtos = AutoDistribute(articleList, reviewers);
            if (articleDtos != null && articleDtos.size() > 0){
                distributeArticleDTOS.addAll(articleDtos);
            }
            //将以文章为主键的文章评阅列表转化成以评阅人的视角列表
            List<ReviewerDistributeDTO> reviewerDTOS = convertToReviewerDTO(articleDtos, reviewers);
            if (reviewerDTOS != null && reviewerDTOS.size() > 0){
                distributeResultDTOS.addAll(reviewerDTOS);
            }
        }
        listMap.put("ArticleDistributeResult", distributeArticleDTOS);
        listMap.put("ReviewerDistributeResult", distributeResultDTOS);
        return listMap;
    }

    @Override
    public void saveDistributeResult(Map<String, Object> resultMap) {
        //Map<String, Object> resultMap = distributeFunction();
        List<ArticleDistributePo> articleDistributePos = new ArrayList<>();
        List<ReviewerDistributePo> reviewerDistributePo = new ArrayList<>();
        List<ArticleDistributeDTO> articleDistributeDTOS = new ArrayList<>();
        List<ReviewerDistributeDTO> reviewerDistributeDTOS = new ArrayList<>();

        articleDistributeDTOS = (List<com.woniu.markingsystem.dto.ArticleDistributeDTO>) resultMap.get("ArticleDistributeResult");
        reviewerDistributeDTOS = (List<com.woniu.markingsystem.dto.ReviewerDistributeDTO>) resultMap.get("ReviewerDistributeResult");
        articleDistributePos = ArticleDistributeDTO.convertToPos(articleDistributeDTOS);
        reviewerDistributePo = ReviewerDistributeDTO.convertToPos(reviewerDistributeDTOS);
        articleExamineDao.clearDistributeResult(); //先清空结果表
        articleExamineDao.saveArticleDistributeResult(articleDistributePos);
        articleExamineDao.saveReviewerDistributeResult(reviewerDistributePo);
    }


    /**
     * 对需要自动分配评阅文章类型的书目进行匹配
     * @return
     */
    private List<ArticleDistributeDTO> AutoDistribute(List<Article> articleList, List<Reviewer> reviewers) {

        int current_index = 0;
        List<ArticleDistributeDTO> articleDistributeDTOS = new ArrayList<>();
        for (Article article: articleList) {
            ArticleDistributeDTO distributeDTO;
            distributeDTO = distributeToReviewers(article, reviewers, current_index);
            articleDistributeDTOS.add(distributeDTO);
        }
        return articleDistributeDTOS;
    }

    /**
     * 将一个文章进行分发
     * @param article
     * @param reviewers
     * @param currentIndex
     * @return
     */
    private ArticleDistributeDTO distributeToReviewers(Article article, List<Reviewer> reviewers, int currentIndex){
        ArticleDistributeDTO articleDTO = new ArticleDistributeDTO();
        int count = article.getExamineCount(); //文章分发次数
        List<Integer> reviewerList = articleDTO.getReviewerList(); //待分发人员列表
        int fullReadCount = reviewers.size();//同类型文章评阅人数（包含自己的文章）
        while (count < NEED_COUNT && fullReadCount > 0){
            Reviewer reviewer = reviewers.get(currentIndex);
            int reviewerId = reviewer.getReviewerId();
            if (article.getAuthor().equals(reviewer.getName())){
                currentIndex = (currentIndex+1)%reviewers.size();
            } else if (!reviewerList.contains(reviewerId)){
                reviewerList.add(reviewerId);
                currentIndex = (currentIndex+1)%reviewers.size();
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
