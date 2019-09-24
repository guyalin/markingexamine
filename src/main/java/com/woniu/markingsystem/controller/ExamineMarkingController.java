package com.woniu.markingsystem.controller;

import com.woniu.markingsystem.service.ArticleExamineService;
import com.woniu.markingsystem.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/net")
public class ExamineMarkingController {

    @Autowired
    private ArticleExamineService articleExamineService;

    @GetMapping(value = "/examine/distribute")
    public JsonResult loadNetNewsArticleToDB(){
        JsonResult jsonResult = new JsonResult();
        try {
            articleExamineService.distributeFunction();
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            return jsonResult;
        }
    }

}
