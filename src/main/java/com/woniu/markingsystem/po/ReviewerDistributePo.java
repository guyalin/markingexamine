package com.woniu.markingsystem.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewerDistributePo {

    private int reviewerId;
    private String reviewerName;
    private int bookId;
    private String articleListStr;
}
