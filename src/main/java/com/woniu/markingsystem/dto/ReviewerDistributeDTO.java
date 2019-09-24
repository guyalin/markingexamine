package com.woniu.markingsystem.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReviewerDistributeDTO implements Serializable {

    private int reviewerId;
    private String reviewerName;
    private int bookId;
    private List<Integer> articleList = new ArrayList<>();
}
