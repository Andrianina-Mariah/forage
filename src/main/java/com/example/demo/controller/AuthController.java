package com.example.demo.controller;

import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Client;
import com.example.demo.repository.ClientRepository;

@Controller
public class AuthController {
	private final ClientRepository clientRepository;

	public AuthController(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("appTitle", "Forage - Portail");
		return "login";
	}

	@PostMapping("/login")
	public String doLogin(@RequestParam("email") String email,
			@RequestParam("password") String password,
			Model model,
			HttpSession session) {
		return clientRepository.findByEmailAndPassword(email, password)
				.map(client -> onSuccess(client, session))
				.orElseGet(() -> onFailure(model));
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/login";
	}

	private String onSuccess(Client client, HttpSession session) {
		session.setAttribute("currentUser", Map.of(
				"id", String.valueOf(client.getId()),
				"nom", client.getNom(),
				"role", client.getRole() == null ? "Demandeur" : client.getRole()
		));
		return "redirect:/demande/new";
	}

	private String onFailure(Model model) {
		model.addAttribute("appTitle", "Forage - Portail");
		model.addAttribute("error", "Email ou mot de passe incorrect.");
		return "login";
	}
}
