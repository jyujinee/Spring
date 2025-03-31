package com.hello.bbs.web;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;

import com.hello.bbs.service.BoardService;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

}