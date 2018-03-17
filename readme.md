# Build, Report and Deploy

	mvn clean package dockerfile:build dockerfile:push

# CI

Build status: ![Build status image](https://travis-ci.org/teggr/news-crud-app.svg?branch=master)

https://travis-ci.org/teggr/news-crud-app	
	
# Docker 

https://hub.docker.com/r/teggr/news-crud-app/

	docker run -p 8080:8080 teggr/news-crud-app:0.0.1-SNAPSHOT

# Web application

* Home page: http://localhost:8080/
* Swagger Documentation: http://localhost:8080/swagger-ui.html
* Code coverage report: http://localhost:8080/jacoco/index.html
* H2 Console: http://localhost:8080/h2 (use JDBC URL: jdbc:h2:mem:NEWS;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE)
