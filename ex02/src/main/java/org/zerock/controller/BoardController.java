package org.zerock.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j //로그 출력을 위한 어노테이션
@RequestMapping("/board/*") //대표주소를 board로 정한것임!
@AllArgsConstructor

public class BoardController {
	private BoardService service;
	
	//컨트롤러는 모든 게시판들 이동을 말그대로 '컨트롤'해주는 곳이기 때문에 여기서 모든 jsp파일로 이동할 수 있도록 컨트롤하는 공간임.
	
	//목록화면
	@GetMapping("/list")
	public void list (Model model) {
		
		log.info("list");
		
		//model을 사용해서 list라는 이름으로 jsp에 목록을 표시하게됨. jsp에서는 list로 모든 attribute전달받게되어, 리스트로 쭉- 나오게 됨.
		
		model.addAttribute("list",service.getList());
	}

	//등록화면. 
	@GetMapping("/register")
	public void register() {} //register.jsp로 이동
	
	
	//등록처리. 폼에입력할 때 BoardVO의 board라는 이름으로 출력하게됨.?
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("register : " + board);
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		return "redirect:/board/list";
	}
	
	//상세보기
	@GetMapping("/get")
	public void get(@RequestParam("bno")Long bno, Model model) {
		
		log.info("/get");
		model.addAttribute("board",service.get(bno));
	}
	
	
}
