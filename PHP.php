$API_key = 'YOUR_API_KEY'; // put your API key here
$API_URL = 'https://semanti-ca.cloud.tyk.io';

$payload = array('url' => 'URL_TO_SCRAPE'); // put the URL you want to scrape here

$semanti_ca = curl_init();

$curl_opts = array(
    CURLOPT_URL => $API_URL . '/extract-web-article?' . http_build_query($payload),
    CURLOPT_RETURNTRANSFER => true,
    CURLOPT_HTTPHEADER => array(
        'Content-Type: application/x-www-form-urlencoded',
        'authorization: ' . $API_key
    ),
    CURLOPT_FOLLOWLOCATION => true
);

curl_setopt_array($semanti_ca, $curl_opts);

// the "$data" variable will contain the extracted data
$data = json_decode(curl_exec($semanti_ca));
