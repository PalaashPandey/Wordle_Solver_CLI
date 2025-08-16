# Wordle Solver CLI

A simple command-line Wordle solver made in 2023, written in Java. Hope you enjoy!

## Overview

This tool lets you interactively solve Wordle puzzles by pruning a dictionary of five-letter words, guiding your next guesses based on the feedback (green/yellow/gray).

### Files

- `Main.java` – Main application entry point for running the solver.
- `WordleChar.java` – Class that outlines attributes of a Wordle charcter (letter, color).
- `words.txt` – The list of five-letter words the solver uses.

## Prerequisites

- **Java Development Kit (JDK)** installed (version 8+ recommended)
- **Git** (for cloning the repository)

## Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/PalaashPandey/Wordle_Solver_CLI.git
cd Wordle_Solver_CLI
```

### 2. Compile and Run the Solver

```bash
javac Main.java
java Main
```

## Example Usage

Assume your starting word is "sleep" and appears on Wordle as (GYBBB) - G: Green, Y: Yellow, B: Black/Gray
The terminal should look like the following:

```bash
Enter your starting word in here and wordle! (Q to quit): sleep
Enter the 1st position (1-5) from the left of a green character (-1 for none): 1
Enter the 2nd position (1-5) from the left of a green character (-1 for none): -1
Enter the 1st position (1-5) from the left of a yellow character (-1 for none): 2
Enter the 2nd position (1-5) from the left of a yellow character (-1 for none): -1
Enter the 1st position (1-5) from the left of a gray character (-1 for none): 3
Enter the 2nd position (1-5) from the left of a gray character (-1 for none): 4
Enter the 3rd position (1-5) from the left of a gray character (-1 for none): 5
Enter the 4th position (1-5) from the left of a gray character (-1 for none): -1

Remaining candidates: 58
Recommended word to try on Wordle: sable
Get another? (y/n): y
Recommended word to try on Wordle: shoal
Get another? (y/n): n
Enter the 1st position (1-5) from the left of a green character (-1 for none): 
```

## Note: 

- The word list is shuffled so recommended words may differ despite identical inputs
- To continue this example, enter "shoal" on Wordle to get feeback, then enter the position info for "shoal" ONLY, following the structure for "sleep"
- Make sure all 5 letters have been covered and position info is entered correctly
