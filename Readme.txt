Readme.txt
*************************
Still under construction
*************************

ENVIRONMENT :
The program was tested with following 
Java version: 1.7.0_67
Apache Maven 3.1.1

Mnyrs-project
	mfore-frontend
	mfore-core
	mfore-utils	
mnfore.sql
Readme.txt


STEPS:

1) Setup Datbase schema by using mfore.sql 
Go to the folder where mfore is located then execute mysql -u root -p < mfore.sql
2) Inside mfore-utils/user-agent-parser run "mvn dependency:resolve;mvn clean;mvn compile;mvn package;mvn clean install"
3) Inside mfore-core run "mvn dependency:resolve;mvn clean;mvn compile;mvn package;mvn clean install"
3) Inside mfore-frontend run "mvn dependency:resolve;mvn clean;mvn compile;mvn package;mvn install”
4) Start drop wizard server using following command under mfore-frontend directory
	 java -jar target/mfore-frontend-1.0-SNAPSHOT.jar server config.yaml

mvn exec:java -Dexec.classpathScope=compile -Dexec.mainClass=com.mfore.frontend.App

Application can be accessed by using below url
	http://localhost:9999/mfore/html/login-form.html


Deployment hints:

1) If you hadn’t used the credentials given in mnyrs_mysql.txt then modify DB credentials in .yaml



