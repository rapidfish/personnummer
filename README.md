# Personnummer API (2.1-beta)

# Swedish Personnummer (and Samordningsnummer) - Verify/Extract/Analyze/Repair/Create

## Java API to handle everything Swedish Personnummer

Extract information from any Swedish Personnummer (or Samordningsnummer) in a comprehensive manner, yet fast and simple to use!

...gather statistics from collections of peronnummer/samordningsnummer using included helper classes.

...generate personnummer (sometimes useful for test developers when a personnummer has to 'make it through' form validation etc...)

...or simply use it to verify if a personnummer having a correct checksum by its built in use of the luhn-10 algorithm.


## New Features added (since 2024-04-18)
  - Rewritten using Java 21
  - The Personnummer class now also handles Samordningsnummer (automatically detected when parsing)
  - new class to handle swedish Organisationsnummer (work in progress)
  - Find out what animal a birth date is associated with, according to the traditional Chinese Zodiac calendar.


## Basic Features
  - Automatic checksum validation, when parsing a personnummer string into an Optional<Personnummer> object.
  - If parsing was unsuccessful due to malformed string, or checksum being wrong, an empty Optional is returned.
  - Automatically detects type of swedish id string (Personnummer, Samordningsnummer or even Organisationsnummer).
  - Extract information such as birthdate, age, number of days since birth, gender, and sometimes even regional place of birth.
  - Support of four valid permutations of an input string. (10 trough 13 characters in length)
  - Once parsed, output can be presented using multiple toString methods. e.g. toString(), toString10(), toString11(), toString12(), toString13()
  - Extracted data can be accessed through getters methods on the object. e.g. getChecksum(), getAge(), getGender() etc...
  - Invalid checksum (last digit) can be error corrected (automatically) if using a 'forgiving flag' when parsing.


### Build using Maven 'mvn clean install'
- git clone https://github.com/rapidfish/personnummer.git
- cd personnummer/
- mvn clean install


### Use the API within your own Java project ...
- Once built (mvn clean install) a local copy of the dependency (.jar) is stored in your local Maven repo ( in the 'm2/' folder )
- Now, include it as a depedency, inside your own local Maven project - super easy!
- Refresh your pom.xml file, and start working using its classes. (Use its JavaDoc)


```
<dependencies>
   ...
   <dependency>
    <groupId>se.osbe.id</groupId>
    <artifactId>personnummer</artifactId>
    <version>2.1-SNAPSHOT</version>
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
- chmod +x target/Personnummer2-2.1-SNAPSHOT-jar-with-dependencies.jar
- mv target/Personnummer2-2.1-SNAPSHOT-jar-with-dependencies.jar target/personnummer

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


Java code example:
```
  // Example - calling the parse() meehod from the Java class Personnummer ...

  Optional<Personnummer> pnrOpt = Personnummer.parse("121212-1212"); // year = 12, month = 12, day = 12
  Personnummer pnr = pnrOpt.get();
  System.out.println(pnr.toString13()); // Showing full length, having leading digits for era and century (20) auto resolved
```

  Console output:
```
  20121212-1212
```


### More on what can be done using this API ...
- toString() can be used as default output after parsing, but there is also four other methods to represent it

- compareTo() can be used to compare age between any two Personnummer

- Extract meta data from a Personnummer; age, gender, zodiac sign and sometimes even the place of birth (on region level)

- Automatically resolves missing '-' separator  (or a '+' indicating ages above a hundred years)

- Quickly generate any number of Personnummer - useful when testing.

- Era- and century gets calculated automatically if missing in the input string.
  As an example the year 99' automatically becomes 1999. It can not become '2099' as the whole date part is compared against the present date, when making this decision.
  This mechanism is also a kind of protection, as it is virtually not possible to hand out personnummer to "unborn" persons a future.
  However, it is actually still possible to handle future dates (thus 'unborn' persons) by using the 'forgiving flag'.

- Use the 'Forgiving flag' [Optional] overrides normal checksum control.
  - Automatic error correction calculates the correct checsum regardless of it being invalid, or not.
  - Personnummer created using the 'forgiving flag' is indicated by always keeping the forgiving flag (true) afterwards.
  - the 'forgiving flag' also lets you handle Personnumer with a birthdate set in the future (thus 'unborn' persons). Making its category still parsable. Personnummer created this way, is indicated by always keeping the forgiving flag set to true afterwards.

    As an example the year 99' automatically becomes 1999. It can not become '2099' as the date part is in the future.
	However, it is actually still possible to handle future dates (thus 'unborn' persons) by using the 'forgiving flag' when parsing an id string.

- Extract zodiac information from any birthdate. A helper class lets you extract both Western- and Chinese zodiac calendar info.


## Usage examples coding in Java ...

Examples below show how to parse a Personnummer string.
Now an input string with a valid swedish personnummer can be written in a couple of different variations.
All of them are supported. Four different but equivalent input versions have the same outcome when using the Personnummer class. 
Input strings can vary in leghts as the year can be written on a short- or long form, and combined with- or without the use of a delimiter (-), between the last four digits.
No worries, all different variations of the input string for a personnummer is covered. When using just two digits for years it automaticalle detects wether a person is born before- or after the millenium (2000). If your're not getting the checksum right, we can calculat it for you, by setting the special option (the 'forgiving flag') to handle it when necessary.


Example with different permutations of strings representing the very same personnummer.
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


Validate example...
```
if(pnrOpt.isPresent() == true) {
    System.out.println("Personnummer is valid!");
} else {
    System.out.println("Personnummer is NOT valid!");
}
```

toString method examples ...

```
System.out.println(pnr.toString10()); // 4604300014
System.out.println(pnr.toString11()); // 460430-0014, same as toString()
System.out.println(pnr.toString12()); // 194604300014, twelve digits having era (automatically) 
System.out.println(pnr.toString13()); // 19460430-0014, twelve digits, with era and '-' sign
```


**Version 2.1-SNAPSHOT**
Current stable branch is master
Built using Java Open JDK 21


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
