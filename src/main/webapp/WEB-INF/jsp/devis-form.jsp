<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
			<p>Ajoutez des lignes au devis avant d’enregistrer.</p>
		</div>
		<div class="user-chip">
			<span>${currentUser.nom}</span>
			<span class="muted">${currentUser.id}</span>
		</div>
	</header>

	<main class="container split">
		<section class="card">
			<h2>Informations du devis</h2>
			<form class="form grid" action="#" method="post" onsubmit="return false;">
				<label>
					<span>Demande sélectionnée</span>
					<select name="id_demande">
						<option>RF-2026-001 • Forage école primaire</option>
						<option>RF-2026-002 • Forage centre de santé</option>
						<option>RF-2026-003 • Forage village Andranomena</option>
					</select>
				</label>
				<label>
					<span>Type de devis</span>
					<select name="type_devis">
						<option value="etude">Étude</option>
						<option value="realisation">Réalisation</option>
					</select>
				</label>
				<label>
					<span>Date du devis</span>
					<input type="date" name="date_devis">
				</label>
				<label>
					<span>Référence devis</span>
					<input type="text" name="reference_devis" placeholder="DV-2026-001">
				</label>
			</form>

			<div class="divider"></div>

			<div class="section-header">
				<div>
					<h3>Détails du devis</h3>
					<p class="muted">Les lignes sont stockées en mémoire tant que vous n’enregistrez pas.</p>
				</div>
				<div class="actions">
					<button class="btn ghost" type="button" id="resetTable">Vider la table</button>
					<button class="btn primary" type="button" id="addRow">Ajouter une ligne</button>
				</div>
			</div>

			<div class="table" id="devisTable">
				<div class="table-row table-head cols-6">
					<span>Libellé</span>
					<span>Quantité</span>
					<span>Unité</span>
					<span>Prix unitaire</span>
					<span>Total</span>
					<span>Actions</span>
				</div>
			</div>

			<div class="total-card">
				<span>Total estimé</span>
				<strong id="totalGlobal">Ar 0</strong>
			</div>

			<div class="form-actions">
				<button type="button" class="btn primary">Enregistrer</button>
				<button type="button" class="btn ghost">Annuler</button>
			</div>
		</section>

		<aside class="card glass">
			<h3>Rappel</h3>
			<ul class="list">
				<li><strong>devis</strong> (id, idDemande, type, date)</li>
				<li><strong>devis_detail</strong> (id, idDevis, libellé, quantité, prix_unitaire)</li>
			</ul>
			<div class="note">
				<p>Vous pourrez filtrer les devis par type et consulter chaque détail.</p>
				<a class="link" href="/devis/list">Voir la liste des devis</a>
			</div>
		</aside>
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
					<input type="text" placeholder="Ex: Étude hydrogéologique" value="${row.libelle}">
				</div>
				<div class="cell">
					<input type="number" min="1" value="${row.quantite}">
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
					<input type="number" min="0" value="${row.prix}">
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
