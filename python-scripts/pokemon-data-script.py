import requests
import psycopg2
import random

url = 'http://pokeapi.co/api/v2/pokemon/'


try:
    conn = psycopg2.connect("dbname='pokemon' user='' host='localhost' password=''")
except:
    print "I am unable to connect to the database"

cur = conn.cursor()

# random with exclude, for choosing random moves for pokemon
def rand(exclude, upper):
    r = None
    while r in exclude or r is None:
        r = random.randrange(0, upper)
    return r


# there are 721 pokemon in the api
for i in range(1, 722):
    response_json = requests.get(url + str(i)).json()
    name = response_json["name"].replace("-", " ")
    pokedexNum = i
    type1 = response_json["types"][0]["type"]["name"]
    type2 = None
    # get the second type if the pokemon has one
    if len(response_json["types"]) > 1:
        type2 = response_json["types"][1]["type"]["name"]

    movesList = response_json["moves"]

    # big ugly move choosing block
    # if pokemon has 4 or fewer moves, just use them all
    # otherwise, randomly choose them! it's cooler
    move1 = response_json["moves"][0]["move"]["name"].replace("-", " ")
    move2 = None
    move3 = None
    move4 = None

    if len(movesList) is 2:
        move2 = response_json["moves"][1]["move"]["name"].replace("-", " ")
    elif len(movesList) is 3:
        move2 = response_json["moves"][1]["move"]["name"].replace("-", " ")
        move3 = response_json["moves"][2]["move"]["name"].replace("-", " ")
    elif len(movesList) is 4:
        move2 = response_json["moves"][1]["move"]["name"].replace("-", " ")
        move3 = response_json["moves"][2]["move"]["name"].replace("-", " ")
        move4 = response_json["moves"][3]["move"]["name"].replace("-", " ")
    elif len(movesList) > 4:
        move1Index = random.randint(0, len(movesList) - 1)
        move2Index = rand([move1Index], len(movesList) - 1)
        move3Index = rand([move1Index, move2Index], len(movesList) - 1)
        move4Index = rand([move1Index, move2Index, move3Index], len(movesList) - 1)

        move1 = response_json["moves"][move1Index]["move"]["name"].replace("-", " ")
        move2 = response_json["moves"][move2Index]["move"]["name"].replace("-", " ")
        move3 = response_json["moves"][move3Index]["move"]["name"].replace("-", " ")
        move4 = response_json["moves"][move4Index]["move"]["name"].replace("-", " ")

    # add this pokemon to the database
    try:
        cur.execute("""INSERT INTO pokemon(name, pokedex_num, evolves_to, type1, type2, move1, move2, move3, move4) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s)""",
                    (name, pokedexNum, None, type1, type2, move1, move2, move3, move4))
        print("Successfully added pokemon: " + name)
    except:
        print("Could not insert pokemon: " + name)

conn.commit()
