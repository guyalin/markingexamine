package com.woniu.markingsystem.dto;

import com.woniu.markingsystem.po.ArticleDistributePo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleDistributeDTO implements Serializable {

    private static final long serialVersionUID = 3726729432814864195L;
    private int articleId;
    private String articleName;
    private int bookId;
    private List<Integer> reviewerList = new ArrayList<>();

    public static List<ArticleDistributePo> convertToPos(List<ArticleDistributeDTO> articleDistributeDTOS){
        List<ArticleDistributePo> articleDistributePos = new ArrayList<>();
        for (ArticleDistributeDTO articleDistributeDTO : articleDistributeDTOS) {
            List<String> reviewerListStr = new ArrayList<>();
            for (Integer i : articleDistributeDTO.getReviewerList()) {
                reviewerListStr.add(Integer.toString(i));
            }
            ArticleDistributePo po = new ArticleDistributePo(
                    articleDistributeDTO.getArticleId(),
                    articleDistributeDTO.getArticleName(),
                    articleDistributeDTO.getBookId(),
                    String.join(",", reviewerListStr)
            );
            articleDistributePos.add(po);
        }
        return articleDistributePos;
    }

}
