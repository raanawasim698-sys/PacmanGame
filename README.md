# Pacman (Console Edition)

A terminal-based recreation of classic Pacman, written in Java. Navigate the maze, eat dots and power pellets, and avoid (or hunt) four ghosts — each with its own AI behavior — rendered live in your terminal with ANSI colors and emoji.

## Features

- **Full maze board** with walls, dots, and power pellets
- **Four ghosts with unique chase AI**, just like the original arcade game:
  - **Blinky** (Red) — directly chases Pacman's current position
  - **Pinky** (Pink) — targets 4 tiles ahead of Pacman's direction of travel
  - **Inky** (Cyan) — moves unpredictably, targeting random tiles
  - **Clyde** (Orange) — chases aggressively when far away, but retreats to his scatter corner when close
- **SCATTER / CHASE mode switching** — ghosts periodically alternate between retreating to home corners and actively hunting Pacman
- **Power pellets** — eating one triggers **Frightened Mode**, making ghosts flee randomly and become vulnerable (eat them for bonus points!)
- **Lives, scoring, and win/lose conditions**
- **Colorized terminal rendering** using ANSI escape codes and Unicode/emoji character sprites

## Controls

| Key | Action |
|-----|--------|
| `W` | Move Up |
| `A` | Move Left |
| `S` | Move Down |
| `D` | Move Right |

Eat all the dots and power pellets to win. Lose all 3 lives and it's game over.

## Tech Stack

- **Language:** Java
- **Rendering:** Console/terminal output with ANSI color codes
- **No external dependencies** — pure Java standard library (`java.util.Scanner`)

## Project Structure

```
Pacman/
├── Main.java          # Entry point
├── game.java           # Game — main game loop, input handling, collision & win/lose logic
├── Board.java          # Maze grid, dots/pellets, rendering
├── Position.java        # Simple (x, y) coordinate class
├── Character.java       # Abstract base class for Pacman and Ghosts
├── Pacman.java          # Player character: movement, score, lives
├── Ghost.java           # Abstract base class for all ghosts (mode/frightened logic)
├── Blinky.java          # Red ghost — direct chase AI
├── pinky.java           # Pink ghost — ambush AI (4 tiles ahead)
├── inky.java            # Cyan ghost — random-target AI
└── Clyde.java           # Orange ghost — distance-based chase/flee AI
```

> **Note:** A few files (`game.java`, `pinky.java`, `inky.java`) use lowercase filenames while their public classes are capitalized (`Game`, `Pinky`, `Inky`). This compiles fine on case-insensitive filesystems (Windows/Mac default) but can cause issues on case-sensitive ones (Linux). Consider renaming these to `Game.java`, `Pinky.java`, and `Inky.java` for consistency.

## Getting Started

### Prerequisites

- Java JDK 8+

### Compile and Run

```bash
git clone https://github.com/<your-username>/Pacman.git
cd Pacman
javac *.java
java Main
```

For best results, run in a terminal that supports ANSI colors and Unicode (most modern terminals do).

## How the Ghost AI Works

Each ghost calculates a **target tile** each turn based on its mode:

- **SCATTER** — heads toward a fixed corner of the maze
- **CHASE** — heads toward a target determined by its unique personality (see Features above)
- **FRIGHTENED** — moves to a random adjacent tile (triggered by eating a power pellet)

Ghosts then take one step per turn toward their target, moving along whichever axis (X or Y) has the greater distance to close.

## License

Add a license of your choice (e.g. MIT) — see [choosealicense.com](https://choosealicense.com/) for guidance.
