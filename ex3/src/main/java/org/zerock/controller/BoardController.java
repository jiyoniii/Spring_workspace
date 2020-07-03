package org.zerock.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j //�α� ����� ���� ������̼�
@RequestMapping("/board/*") //��ǥ�ּҸ� board�� ���Ѱ���!
@AllArgsConstructor

public class BoardController {
	private BoardService service;
	
	//��Ʈ�ѷ��� ��� �Խ��ǵ� �̵��� ���״�� '��Ʈ��'���ִ� ���̱� ������ ���⼭ ��� jsp���Ϸ� �̵��� �� �ֵ��� ��Ʈ���ϴ� ������.
	
	//���ȭ��
//	@GetMapping("/list")
//	public void list (Model model) {
//		log.info("list");
//		//model�� ����ؼ� list��� �̸����� jsp�� ����� ǥ���ϰԵ�. jsp������ list�� ��� attribute���޹ްԵǾ�, ����Ʈ�� ��- ������ ��.
//		model.addAttribute("list",service.getList());
//	}
	
	@GetMapping("/list")
	public void list (Criteria cri,Model model) {
		
		log.info("list: "+cri);
		//model�� ����ؼ� list��� �̸����� jsp�� ����� ǥ���ϰԵ�. jsp������ list�� ��� attribute���޹ްԵǾ�, ����Ʈ�� ��- ������ ��.
		model.addAttribute("list",service.getList(cri));
		
		//cri������ ���� 123�� ���� ������ 123���� ���Ƿ� �����Ͽ� �ۼ��Ͽ���.
		//model.addAttribute("pageMaker", new PageDTO(cri,123));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
		
		
	}

	//���ȭ��. 
	@GetMapping("/register")
	public void register() {} //register.jsp�� �̵�
	
	
	//���ó��. �����Է��� �� BoardVO�� board��� �̸����� ����ϰԵ�.?
	@PostMapping("/register")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("-----------------------------------------------");
		log.info("register : " + board);
		
		if(board.getAttachList() != null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		log.info("-----------------------------------------------");
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno());
		return "redirect:/board/list";
	}
	
	//�󼼺��� & ����ȭ��.
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno")Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/get or /modify");
		model.addAttribute("board",service.get(bno));
	}
	
	/*
	 * //�󼼺��� & ����ȭ��. ���� ������ get�� modify�� �и��ϸ� �̷��� �ڵ��� ���� ����.
	 * 
	 * @GetMapping("/modify") public void get(@RequestParam("bno")Long bno, Model
	 * model) {
	 * 
	 * log.info("/modify"); model.addAttribute("board",service.get(bno)); }
	 */
	
	//����ó��
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri ,RedirectAttributes rttr) {
		log.info("modify: " + board);
		
		if(service.modify(board)) {
			rttr.addFlashAttribute("result", "success");
		}
		
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
		//�ٷ����� �ڵ� ��ſ� +cri.getListLink()�� �ٿ� ����ϴ°͵� ������.
		return "redirect:/board/list"+cri.getListLink();
	}
	
	//����ó��
	@PostMapping("/remove")
	public String remove (@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove......."+bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		log.info("attachList :" + attachList);
		
		if(service.remove(bno)) {
			
			deleteFiles(attachList); //÷�����ϵ� �����
			
			rttr.addFlashAttribute("result","success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
//		//�ٷ����� �ڵ� ��ſ� +cri.getListLink()�� �ٿ� ����ϴ°͵� ������.
		return "redirect:/board/list"+cri.getListLink();
	}
	
	@GetMapping(value="/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("getAttachList" + bno);
		
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() ==0) {
			return;
		}
		
		log.info("delete attach files....");
		log.info(attachList);
		
		attachList.forEach(attach -> {
			try {
				Path file = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					
					Files.delete(thumbNail);
				}
			}catch(Exception e) {
				log.error("delete file error" + e.getMessage());
			}
		});
	}

	
	
}
