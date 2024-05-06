# Personnummer API (2.1-beta)

## Java API to handle everything Swedish Personnummer and Samordningsnummer
Validate any Swedish Personnummer or Samordningsnummer in a fast, comprehensive manner, yet simple to use!
Gather statistics from a collection of peronnummer using included helper classes.
Generate personnummer, sometimes useful for test developers when a personnummer has to 'make it through' form validation etc.

### Build it using the Maven command 'mvn clean install' (Maven3 needs to be installed)
- git clone https://github.com/rapidfish/personnummer.git
- mvn clean install


### Use it within your own Java projects
- To use this inside your own projects as a depedency, you will have to compile this project locally. 
- Then include it as a dependency in your own project (pom.xml) like this ...

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

### Run it as a standalone (runnable jar) command from your terminal
- git clone https://github.com/rapidfish/personnummer.git
- mvn clean package
- chmod +x target/Personnummer2-2.1-SNAPSHOT-jar-with-dependencies.jar
- mv target/Personnummer2-2.1-SNAPSHOT-jar-with-dependencies.jar target/personnummer

Example:
```
java -jar target/personnummer -xjf 121212-1212
```

Console output example:
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


... or use -h option to see help screen!

```
	java -jar target/personnummer.jar -h
```

```
	usage: personnummer [pnr] [args]
	 -c,--century     use era and century in output
	 -f,--forgiving   Be forgiving when the checksum (last digit) is wrong
	 -h,--help        Bring up this help screen
	 -j,--json        Show output as JSON
	 -v,--version     Show version of this command
	 -x,--extended    View all info about a Personnummer
```

## New Features added (since 2024-04-18)
  - Rewritten using Java 21
  - The Personnummer class now also handles Samordningsnummer (automatically detected when parsing)
  - new class to handle swedish Organisationsnummer (work in progress)
  - Find out what animal a birth date is associated with, according to the traditional Chinese Zodiac calendar.

## Basic Features
  - Automatic checksum validation, when parsing a personnummer string into an Optional<Personnummer> object.
  - Automatically detects type of swedish id string (Personnummer, Samordningsnummer or Organisationsnummer).
  - Parsing takes care of all permutations of input strings, as long as the input is a valid id string.
  - All extracted output data can be presented the same way, no matter what the input string looked like, as long as it was valid.
  - When/if needed the checksum (last digit) can be calculated, using the a forgiving flag set to true, when parsing.
    (Personnummer created this way always returns true, when invoking the isForgiving() method on it afterwards.

E.g. These string examples, can be served as input strings, represents the very same Swedish Personnummer.  
All of them can be parsed separatley, still having the same result when using the parser method from the class Personnummer.
	"1212121212"
 	"121212-1212"
  	"201212121212"
   	"20121212-1212"

```
// Example - calling the parse() meehod from the Java class Personnummer ...

Optional<Personnummer> pnrOpt = Personnummer.parse("201212121212");
Personnummer pnr = pnrOpt.get();
System.out.println(pnr.toString13());

Console output:
121212-1212
```


### Extracting information
- toString() can be used as default output after parsing, but there is also four other methods to represent it

- compareTo() can be used to compare age between any two Personnummer

- Extract meta data from a Personnummer; age, gender, zodiac sign and sometimes even the place of birth (on region level)

- Quickly generate any number of Personnummer - useful when testing.

- Automatically resolves missing '-' separator  (or a '+' indicating ages above a hundred years)

- Era- and century gets calculated automatically if missing in the input string.
  As an example the year 99' automatically becomes 1999. It can not become '2099' as the whole date part is compared against the present date, when making this decision.
  This mechanism is also a kind of protection, as it is virtually not possible to hand out personnummer to "unborn" persons a future.
  However, it is actually still possible to handle future dates (thus 'unborn' persons) by using the 'forgiving flag'.

- Use the 'Forgiving flag' [Optional] overrides normal checksum controll and enables automatic error correction of the checksum (kontrollsiffran)
  - Automatic error correction calculates the correct checsum regardless of it being invalid, or not.
  - the 'forgiving flag' also lets you handle Personnumer with a birthdate set in the future (thus 'unborn' persons). Making its category still parsable.

    As an example the year 99' automatically becomes 1999. It can not become '2099' as the date part is in the future.
	However, it is actually still possible to handle future dates (thus 'unborn' persons) by using the 'forgiving flag' when parsing an id string.

- Extract zodiac information from any birthdate. A helper class lets you extract both Western- and Chinese zodiac calendar info.


## Usage examples coding in Java ...

Examples below show how to parse a Personnummer string.
Now an input string with a valid swedish personnummer can be written in a couple of different variations.
All of them are supported. Four different but equivalent input versions have the same outcome when using the Personnummer class. 
Input strings can vary in leghts as the year can be written on a short- or long form, and combined with- or without the use of a delimiter (-), between the last four digits.
No worries, all different variations of the input string for a personnummer is covered. When using just two digits for years it automaticalle detects wether a person is born before- or after the millenium (2000). If your're not getting the checksum right, we can calculat it for you, by setting the special option (the 'forgiving flag') to handle it when necessary.



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


validate ...
```
if(pnrOpt.isPresent() == true) {
    System.out.println("Personnummer is valid!");
} else {
    System.out.println("Personnummer is NOT valid!");
}

```

Validate and create a Personnummer object, or else throw an exception ...

```
Personnummer pnr = pnrOpt.orElseThrow(RuntimeException::new);
```

```
System.out.println("Gender: " + pnr.getGender("Woman", "Man")); // Man
System.out.println("Age " + pnr.getAgeNow() + " years old!"); // Age 73 years old!"
System.out.println("Birth date: " + pnr.getBirthDate()); // Birth date: 1946-04-30
System.out.println("Born on a: " + DateTimeFormat.forPattern("EEEE").print(pnr.getBirthDate())); // Born on a: Tuesday
System.out.println("Days since birth: " + pnr.getDaysSinceBirth()); // Days since birth: 26958
System.out.println("Personnummer checksum: " + pnr.getChecksum()); // Personnummer checksum: 4
System.out.println("Zodiac sign: " + PersonnummerHelper.getZodiacSign(pnr).getLatinName()); // Zodiac sign: Taurus

```

toString methods
```
System.out.println(pnr.toString10()); // 4604300014
System.out.println(pnr.toString11()); // 460430-0014, same as toString()
System.out.println(pnr.toString12()); // 194604300014, twelve digits having era (automatically) 
System.out.println(pnr.toString13()); // 19460430-0014, twelve digits, with era and '-' sign
```

## Example code (2)...
The API also supports the use of '+' sign, to indicate +100 years of age when Personnummer is written with only 10 digits (dates without era)...


```

	Personnummer pnr = Personnummer.parse("101201+0342").get(); // using + to indicate >100 years old.
		
	System.out.println(pnr.toString13()); // 19101201-0342, Correct era (19) automatically, regardless of input string. 
	System.out.println("Age: " + pnr.getAgeNow()); // Age: 109

```


## Set up
* Clone the project from bitbucket with Git or simply download it manually.
* Import the project as an "existing maven project" in your favourite IDE (e.g. IntelliJ).
* Build using your IDE, or use Maven from command line, navigate to your project and type:  **mvn clean install**
* Afterwards its in your local Maven repo (~/.m2/) and you can start using it as a dependency in your own projects (se POM.xml fragment below).

```
<dependencies>
   ...
   <dependency>
    <groupId>se.osbe.id</groupId>
    <artifactId>personnummer</artifactId>
    <version>2.1-SNAPSHOT</version>
   </dependency>
   ...
</dependencies>

```

**Version 2.1-SNAPSHOT**

Current stable branch is master

**Configuration**

Import the project as an existing maven projekt into your IDE, compile with Maven (mvn clean install), Junit tests are run automatically and should all work otherwise there could be a problem with the project setup. Check compiler settings for Java (JDK).

**Dependencies**

**How to run unit tests**

Unit tests are based on JUnit and is automatically run upon compilation using (mvn clean install). It can also be ran from your favourite IDE (such as IntelliJ) or using this Maven command from terminal ...
```
mvn test
```

## Project Background
The aim for this project is was to provide the developer community with a free, comprehensive, reliable, accurate and yet fast Java API. 
To handle anything Swedhish id strings, from Personnummer, Samordningsnummer and Organisationsnummer.

The main purpose for it is to validate, extract info, analyze and even create any Swedish Personnummer/Samordningsnummer/Organisationsnummer.

Disclaimer
All information about Swedish Personnummer/Samordningsnummer/Organisationsnummer can be found in the public domain, form official Swedish authority websites, and on Wikipedia.
There is absolutley no way this API can extract any personal information other than birthdate, age, gender. Sometimes the place of birth can be derivide when availible. But only for personnummer having its birthdate set prior to the year 1990. After 1990 this practice was removed.

The future plans for this API is for it to also bring in more support for Swedish Organisationsnummer (but work is still 'in progress').


## More facts on Swedish Personnummer

* Checksum algorithm is based upon the Luhn-algorithm a.k.a modulus-10-algorithm [https://sv.wikipedia.org/wiki/Luhn-algoritmen].

* This API is based upon documents publicly available from the official Swedish Tax Agency: [http://www.skatteverket.se].

* This project was originally hosted on bitbucket 2015-09-27, but has moved to GitHub since 2020-02-19 *

* This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt *


## Who do I talk to if I have any questions about this project?

Oskar Bergström (repo owner).
