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
import org.zerock.service.BoardServiceImpl;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller // <-- 이 어노테이션으로 스프링의 Bean으로 인식할 수 있게 해야함.
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor  //<--얘를 이용해 생성자를 만들고 자동주입.
public class BoardController {
	
	//BoardService.java를 가져옴. 콘트롤러는 boardService에 대해서 의존적임.
	private BoardService service;
	
	//목록
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}
	
	@GetMapping("/register")
	public void register() {}
	
	//등록
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("register: "+board);
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		//spring에서 'redirect:'를 사용하면 response.sendRedirect()를 처리해줌. 
		//'redirect:'를 사용하기 위해선 위의 rttr.addFlashAttribute를 사용해야 함.
		//이렇게 사용하게되면 board/list로 이동하게됨.
		return "redirect:/board/list";
	}
	
	//조회
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		//model.addAttribute("변수이름","변수에 넣을 데이터값");
		//이렇게 하게되면 Model객체를 파라미터로 받아서 데이터를 View로 넘길 수 있음.
		//View에서는 ${board} 괄호안에 변수이름을 넣어 값을 가져온다.
		model.addAttribute("board", service.get(bno));
	}
	
	//수정
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify: "+board);
		
		//boardService에서 modify는 boolean으로 지정했기 때문에 성공한 경우에 "result" "success"로 작성됨.
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
	
	//삭제
	@PostMapping("/remove")
	public String remove (@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove....." + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	
}
