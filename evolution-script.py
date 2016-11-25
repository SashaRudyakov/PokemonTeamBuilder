import requests
import psycopg2

url = 'http://pokeapi.co/api/v2/evolution-chain/'

try:
    conn = psycopg2.connect("dbname='pokemon' user='' host='localhost' password=''")
except:
    print "I am unable to connect to the database"

cur = conn.cursor()

evolesFromMap = {}

# there are 373 evolution chains
for i in range(1, 374):
    responseJson = requests.get(url + str(i)).json()

    if "chain" in responseJson:
        base_pokemon = responseJson["chain"]["species"]["name"].replace("-", " ")
        second_tier = responseJson["chain"]["evolves_to"]

        for pokemon in second_tier:
            evolesFromMap[pokemon["species"]["name"].replace("-", " ")] = base_pokemon
            print(pokemon["species"]["name"].replace("-", " ") + " added")
            third_tier = pokemon["evolves_to"]
            for pokemon2 in third_tier:
                evolesFromMap[pokemon2["species"]["name"].replace("-", " ")] = pokemon["species"]["name"].replace("-", " ")
                print(pokemon2["species"]["name"].replace("-", " ") + " added")

for key in evolesFromMap:
    try:
        cur.execute("SELECT pokedex_num FROM pokemon WHERE name='" + evolesFromMap[key] + "'")
        evolvesFromId = cur.fetchone()[0]
        cur.execute("UPDATE pokemon SET evolves_from=(%s) WHERE name = (%s)",
                       (evolvesFromId, key,))
        print("Successfully updated pokemon: " + key)
    except:
        print("Could not update pokemon: " + key)

conn.commit()
cur.close()
