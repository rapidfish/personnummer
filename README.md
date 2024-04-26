# Personnummer API (2.0 Beta)

## Java API to handle everything Swedish Personnummer

The goal for this project is to provide an accurate and comprehensive Java API to handle Swedish Personnummer-, Samordningsnummer- and hopfully also to cover Swedish Organisationsnummer (Implemented but still some work in progress). 

Background
Before starting a project like this project I did some research to see if others already had a solution in place. But to my surprise I did not find anything that satisfied me as a programmer. And since this is still being the case 2024, i keep on trying, adding unittests and refaktoring the code until it reaches "perfection".


### Use it within your own Java project, as a dependecy!
- First, you will have to compile this project locally. Build it using Maven3.x
- mvn clean install
- Then include it as a dependecy in your own pom.xml (Maven) by following the next point.
- In your pom.xml, make sure you copy the <dependecy> block, then paste it somewhare in your own <dependencies> block:

```
<dependencies>
   ...

   <dependency>
    <groupId>se.osbe.id</groupId>
    <artifactId>personnummer</artifactId>
    <version>2.0-SNAPSHOT</version>
    <scope>compile</scope>
   </dependency>

   ...
</dependencies>

```


### Run it as a standalone (runnable jar) command from terminal
- git clone https://github.com/rapidfish/personnummer.git
- mvn clean package
- chmod +x target/Personnummer2-2.0-SNAPSHOT-jar-with-dependencies.jar

Now run the jar-file as a standalone command from terminal and explore what you can find out about any Swedish personnummer
```
java -jar target/Personnummer2-2.0-SNAPSHOT-jar-with-dependencies.jar -xjf 121212-1212
```

NOTE: this perticular personnummer (121212-1212, Tolvan Tolvansson) is used as example, it is correct but is not held by any real person (It is used just for demonstration purpose).

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


## New Features added (since 2024-04-18)
  - a way to handle Samordningsnummer within the same Personnummer class (a special form of Personnummer)
  - new class to handle swedish Organisationsnummer
  - Support for the Chinese Zodiac - added a new helper method to calculate which animal a person belongs to based on his/her date of birth. 
All according to the ancient Chinese Zodiac calendar (Tungshu). (more to come later ...)


## Features

- Automatic validation of checksum, directly when parsing, of strings with Personnummer

- Produces an Optional<Personnummer> when parsing. If not valid it becomes an empty Optional

- toString() can be used as default output after parsing, but there is also four other methods to represent it

- compareTo() can be used to compare age between any two Personnummer

- Extract meta data from a Personnummer; age, gender, zodiac sign and sometimes even the place of birth (on region level)

- Create Personnummer - quickly generate any number of Personnummer (random, but yet valid), useful for testing purposes.

- Automatically resolving missing '-' separator  (or a '+' whan indicating an age above hundred years)

- When era is missing it gets calculated automatically (e.g. '89' becomes '1989', not '2089' using present date comparison.

- Input strings may have a leghts from 10 to 13 characters (e.g. '1212121212', '121212-1212', '201212121212', '20121212-1212')

- Use the 'Forgiving flag' [Optional] for automatic error correction
  - Automatic error correction is achieved if turn on the 'forgiving flag' when trying to calculate a Personnummer with an 'invalid checksum' number.
  - The forgiving flag also lets you handle Personnumer with a birthdate set in the future (thus 'unborn' persons). Making its category still parsable.

- Helper class method to extract Zodiac information (Western zodiac signs)


## Usage examples coding in Java ...

Examples below show how to parse a Personnummer string.
Now an input string with a valid swedish personnummer can be written in a couple of different variations.
All of them are supported. Four different but equivalent input versions have the same outcome when using the Personnummer class. 
Input strings can vary in leghts as the year can be written on a short- or long form, and combined with- or without the use of a delimiter (-), between the last four digits.
No worries, all different variations of the input string for a personnummer is covered. When using just two digits for years it automaticalle detects wether a person is born before- or after the millenium (2000). If your're not getting the checksum right, we can calculat it for you, by setting the special option (the forgiving flag) to handle it when necessary.



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
if(pnrOpt.isPresent()) {
	System.out.println("Personnummer is valid!");
} else {
	System.out.println("Personnummer is NOT valid!");
	System.exit(1);
}
```

Validate and create a Personnummer object, or else throw an exeption ...

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
System.out.println("Zodiac sign: " + IDHelper.getZodiacSign(pnr).getLatinName()); // Zodiac sign: Taurus

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
    <version>2.0-SNAPSHOT</version>
   </dependency>
   ...
</dependencies>

```

**Version 2.0-SNAPSHOT**

Current stable branch is master

**Configuration**

Import the project as an existing maven projekt into your IDE, compile with Maven (mvn clean install), Junit tests are run automatically and should all work otherwise there could be a problem with the project setup. Check compiler settings for Java (JDK).

**Dependencies**

This project uses Jodatime ver 2.7 from Apache to handle all dates (see pom.xml file in the project root folder). It is automatically installed by maven at build time. Jodatime ensures that the API can be used in both old and newer versions of Java. Joda-Time was the "de facto" standard date and time library for Java prior to Java SE 8. It may be so that this Joda time dependency will become deprecated in future versions of Personnummer. For more information, go to http://www.joda.org/joda-time/

**How to run unit tests**

Unit tests are based on JUnit.
Use mvn command:
```
mvn test
```

**How to run as a standalone command**
Type "mvn clean package" in your terminal to build the project to a 'standalone' jarfile
Use the file target/personnummer-0.5.0-jar-with-dependencies.jar when running it as a standalone command (terminal)

Linux/Mac/Unix (terminal):
```
  java -jar target/personnummer-0.5.0-jar-with-dependencies.jar -jx 8010101015
```

Windows (CMD/DOS):
```
  java -jar target\personnummer-0.5.0-jar-with-dependencies.jar -jx 8010101015
```



In this case we use the "-jx" argument, where -j means "json formatted" and -x means "extended" info about a Personnummer.

_Output:_
```

PnrCliDao(personnummer10=8010101015, personnummer11=801010-1015, personnummer12=198010101015, personnummer13=19801010-1015, lastFourDigits=1015, isForgiving=false, correctChecksum=5, birthDate=1980-10-10, age=42, gender=MALE, zo
diacSign=Libra, zodiacSignSwe=Vågen)

```

...also try the -h (or --help) argument for help.
```
java -jar ./target/personnummer-0.5.0-jar-with-dependencies.jar -h
```

## More facts on Swedish Personnummer

* Checksum algorithm is based upon the Luhn-algorithm a.k.a modulus-10-algorithm [https://sv.wikipedia.org/wiki/Luhn-algoritmen].

* This API is based upon documents publicly available from the official Swedish Tax Agency: [http://www.skatteverket.se].

* This project was originally hosted on bitbucket 2015-09-27, but has moved to GitHub since 2020-02-19 *

* This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt *


## Who do I talk to if I have any questions about this project?

Oskar Bergström (repo owner).
