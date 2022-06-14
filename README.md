# rock-paper-scissors

# Köra i postman
- Spring port default är satt till 8080, kör du via en annan port så byt till denna 

### 1. Skapa nytt game
- POST request med JSON body av { "p1Name": ValfrittNamn }
- http://localhost:8080/api/games/new 
- Du får här ID:t skickat som response, skicka det som inbjudan till den du vill möta i spelet

### 2. Anslut till spel som spelare två
- PUT request med body av raw text ValfrittNamn
- http://localhost:8080/api/games/join/{ID} där ID är det du fått skickat av spelskaparen

### 3. Göra ett move
- PUT request med JSON body av { "playerName": DittValdaNamn, "move": ValfrittMove }
- Där ina valfria moves (ValfrittMove) är "rock", "paper", "scissors"
- DittValdaNamn är det du angav vid spelinitiering eller när du anslöt dit som spelare två. Det är case sensitive
- http://localhost:8080/api/games/move/{ID} där ID är det du fått vid spelskapande/fått av spelskaparen
- Efter att bägge spelare gjort varsitt move skrivs resultatet ut som en ResponseEntity, alt nås genom att leta upp spelet via ID (se nedan)

### Hitta game via dess id
- GET request till
- http://localhost:8080/api/games/{ID} där ID är det du fått vid spelskapande/fått av spelskaparen

### Se lista över alla games 
- GET request till
- http://localhost:8080/api/games/all
