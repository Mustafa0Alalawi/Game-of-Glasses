# Game of Glasses (Viinaharava)

A Scala-based twist on the classic Minesweeper game. In this version, players attempt to "drink" all the **water glasses** on the grid while avoiding the **booze**. Each move reveals danger levels, helping players navigate safely across the board.

## Game Concept

The grid-based board contains two types of glasses:
- **Water** (safe)
- **Booze** (dangerous)

Each square reveals a number indicating how many neighboring squares contain booze. Your goal is to **drink all the water glasses** without accidentally drinking booze.

## Key Classes

### `GameBoard`
Handles the grid logic, placing booze at random and tracking:
- Remaining glasses
- Game status (ongoing/lost/won)
- Neighbor analysis for danger levels

### `Glass`
Represents each grid cell. Tracks:
- Type: water or booze
- State: full or empty
- Danger level (number of neighboring booze glasses)

## GUI

The GUI is provided in the package `o1.viinaharava.gui`. You don’t need to understand its implementation — simply run the `Viinaharava` object to launch the app.

## How to Run

### Prerequisites
- [Scala](https://www.scala-lang.org/download/)
- [SBT (Scala Build Tool)](https://www.scala-sbt.org/)

### Steps

```bash
git clone https://github.com/Mustafa0Alalawi/Game-of-Glasses.git
cd Game-of-Glasses
sbt run
```

## Demo

<img width="806" alt="Screenshot 2025-05-13 at 18 30 46" src="https://github.com/user-attachments/assets/cac10f16-4135-4035-a5b9-e965b8ea1a82" />
