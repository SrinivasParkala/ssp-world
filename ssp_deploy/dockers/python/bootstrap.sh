#!/bin/sh
export FLASK_APP=./mlPredictService/index.py
export FLASK_ENV=development
export FLASK_DEBUG=0
flask run -h 0.0.0.0
