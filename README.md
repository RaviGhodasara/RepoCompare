# RepoCompare

This repository contain java code to compare two repositories.

##Important Files

* `config.properties` : This file is configuration file in which user need to provide details of repositories and exclude file and directory.


******************


##Run Configuration
* You should have maven and Java 1.8 installed in your machine.

* First you need to clone this repository and perform below command to generate jar file

```
mvn clean install

```

* Once above command execute successfully then in target folder you will get jar file.

* Copy this jar to your desired folder and open command prompt where you place the jar

* Put `config.properties` file besides your jar and update based on your requirement

* All the details related `config.properties` file is present in that file only.

* Now execute below command to compare your repositories.


```
java -jar RepoCompare-jar-with-dependencies.jar

```


* If you want to enable logs for this jar file then just append `enableLog` in above command.

* Once execution successful then you will get `Output.txt` file at your current location.
