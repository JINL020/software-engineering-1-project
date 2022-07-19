
## About the project

The goal of the project was to create a simple game with a client-server implementation
while keeping good practices and architecture in mind.

**The Game**  
The game is a turn-based multiplayer game, where two clients play against eachother
and win by finding a hidden treassure on the game map and bringinig it to their fort first.


**The Client**  
- communication to the server (sending and receiving data)
- command line interface (MVC pattern)
- generation of a game map (floodfill)
- AI that calculates the moves for us
    - finding a destination to move to
    - finding a path to the destination (dijkstra)
- Logging, error handling & unit testing (mockito)


**The Server**  
- communication to the client (sending and receiving data)
- checking business rule violations
- hide treassure on map
- validate player input
- keep track of game state
- manage parallel games from differnt clients
