package com.woniu.markingsystem.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Book implements Serializable {

    private static final long serialVersionUID = 2197704234527825566L;
    private int bookId;
    private String bookName;
    private int examineKind;
}
