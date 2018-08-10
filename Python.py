import requests, urllib
API_key = "YOUR_API_KEY" #put your API key here

API_URL = "https://semanti-ca.cloud.tyk.io"

payload = {"url" : "URL_TO_SCRAPE"} #put the URL you want to scrape here

semanti_ca = requests.Session()

#the "data" variable will contain the extracted data
data = semanti_ca.get(API_url + "/extract-web-article?" + urllib.urlencode(payload), headers = {'authorization': API_key}).json()
