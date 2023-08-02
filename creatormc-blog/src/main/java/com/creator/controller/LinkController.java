package com.creator.controller;

import com.creator.annotation.SystemLog;
import com.creator.domain.ResponseResult;
import com.creator.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    @SystemLog(businessName = "获取审核通过的友链")
    public ResponseResult getAllLink() {
        return linkService.getAllLink();
    }
}
