A. PreReq: 
	Setup Minikube/k8 using scripts %PATH/ssp-world/ssp_deploy/bin/setupMinikube_*.sh
	
B. Pod Deployment Details:
	OAuth Pod - Sring boot project used to create OAuth Token
	mySql Pod - MySql DB Project used to store user details - It has 1 DB for user management and another for Application(To be created)
	python Pod - Has Rest Service exposed via Cluster IP, which can be used to get Model Prediction
	rmxml Pod - Rest Service Project used to expose business Services
	
	Note: Checke the application.properties to fetch DB credetials
	
C. Storage Details:
	PV for all the the Pods are created pointing to folder %PATH/ssp-world/ssp_deploy/storage
	
	PV for all the pods are created using script in %PATH/ssp-world/ssp_deploy/cluster_setup folder
	
	Folder Structure
		
		storage
			apps
				oAuth
					conf
						application.properties
					lib
						*.jar -> String boot jar
					security
						keystore.p12 -> SSH Key file
				rmxMl
					conf
						application.properties
					lib
						*.jar
					security
						keystore.p12
			dbdata
				schema
					SSPUSERMGMT.sql -> This has schema to be imported for creating base data for User Management
				data
					#Data folder where MySQL is storing the DataBase
					
D. Docker Details:
	Folder $PATH/ssp-world/ssp_deploy/dockers has 2 docker image build resources
		
		dockers
			python
			springboot	
		
	Steps to create Python image
	
		1. cd  %PATH/ssp-world/ssp_deploy/dockers/python
		2. docker build -t 9945440831/bulletin:pythonv1 .
		3. docker tag 02929218a995 9945440831/bulletin:pythonv1
		4. docker tag 02929218a995 python:v1
		
	Steps to create Spring image
	
		1. cd  %PATH/ssp-world/ssp_deploy/dockers/springboot
		2. docker build -t 9945440831/bulletinboard:springbootv2 .
		3. docker tag 71b31cff0cec 9945440831/bulletinboard:springbootv2
		4. docker tag 71b31cff0cec springboot:v2
		
	Steps to PUSH image to docker repository, Options for Dev/QA, only if you want to have the remote repository
	
		1. docker push 9945440831/bulletinboard:springbootv2
		
	Note: Docker Login should be done before pushing the image
	
		1. Login to Docker, this will create /home//.docker/config.json file sudo docker login
		2. Create docker id secret sudo kubectl create secret generic regcred \ --from-file=.dockerconfigjson=/home//.docker/config.json \ --type=kubernetes.io/dockerconfigjson
		
		Guidelines to push local image
			Name your local images using one of these methods:

		    When you build them, using docker build -t <hub-user>/<repo-name>[:<tag>]
		    By re-tagging an existing local image docker tag <existing-image> <hub-user>/<repo-name>[:<tag>]
		    By using docker commit <existing-container> <hub-user>/<repo-name>[:<tag>] to commit changes

			Now you can push this repository to the registry designated by its name or tag.
	
	To test images in Docker
	
		1. docker run --name pythonv1 -d -p 5000:5000 <imageid>
			This will run the docker image in process, check the docker logs and login to pod to verify the folder details
				
E. Project Details
	1. SSPUserManagement - Code for oAuth token generation using DB
	2. SSPRmxService - Code for business rest service calls
	3. ssp_deploy/dockers/python - Had sample python project for doing Rest call and building the python image
	
F. Running the Code
	
	Pre-req
		1. Setup minikube/k8
		2. Build docker image and use locally or from remote repository
		2. Checkout ssp_deploy project
		3. Make sure right path to "storage" is given in PV 
		4. Run .%PATH/ssp-world/ssp_deploy/install.sh - This will create required PV,PVC , PDS and SVC

G. To test

	1. curl --location --request POST 'https://pyrazole1.fyre.ibm.com:30005/oauth/token' --header 'Authorization: Basic c3NwX2RvbWFpbjpwYXNzd29yZA==' --header 'Content-Type: application/x-www-form-urlencoded' -d 'grant_type=password' -d 'username=system_sshettigar75@gmail' -d 'password=password' -k
	
	2. curl --location --request GET 'https://pyrazole1.fyre.ibm.com:30006/sspService/getOAuthDetails' -H 'Authorization: Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NDk3NjkxMDMsInVzZXJfbmFtZSI6InN5c3RlbV9zc2hldHRpZ2FyNzVAZ21haWwiLCJhdXRob3JpdGllcyI6WyJzdXBlcnVzZXIiXSwianRpIjoiZTJiYzk3ZjktMTVmOS00OWUwLTkxNTMtNWRhODZmMTJlNzJiIiwiY2xpZW50X2lkIjoic3NwX2RvbWFpbiIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdfQ.h6Q36IZHPZzcoSDp0dMLIoK-HQtStMbBmPzen3xbxDmbQpVV3koTshBTPP9R6HOh_csKIo_7qNdL9ypal3UL51s6_3et0QSD9gE_25CRUecrvc_axAJIfvS1UkD6SAAOLrrXhXf-QP9zkRQtbOPDu22RBQgReTHf2NqKiS9dOMV-2JzLoiIoB9oy6MLR0U7jAYttsUPmp1RCLKjKIjVVCjl1_qwPzNryB6tcFVipcMyrmIjQtY8TFePY0RZGuiY9MIXtXRUybqYGPt7ltIoV7KGksfJSpn8lbG1xnmEHr5KvZZSsT3200WlRZnbJtFUXhjcE5nT1qfeipifv-E5sHw' -k
	
	3. https://pyrazole1.fyre.ibm.com:30006/swagger-ui.html 


H. Git Clone Steps
------------------
git clone https://github.com/SrinivasParkala/ssp-world.git

su - srinipu
cd /Users/srinipu/projects/ssp-world

git checkout ssp_branch_aug20
git add .
git config --global user.email "rash.srini@gmail.com"
git commit -m "ssp service and client"
git push

git user: rash.srini@gmail.com	

-------------------
K8 Tutorial-> https://kubernetes.io/docs/home/
Docker Tutorial -> https://docs.docker.com/get-started/

Machine Learning(ML)

https://jupyter.org/install
https://www.javatpoint.com/python-pandas
https://www.tutorialspoint.com/python/python_exceptions.htm



https://searchengineland.com/heres-how-i-used-python-to-build-a-regression-model-using-an-e-commerce-dataset-326493

https://www.tutorialspoint.com/machine_learning_with_python/machine_learning_with_python_ecosystem.htm

https://towardsdatascience.com/deploying-a-machine-learning-model-as-a-rest-api-4a03b865c166

Rest Service
https://programminghistorian.org/en/lessons/creating-apis-with-python-and-flask
https://auth0.com/blog/developing-restful-apis-with-python-and-flask/

OAuth
https://auth0.com/docs/quickstart/backend/python/01-authorization

https://adamzareba.github.io/Secure-Spring-REST-With-Spring-Security-and-OAuth2/

https://blog.marcosbarbero.com/centralized-authorization-jwt-spring-boot2/
https://roytuts.com/preauthorize-annotation-hasrole-example-in-spring-security/

https://memorynotfound.com/spring-boot-configure-tomcat-ssl-https/
https://stackoverflow.com/questions/48840965/deploy-an-ssl-enabled-java-springboot-war-to-external-tomcat-server

https://hub.docker.com/

Docker ID : Mob
Docker password: welcome2xxxxxx

Git ID: rash.srini@gmail.com
Git password: welcome2xxx
-------------------
