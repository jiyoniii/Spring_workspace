package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/sample/*")
@Controller
public class SampleController {

	@GetMapping("/all")
	public void doAll() {
		log.info("누구나 진입가능");
	}
	
	@GetMapping("/member")
	public void doMember() {
		log.info("멤버만 진입가능");
	}

	@GetMapping("/admin")
	public void doAdmin() {
		log.info("관리자모드");
	}
	
}
