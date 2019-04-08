# > MowItNow Mower project <

**This is a spring boot application.**  
The purpose of the application is to read an instruction file as input and move a set of mowers accordingly. The instructions file is named **"lawn_mowers_instructions.lmw"** and has to be placed into the **"inputs/"** directory at the root of the project, or at the root of the **.jar** file (in production mode).

**Example of instructions:**  
5 5  
1 2 N  
GAGAGAGAA  
3 3 E  
AADAADADDA  
 


REQUIREMENTS
------------

JDK 1.8  
Maven 3.6.0   

## Installation

Use Maven commands to build and play with the application.

```bash
mvn clean # To clean the project.
mvn install # To install and build the jar. The jar generated is in the mowitnow-mowers/target subdirectory.
```

## Run Junit tests with maven

```
mvn test
```

## Run the application
The application can runs in two modes:

**Standard mode :** without arguments.  
**Interactive mode :** using a **-i** argument (allows user to visualize the mowers moving).

```
java -jar mowitnow-mower-0.0.1-SNAPSHOT.jar # run in standard mode from the .jar snapshot.
java -jar mowitnow-mower-0.0.1-SNAPSHOT.jar -i # run in interractive mode from the jar snapshot.
```

## Documentation

Check the documentation of the application in **doc/** directory at the root of the project.


## License
@Author : **aZeufack** - Software developper.  
[MIT](https://choosealicense.com/licenses/mit/)