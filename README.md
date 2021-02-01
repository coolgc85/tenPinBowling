# Ten Pin Bowling

Java coding challenge, the main objective is process a file with the lines that represents
every throw by multiple players and after that get the final score and print a score sheet representation

## Packaging

Use maven

```bash
mvn clean package -DskipTests=true
```

## Resources

Example resources, as a perfect game file are placed in the root directory

```bash
PerfectMatchSinglePlayer.txt
TwoPlayers.txt
```

## Usage

```bash
cd ./target
java -jar TenPinBowling-1.0-SNAPSHOT.jar <<FILE_PATH>>
java -jar TenPinBowling-1.0-SNAPSHOT.jar TwoPlayers.txt
java -jar TenPinBowling-1.0-SNAPSHOT.jar /home/user/TwoPlayers.txt
```
