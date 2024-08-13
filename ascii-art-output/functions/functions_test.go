package functions

import (
	"flag"
	"os"
	"regexp"
	"strings"
	"testing"
)

func init() {
	color1 := flag.String("color1", "\033[0m", "Provide color code to be used for coloring.")

	*color1 += ""
}

func TestColor(t *testing.T) {
	test_cases := map[string]string{
		"red":               "\033[31m",
		"#FF0000":           "\033[31m",
		"rgb(255, 0, 0)":    "\033[31m",
		"hsl(0, 100%, 50%)": "\033[31m",
	}
	flag.Parse()

	for input, expectedCode := range test_cases {
		// subtest to get the content from the file in the specified filepath
		t.Run(input, func(t *testing.T) {
			err := flag.Set("color1", input)
			if err != nil {
				t.Fatal(err)
			}
			result := Color()

			if result != expectedCode {
				t.Errorf("For input:\n'%s'\nExpected:\n%s\n but got:\n%s", input, expectedCode, result)
			}
		})
	}
}

func TestPrintWords(t *testing.T) {
	input := "a kit has kitten"
	match := "kit"

	banner := "standard.txt"
	file, err := os.ReadFile(banner)
	if err != nil {
		t.Fatalf("Failed to read expected banner file '%s': %v", banner, err)
	}
	asciiFields := strings.Split(string(file), "\n")

	test_cases := map[string]string{
		input: "testCases/expectedOutput1.txt",
	}
	flag.Parse()
	for input, expectedFilePath := range test_cases {
		// subtest to get the content from the file in the specified filepath
		t.Run(input, func(t *testing.T) {
			flag.Set("color1", "red")
			expectedContent, err := os.ReadFile(expectedFilePath)
			if err != nil {
				t.Fatalf("Failed to read expected output file '%s' for input '%s': %v", expectedFilePath, input, err)
			}
			// convert content read from the file to string
			expectedContentStr := string(expectedContent)
			result := PrintWords(input, asciiFields, match)

			output := displayAnsiCodes(result)

			if output != expectedContentStr {
				t.Errorf("For input:\n'%s'\nExpected:\n%s\n but got:\n%s", input, expectedContentStr, output)
			}
		})
	}
}

func displayAnsiCodes(coloredString string) string {
	// Regular expression to match ANSI escape sequences
	ansiEscape := regexp.MustCompile("\x1b\\[[0-9;]*m")

	// Replace escape sequences with their escaped representation
	return ansiEscape.ReplaceAllStringFunc(coloredString, func(match string) string {
		return strings.ReplaceAll(match, "\x1b", "\\x1b")
	})
}
