package com.woniu.markingsystem.dto;

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

}
