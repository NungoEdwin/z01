package functions

import (
	"flag"
	"fmt"
	"os"
	//"fmt"
)

func Color() string {
	colors := map[string]string{
		"red":               "\033[31m",
		"#FF0000":           "\033[31m",
		"rgb(255, 0, 0)":    "\033[31m",
		"hsl(0, 100%, 50%)": "\033[31m",

		"green":               "\033[32m",
		"#00FF00":             "\033[32m",
		"rgb(0, 255, 0)":      "\033[32m",
		"hsl(120, 100%, 50%)": "\033[32m",

		"yellow":             "\033[33m",
		"#FFFF00":            "\033[33m",
		"rgb(255, 255, 0)":   "\033[33m",
		"hsl(60, 100%, 50%)": "\033[33m",

		"blue":                "\033[34m",
		"#0000FF":             "\033[34m",
		"rgb(0, 0, 255)":      "\033[34m",
		"hsl(240, 100%, 50%)": "\033[34m",

		"magenta":             "\033[35m",
		"#FF00FF":             "\033[35m",
		"rgb(255, 0, 255)":    "\033[35m",
		"hsl(300, 100%, 50%)": "\033[35m",

		"cyan":                "\033[36m",
		"#00FFFF":             "\033[36m",
		"rgb(0, 255, 255)":    "\033[36m",
		"hsl(180, 100%, 50%)": "\033[36m",

		"orange":             "\033[38;5;208m",
		"#FFA500":            "\033[38;5;208m",
		"rgb(255, 165, 0)":   "\033[38;5;208m",
		"hsl(39, 100%, 50%)": "\033[38;5;208m",

		"white":              "\033[97m",
		"#FFFFFF":            "\033[97m",
		"rgb(255, 255, 255)": "\033[97m",
		"hsl(0, 0%, 100%)":   "\033[97m",

		"pink":                "\033[38;5;213m",
		"#FFB6C1":             "\033[38;5;213m",
		"rgb(255, 182, 193)":  "\033[38;5;213m",
		"hsl(351, 100%, 86%)": "\033[38;5;213m",

		"purple":              "\033[38;5;129m",
		"#800080":             "\033[38;5;129m",
		"rgb(128, 0, 128)":    "\033[38;5;129m",
		"hsl(300, 100%, 25%)": "\033[38;5;129m",

		"indigo":              "\033[38;5;54m",
		"#4B0082":             "\033[38;5;54m",
		"rgb(75, 0, 130)":     "\033[38;5;54m",
		"hsl(275, 100%, 25%)": "\033[38;5;54m",

		"brown":             "\033[38;5;130m",
		"#A52A2A":           "\033[38;5;130m",
		"rgb(139, 69, 19)":  "\033[38;5;130m",
		"hsl(30, 76%, 41%)": "\033[38;5;130m",

		"violet":             "\033[38;5;93m",
		"#8A2BE2":            "\033[38;5;93m",
		"rgb(138, 43, 226)":  "\033[38;5;93m",
		"hsl(271, 76%, 53%)": "\033[38;5;93m",

		"black":          "\033[30m",
		"#000000":        "\033[30m",
		"rgb(0,0,0)":     "\033[30m",
		"hsl(0, 0%, 0%)": "\033[30m",

		"reset": "\033[0m",
	}

	result := "'"
	flag.Visit(func(f *flag.Flag) {
		if f.Name == "color" {
			result = string(f.Value.String())
		}
	})

	colorcode, ok := (colors[result])

	if !ok {
		fmt.Println("Color not found")
		os.Exit(0)
	}

	return colorcode
}