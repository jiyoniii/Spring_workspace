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
//Log4j�� �Һ��ε�,���� ���������� ������ ���� ��쿣 pom.xml�� ���� <scope>provided<scope>�� �ּ�ó����.junit�� scope�� �ּ�ó��!

public class SampleController {
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}
	
	
	
	//requestMethod���� get, post, put, delete �� �װ��� ����� ��� ������. jsp������doGet, doPost�� �����߾���.
	@RequestMapping(value="/basic",method= {RequestMethod.GET,RequestMethod.POST})
	public void basic() {
		
		/* Log.info("basic...."); */
			System.out.println("basic....");
	}
	
	@GetMapping("/ex01")
	public void ex01(SampleDTO dto) {
		System.out.println(""+dto); 
		//SampleDTO(name=null, age=0) �� ����� ��. dto���� �Ѿ���� ���� ���� ������ ������ ���� �ʰ� null�� ������ ��.
		//�޼����̸��� �Ȱ��������� �̵��� ��쿡�� return�� �������� void�� ó���ϰ�, �̵� �� jsp�� �̸��� �ٸ� ��� return�� ����ؾ� ��.
	}
	
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		
		System.out.println("name: "+ name);
		System.out.println("age: "+ age);
		//http://localhost:8181/sample/ex02?name=AAA&age=10 �̷��� url�� ���� ���� �Է��ϸ�
		//name: AAA age: 10 ���� �ֿܼ� ����ϰԵ�.
		//������ @RequestParam�� �����ص� �����ϰ� �о���Ե�.
		
		
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
		//��ü�����Ϳ� �𵨾�Ʈ����Ʈ�� jsp���� ���� �� �Ѱ��ش�....����.
		//modelattribute�� ���� ������, ���� ���� ������ jsp���� ������ ���� ����.
		//jsp���� �� �ѱ�� ���� �� modelAttribute�� ����ؾ� ��.
		//sampleDTO�� �������� jsp���� �Ѱ��ش�.
		//("s")("p") �Ͱ��� �̸��� ex04.jsp���� s�� p�� �Ѱ��ְ� �޴°ű� ������ ������ �̸����� �����ؾ� ��.
		
		
		System.out.println("dto : "+dto);
		System.out.println("page :"+page);
		
		log.info("dto : "+dto);
		log.info("page :"+page);
		//http://localhost:8181/sample/ex04?name=11&age=40&page=9
		//��� ��� --> dto : SampleDTO(name=11, age=40)page :9
		
		
		return "/sample/ex04";
		
		//143�� RedirectAttributes ���÷� ó���� ��쿣 �Ʒ��Ͱ��� �ۼ��ϸ� ��.
		
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
		dto.setName("ȫ�浿");
		
		return dto;
		//http://localhost:8181/sample/ex06 �̷��� �Է��ϸ� ȭ�鿡 {"name":"ȫ�浿","age":10}�� ����� ��.
	}
	
	//å�� ���� ���� ���� ex06�� ����. @ResponseBody���� �迭�� ��Ÿ�� �� �ۼ����.
	@GetMapping("ex00")
	public @ResponseBody List<SampleDTO> ex00(){
		List<SampleDTO> arrayList=new ArrayList<SampleDTO>();
		
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("ȫ�浿");
		
		SampleDTO dto2 = new SampleDTO();
		dto.setAge(20);
		dto.setName("�̼���");
		
		arrayList.add(dto);
		arrayList.add(dto2);
		
		return arrayList;
		
		//http://localhost:8181/sample/ex00 �̷��� �ϸ� ����� [{"name":"�̼���","age":20},{"name":null,"age":0}]
		//���� ���� �迭�� ���·� �Ѿ���� ��.
	}
	
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07(){
		log.info("ex07..........");
		
		String msg="{\"name\": \"ȫ�浿\"}";
		
		HttpHeaders header = new HttpHeaders();
		//header�� Ÿ���� �����ϰ� ���� �� �Ʒ� �ڵ带 ����ϰ� ��.
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<String>(msg, header, HttpStatus.OK);
		//å��ζ�� <>���̿� String�� ����� �ϴµ� ������ ���� string�߰�
		
	}
	
	
}