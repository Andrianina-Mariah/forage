<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${appTitle}</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;600;700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="/css/app.css">
</head>
<body class="page">
	<header class="topbar">
		<div>
			<span class="pill">Forage • Paramètres</span>
			<h1>Gestion des statuts</h1>
			<p>Ajoutez ou modifiez les statuts existants.</p>
		</div>
		<a class="btn ghost" href="/demande/list">Retour aux demandes</a>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Statuts existants</h2>
			<div class="list-cards">
				<c:forEach var="statut" items="${statut}">
				<div class="mini-card">
					<div>
						<h3>${statut.libelle}</h3>
						<p>ID: <span>${statut.id}</span></p>
					</div>
					<button class="btn ghost">Modifier</button>
				</div>
				</c:forEach>
			</div>
		</section>

		<section class="card glass">
			<h2>Ajouter un statut</h2>
			<form class="form" action="/statut" method="post">
				<label>
					<span>Libellé</span>
					<input type="text" name="libelle" placeholder="Ex: En attente" required>
				</label>
				<button type="submit" class="btn primary">Ajouter</button>
			</form>

			<div class="divider"></div>

			<h2>Modifier un statut</h2>
			<form class="form" action="/statut/update" method="post">
				<label>
					<span>Statut</span>
					<select name="statusId">
						<c:forEach var="statut" items="${statut}">
						<option value="${statut.id}">${statut.id} • ${statut.libelle}</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Nouveau libellé</span>
					<input type="text" name="newLibelle" placeholder="Ex: Validée">
				</label>
				<button type="submit" class="btn primary">Mettre à jour</button>
			</form>
		</section>
	</main>
</body>
</html>
