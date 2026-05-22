<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
			<span class="pill">Forage • Devis</span>
			<h1>Liste des devis</h1>
			<p>Consultez les devis par type.</p>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container">
		<section class="card">
			<form class="filter" action="/devis/list" method="get">
				<label>
					<span>Type de devis</span>
					<select name="type">
						<option value="">Tous</option>
						<option value="etude">Étude</option>
						<option value="realisation">Réalisation</option>
					</select>
				</label>
				<label>
					<span>Référence</span>
					<input type="text" placeholder="DV-2026-001">
				</label>
				<button type="button" class="btn primary">Filtrer</button>
			</form>

			<div class="table">
				<div class="table-row table-head cols-5">
					<span>Id Demande</span>
					<span>Type</span>
					<span>Description</span>
					<span>Date</span>
					<span>Actions</span>
				</div>
				<c:forEach var="devis" items="${devis}">
				<div class="table-row cols-5">
					<span>${devis.idDemande}</span>
					<span class="tag">${devis.type}</span>
					<span>${devis.description}</span>
					<span>${devis.date}</span>
					<a class="link" href="/devis/detail">Détails</a>
				</div>
			</c:forEach>
			</div>
		</section>
	</main>
</body>
</html>
