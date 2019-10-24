package com.woniu.markingsystem.dto;

import com.woniu.markingsystem.po.ReviewerDistributePo;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewerDistributeDTO implements Serializable {

    private static final long serialVersionUID = 2837588044467799330L;
    private int reviewerId;
    private String reviewerName;
    private int bookId;
    private List<Integer> articleList = new ArrayList<>();

    public static List<ReviewerDistributePo> convertToPos(List<ReviewerDistributeDTO> reviewerDistributeDTOS){
        List<ReviewerDistributePo> reviewerDistributePos = new ArrayList<>();
        for (ReviewerDistributeDTO reviewerDistributeDTO : reviewerDistributeDTOS) {
            List<String> reviewerListStr = new ArrayList<>();
            for (Integer i : reviewerDistributeDTO.getArticleList()) {
                reviewerListStr.add(Integer.toString(i));
            }
            ReviewerDistributePo po = new ReviewerDistributePo(
                    reviewerDistributeDTO.getReviewerId(),
                    reviewerDistributeDTO.getReviewerName(),
                    reviewerDistributeDTO.getBookId(),
                    String.join(",", reviewerListStr)
            );
            reviewerDistributePos.add(po);
        }
        return reviewerDistributePos;
    }

}
