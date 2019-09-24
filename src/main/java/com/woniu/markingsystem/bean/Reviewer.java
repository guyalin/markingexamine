package com.woniu.markingsystem.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class Reviewer implements Serializable {
    private static final long serialVersionUID = 6562515127618445447L;

    private int reviewerId;
    private String name;
    private String unit;
    private int examineBook;

}
