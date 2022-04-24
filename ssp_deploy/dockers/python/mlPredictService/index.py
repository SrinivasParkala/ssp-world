from typing import Dict

from flask import Flask, request, jsonify, Response
from flask import Flask, jsonify, request
import json
import joblib
from types import SimpleNamespace

app = Flask(__name__)

#cd /Users/srinivasaparkala/workspace/publisher-x/PythonRestService/mlPredictService
#pipenv install
#./bootstrap.sh
#curl -X POST http://127.0.0.1:5000/prediction -H 'Content-Type: application/json' -d @/Users/srinivasaparkala/srini/MLLearning/pythonHelloWorld/data.json

class PredictRes:
    arrayData = [3.4,4.5]
    
@app.route('/prediction/v1.0/rmxPredict', methods=['POST'])
def prediction():
    requestPayload = json.loads(request.data, object_hook=lambda d: SimpleNamespace(**d))
    print(requestPayload.path)
    print(requestPayload.xArrayInput)
    print(requestPayload.yArrayInput)
  
    loaded_model = joblib.load(requestPayload.path)
    resData = loaded_model.predict(requestPayload.xArrayInput)
    
    predictRes = PredictRes()
    predictRes.arrayData.clear()
    
    for x in resData:
        predictRes.arrayData.append(x)
  
    return jsonify({'prediction': predictRes.arrayData}), 200

if __name__ == "__main__":
    app.run()
