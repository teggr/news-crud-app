# News Platform

CRUD REST/HTTP API proof-of-concept (POC) application which stores data in an in-memory database and serves this back in JSON format.

The **Platform** manages a set of **Journalists** who can publish **News Stories** to the **Platform**. Those **News Stories** can be updated and retracted. The latest **News Stories** are also published as **Breaking News**.

# Build, Report and Deploy

The code can be tested, build and deployed to Docker Hub using the following command.

	mvn clean package dockerfile:build dockerfile:push

The Jacoco code coverage report runs during the package phase and is bundled into the final jar artifact. See below for details.

# CI

![Build status image](https://travis-ci.org/teggr/news-crud-app.svg?branch=master)

Build information available @ <https://travis-ci.org/teggr/news-crud-app>

TravisCI job setup to build on GitHub commit to master, run the above command and publish the Docker image to Docker Hub.
	
# Docker 

Docker repository available @ <https://hub.docker.com/r/teggr/news-crud-app/>

The docker image can be run using the following command.

	docker run -p 8080:8080 teggr/news-crud-app:0.0.1-SNAPSHOT

# News Platform Web application

The web application hosts a web interface available @ <http://localhost:8080/>

* Home page: <http://localhost:8080/>
* API: <http://localhost:8080/api>
* Swagger Documentation: <http://localhost:8080/swagger-ui.html>
* Code coverage report: <http://localhost:8080/jacoco/index.html>
* H2 Console: <http://localhost:8080/h2> (use JDBC URL: jdbc:h2:mem:NEWS;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE)
* Breaking news: <http://localhost:8080/api/breaking-news>

## Get started with the API 

	curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api'
