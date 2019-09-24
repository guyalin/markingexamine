package com.woniu.markingsystem.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Article implements Serializable {
    private static final long serialVersionUID = -5079823117955963819L;

    private int articleId;
    private String articleName;
    private String author;
    private String unit;
    private int bookId;
    private int examineCount;
    private float avg;

}
