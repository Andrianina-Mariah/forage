<?php
?>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Consulter les alertes</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Manrope:wght@300;400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            color-scheme: light;
            --bg: #f4f7fb;
            --card: #ffffff;
            --accent: #2d8f6f;
            --accent-dark: #1d5e48;
            --text: #1c2430;
            --muted: #637087;
            --shadow: 0 20px 45px rgba(35, 55, 80, 0.12);
            --radius: 18px;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
            font-family: "Manrope", system-ui, sans-serif;
        }

        body {
            background: var(--bg);
            color: var(--text);
            min-height: 100vh;
        }

        .page {
            padding: 40px 32px 64px;
        }

        .topbar {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 24px;
            margin-bottom: 32px;
        }

        .topbar h1 {
            font-size: 30px;
            margin-top: 8px;
        }

        .topbar p {
            color: var(--muted);
        }

        .pill {
            display: inline-block;
            padding: 6px 12px;
            background: #e9f6f0;
            color: var(--accent-dark);
            border-radius: 999px;
            font-weight: 600;
            font-size: 12px;
            letter-spacing: 0.3px;
        }

        .container {
            max-width: 1100px;
            margin: 0 auto;
        }

        .split {
            display: grid;
            gap: 24px;
            grid-template-columns: 2fr 1fr;
        }

        .card {
            background: var(--card);
            border-radius: var(--radius);
            padding: 32px;
            box-shadow: var(--shadow);
        }

        .card.glass {
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(12px);
        }

        .form {
            display: grid;
            gap: 18px;
            margin-top: 22px;
        }

        .form label {
            display: flex;
            flex-direction: column;
            gap: 8px;
            font-size: 14px;
            color: var(--muted);
        }

        .form input {
            padding: 12px 14px;
            border-radius: 12px;
            border: 1px solid #dfe5ee;
            background: #fff;
            color: var(--text);
            font-size: 15px;
        }

        .form-actions {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .btn {
            display: inline-flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            padding: 10px 18px;
            border-radius: 12px;
            border: none;
            cursor: pointer;
            font-weight: 600;
            text-decoration: none;
            font-size: 14px;
        }

        .btn.primary {
            background: var(--accent);
            color: #fff;
        }

        .btn.primary:hover {
            background: var(--accent-dark);
        }

        .btn.ghost {
            background: transparent;
            border: 1px solid #d6dee8;
            color: var(--text);
        }

        .link {
            color: var(--accent);
            text-decoration: none;
            font-size: 14px;
        }

        .note {
            background: #f1f5fb;
            padding: 16px;
            border-radius: 14px;
            margin-top: 20px;
        }

        .list {
            list-style: none;
            display: grid;
            gap: 12px;
            margin-top: 16px;
        }

        #resultat {
            display: none;
            margin-top: 24px;
        }

        .succes {
            background: #fff;
            color: var(--text);
        }

        .alerte-item {
            margin-top: 14px;
            padding: 16px;
            border: 1px solid #dfe5ee;
            border-radius: 14px;
            font-size: 14px;
            background: #f8fbff;
            color: var(--text);
        }

        .alerte-item strong {
            color: var(--accent-dark);
        }

        .alerte-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 999px;
            color: #fff;
            font-size: 12px;
            font-weight: 700;
        }

        .alerte-rouge { background: #d32f2f; }
        .alerte-jaune { background: #f9a825; color: #1c2430; }
        .alerte-aucune { background: #607d8b; }

        .erreur {
            display: none;
            margin-top: 24px;
            background: #fff5f5;
            border: 1px solid #ffd6d6;
            color: #b42318;
            border-radius: 14px;
            padding: 18px;
            font-weight: 600;
        }

        #json-brut {
            display: none;
            margin-top: 16px;
            padding: 14px;
            background: #f1f5fb;
            border: 1px solid #dfe5ee;
            border-radius: 14px;
            font-family: monospace;
            font-size: 12px;
            color: var(--muted);
            overflow-x: auto;
        }

        @media (max-width: 800px) {
            .page {
                padding: 24px 18px 48px;
            }

            .topbar,
            .split {
                grid-template-columns: 1fr;
            }

            .topbar {
                display: grid;
            }
        }
    </style>
</head>
<body class="page">
    <header class="topbar">
        <div>
            <span class="pill">Forage • Alertes</span>
            <h1>Liste d'alerte d'une demande</h1>
            <p>Entrez le numéro d'une demande pour calculer les durées travaillées et les seuils atteints.</p>
        </div>
        <a class="btn ghost" href="http://localhost:8080/demande/new">Retour a l'application</a>
    </header>

    <main class="container split">
        <section class="card">
            <h2>Recherche</h2>
            <form class="form" id="soldeForm">
                <label for="numero_demande">
                    <span>Numéro de demande</span>
                    <input type="number" id="numero_demande" name="numero_demande" placeholder="Ex: 1" required>
                </label>
                <div class="form-actions">
                    <button class="btn primary" type="submit">Rechercher</button>
                </div>
            </form>

            <div id="resultat"></div>
            <div id="json-brut"></div>
        </section>

        <aside class="card glass">
            <h3>Règle de calcul</h3>
            <ul class="list">
                <li>Les minutes sont comptées entre <strong>08:00</strong> et <strong>16:00</strong>.</li>
                <li>Les alertes viennent de la table <strong>parametre</strong>.</li>
                <li>La durée calculée est enregistrée dans <strong>demande_statut</strong>.</li>
            </ul>
            <div class="note">
                <a class="link" href="http://localhost:8080/demande-statut/list">Voir les historiques</a>
            </div>
        </aside>
    </main>

    <script>
        document.getElementById('soldeForm').addEventListener('submit', function(e) {
            e.preventDefault();

            var numeroDemande = document.getElementById('numero_demande').value;
            var resultatDiv = document.getElementById('resultat');
            var jsonDiv = document.getElementById('json-brut');

            fetch('Verify_demande.php', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'numero_demande=' + encodeURIComponent(numeroDemande)
            })
                .then(function(response) {
                    return response.json();
                })
                .then(function(data) {
                    console.log('Reponse JSON Spring :', data);
                    jsonDiv.style.display = 'block';
                    jsonDiv.textContent = 'JSON recu : ' + JSON.stringify(data);

                    if (data.erreur) {
                        resultatDiv.className = 'erreur';
                        resultatDiv.innerHTML = '<strong>Erreur :</strong> ' + data.erreur;
                    } else {
                        var html = '<strong>Demande n° ' + data.idDemande + '</strong>';
                        if (!data.alertes || data.alertes.length === 0) {
                            html += '<div class="alerte-item">Aucun statut trouve pour cette demande.</div>';
                        } else {
                            data.alertes.forEach(function(item) {
                                var classe = 'alerte-aucune';
                                if (item.alerte && item.alerte.toLowerCase() === 'rouge') {
                                    classe = 'alerte-rouge';
                                } else if (item.alerte && item.alerte.toLowerCase() === 'jaune') {
                                    classe = 'alerte-jaune';
                                }

                                html += '<div class="alerte-item">' +
                                    '<div><strong>' + item.statutDepart + '</strong>' +
                                    (item.statutArrivee ? ' vers <strong>' + item.statutArrivee + '</strong>' : '') +
                                    '</div>' +
                                    '<div>Duree travaillee : ' + item.dureeTravailMinute + ' minute(s)</div>' +
                                    '<div>Alerte : <span class="alerte-badge ' + classe + '">' + item.alerte + '</span></div>' +
                                    (item.seuilMinute ? '<div>Seuil atteint : ' + item.seuilMinute + ' minute(s)</div>' : '') +
                                    '</div>';
                            });
                        }
                        resultatDiv.className = 'succes';
                        resultatDiv.innerHTML = html;
                    }
                    resultatDiv.style.display = 'block';
                })
                .catch(function(err) {
                    console.error('Erreur fetch :', err);
                    resultatDiv.className = 'erreur';
                    resultatDiv.innerHTML = '<strong>Erreur :</strong> Impossible de contacter le serveur Java.';
                    resultatDiv.style.display = 'block';
                    jsonDiv.style.display = 'none';
                });
        });
    </script>

</body>
</html>
