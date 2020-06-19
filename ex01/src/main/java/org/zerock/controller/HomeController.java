package org.zerock.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
//@controller�� ���̸� ��Ʈ�ѷ� ������ �����ϰ� �� (�������� ���)
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
//	"/"�� �ּҸ� ����. ��Ʈ�ѷ��� ������ ������ �ϰ� ����. ���ּҷ� �����ϰ� �Ǹ� �Ʒ� ������� ������ �ϰ� ��.
	//doGet/doPost�� �ƴϰ� �޼ҵ带 �˾Ƽ� �����ϰ� ��.
	//method=RequestMethod.GET�� �ٹ������ ������ �Ʒ� �ڵ尡 �����̵��� �Ѵٴ� ��.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void  home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//request.setAttribute("serverTime", formattedDate); ���� model.�� �����Ͱ� ����������.
		
		//return "home"�� jsp������ forward / sendredirect�� ���� �̵��� �ϰ� ��.home.jsp�� �̵��� ��. 
		//public String home���� �Ǿ������� ���� return"home"�̶� ǥ�� �� �ʿ䰡 ����  �����ϸ� void �� �پ�� ��.
//		return "home";
	}
	
}