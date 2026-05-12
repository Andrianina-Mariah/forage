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
			<span class="pill">Forage • Suivi</span>
			<h1>Liste des demandes</h1>
			<p>Filtrez par statut pour prioriser le traitement.</p>
		</div>
		<div class="actions">
			<a class="btn ghost" href="/demande/new">Nouvelle demande</a>
			<a class="btn primary" href="/statuts">Gérer les statuts</a>
		</div>
	</header>

	<section class="container card">
		<form class="filter" method="get" action="/demande/list">
			<label>
				<span>Statut</span>
				<select name="status">
					<option value="">Tous</option>
				</select>
			</label>
			<button type="submit" class="btn primary">Filtrer</button>
		</form>

		<div class="table">
			<div class="table-row table-head">
				<span>Référence</span>
				<span>Libellé</span>
				<span>Commune</span>
				<span>Lieu</span>
				<span>Date</span>
			</div>
			<c:forEach var="demande" items="${demandes}">
				<div class="table-row">
					<span>${demande.reference}</span>
					<span>${demande.libelleDemande}</span>
					<span>${demande.idCommune}</span>
					<span class="tag">${demande.lieu}</span>
					<span>${demande.dateDemande}</span>
				</div>
			</c:forEach>
		</div>
	</section>
</body>
</html>
