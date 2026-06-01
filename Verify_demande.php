<?php
$numeroDemande = isset($_POST['numero_demande']) ? intval($_POST['numero_demande']) : 0;

header('Content-Type: application/json');

if ($numeroDemande <= 0) {
    http_response_code(400);
    echo json_encode(["erreur" => "Numero de demande invalide"]);
    exit;
}

$url = "http://localhost:8080/api/demandes/" . urlencode($numeroDemande) . "/alertes";
$response = @file_get_contents($url);

if ($response === false) {
    http_response_code(500);
    echo json_encode(["erreur" => "Impossible de contacter le serveur Spring"]);
    exit;
}

echo $response;
?>
