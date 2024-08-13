# ASCII-ART-OUTPUT

## Description
   This is a program built on the previously done [ascii-art](https://learn.zone01kisumu.ke/git/enungo/ascii-art.git) project together with other sub projects built upon it as a base including ascii-art-fs,ascii-art-color. It uses a flag named 'output' that allows printing of the resultant ascii art in a text file
    
## Installation
If you don't already have Go installed on your machine, use this [link](https://go.dev/doc/install) to do so.

* **Clone the repo**: 
```bash
git clone https://learn.zone01kisumu.ke/git/enungo/ascii-art-output.git
```
*   **Navigate to the directory**
```bash
cd ascii-art-output
```

## Usage

```bash
Usage: go run . [OPTION] [STRING] [BANNER]

EX: go run . --output=<fileName.txt> something standard
```


## NOTE
1. This program builds on the ASCII Art from  **[ASCII-REPOSITORY](https://learn.zone01kisumu.ke/git/wyonyango/ascii-art.git)**
2. This program supports ASCII Art Color from **[ASCII-ART-COLOR-REPOSITORY](https://learn.zone01kisumu.ke/git/lakoth/ascii-art-color)**
3. This program also supports ASCII-Art-fs from  **[ASCII-ART-FS REPOSITOTY](https://learn.zone01kisumu.ke/git/wyonyango/ascii-art-fs.git)**

## Example 1

```console
go run . --output=banner.txt "hello" standard
cat -e banner.txt
```
- Output
``` 
 _              _   _          $
| |            | | | |         $
| |__     ___  | | | |   ___   $
|  _ \   / _ \ | | | |  / _ \  $
| | | | |  __/ | | | | | (_) | $
|_| |_|  \___| |_| |_|  \___/  $
                               $
                               $
```

## Example 2

```console
go run . --output=banner.txt 'Hello There!' shadow
cat -e banner.txt
```
- Output
``` 
                                                                                         $
_|    _|          _| _|                _|_|_|_|_| _|                                  _| $
_|    _|   _|_|   _| _|   _|_|             _|     _|_|_|     _|_|   _|  _|_|   _|_|   _| $
_|_|_|_| _|_|_|_| _| _| _|    _|           _|     _|    _| _|_|_|_| _|_|     _|_|_|_| _| $
_|    _| _|       _| _| _|    _|           _|     _|    _| _|       _|       _|          $
_|    _|   _|_|_| _| _|   _|_|             _|     _|    _|   _|_|_| _|         _|_|_| _| $
                                                                                         $
                                                                                         $
```

## Dependencies
This program requires Go (Golang) to be installed on your system. You can download and install it from the [official Go website](https://golang.org/dl/).

## Contributing
Contributions to this project are welcome! If you'd like to contribute, please fork the repository and submit a pull request with your changes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.





## Contributors

* [Nungo Edwin](https://github.com/NungoEdwin)

