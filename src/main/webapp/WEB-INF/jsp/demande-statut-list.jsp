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
			<span class="pill">Forage • Demande Statut</span>
			<h1>Liste des demande statuts</h1>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container">
		<section class="card">
			<div class="table">
				<div class="table-row table-head cols-5">
					<span>Id Demande</span>
					<span>Type</span>
					<span>Date début</span>
					<span>Date fin</span>
                    <span>Observation</span>
				</div>
				<c:forEach var="demandeStatut" items="${demandesStatuts}">
				<div class="table-row cols-5">
					<span>${demandeStatut.libelleDemande}</span>
					<span class="tag">${demandeStatut.libelleStatut}</span>
					<span>${demandeStatut.dateDebut}</span>
					<span>${demandeStatut.dateFin}</span>
                    <span>${demandeStatut.observation}</span>
				</div>
			</c:forEach>
			</div>
		</section>
        <div class="note">
            <p><a class="link" href="/demande-statut/new">Entrer nouveau demande statut</a></p>
            <p><a class="link" href="/demande-statut/edit">Modifier une statut de demande</a></p>			
            <a class="link" href="/demande/list">Voir toutes les demandes</a>
        </div>
	</main>
</body>
</html>
