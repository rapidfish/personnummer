# Java project to handle Swedish Personnummer
(2.2-beta)


## Java classes to verify, extract, analyze and more...

Java project to process Swedish Personnummer, including Samordningsnummer and Organisationsnummer.
 
Use it as dependency in your project, or run it as a 'stand alone' command (runnable jar)

Verify checksum of a personnummer (using its built in Luhn-10 algorithm).

Extract every possible built in characteristics of a personnummer, including age, gender, checksum, and even zodiac sign

Helper class to quickly generate any number of valid personnummer, based on your criteria - useful when developing tests etc...



## New Features added (since 2024-04-18)
  - Re-written using Java 21
  - The Personnummer class also handles Samordningsnummer (implicitly)
  - Added a new class to handle Swedish Organisationsnummer (work in progress)
  - Calculate the animals of the traditional Chinese zodiac calendar


## Basic Features
  - Automatic checksum validation
  - Extract information such as 'birthdate', 'age', 'gender' and more ...
  - Parsed objects is treated as an immutable object
  - Uses Java Optional - No more null-checking!
  - Easy detection of type (Personnummer, Samordningsnummer or Organisationsnummer)
  - Automatic awareness of delimiter indicator ('-', or '+' when indicating ages beyond hundred years of age)
  - Supporting different permutations of input strings
  - Parsed personnummer can be presented in multiple ways, using a set of different toString methods
  - Use the 'forgiving flag'. If you for some reason, want to correct an 'invalid' checksum when parsing.
  - The 'forgiving flag' also permitting having a birthdate set in the future (otherwise protected against)


### Build using Maven 'mvn clean install'
- git clone https://github.com/rapidfish/personnummer.git
- cd personnummer/
- mvn clean install


### Use the project as a dependency in your own Java projects ...
- Once built, the dependency (.jar) is accessible from your local Maven repo
- Now, include it as a dependency in your project by adding the dependency in your pom.xml
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
All of them can be parsed separately, still having the same result when using the parser method from the class Personnummer.
	"1212121212"
 	"121212-1212"
  	"201212121212"
   	"20121212-1212"




Java code - usage example:

```
  // Calling the parse() method from the Java class Personnummer ...

  Optional<Personnummer> pnrOpt = Personnummer.parse("121212-1212"); // year = 12, month = 12, day = 12

  Personnummer pnr = pnrOpt.get();

  System.out.println(pnr.toString13()); // Showing full length, having leading digits for era and century (20) auto resolved
```


Console output:
```
  20121212-1212
```



## Java code example ...

This example show you how to parse a Personnummer string into a Personnummer object.
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
The main class se.osbe.id.main.MainCLI is used as entry when using the project as a CLI command (runnable jar).


**Dependencies**
Junit, Apache (commons, collections, cli), Jackson fasterxml (databind), maven-assembly-plugin. 

**How to run unit tests**
Unit tests are based on JUnit and is automatically run upon compilation using (mvn clean install).
```
mvn test
```

## Project Background - Why make another project like this?
Nowdays, there is a lot of projects 'out there' and they all promise to handle Swedish personnummer correctly. So which one should you choose?
Way back, when I started this little project, there was virtually no competition. All I found, while searching the web, was a few simple script here and there.
Some solutions where promising, but was often coded in some 'esoteric' language. Others looked promising at first, such as this webpage 
I stumbled upon. Having a form backed up JavaScript to calculate the checksum of a personnummer. The web page looked all nice and shiny (I give them that).
However, it did'nt calculate the checksum correctly for some cases, so...

That's why I took upon me to 'invent the wheel' myself. In my proffession I am a Java developer, so the choice of implementation became obious to me.
A couple of years later, and a low pace of developing. The projct has become somewhat 'mature enough' to be released to the public.

Allbeit a 'nerd' project, I still find it very stimulating and fun to develop.



## More facts on Swedish Personnummer
* Checksum algorithm is based upon the Luhn-algorithm a.k.a modulus-10-algorithm [https://sv.wikipedia.org/wiki/Luhn-algoritmen]

* This API is based upon documents publicly available from the official Swedish Tax Agency: [http://www.skatteverket.se]

* This project was originally hosted on bitbucket 2015-09-27, but has moved to GitHub since 2020-02-19

* This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt


## Disclaimer
There is absolutley no gurantee that the implementation is a 100% accurate when running (executing) it.

This project is only based upon publicly availible information about Swedish Personnummer/Samordningsnummer/Organisationsnummer.
Mainly from official Swedish authority websites (se above) and Wikipedia online articles.
No sensitive information about any real Swedish citizen (still alive or dead) is represented here. 
Personnummer in all examples are all just 'made up'. Any similarities to any real person is therefor just a coincident. 
For example: the use of personnummer 19121212-1212, is a commonly used fictive person. He is frequently used among IT-staff as a 'dummy' person and goes way back in tradition.
This 'not so real person' goes by the name "Tolvan Tolvansson" (eng. "Twelve Twelveson").

Personnummer having its birthdate set prior to the year 1990, could also reveal what region/province (Län) in Sweden a person was born. 
However, since the year 1990, this no longer practice. Hence it is only relevant for personnummer having a birthdate before this year.
Regions/provinces (Län) has also gone through renaming since 1990, adding to the case of no longer being relevant.



## Who do I talk to if I have any questions about this project?

Oskar Bergström (repo owner).
