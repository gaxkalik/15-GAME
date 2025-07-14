# N-Puzzle Game

A Java implementation of the classic **N-Puzzle** game, developed as part of a homework assignment. The game supports multiple board configurations and includes a robust core architecture for tile management, configuration validation, and puzzle initialization.

## Project Structure

```bash
am/
└── aua/
└── npuzzle/
├── NPuzzle.java # Main entry point of the game
├── Configs.txt # Sample configurations
├── core/
├── ArrayTiles.java
├── Configuration.java
├── ConfigurationStore.java
├── InvalidConfigurationException.java
├── MatrixTiles.java
├── PositionOutOfBoardException.java
└── Tiles.java # Core game logic
```


## How to Compile and Run

1. Navigate to the source directory:
    ```bash
    cd am/aua/npuzzle
    ```

2. Compile the Java files:
    ```bash
    javac  nPuzzle.java
    ```

3. Run the main program:
    ```bash
    java am.aua.npuzzle.NPuzzle
    ```

>  Make sure `Configs.txt` is in the correct path if it’s required at runtime.

## Features

- Load board configurations from file or URL
- Validate tile positions and board integrity
- Support for multiple tile implementations:
  - `ArrayTiles`
  - `MatrixTiles`
- Exception handling for invalid states:
  - `InvalidConfigurationException`
  - `PositionOutOfBoardException`

## Files Included

- `NPuzzle.java`: Main driver class
- `Configs.txt`: Predefined puzzle configurations
- `core/`: Core logic classes
- `README.txt`: Original readme provided by the author
- `REPORT.txt`: Project report or documentation

## Author

**Armen Balagyozyan**  
Coursework project — AUA Java Programming

---

*This project was developed for educational purposes and may be extended for further experimentation with AI-based solvers, graphical interfaces, or mobile adaptation.*
