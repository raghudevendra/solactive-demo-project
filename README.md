Run Below steps to run a Spring Boot application on your local machine. 

1) Download the zip or clone the Git repository. Unzip the zip file (if you downloaded one)
$ git clone https://github.com/raghudevendra/solactive-demo.git

2) Open STS/Eclipse
  File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
  
3) Build the project with maven clean install. Right click on the project --> run as maven clean and maven install.

4) Run the project as Spring boot application execute below url's. 
  
3) POST Method URL  --> http://localhost:8080/api/ticks

4) GET Method --> http://localhost:8080/api/statistics

5) GET method for a Particular Instrument Name --> http://localhost:8080/api/statistics/IBM
