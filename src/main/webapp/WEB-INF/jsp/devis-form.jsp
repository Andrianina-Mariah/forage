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
			<h1>Créer un devis</h1>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container">
		<section class="card">
			<h2>Informations du devis</h2>
			<form class="form grid" action="/devis" method="post">
				<label>
					<span>Demande sélectionnée</span>
					<select name="id_demande">
						<c:forEach var="demande" items="${demandes}">
							<option value="${demande.id}">${demande.reference} • ${demande.libelleDemande}</option>
						</c:forEach>
					</select>
				</label>
				<label>
					<span>Type de devis</span>
					<select name="type">
						<option value="etude">Étude</option>
						<option value="realisation">Réalisation</option>
					</select>
				</label>
				<label>
					<span>Date du devis</span>
					<input type="date" name="date">
				</label>
				<label>
					<span>Description du devis</span>
					<textarea name="description" rows="3" placeholder="Détails généraux du devis"></textarea>
				</label>

			<div class="divider full-width"></div>

			<div class="section-header full-width">
				<div>
					<h3>Détails du devis</h3>
					<p class="muted">Les lignes sont stockées en mémoire tant que vous n’enregistrez pas.</p>
				</div>
				<div class="actions">
					<button class="btn ghost" type="button" id="resetTable">Vider la table</button>
					<button class="btn primary" type="button" id="addRow">Ajouter une ligne</button>
				</div>
			</div>

			<div class="table full-width" id="devisTable">
				<div class="table-row table-head cols-6">
					<span>Libellé</span>
					<span>Quantité</span>
					<span>Unité</span>
					<span>Prix unitaire</span>
					<span>Total</span>
					<span>Actions</span>
				</div>
			</div>

			<div class="total-card full-width">
				<span>Total estimé</span>
				<strong id="totalGlobal">Ar 0</strong>
			</div>

			<div class="form-actions full-width">
				<button type="submit" class="btn primary">Enregistrer</button>
				<button type="reset" class="btn ghost">Annuler</button>
			</div>
		</form>
			<div class="link-row">
				<a class="link" href="/devis/list">Voir la liste des devis</a>
			</div>
		</section>
	</main>

	<script>
		const devisTable = document.getElementById("devisTable");
		const totalGlobal = document.getElementById("totalGlobal");
		const rows = [];

		const formatMoney = (value) => {
			return new Intl.NumberFormat("fr-FR").format(value) + " Ar";
		};

		const recalcTotal = () => {
			const total = rows.reduce((sum, row) => sum + row.total, 0);
			totalGlobal.textContent = formatMoney(total);
		};

		const createRow = (data = {}) => {
			const row = {
				libelle: data.libelle || "",
				quantite: data.quantite || 1,
				unite: data.unite || "forfait",
				prix: data.prix || 0,
				total: 0,
				element: document.createElement("div")
			};

			row.element.className = "table-row cols-6";
			row.element.innerHTML = `
				<div class="cell">
					<input type="text" name="libelle" placeholder="Ex: Étude hydrogéologique" value="${row.libelle}">
				</div>
				<div class="cell">
					<input type="number" name="quantite" min="1" value="${row.quantite}">
				</div>
				<div class="cell">
					<select>
						<option value="forfait">Forfait</option>
						<option value="jour">Jour</option>
						<option value="ml">mètre linéaire</option>
						<option value="unite">Unité</option>
					</select>
				</div>
				<div class="cell">
					<input type="number" name="prix_unitaire" min="0" value="${row.prix}">
				</div>
				<div class="cell total-cell">Ar 0</div>
				<div class="cell actions-cell">
					<button class="btn ghost" type="button">Supprimer</button>
				</div>
			`;

			const [libelleInput, quantiteInput, uniteSelect, prixInput] = row.element.querySelectorAll("input, select");
			const totalCell = row.element.querySelector(".total-cell");
			const deleteBtn = row.element.querySelector("button");

			const updateRow = () => {
				row.libelle = libelleInput.value;
				row.quantite = Number(quantiteInput.value || 0);
				row.unite = uniteSelect.value;
				row.prix = Number(prixInput.value || 0);
				row.total = row.quantite * row.prix;
				totalCell.textContent = formatMoney(row.total);
				recalcTotal();
			};

			[libelleInput, quantiteInput, uniteSelect, prixInput].forEach((input) => {
				input.addEventListener("input", updateRow);
				input.addEventListener("change", updateRow);
			});

			deleteBtn.addEventListener("click", () => {
				const index = rows.indexOf(row);
				if (index > -1) {
					rows.splice(index, 1);
				}
				row.element.remove();
				recalcTotal();
			});

			uniteSelect.value = row.unite;
			updateRow();

			rows.push(row);
			devisTable.appendChild(row.element);
		};

		document.getElementById("addRow").addEventListener("click", () => createRow());
		document.getElementById("resetTable").addEventListener("click", () => {
			rows.splice(0, rows.length);
			devisTable.querySelectorAll(".table-row.cols-6").forEach((row) => row.remove());
			recalcTotal();
		});

		createRow({ libelle: "Étude hydrogéologique", quantite: 1, unite: "forfait", prix: 350000 });
		createRow({ libelle: "Déplacement équipe", quantite: 2, unite: "jour", prix: 120000 });
	</script>
</body>
</html>
