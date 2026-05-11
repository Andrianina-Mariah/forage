package com.example.demo.controller;

import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class CurrentUserAdvice {
	@ModelAttribute("currentUser")
	public Map<String, String> currentUser(HttpSession session) {
		Object existing = session.getAttribute("currentUser");
		if (existing instanceof Map<?, ?> map) {
			@SuppressWarnings("unchecked")
			Map<String, String> typed = (Map<String, String>) map;
			return typed;
		}
		return Map.of("nom", "Invité", "role", "Visiteur");
	}
}
