package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j //로그 출력을 위한 어노테이션
@RequestMapping("/board/*") //대표주소를 board로 정한것임!
@AllArgsConstructor

public class BoardController {
	private BoardService service;
	
	@GetMapping("/list")
	public void list (Model model) {
		
		log.info("list");
		
		//model을 사용해서 list라는 이름으로 jsp에 목록을 표시하게됨.
		model.addAttribute("list",service.getList());
	}
	
}
