# Swedish Personnummer Java Library

## Overview
This Java library processes Swedish personal identity numbers (personnummer), including coordination numbers (samordningsnummer) and organization numbers (organisationsnummer). It can be used as a dependency in your Java project or as a standalone CLI tool.

### Features:
- **Checksum validation** using the Luhn-10 algorithm.
- **Data extraction**, including birthdate, age, gender, and zodiac signs (both Western and Chinese).
- **Flexible parsing**, supporting various input formats.
- **Immutability**, ensuring parsed objects remain unchanged.
- **Optional forgiving mode**, allowing minor corrections for testing purposes.

## New in v2.3.1:
- Rewritten for Java 21.
- Improved handling of coordination numbers.
- Added support for Swedish organization numbers (work in progress).
- Chinese zodiac calculation.

## How to

### Maven
Step 1: Add repository to pom.xml
```xml
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```

Step 2: Add the dependency to pom.xml
```xml	
	<dependency>
	    <groupId>com.github.rapidfish</groupId>
	    <artifactId>personnummer</artifactId>
	    <version>v2.3.1</version>
	</dependency>
```

For more information see: https://jitpack.io/#rapidfish/personnummer/v2.3.1


### Clone & build locally with Maven

```sh
git clone https://github.com/rapidfish/personnummer.git
cd personnummer/
mvn clean install
```

### Run as CLI:
```sh
java -jar target/personnummer.jar -xj 19121212-1212

args:
	 -c,--century     use era and century in output
	 -f,--forgiving   Be forgiving when the checksum (last digit) is wrong
	 -h,--help        Bring up help text
	 -j,--json        Show output on JSON format
	 -v,--version     Show version of this command
	 -x,--extended    View extended info about a Personnummer
```

### Example Output:
```json
{
  "personnummer10": "1212121212",
  "birthDate": "2012-12-12",
  "age": 11,
  "gender": "MALE",
  "zodiacSign": "Sagittarius",
  "chineseZodiacAnimal": "The Year of the Dragon",
  "idType": "PERSONNUMMER"
}
```

### Java Example:
```java
Optional<Personnummer> pnrOpt = Personnummer.parse("121212-1212");
if (pnrOpt.isPresent()) {
    System.out.println(pnrOpt.get().toString13()); // 20121212-1212
}
```

## About the Project
Originally created due to the lack of reliable personnummer libraries, this project has evolved into a mature tool. Built using Java 21, it is based on publicly available information from the Swedish Tax Agency.

### License
GPL v3 - See [GNU GPL v3](https://www.gnu.org/licenses/gpl-3.0.txt)

### Questions?
Contact: Oskar Bergström (repo owner).

