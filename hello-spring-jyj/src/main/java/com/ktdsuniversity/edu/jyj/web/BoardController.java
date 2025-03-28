package com.ktdsuniversity.edu.jyj.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ktdsuniversity.edu.jyj.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/hello-jyj/board/list")
	public ModelAndView modelAndView = new ModelAndView();
}
