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

@Controller // <-- �� ������̼����� �������� Bean���� �ν��� �� �ְ� �ؾ���.
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor  //<--�긦 �̿��� �����ڸ� ����� �ڵ�����.
public class BoardController {
	
	//BoardService.java�� ������. ��Ʈ�ѷ��� boardService�� ���ؼ� ��������.
	private BoardService service;
	
	//���
	@GetMapping("/list")
	public void list(Model model) {
		log.info("list");
		model.addAttribute("list", service.getList());
	}
	
	@GetMapping("/register")
	public void register() {}
	
	//���
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		
		log.info("register: "+board);
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		
		//spring���� 'redirect:'�� ����ϸ� response.sendRedirect()�� ó������. 
		//'redirect:'�� ����ϱ� ���ؼ� ���� rttr.addFlashAttribute�� ����ؾ� ��.
		//�̷��� ����ϰԵǸ� board/list�� �̵��ϰԵ�.
		return "redirect:/board/list";
	}
	
	//��ȸ
	@GetMapping("/get")
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		//model.addAttribute("�����̸�","������ ���� �����Ͱ�");
		//�̷��� �ϰԵǸ� Model��ü�� �Ķ���ͷ� �޾Ƽ� �����͸� View�� �ѱ� �� ����.
		//View������ ${board} ��ȣ�ȿ� �����̸��� �־� ���� �����´�.
		model.addAttribute("board", service.get(bno));
	}
	
	//����
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr) {
		log.info("modify: "+board);
		
		//boardService���� modify�� boolean���� �����߱� ������ ������ ��쿡 "result" "success"�� �ۼ���.
		if(service.modify(board)) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/board/list";
	}
	
	//����
	@PostMapping("/remove")
	public String remove (@RequestParam("bno") Long bno, RedirectAttributes rttr) {
		log.info("remove....." + bno);
		if (service.remove(bno)) {
			rttr.addFlashAttribute("result", "success");
		}
		return "redirect:/board/list";
	}
	
	
}
