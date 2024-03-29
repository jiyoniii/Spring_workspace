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
//@controller를 붙이면 컨트롤러 역할을 수행하게 됨 (서블릿과 비슷)
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	
//	"/"가 주소를 뜻함. 컨트롤러로 서블릿 역할을 하고 있음. 이주소로 접근하게 되면 아래 내용들이 동작을 하게 됨.
	//doGet/doPost가 아니고 메소드를 알아서 설정하게 됨.
	//method=RequestMethod.GET은 겟방식으로 들어오면 아래 코드가 움직이도록 한다는 뜻.
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void  home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		//request.setAttribute("serverTime", formattedDate); 위의 model.이 붙은것과 같은역할임.
		
		//return "home"이 jsp에서의 forward / sendredirect와 같이 이동을 하게 됨.home.jsp로 이동이 됨. 
		//public String home으로 되어있으니 굳이 return"home"이라 표시 할 필요가 없음  생략하면 void 가 붙어야 함.
//		return "home";
	}
	
}
