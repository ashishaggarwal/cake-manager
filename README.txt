Cake Manager Micro Service
=======================================

Getting Started

Cake Manager Micro Service is a simple application built using rest APIs.

The project is set-up as a maven project. It is  using Spring-boot, Spring MVC and Spring RestTemplate and JPA.
The project also uses HSQL db as a in-memory database.
It also uses thymeleaf for implementing the view with Bootstrap for stylesheets (This is optional and is just used to display the list of cakes available in the system as a html page)

Following end-points are available in this project:

"/" - This is a GET end-point and will redirect the users to cake.html page which will show the list of available cakes in the system
"/cakes" -  This is a GET end-point and will return the list of cakes as "JSON"
"/cakes" -  This is a POST end-point and will add a new cake to the list of cakes
"/v2/cakes" -  This is a POST end-point and will add a new cake to the list of cakes. This end-point is invoked by addCake.html page which is a user form
 (As the end-points are secured using OAuth 2.0, it's a bit hard to invoke them using rest clients like Postman or CURL. As the user is already authenticated
 when they hit "/" or "/cakes" end-point, this end-point is created to add a new cake from the web page itself

Running the tests
The project uses two sets of tests:
1. Controller tests - These are based on spring-test module. When the test is run as a JUnit test it mocks the HttpRequest
and hits the end-points exposed in CakeController.java
2. Service test - This is a simple JUnit test using Mockito to mock dependencies

Working:

    Without Docker:
    1. The application is started by running the main class (CakeManagerApplication.java). It uses Oauth2 for authentication with GitHub acting as an authorisation server.
    2. When you try to access the application using url http://localhost:8080/, you will be redirected to GitHub. Once you enter valid GitHub credentials you will be asked for permission to share basic details with cake-manager-oauth app in GitHub.
    3. Once you provide the permission you will be redirected to http://localhost:8080/ where you can see the list of cakes.
    4. There is a link called "Add Cake". You will be taken to a user form when you click on that link. You can provide cake parameters and submit to add a new cake.
    5. You can also use following url to get the list of cakes in json format:
        http://localhost:8080/cakes
    6. You can also submit a new cake to the existing list by using following end-point in a "POST" request. However they should provide cake parameters
    (title, description and image url) in request body in order to add a new cake to the list. They should also provide OAuth2.0 access token as an authorisation header
    http://localhost:8080/cakes
    7. Once the cake is added you can again visit http://localhost:8080/ to check that the cake is added to the database.

    With Docker:
    The application can also be run as a docker image. Users can run following command to package the application:

    1. Clone the repo using following url:

    https://github.com/ashishaggarwal/cake-manager.git

    2. cd to {directory where cake-manager is cloned}

    3. mvn clean package (It will create a jar file under cake-manager/target)

    4. docker build -t waracle/cake-manager . (This will build the docker image for cake-manager application)

    5.  docker run -d -p 8080:8080 -t waracle/cake-manager (This will run the image and load the application)

    Users can now use any of the end-points mentioned above.
