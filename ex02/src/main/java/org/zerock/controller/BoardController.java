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
@Log4j //로그 출력을 위한 어노테이션
@RequestMapping("/board/*") //대표주소를 board로 정한것임!
@AllArgsConstructor

public class BoardController {
	private BoardService service;
	
	//컨트롤러는 모든 게시판들 이동을 말그대로 '컨트롤'해주는 곳이기 때문에 여기서 모든 jsp파일로 이동할 수 있도록 컨트롤하는 공간임.
	
	//목록화면
//	@GetMapping("/list")
//	public void list (Model model) {
//		log.info("list");
//		//model을 사용해서 list라는 이름으로 jsp에 목록을 표시하게됨. jsp에서는 list로 모든 attribute전달받게되어, 리스트로 쭉- 나오게 됨.
//		model.addAttribute("list",service.getList());
//	}
	
	@GetMapping("/list")
	public void list (Criteria cri,Model model) {
		
		log.info("list: "+cri);
		//model을 사용해서 list라는 이름으로 jsp에 목록을 표시하게됨. jsp에서는 list로 모든 attribute전달받게되어, 리스트로 쭉- 나오게 됨.
		model.addAttribute("list",service.getList(cri));
		
		//cri다음에 나온 123은 글의 갯수를 123개로 임의로 지정하여 작성하였음.
		//model.addAttribute("pageMaker", new PageDTO(cri,123));
		
		int total = service.getTotal(cri);
		
		log.info("total: " + total);
		
		model.addAttribute("pageMaker",new PageDTO(cri,total));
		
		
	}

	//등록화면. 
	@GetMapping("/register")
	public void register() {} //register.jsp로 이동
	
	
	//등록처리. 폼에입력할 때 BoardVO의 board라는 이름으로 출력하게됨.?
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
	
	//상세보기 & 수정화면.
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno")Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
		
		log.info("/get or /modify");
		model.addAttribute("board",service.get(bno));
	}
	
	/*
	 * //상세보기 & 수정화면. 위의 내용을 get과 modify를 분리하면 이렇게 코딩할 수도 있음.
	 * 
	 * @GetMapping("/modify") public void get(@RequestParam("bno")Long bno, Model
	 * model) {
	 * 
	 * log.info("/modify"); model.addAttribute("board",service.get(bno)); }
	 */
	
	//수정처리
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
		
		//바로위에 코드 대신에 +cri.getListLink()를 붙여 사용하는것도 가능함.
		return "redirect:/board/list"+cri.getListLink();
	}
	
	//삭제처리
	@PostMapping("/remove")
	public String remove (@RequestParam("bno") Long bno,@ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("remove......."+bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		log.info("attachList :" + attachList);
		
		if(service.remove(bno)) {
			
			deleteFiles(attachList); //첨부파일도 지우기
			
			rttr.addFlashAttribute("result","success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("type", cri.getType());
//		rttr.addAttribute("keyword", cri.getKeyword());
		
//		//바로위에 코드 대신에 +cri.getListLink()를 붙여 사용하는것도 가능함.
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
