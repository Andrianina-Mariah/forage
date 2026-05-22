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
			<span class="pill">Forage • Demande</span>
			<h1>Formulaire de demande</h1>
			<p>Enregistrez une demande de forage dans la base.</p>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Détails de la demande</h2>
			<form class="form grid" action="/demande" method="post">
				<label>
					<span>Libellé de la demande</span>
					<input type="text" name="libelle_demande" placeholder="Ex: Forage école primaire" required>
				</label>
				<label>
					<span>Référence</span>
					<input type="text" name="reference" placeholder="RF-2026-004" required>
				</label>
				<label>
					<span>Lieu de la demande</span>
					<input type="text" name="lieu_demande" placeholder="Commune / Fokontany" required>
				</label>
				<label>
					<span>Commune</span>
					<select name="id_commune" required>
						<c:forEach var="commune" items="${communes}">
							<option value="${commune.id}">${commune.nom}</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Date de demande</span>
					<input type="date" name="date_demande">
				</label>
				<label>
					<input type="hidden" name="id_demandeur" value="${currentUser.id}">
				</label>
				<div class="form-actions">
					<button type="submit" class="btn primary">Enregistrer</button>
					<button type="button" class="btn ghost">Annuler</button>
				</div>
			</form>
		</section>

		<aside class="card glass">
			<h3>Résumé table `demande`</h3>
			<ul class="list">
				<li><strong>libelle_demande</strong> (varchar 200)</li>
				<li><strong>id_demandeur</strong> (int)</li>
				<li><strong>reference</strong> (unique)</li>
				<li><strong>lieu_demande</strong> (varchar 200)</li>
				<li><strong>id_commune</strong> (int)</li>
				<li><strong>date_demande</strong> (date)</li>
			</ul>
			<div class="note">
				<a class="link" href="/devis/new">Entrer nouveau devis</a>				
				<a class="link" href="/demande/list">Voir toutes les demandes</a>
			</div>
		</aside>
	</main>
</body>
</html>
