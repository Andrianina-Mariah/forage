package com.example.demo.model;

public class Client {
	private int id;
	private String nom;
	private String mail;
	private String role;
	private String motDePasse;

	public Client(int id, String nom, String mail, String role, String motDePasse) {
		this.id = id;
		this.nom = nom;
		this.mail = mail;
		this.role = role;
		this.motDePasse = motDePasse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
}
