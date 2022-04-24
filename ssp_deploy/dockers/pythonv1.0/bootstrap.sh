#!/bin/sh
echo Running the script
FLASK_APP=./cashman/index.py
export FLASK_APP
FLASK_ENV=development
export FLASK_ENV
FLASK_DEBUG=0
export FLASK_DEBUG
source $(pipenv --venv)/bin/activate
flask run -h 0.0.0.0
