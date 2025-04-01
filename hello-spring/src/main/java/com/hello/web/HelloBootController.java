package com.hello.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller를 작성하지 않으면 end-point가 만들어지지 않는다!
@Controller
public class HelloBootController {
	
	public HelloBootController() {
		System.out.println("Hello Boot Controller Instance.");
	}
 	
//	@GetMapping("/hello")
//	public ResponseEntity<String> hello(){ // 응답의 형태를 제네릭<>으로 정의한다.
//		return new ResponseEntity<>("Hello Boot Controller123", HttpStatus.OK);
//	}
	
//	@GetMapping("/hello")
//	public String viewHomePage() {
//		return "redirect:/board/list"; // forward와 비슷하지만, forward는 잘 쓰지 않는다.
////		return "forward:/board/list";
//		// redirect는 실제로 해당 url로 이동시킨다. forward는 url은 유지되지만, 노출되는 화면은 해당 url로 변경된다.
//	}

	@GetMapping("/hello")
	// ResponseEntity는 특별한 경우가 아니면 쓰지 않는다.
	public String hello(Model model) { // 파라미터에 받고 싶은 데이터를 넣으면 스프링이 알아서 다 넣어준다!
		// View에 Model을 추가해보자!
		model.addAttribute("app_name", "Hello Boot!"); // view에게 이 Model을 key:value 형태로 만들어 보내준다.
//		model.addAttribute("app_name", "Hello Boot!");
//		model.addAttribute("app_name", "Hello Boot!");
//		model.addAttribute("app_name", "Hello Boot!");
//		model.addAttribute("app_name", "Hello Boot!");
		return "helloboot"; // 파일명으로 return 하면 jsp가 나온다.
	}
}
