package org.zerock.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/sample/*")
@Log4j
//Log4j는 롬복인데,만약 빨간색으로 오류가 나는 경우엔 pom.xml로 가서 <scope>provided<scope>를 주석처리함.junit의 scope도 주석처리!

public class SampleController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	
	//requestMethod에서 get, post, put, delete 의 네가지 방식이 사용 가능함. jsp에서는doGet, doPost만 가능했었음.
	@RequestMapping(value="/basic",method= {RequestMethod.GET,RequestMethod.POST})
	public void basic() {
		
		/* Log.info("basic...."); */
			System.out.println("basic....");
	}
	
	@GetMapping("/ex01")
	public void ex01(SampleDTO dto) {
		System.out.println(""+dto); 
		//SampleDTO(name=null, age=0) 로 출력이 됨. dto에서 넘어오는 값이 없기 때문에 오류가 나지 않고 null로 나오게 됨.
		//메서드이름과 똑같은곳으로 이동할 경우에는 return을 쓰지말고 void로 처리하고, 이동 할 jsp의 이름이 다를 경우 return을 사용해야 함.
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		
		System.out.println("name: "+ name);
		System.out.println("age: "+ age);
		//http://localhost:8181/sample/ex02?name=AAA&age=10 이렇게 url에 값을 직접 입력하면
		//name: AAA age: 10 으로 콘솔에 출력하게됨.
		//위에서 @RequestParam을 삭제해도 동일하게 읽어오게됨.
		
		
		return "ex02";
	}
	
	//InitBinder (137p)
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		
		System.out.println("todo : "+todo);
		log.info("todo : "+todo);
		
		return "ex03";
	}
	
	//142page model
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto,@ModelAttribute("page")int page) {
		//객체데이터와 모델어트리뷰트는 jsp까지 값을 다 넘겨준다....희한.
		//modelattribute를 쓰지 않으면, 값은 수집 되지만 jsp까지 전달이 되지 않음.
		//jsp까지 쭉 넘기고 싶을 땐 modelAttribute를 사용해야 함.
		//sampleDTO와 페이지는 jsp까지 넘겨준다.
		//("s")("p") 와같은 이름은 ex04.jsp에서 s와 p로 넘겨주고 받는거기 때문에 동일한 이름으로 지정해야 함.
		
		
		System.out.println("dto : "+dto);
		System.out.println("page :"+page);
		
		log.info("dto : "+dto);
		log.info("page :"+page);
		//http://localhost:8181/sample/ex04?name=11&age=40&page=9
		//결과 출력 --> dto : SampleDTO(name=11, age=40)page :9
		
		
		return "/sample/ex04";
		
		//143쪽 RedirectAttributes 예시로 처리할 경우엔 아래와같이 작성하면 됨.
		
		//rttr.addFlashAttribute("name","AAA");
		//rttr.addFlashAttribute("age",10);
		//return "redirect:/sample/list";
		
	}
	
	@GetMapping("/ex05")
	public void ex05() {
		System.out.println("/ex05.............");
	}
	
	
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("ex060000000000");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
		//http://localhost:8181/sample/ex06 이렇게 입력하면 화면에 {"name":"홍길동","age":10}로 출력이 됨.
	}
	
	//책에 없는 내용 설명 ex06과 연관. @ResponseBody에서 배열로 나타낼 때 작성방법.
	@GetMapping("ex00")
	public @ResponseBody List<SampleDTO> ex00(){
		List<SampleDTO> arrayList=new ArrayList<SampleDTO>();
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		SampleDTO dto2 = new SampleDTO();
		dto.setAge(20);
		dto.setName("이순신");
		
		arrayList.add(dto);
		arrayList.add(dto2);
		
		return arrayList;
		
		//http://localhost:8181/sample/ex00 이렇게 하면 결과는 [{"name":"이순신","age":20},{"name":null,"age":0}]
		//위와 같이 배열의 형태로 넘어오게 됨.
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("ex07..........");
		
		String msg="{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		//header에 타입을 명시하고 싶을 때 아래 코드를 사용하게 됨.
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<String>(msg, header, HttpStatus.OK);
		//책대로라면 <>사이에 String이 없어야 하는데 에러가 떠서 string추가
		
	}
	
	
}
