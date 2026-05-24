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
			<h1>Modifier un historique de statut</h1>
			<p>Mettez à jour les informations de la ligne sélectionnée.</p>
		</div>
		<div class="actions">
			<a class="btn ghost" href="/demande/list">Retour aux demandes</a>
			<a class="btn primary" href="/statut">Gérer les statuts</a>
		</div>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Modifier une ligne demande_statut</h2>
			<form class="form grid" action="/demande-statut/update" method="post">
				<input type="hidden" name="id" value="${demandeStatut.id}">
				<label>
					<span>Demande</span>
					<select name="type_demande" required>
						<c:forEach var="demande" items="${demandes}">
							<option value="${demande.id}" ${demande.id == demandeStatut.typeDemande ? 'selected' : ''}>
								${demande.reference} • ${demande.libelleDemande}
							</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Statut</span>
					<select name="type_statut" required>
						<c:forEach var="statut" items="${statuts}">
							<option value="${statut.id}" ${statut.id == demandeStatut.typeStatut ? 'selected' : ''}>
								${statut.libelle}
							</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Date début</span>
					<input type="datetime-local" name="date_debut" value="${demandeStatut.dateDebut}" required>
				</label>
				<label>
					<span>Date fin (optionnel)</span>
					<input type="datetime-local" name="date_fin" value="${demandeStatut.dateFin}">
				</label>
				<div class="form-actions">
					<button type="submit" class="btn primary">Enregistrer</button>
					<a class="btn ghost" href="/demande/list">Annuler</a>
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
			</ul>
			<div class="note">
				<a class="link" href="/demande/list">Voir toutes les demandes</a>
			</div>
		</aside>
	</main>
</body>
</html>
