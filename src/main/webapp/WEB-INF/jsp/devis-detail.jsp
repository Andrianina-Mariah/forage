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
			<span class="pill">Forage • Devis</span>
			<h1>Détails du devis</h1>
			<p>Vue détaillée des lignes pour une demande.</p>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Devis n° ${devis.id}</h2>
			<div class="list-cards">
				<div class="mini-card">
					<div>
						<strong>Demande</strong>
						<p class="muted">Demande n° ${devis.idDemande}</p>
					</div>
					<span class="tag">${devis.type}</span>
				</div>
				<div class="mini-card">
					<div>
						<strong>Date</strong>
						<p class="muted">${devis.date}</p>
					</div>
					<span class="badge">En cours</span>
				</div>
			</div>

			<div class="divider"></div>

			<div class="table">
				<div class="table-row table-head cols-5">
					<span>Libellé</span>
					<span>Quantité</span>
					<span>Prix unitaire</span>
					<span>Total</span>
					<span>Action</span>
				</div>
				<c:forEach items="${details}" var="detail">
					<div class="table-row cols-5">
						<span>${detail.libelle}</span>
						<span>${detail.quantite}</span>
						<span>${detail.prix} Ar</span>
						<span>${detail.quantite * detail.prix} Ar</span>
						<button class="btn ghost" type="button">Voir</button>
					</div>
				</c:forEach>
				</div>
			</div>

			<div class="total-card">
				<span>Total estimé</span>
				<strong>${total} Ar</strong>
			</div>
		</section>

		<aside class="card glass">
			<h3>Résumé</h3>
			<ul class="list">
				<li><strong>Type</strong> : Étude</li>
				<li><strong>Responsable</strong> : Chef de projet</li>
				<li><strong>Dernière mise à jour</strong> : 12/05/2026</li>
			</ul>
			<div class="note">
				<p>Utilisez cette page pour valider ou exporter le devis.</p>
				<a class="link" href="/devis/list">Retour à la liste des devis</a>
			</div>
		</aside>
	</main>
</body>
</html>
