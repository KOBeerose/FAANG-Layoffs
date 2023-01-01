# importing all the necessary libaries

from flask import Flask, request, jsonify, Response
from flask_cors import CORS, cross_origin
import json
from profile_fetcher import fetch_by_url, driver_init, login
import time
import joblib
import pandas as pd

initialized = False
rest_port = 5000



app = Flask(__name__)
app.config['SECRET_KEY'] = 'the quick brown fox jumps over the lazy dog'
app.config['CORS_HEADERS'] = 'Content-Type'



# cors = CORS(app, resources=(
#             r"": ("origins": "http://localhost:4200")))

CORS(app, origins=["http://localhost:4200", "https://localhost:8082"])


data = ()
loaded_model = joblib.load("./Models/LR.sav")
headline_dict = {'Software Engineer': 1,'Project Manager': 2,'Data Engineer': 3,'Data Analyst': 4,'Program Manager': 5,'3D Artist': 6,'Product Manager': 7,'Data Scientist': 8,'Machine Learning Engineer': 9,'Data Scientist': 10, 'Associate General Counsel Data Protection': 11}
location_dict = {'Nevada': 1,'Arizona': 2,'Minnesota': 3,'Maryland': 4,'Indiana': 5,'Iowa': 6,'Vermont': 7,'North Carolina': 8,'Missouri': 9,'Alaska': 10,'South Carolina': 11,'New Jersey': 12,'Colorado': 13,'Connecticut': 14,'Wyoming': 15,'Maine': 16,'Kentucky': 17,'Oklahoma': 18,'Texas': 19,'District of Columbia': 20,'Michigan': 21,'Massachusetts': 22,'Rhode Island': 23,'Illinois': 24,'Delaware': 25,'Pennsylvania': 26,'Kansas': 27,'Hawaii': 28,'Oregon': 29,'Alabama': 30,'Ohio': 31,'New Mexico': 32,'Washington': 33,'California': 34,'Montana': 35,'Virginia': 36,'Idaho': 37,'West Virginia': 38,'Arkansas': 39,'Wisconsin': 40,'Louisiana': 41,'Georgia': 42,'Mississippi': 43,'North Dakota': 44,'Tennessee': 45,'New Hampshire': 46,'South Dakota': 47,'New York': 48,'Florida': 49,'Utah': 50,'Nebraska': 51}

# driver_init()
# login()


@app.route("/profilefetcher", methods=['POST'])
@cross_origin(origin='localhost', headers=['Content-Type', 'Authorization'])
def profile():

    request_data = request.json

    url = request_data['link']
    global data
    global initialized
    data = fetch_by_url(url, initialized)
    initialized = True

    return jsonify(data)


@app.route("/prediction", methods=['POST'])
@cross_origin(origin='localhost', headers=['Content-Type', 'Authorization'])
def keywords():
    time.sleep(5)
    data = request.json

    # url = request_data['link']
    # data = fetch_by_url(url)
    print(data)
    # prediction
    cmp_months = data["company_worktime"]
    exp_months = data["work_months"]
    headline = headline_dict[data["headline"]]
    location = location_dict[data["location"]]

    entry = {"Company_Months": [cmp_months], "Work_Months": [exp_months], "Headline": [headline], "Location": [location]}
    entry_frame = pd.DataFrame.from_dict(entry)

    prediction =  loaded_model.predict(entry_frame) 
    label = 1 if prediction[0] == True else 0
    print(label)

    prediction_proba =  loaded_model.predict_proba(entry_frame) 
    json_data = jsonify({"prediction": prediction_proba[0][0]})

    return json_data


if __name__ == "__main__":
    # app.run(host='0.0.0.0', port= rest_port, debug=True)
    app.run( port= rest_port, debug=True)