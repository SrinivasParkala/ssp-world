#Using lightweight alpine image
FROM python:3.8.3-alpine

# Installing packages
RUN apk update
RUN apk --no-cache add curl
RUN pip install --upgrade pip

RUN adduser -D puser
USER puser
WORKDIR /home/puser

ENV PATH="/home/puser/.local/bin:${PATH}"
RUN pip install pipenv
RUN pip install python-dotenv
RUN pip install python-jose

# Defining working directory and adding source code
COPY --chown=puser:puser Pipfile Pipfile.lock bootstrap.sh ./
COPY --chown=puser:puser cashman ./cashman

# Install API dependencies
RUN pipenv install

RUN chmod +x /home/puser/bootstrap.sh

# Start app
ENTRYPOINT ["sh","/home/puser/bootstrap.sh"]
