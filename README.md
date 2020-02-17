# Personnummer API

## Open Source Java API to handle Swedish "Personnummer" (personal identity number)

* API to extract information from Swedish personnummer.

*project originally initiated by Oskar Bergstrom as a project on bitbucket 2015-09-27.*

*This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt)*



##Example code (1)...
Same personnummer can be written in four different ways (lengths), still representing same person. 

```
Personnummer pnr = Personnummer.parse("4604300014");
```
or...
```
Personnummer pnr = Personnummer.parse("460430-0014");
```
or...
```
Personnummer pnr = Personnummer.parse("194604300014");
```
or...
```
Personnummer pnr = Personnummer.parse("19460430-0014");
```
then extract information...
```
System.out.println(pnr.toString10()); // same as toString()
System.out.println(pnr.toString11()); // ten digits, with '-' (or '+')
System.out.println(pnr.toString12()); // twelve digits, with era 
System.out.println(pnr.toString13()); // twelve digits, with era and '-' sign
System.out.println("Gender: " + pnr.getGender("Woman", "Man"));
System.out.println("Age right now: " + pnr.getAgeNow() + " years old");
System.out.println("Birth date: " + pnr.getBirthDate());
System.out.println("Born on a: " + DateTimeFormat.forPattern("EEEE").print(pnr.getBirthDate()));
System.out.println("Days from birth: " + pnr.getDaysFromBirth());
System.out.println("Personnummer checksum: " + pnr.getChecksum());
System.out.println("Zodiac sign: " + IDHelper.getZodiacSign(pnr).getLatinName());

```

and the output becomes...


```
4604300014
460430-0014
194604300014
19460430-0014
Gender: Man
Age right now: 70 years old
Birth date: 1946-04-30
Born on a: Tuesday
Days from birth: 25730
Personnummer checksum: 4
Zodiac sign: Taurus
```

##Example code (2)...
The API also supports the use of '+' sign, to indicate +100 years of age when Personnummer is written with only 10 digits (dates without era)...


```

	Personnummer pnr = Personnummer.parse("101201+0342"); // using + to indicate >100 years old.
		
	System.out.println(pnr.toString13()); // Correct era automatically, regardless of input string. 
	System.out.println("Age: " + pnr.getAgeNow());

```
output...

```
19101201-0342
Age: 105
```


## Set up
* Clone the project from bitbucket with Git or simply download it manually from the Bitbucket.org.
* Import the project as an "existing maven project" in your favourite IDE (e.g. Eclipse).
* Build using Maven from your IDE or from your command prompt (under the project root folder): **mvn clean install**
* ...or build using Maven plugin inside your favourite IDE (e.g. Eclipse) using a Maven plugin.
* When you receive "Build success" message from Maven, you are good to go! Start using it as a dependency in your own projects.
* To add the API in your project as a Maven dependency in you pom.xml, your pom.xml should look like this...

```
<dependencies>
   ...
   <dependency>
      <groupId>se.osbe.id</groupId>
      <artifactId>personnummer</artifactId>
      <version>0.4.0</version>
   </dependency>
   ...
</dependencies>

```

## Features... 
* Validate any Swedish personnummer.
* Validation done through control of both **valid date** and **valid checksum** , not just the checksum (like other APIs).
* Personnummer can present **output in four different formats**, regardless of original input string.
* Once parsing is done, the Personnummer class can extract information such as **birth date, age, gender and even zodiac sign**.
* In some cases it is also possible to **extract place of birth** (only when birth date is before 1990). 
* Statistics class for **easy analyse of whole collections** with personnummer data (average/minimum/maximum age, amount of men/women etc...) 
* Builder class to quickly **generate any number of valid personnummer** (for testing purpose to produce perosnnummer in situations where use of sensitive "live" data is prohibited.
* Optional feature, to "repair" Personnummer with invalid checksums automatically.
* **Helper class** with useful methods to support searching, calculation of checksums (separate stand-alone methods), date handling etc...

**Version 0.4.0**

Current stable branch is (not ready)

**Configuration**

Import the project as an existing maven projekt into your IDE, compile with Maven (men clean install), Junit tests are run automatically and should all work otherwise there could be a problem with the project setup. Check compiler settings for Java (JDK).

**Dependencies**

This project uses Jodatime ver 2.7 from Apache to handle all dates (see pom.xml file in the project root folder). It is automatically installed by maven at build time. Jodatime ensures that the API can be used in both old and newer versions of Java. Joda-Time was the "de facto" standard date and time library for Java prior to Java SE 8. It may be so that this Joda time dependency will become deprecated in future versions of Personnummer. For more information, go to http://www.joda.org/joda-time/

** How to run tests **
Unit tests are based on JUnit.

** more facts **

* Checksum algorithm is based upon the Luhn-algorithm a.k.a modulus-10-algorithm.

* This API is based on documents publicly available at the Swedish Tax Agency: [http://www.skatteverket.se].


### Who do I talk to? ###

Oskar Bergström (repo owner).
