package asciiart

import (
	"testing"
)

// TestGenerateAsciiArt tests the GenerateAsciiArt function with various cases.
func TestGenerateAsciiArt(t *testing.T) {
	// Mock ASCII characters slice with simplified example characters.
	// Each character should have 8 lines for testing purposes.
	mockCharacters := []string{
		" A ", " B ", " C ", " D ", " E ", " F ", " G ", " H ", " I ",
		" J ", " K ", " L ", " M ", " N ", " O ", " P ", " Q ", " R ",
		" S ", " T ", " U ", " V ", " W ", " X ", " Y ", " Z ", " 0 ",
		// Mocking a few more to simulate full range
		" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ",
	}

	tests := []struct {
		name         string
		text         string
		characters   []string
		want         string
		expectError  bool
		errorMessage string
	}{
		{
			name:         "Empty input",
			text:         "",
			characters:   mockCharacters,
			want:         "\n", // Expecting a single newline for empty input
			expectError:  false,
		},
		{
			name:         "Newline",
			text:         "\n",
			characters:   mockCharacters,
			want:         "\n\n", // Expecting a single newline for empty input
			expectError:  false,
		},
	}

	for _, test := range tests {
		t.Run(test.name, func(t *testing.T) {
			got, err := GenerateAsciiArt(test.text, test.characters)
			if (err != nil) != test.expectError {
				t.Errorf("GenerateAsciiArt() error = %v, expectError %v", err, test.expectError)
			}
			if err != nil && err.Error() != test.errorMessage {
				t.Errorf("GenerateAsciiArt() error message = %v, want %v", err.Error(), test.errorMessage)
			}
			if got != test.want {
				t.Errorf("GenerateAsciiArt() = %v, want %v", got, test.want)
			}
		})
	}
}
