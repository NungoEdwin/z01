package main

import (
	"fmt"
	//	"io"
	"os"
	"strconv"
	"strings"
)

func main() {
	// for i := 0; i < len(os.Args); i++ {
	// 	fmt.Println(len(os.Args))
	// }
	// data,err:=os.Args
	// if (err!=nil){
	// 		panic(err)
	// }
	// fmt.Print(string(data))

	// file, er := os.OpenFile(os.Args[1],os.O_APPEND|os.O_WRONLY,0644)
	// if er!=nil{ panic(er)}
	// defer file.Close()
	// _,err2 :=file.Write([]byte("dadat\n"))
	// if (err2!=nil){panic("failed to append")}
	// data, _ :=os.ReadFile("../test")
	// fmt.Print(string(data))
	// file.Sync()
	data, err := os.ReadFile(os.Args[1:][0])
	if err != nil {
		fmt.Println("Error reading File")
	}
	content := string(data)

	n := hex(content)
	n = bin(n)
	n = up(n)
	n = low(n)
	n = cap(n)
	n = cappos(n)
	n = vowels(n)
	n = punc1(n)
	n = punc2(n)

	er := os.WriteFile(os.Args[1:][1], []byte(n), 0o644)
	if er != nil {
		fmt.Println("Error writing the File")
	}
}

func hex(n string) string {
	line := ""
	slicehex := strings.Fields(n)
	// var strn[] string

	for i, word := range slicehex {
		if word == "(hex)" {
			w, _ := strconv.ParseInt(slicehex[i-1], 16, 10)
			strf := strconv.Itoa(int(w))
			slicehex[i-1] = strf
			slicehex = append(slicehex[:i], slicehex[i+1:]...)
		}
	}
	line = strings.Join(slicehex, " ")
	return line
}

// // line1 := "It has been 10 (bin) years"
func bin(n string) string {
	slicebin := strings.Fields(n)
	// var strn[] string

	for i, word := range slicebin {
		if word == "(bin)" {
			w, _ := strconv.ParseInt(slicebin[i-1], 2, 10)
			strf := strconv.Itoa(int(w))
			slicebin[i-1] = strf
			slicebin = append(slicebin[:i], slicebin[i+1:]...)
		}
	}
	// fmt.Println(slicebin)
	line1 := strings.Join(slicebin, " ")
	return line1
}

// // line2 := "Ready, set, go (up) !"
func up(n string) string {
	sliceup := strings.Fields(n)
	for i, word := range sliceup {
		if word == "(up)" {
			sliceup[i-1] = strings.ToUpper(sliceup[i-1])
			sliceup = append(sliceup[:i], sliceup[i+1:]...)
		}
	}
	line2 := strings.Join(sliceup, " ")
	return line2
}

// // line3 := "I should stop SHOUTING (low)"
func low(n string) string {
	slicelow := strings.Fields(n)
	for i, word := range slicelow {
		if word == "(low)" {
			slicelow[i-1] = strings.ToLower(slicelow[i-1])
			slicelow = append(slicelow[:i], slicelow[i+1:]...)
		}
	}
	// fmt.Println(slicelow)
	line3 := strings.Join(slicelow, " ")
	return line3
}

// // line4 := "Welcome to (cap) the Brooklyn bridge (cap)"
func cap(n string) string {
	slicecap := strings.Fields(n)
	for i, word := range slicecap {
		if word == "(cap)" && i != 0 {
			strn := ""
			for l := 0; l < len(slicecap[i-1]); l++ {
				b := slicecap[i-1][l]
				if l == 0 {
					b -= 32
				}
				strn += string(b)

			}
			slicecap[i-1] = strn
		}

		if word == "(cap)" && i == len(slicecap)-1 {
			slicecap = slicecap[:i]
		} else if word == "(cap)" && i < len(slicecap)-1 && i != 0 {
			slicecap = append(slicecap[:i], slicecap[i+1:]...)
		}

	}
	line4 := strings.Join(slicecap, " ")
	return line4
}

// // line5 := "Welcome to the Brooklyn bridge (cap, 3)"
func cappos(n string) string {
	slicepos := strings.Fields(n)
	for i, word := range slicepos {
		// var pos int
		if word == "(up," {
			a := slicepos[i+1][:len(slicepos[i+1])-1]
			pos, _ := strconv.Atoi(string(a))
			for l := 1; l < pos+1; l++ {
				slicepos[i-l] = strings.ToUpper(slicepos[i-l])
			}
			if i != len(slicepos)-1 {
				slicepos = append(slicepos[:i], slicepos[i+2:]...)
			} else {
				slicepos = slicepos[:i]
			}

		}
		if word == "(low," {
			a := slicepos[i+1][:len(slicepos[i+1])-1]
			pos, _ := strconv.Atoi(string(a))
			for l := 1; l < pos+1; l++ {
				slicepos[i-l] = strings.ToLower(slicepos[i-l])
			}
			if i != len(slicepos)-1 {
				slicepos = append(slicepos[:i], slicepos[i+2:]...)
			} else {
				slicepos = slicepos[:i]
			}

		}
		if word == "(cap," {
			a := slicepos[i+1][:len(slicepos[i+1])-1]
			pos, _ := strconv.Atoi(string(a))
			for l := 1; l < pos+1; l++ {
				// slicepos[i-l][0] = -32
				strn := ""
				for n := 0; n < len(slicepos[i-l]); n++ {
					b := slicepos[i-l][n]
					if n == 0 {
						b -= 32
					}
					strn += string(b)
				}
				slicepos[i-l] = strn

			}
			if i != len(slicepos)-1 {
				slicepos = append(slicepos[:i], slicepos[i+2:]...)
			} else {
				slicepos = slicepos[:i]
			}

		}
	}
	line5 := strings.Join(slicepos, " ")
	return line5
}

// // vowels
func vowels(n string) string {
	vowels := "aeiouAEIOUHh"
	// line7 := "There it was. A amazing rock!"
	slicevow := strings.Fields(n)
	for i := range slicevow {
		for _, v := range vowels {
			if (slicevow[i] == "a" || slicevow[i] == "A") && slicevow[i+1][0] == byte(v) {
				if slicevow[i] == "a" {
					slicevow[i] = "an"
				} else {
					slicevow[i] = "An"
				}
			}
		}
	}
	fmt.Println(slicevow)
	line7 := strings.Join(slicevow, " ")
	return line7
}

// // punctuation space
func punc1(n string) string {
	// line6 := "I was sitting over there ,and then BAMM !!"
	sentence := strings.ReplaceAll(n, " !", "!")
	sentence = strings.ReplaceAll(sentence, " ,", ", ")
	sentence = strings.ReplaceAll(sentence, " .", ".")
	sentence = strings.ReplaceAll(sentence, " ?", "?")
	sentence = strings.ReplaceAll(sentence, " ;", ";")
	sentence = strings.ReplaceAll(sentence, " :", ":")

	return sentence
}

// // punctuate2
func punc2(n string) string {
	// line8:= "I am exactly how they describe me: ' awesome '"
	// line9 := "As Elton John said: ' I am the most well-known homosexual in the world '"
	// harold wilson (cap, 2) : ' I am a optimist ,but a optimist who carries a raincoat . '
	line := strings.ReplaceAll(n, "' ", " '")
	line = strings.ReplaceAll(line, " '", "'")
	//line = strings.TrimSpace(line)

	return line
}
