import requests
import psycopg2

url = 'http://pokeapi.co/api/v2/type/'

# connect to the pokemon database
try:
    conn = psycopg2.connect("dbname='pokemon' user='' host='localhost' password=''")
except:
    print "I am unable to connect to the database"

cur = conn.cursor()

for i in range(3, 19):
    responseJson = requests.get(url + str(i)).json()
    strong_type_name = responseJson["name"]

    for weak_type in responseJson["damage_relations"]["double_damage_to"]:
        weak_type_name = weak_type["name"]
        try:
            cur.execute("""INSERT INTO strong_against(weak_type, strong_type) VALUES (%s, %s)""",
                        (weak_type_name, strong_type_name))
            print("Successfully added matchup")
        except:
            print("Could not insert matchup")

conn.commit()
cur.close()
