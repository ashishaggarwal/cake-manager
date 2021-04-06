Cake Manager Micro Service
=======================================

Getting Started

Cake Manager Micro Service is a simple application built using rest APIs.

The project is set-up as a maven project. It is  using Spring-boot, Spring MVC and Spring RestTemplate and JPA.
The project also uses HSQL db as a in-memory database.
It also uses thymeleaf for implementing the view with Bootstrap for stylesheets (This is option and is just used to display
the list of cakes available in the system as a html page)

"/" - This is a GET end-point and will redirect the users to cake.html page with a list of available cakes in the syste,
"/cakes" -  This is a GET end-point and will return the list of cakes as "JSON"
"/cakes" -  This is a POST end-point and will add a new cake to the list of cakes

Running the tests
The project uses two sets of tests:
1. Controller tests - These are based on spring-test module. When the test is run as a JUnit test it mocks the HttpRequest
and hits the end-points exposed in CakeController.java
2. Service test - This is a simple unit test using Mockito to mock dependencies

Working:

    Without Docker:
    The application is started by running the main class (CakeManagerApplication.java). It uses Oauth2 for authentication
    with GitHub acting as an authorisation server. When you try to access the application using url http://localhost:8080/,
    you will be redirected to GitHub. Once you enter valid GitHub credentials you will be asked for permission to share
    basic details with cake-manager-oauth app in GitHub. Once you provide the permission the list of cakes can be accessed by using:
    http://localhost:8080/

    You can also use following url to get the list of cakes in json format:
    http://localhost:8080/cakes

    You can also submit a new cake to the existing list by using following end-point in a "POST" request. However they should provide cake parameters
    (title, description and image url) in request body in order to add a new cake to the list.
    http://localhost:8080/cakes

    With Docker:
    The application can also be run as a docker image. Users can run following command to package the application:

    1. Clone the repo using following url:

    https://github.com/ashishaggarwal/cake-manager.git

    2. cd to {directory where cake-manager is cloned}

    3. mvn clean package (It will create a jar file under cake-manager/target)

    4. docker build -t waracle/cake-manager . (This will build the docker image for cake-manager application)

    5.  docker run -d -p 8080:8080 -t waracle/cake-manager (This will run the image and load the application)

    Users can now use any of the end-points mentioned above.
