import requests
import urllib

sprite_destination = '/Users/katelynsalvatori/school/db/project/springTest/src/main/webapp/sprites/'
url = 'http://pokeapi.co/api/v2/pokemon/'

for i in range(1,722):
    response_json = requests.get(url + str(i)).json()
    sprite_url = response_json["sprites"]["front_default"]
    urllib.urlretrieve(sprite_url, sprite_destination + str(i) + ".png")
