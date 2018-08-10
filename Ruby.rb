require 'excon'
require 'addressable/uri'
require 'json'

API_key = 'YOUR_API_KEY' #put your API key here

API_URL = 'https://semanti-ca.cloud.tyk.io'

payload = Addressable::URI.new
payload.query_values = {:url => 'URL_TO_SCRAPE'} #put the URL you want to scrape here

semanti_ca = Excon.new(API_URL, :persistent => true, :headers => { 'authorization': API_key})

#the "@data" variable will contain the extracted data
@data = JSON.parse(semanti_ca.get(path: '/extract-web-article?' + payload.query).body)
