package com.woniu.markingsystem.controller.business;

import com.woniu.markingsystem.service.ArticleExamineService;
import com.woniu.markingsystem.vo.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/examine")
public class ExamineMarkingController {

    @Autowired
    private ArticleExamineService articleExamineService;

    @GetMapping(value = "/distribute")
    public JsonResult ArticleDistributeAndShow(){
        JsonResult jsonResult = new JsonResult();
        try {
            Map<String, Object> reviewerDistributeDTOS;
            reviewerDistributeDTOS = articleExamineService.distributeFunction();
            articleExamineService.saveDistributeResult(reviewerDistributeDTOS);
            jsonResult.setReturnCode("SUCC");
            jsonResult.setReturnMsg("成功");
            jsonResult.setData(reviewerDistributeDTOS);
            return jsonResult;
        }catch (Exception e){
            jsonResult.setReturnCode("FAIL");
            jsonResult.setReturnMsg("失败");
            return jsonResult;
        }
    }

    /**
     * 测试当前用户
     * @return
     */
    @RequestMapping("/whoami")
    public Object whoIm(){
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
