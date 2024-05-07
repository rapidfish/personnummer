# Personnummer - A Java project to handle Swedish Personnummer
(2.2-beta)


## Java classes to verify, extract, analyze and more...

Can be used as dependency in your project, or run as a standa alone command 

This project contains Java classes to process Swedish Personnummer, including Samordningsnummer and Organisationsnummer.

Simply use it to verify the checksum of a personnummer (using its built in luhn-10 algorithm).

Included helper classes enables quick gathering of statistics. Works on collection with peronnummer/samordningsnummer.

Powerful methods to generate vast numbers of correct personnummer. This can be useful when writing unit tests.


## New Features added (since 2024-04-18)
  - Re-written using Java 21
  - The Personnummer class also handles Samordningsnummer (implicitly)
  - Added a new class to handle swedish Organisationsnummer (work in progress)
  - Calculate the animals of the traditional Chinese zodiac calendar


## Basic Features
  - Automatic checksum validation when parsing a personnummer string
  - Access extracted information through getter methods, such as 'birthdate', 'age', 'gender' etc...
  - Once parsed, the result becomes an immutable object (wrapped inside a Java Optional)
  - An empty Optional is returned whenever parsing is unsuccessful
  - Easy detection of id types (Personnummer, Samordningsnummer or Organisationsnummer)
  - Automatically resolves missing '-' separator  (or a '+' indicating ages above a hundred years)
  - Supporting different permutations of a valid input strings
  - Once parsed, the output can be presented in multiple ways by different toString methods
  - When parsing there is an option to use the 'forgiving flag', making 'invalid' checksums corrected.
  - The 'forgiving flag' enables parsing of Personnummer having a birthdate set in the future


### Build using Maven 'mvn clean install'
- git clone https://github.com/rapidfish/personnummer.git
- cd personnummer/
- mvn clean install


### Use the project as a dependency in your own Java projects ...
- Once built, the dependency (.jar) is accessable from your local Maven repo
- Now, include it as a depedency in your project by adding the dependency in your pom.xml
- Refresh your pom.xml file, and start working using its classes. (See the JavaDoc)


```
<dependencies>
   ...
   <dependency>
    <groupId>se.osbe.id</groupId>
    <artifactId>personnummer</artifactId>
    <version>2.2-SNAPSHOT</version>
    <scope>compile</scope>
   </dependency>
   ...
</dependencies>

```


### ...or, run it as a "runnable jar" command, directly from your favourite terminal (CLI)

Example - build and prepare for usage in bash (Linux/Mac/Unix):
- git clone https://github.com/rapidfish/personnummer.git
- cd personnummer/
- mvn clean package
- chmod +x target/Personnummer2-2.2-SNAPSHOT-jar-with-dependencies.jar
- mv target/Personnummer2-2.2-SNAPSHOT-jar-with-dependencies.jar target/personnummer

Example:
```
java -jar target/personnummer -xj 19121212-1212
```

Output:
```
{
  "personnummer10" : "1212121212",
  "personnummer11" : "121212-1212",
  "personnummer12" : "201212121212",
  "personnummer13" : "20121212-1212",
  "lastFourDigits" : "1212",
  "isForgiving" : true,
  "correctChecksum" : 2,
  "birthDate" : "2012-12-12",
  "age" : 11,
  "daysSinceBirth" : 4152,
  "gender" : "MALE",
  "zodiacSign" : "Sagittarius",
  "zodiacSignSwe" : "Skytten",
  "chineseZodiacAnimal" : "The year of the Dragon",
  "chineseZodiacAnimalSwe" : "Drakens år",
  "idType" : "PERSONNUMMER"
}
```


The -h option gives you all the help you need!

```
	java -jar target/personnummer.jar -h
```


Output:
```
	usage: personnummer [pnr] [args]
	 -c,--century     use era and century in output
	 -f,--forgiving   Be forgiving when the checksum (last digit) is wrong
	 -h,--help        Bring up this help screen
	 -j,--json        Show output as JSON
	 -v,--version     Show version of this command
	 -x,--extended    View all info about a Personnummer
```

### Handle different input - still present output the same way!
E.g. These string examples, can be served as input strings, represents the very same Swedish Personnummer.  
All of them can be parsed separatley, still having the same result when using the parser method from the class Personnummer.
	"1212121212"
 	"121212-1212"
  	"201212121212"
   	"20121212-1212"




Java code - usage example:

```
  // Calling the parse() meehod from the Java class Personnummer ...

  Optional<Personnummer> pnrOpt = Personnummer.parse("121212-1212"); // year = 12, month = 12, day = 12

  Personnummer pnr = pnrOpt.get();

  System.out.println(pnr.toString13()); // Showing full length, having leading digits for era and century (20) auto resolved
```


Console output:
```
  20121212-1212
```



## Java code example ...

This example show you how to parse a Personnummer string into a Parsonnummer object.
It shows different permutations of strings representing the very same personnummer.


```
  Optional<Personnummer> pnrOpt = Personnummer.parse("4604300014");
```

or...

```
  Optional<Personnummer> pnrOpt = Personnummer.parse("460430-0014");
```

or...

```
  Optional<Personnummer> pnrOpt = Personnummer.parse("194604300014");
```

or...

```
  Optional<Personnummer> pnrOpt = Personnummer.parse("19460430-0014");
```


Output:

```
// We use the '.get()' hence it being stored inside an Optional
System.out.println(pnrOpt.get().toString10()); // 4604300014
System.out.println(pnrOpt.get().toString11()); // 460430-0014, same as toString()
System.out.println(pnrOpt.get().toString12()); // 194604300014, twelve digits having era (automatically) 
System.out.println(pnrOpt.get().toString13()); // 19460430-0014, twelve digits, with era and '-' sign
```

Validation is done implicitly, as this example may illustrate

```
if(pnrOpt.isPresent() == false) {
    System.out.println("Personnummer is NOT valid!");
    System.exit(1);
} 
System.out.println("Personnummer is valid, carry on ...");

```


**Version 2.2-SNAPSHOT**
Current stable branch is master
Built using Java Open JDK 21

**Version 2.1-SNAPSHOT**
Stable version. Minor flaws and poor documentation.

**Configuration**
Compiler uses OpenJDK-21 ( https://jdk.java.net/21 )
Import the project as an existing maven projekt into your IDE ( https://maven.apache.org )
When all is working, try to run the main class se.osbe.id.main.MainCLI and the output should look like this...

Output from MainCLI.java (without args):
```
usage: personnummer [args] [pnr]
 -c,--century     use era and century in output
 -f,--forgiving   Be forgiving when the checksum (last digit) is wrong
 -h,--help        Bring up this help screen
 -j,--json        Show output as JSON
 -v,--version     Show version of this command
 -x,--extended    View all info about a Personnummer
```



**Dependencies**
Junit, Apache (commons, collections, cli), Jackson fasterxml (databind), maven-assembly-plugin. 

**How to run unit tests**
Unit tests are based on JUnit and is automatically run upon compilation using (mvn clean install). It can also be ran from your favourite IDE (such as IntelliJ) or using this Maven command from terminal ...
```
mvn test
```

## Project Background
The aim for this project is was to provide the developer community with a free, comprehensive, reliable, accurate and yet fast Java API. 
To handle anything Swedhish id strings, from Personnummer, Samordningsnummer and Organisationsnummer.

Main purpose of this API and its code, is to validate, extract info, analyze and even create any Swedish Personnummer/Samordningsnummer/Organisationsnummer.
The future plans for this API is for it to also bring in more support for Swedish Organisationsnummer (but work is still 'in progress').


## More facts on Swedish Personnummer

* Checksum algorithm is based upon the Luhn-algorithm a.k.a modulus-10-algorithm [https://sv.wikipedia.org/wiki/Luhn-algoritmen]

* This API is based upon documents publicly available from the official Swedish Tax Agency: [http://www.skatteverket.se]

* This project was originally hosted on bitbucket 2015-09-27, but has moved to GitHub since 2020-02-19

* This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt


## Disclaimer
All information about Swedish Personnummer/Samordningsnummer/Organisationsnummer can be found in the public domain, form official Swedish authority websites, and on Wikipedia.
There is absolutley no way this API can extract any personal information other than birthdate, age, gender. Sometimes the place of birth can be derivide when availible. But only for personnummer having its birthdate set prior to the year 1990. After 1990 this practice was removed.



## Who do I talk to if I have any questions about this project?

Oskar Bergström (repo owner).
