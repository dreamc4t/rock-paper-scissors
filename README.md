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


## Problem, förbättringsmöjligheter etc
- Cappat till 2 spelare. Hade kunnat skrivas om med en playermodel som sedan kan läggas till ett game x antal ggr.
- Just nu kan man ändra sitt move, hade varit fint om movet låstet efter det var gjort alt när spelet var avslutat (när båda spelarna gjort sina moves)
- Kunna se alla pågående spel och inte bara de som är avslutade kunde varit något. Antingen genom att encoda dragen tills matchen är avslutad eller att lägga till dem i databasen först när båda moves är gjorda. 
### Tester
- Finns inga tester skrivna än, tester i pseudokod som hade kunnat skrivas är: 
    - @BeforeEach
        - Skapa game testGame med testspelare ”Test Spelarsson”
    - create new game 
        - skapa game med spelar namn ”test test”
        - se om ResponseEntity returnerar något som innehåller ’Nytt game startat av dig, test test”
    - joinGame
        - joina game som player 2 utifrån  findById(testGame.getId()) 
        - se om det returnerar en ResponseEntity.ok()
            - (alt om den inte returnerar en felkod, tex 500)
    - makeAMove
        - kör joinGame med spelarnamn ”Testare2”
        - prova makeAMove med id från  findById(testGame.getId()) samt ”Test Spelarsson”
            - prova moves ”rock”, ”paper”, ”scissors”
            - se om ResponseEntity ej är felkod 
            - prova moves felstavat, tex ”rockkk”
            - se om RepsponseEntity är "Endast 'rock', 'paper' eller 'scissors' är tillåtna moves!"
            - prova ändra namnet till ”Testttt Spelarson” 
            - se om ResponseEntity är "Spelarnamnet finns ej. Glöm ej att det är case-sensitive!"
        - prova göra alla moves för de två spelarna ”Test Spelarsson” och ”Testare”, två åt gången
            - scissors emot scissors - Se om returnerad game:s body innehåller ”winner”: "Oavjort"
                - samt rock v rock och paper v paper
            - se om de respektive slår varanda (scissors slår paper, paper slår rock och rock slår scissors)
                - Se om returnerad game:s body innehåller ”winner”: rätt spelares namn 
    - findById
        - Kör makeAmove för att ha spel som är färdigspelade
        - se om findById(testGame.getId()) returnerar ett game
    - getAllGames
        - Kör makeAmove för att ha spel som är färdigspelade
        - se om det returneras en lista med 2 games (ett game skapas redan i @BeforeEach)
