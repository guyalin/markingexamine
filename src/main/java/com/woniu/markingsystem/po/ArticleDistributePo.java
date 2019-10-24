package com.woniu.markingsystem.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDistributePo {

    private int articleId;
    private String articleName;
    private int bookId;
    private String reviewerListStr;
}
