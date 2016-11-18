import requests
import psycopg2

url = 'http://pokeapi.co/api/v2/move/'

# connect to the pokemon database
try:
    conn = psycopg2.connect("dbname='pokemon' user='' host='localhost' password=''")
except:
    print "I am unable to connect to the database"

cur = conn.cursor()

# there are 621 moves in the API
for i in range(1, 622):
    print("Move " + str(i))

    value_dict = {}

    response_json = requests.get(url + str(i)).json()
    name = response_json["name"].replace("-", " ")
    strength = response_json["power"] if (response_json["power"] is not None) else 0
    effect_list = response_json["effect_entries"]
    effect = None
    move_type = response_json["type"]["name"]
    description_list = response_json["flavor_text_entries"]
    description = ""

    # get English effect
    for e in effect_list:
        if e["language"]["name"] == "en":
            # moves that have no effect should be null
            if "with no additional effect" not in e["short_effect"]:
                effect = e["short_effect"].replace("\n", " ")
            break

    # get English description
    for d in description_list:
        if d["language"]["name"] == "en":
            description = d["flavor_text"].replace("\n", " ")
            break

    # add this move to the database
    try:
        cur.execute("""INSERT INTO move(name, strength, effect, description, type) VALUES (%s, %s, %s, %s, %s)""",
                    (name, strength, effect, description, move_type))
        print("Successfully added move " + str(i))
    except:
        print("Could not insert move " + str(i))

conn.commit()
