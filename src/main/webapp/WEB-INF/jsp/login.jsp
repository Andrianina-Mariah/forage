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
<body class="page page-login">
	<div class="bg-orb orb-1"></div>
	<div class="bg-orb orb-2"></div>

	<main class="container card glass">
		<header class="card-header">
			<span class="pill">Forage • Portail</span>
			<h1>Bienvenue 👋</h1>
			<p>Connectez-vous pour déposer une demande de forage.</p>
		</header>

		<c:if test="${not empty error}">
			<div class="badge" style="margin-bottom: 12px;">${error}</div>
		</c:if>

		<form class="form" action="/login" method="post">
			<label>
				<span>Email</span>
				<input type="email" name="email" placeholder="nom@email.com" required>
			</label>
			<label>
				<span>Mot de passe</span>
				<input type="password" name="password" placeholder="••••••••" required>
			</label>
			<button type="submit" class="btn primary">Se connecter</button>
			<div class="meta-row">
				<a class="link" href="/demande/new">Créer une demande sans compte</a>
			</div>
		</form>

		<footer class="card-footer">
			<div class="badge">Session démo active</div>
			<p>admin@forage.mg / admin123 ou rabe@forage.mg / rabe123</p>
		</footer>
	</main>
</body>
</html>
