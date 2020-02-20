# Personnummer API

## Java API to handle Swedish Personnummer, similar to SSN (Social Security Number)

* API to handle Swedish Personnummer

* Do validation, or extract information from a Personnummer (age, gender, or sometimes even place of birth... etc)

* Generate multiple valid Personnummer (useful for creating test data purposes)

* It can parse a Personnummer regardless of different leghts for year (or if the '-' and '+' separator is present), as long as it is a valid Personnummer.

* Era is calculated automatically - If 'year', the leading part a Personnummer input is using two digits instead of four ('89' instead of '1989') then its era part is calculated automatically

* Output of a parsed Personnummer can be presented in five different ways, including toString()

* It makes use of Java Optional - when parsing a Personnummer it produces an Optional<Personnummer>

* An 'invalid' Personnummer produces an empty Optional when parsed

* It is even possible to parse a Personnummer with having an 'invalid' checksum, by setting a 'forgiving' flag when doing a parse

* Examples below show how to parse four equivalent versions of the very same Personnummer (different leghts).


## Example ...
 

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
if(!pnrOpt.isPresent()) {
	throw new RuntimeException("Not a valid Personnummer");
}
```

extracting information...

```
Personnummer pnr = pnrOpt.get(); // get Personnummer from Optional.
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
System.out.println(pnr.toString11()); // 460430-0014
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

* This project was originally hosted on bitbucket 2015-09-27, but has moved to GitHub since 2020-02-19 *

* This project is licensed under the terms of the [GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt *


### Who do I talk to? ###

Oskar Bergström (repo owner).
