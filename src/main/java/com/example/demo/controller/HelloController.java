package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@GetMapping("/")
	public String home() {
		return "redirect:/login";
	}

	@GetMapping("/hello")
	@ResponseBody
	public String hello() {
		return "Ça marche !";
	}
}
