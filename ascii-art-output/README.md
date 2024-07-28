# ASCII-ART-OUTPUT

## Description
   This is a program built on the previously done [ascii-art](https://learn.zone01kisumu.ke/git/enungo/ascii-art.git) project. It uses a flag named 'color' that allows coloring of parts of the string passed that matches the substring passed. The program uses Go.

## Installation
If you don't already have Go installed on your machine, use this [link](https://go.dev/doc/install) to do so.

* **Clone the repo**: 
```bash
git clone https://learn.zone01kisumu.ke/git/enungo/ascii-art-color.git
```
*   **Navigate to the directory**
```bash
cd ascii-art
```

## Usage

```bash
Usage: go run . [OPTION] [STRING]

EX: go run . --color=<color> <substring to be colored> "something"
```

*  The substring to be colored can be a single letter or more.
*  If the substring is not specified the whole string gets
   colored.
*  The flag must have exactly the same format as shown above.
   You can use different --color flag notations like: --color=red, --color=#ff0000, --color=rgb(255, 0, 0) or --color=hsl(0, 100%, 50%).

# Example

![alt text](<Screenshot from 2024-07-09 11-16-05.png>)


## File System

1. **functions**

    This subdirectory has files containing functions used in the program.
*   color.go - it returns the color code to be used for coloring.
*   fileChoice.go  - it handles the banner file containing the ascii-art characters.
*   printWords.go  - it does the actual conversion of regular text to ascii-art and applies  the color specified.
*   userInputString.go -   it handles the input passed by the user in the terminal. The function also ensures that the required format of the flag is used.

2.  **main.go**

    This is the entry point of the program. It calls the functions to produce the desired output according as per the user's input.

3.  **ascii-art-color-test.go**

    This is the test file testing the functionality of the functions.
   

## Contributors

* [Nungo Edwin](https://github.com/NungoEdwin)
