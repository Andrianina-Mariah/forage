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
			<span class="pill">Forage • Statut</span>
			<h1>Nouvel historique de statut</h1>
			<p>Ajoutez une ligne de statut pour une demande.</p>
		</div>
		<div class="actions">
			<a class="btn ghost" href="/demande/list">Retour aux demandes</a>
			<a class="btn primary" href="/statut">Gérer les statuts</a>
		</div>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Créer une ligne demande_statut</h2>
			<form class="form grid" action="/demande-statut" method="post">
				<label>
					<span>Demande</span>
					<select name="type_demande" required>
						<c:forEach var="demande" items="${demandes}">
							<option value="${demande.id}">${demande.reference} • ${demande.libelleDemande}</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Statut</span>
					<select name="type_statut" required>
						<c:forEach var="statut" items="${statuts}">
							<option value="${statut.id}">${statut.libelle}</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Date début</span>
					<input type="datetime-local" name="date_debut" required>
				</label>
				<label>
					<span>Observation</span>
					<input type="text" name="observation" placeholder="Optionnel">
				</label>
				<div class="form-actions">
					<button type="submit" class="btn primary">Enregistrer</button>
					<button type="reset" class="btn ghost">Réinitialiser</button>
				</div>
			</form>
		</section>

		<aside class="card glass">
			<h3>Résumé table `demande_statut`</h3>
			<ul class="list">
				<li><strong>type_demande</strong> (int)</li>
				<li><strong>type_statut</strong> (int)</li>
				<li><strong>date_debut</strong> (datetime)</li>
				<li><strong>date_fin</strong> (datetime)</li>
				<li><strong>observation</strong> (text)</li>
			</ul>
			<div class="note">
				<a class="link" href="/demande/list">Voir toutes les demandes</a>
			</div>
		</aside>
	</main>
</body>
</html>
