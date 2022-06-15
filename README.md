# rock-paper-scissors app
Backend REST api för att spela sten sax påse. Skrivet i java mha maven och spring (skapat genom spring boot). 

## Bygg + starta via jar-fil
- Ta dig till projektmappen via terminalen och slå sedan in:
- java -jar target/rock-paper-scissors-0.0.1-SNAPSHOT.jar

## Köra i postman
- Spring port default är satt till 8080, kör du via en annan port så byt till denna 

### 1. Skapa nytt game
- POST request med JSON body av { "p1Name": ValfrittNamn } där ValfrittNamn är ditt namn
- http://localhost:8080/api/games/new 
- Du får här ID:t skickat som response, skicka med det i inbjudan till den du vill möta i spelet, annars kan de ej ansluta till spelet

### 2. Anslut till spel som spelare två
- PUT request med body av raw text ValfrittNamn, där ValfrittNamn är ditt namn
- Är det samma namn som din motståndare kommer det bli ValfrittNamn_1 (du ser isf detta i ResponseEntity:n du får skickad till dig i postman)
- http://localhost:8080/api/games/join/{ID} där ID är det du fått skickat av spelskaparen

### 3. Göra ett move
- PUT request med JSON body av { "playerName": DittValdaNamn, "move": ValfrittMove }
- Där dina valfria moves (ValfrittMove) är "rock", "paper" eller "scissors"
- DittValdaNamn är det du angav vid spelinitiering eller när du anslöt dit som spelare två. Det är case sensitive
- http://localhost:8080/api/games/move/{ID} där ID är det du fått vid spelskapande/fått av spelskaparen
- Efter att bägge spelare gjort varsitt move skrivs resultatet ut som en ResponseEntity, alt nås genom att leta upp spelet via ID (se nedan)

### Hitta game via dess id
- GET request till
- http://localhost:8080/api/games/{ID} där ID är det du fått vid spelskapande/fått av spelskaparen
- Visar endast om spelet är avslutat

### Se lista över alla avklarade game
- GET request till
- http://localhost:8080/api/games/history
