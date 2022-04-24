#Using lightweight alpine image
FROM tiangolo/uwsgi-nginx-flask:python3.8

RUN pip install --upgrade pip

RUN adduser puser
USER puser
WORKDIR /home/puser

# Defining working directory and adding source code
COPY --chown=puser:puser bootstrap.sh ./
COPY --chown=puser:puser mlPredictService ./mlPredictService
COPY --chown=puser:puser requirements.txt ./

# Install API dependencies
#RUN pipenv install --verbose
RUN pip install -r ./requirements.txt

RUN chmod +x /home/puser/bootstrap.sh

# Start app
ENTRYPOINT ["sh","/home/puser/bootstrap.sh"]
