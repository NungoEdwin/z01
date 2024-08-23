package printingasciipackage_test

import (
	"fmt"
	"os"
	"reflect"
	"strings"
	"testing"
)

func TestPrintingAscii(t *testing.T) {
	fileInput := []string{
		" _              _   _          ",
		"| |            | | | |         ",
		"| |__     ___  | | | |   ___   ",
		"|  _ \\   / _ \\ | | | |  / _ \\  ",
		"| | | | |  __/ | | | | | (_) | ",
		"|_| |_|  \\___| |_| |_|  \\___/  ",
		"                               ",
		"                               ",
	}

	fileInfo, err := os.ReadFile("banner.txt")
	if err != nil {
		fmt.Fprintln(os.Stderr, "Error reading file input/file not found")
	}
	fileOutput := strings.Split(string(fileInfo), "\n")

	if !reflect.DeepEqual(fileOutput[0], fileInput[0]) {
		fmt.Println("Test  line 1 failed")
		t.Errorf("got\n %v, want %v", fileOutput[0], fileInput[0])
		return
	} else {
		fmt.Println(" line 1 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[1], fileInput[1]) {
		fmt.Println("Test line 2 failed")
		t.Errorf("got\n %v, want %v", fileOutput[1], fileInput[1])
		return
	} else {
		fmt.Println(" line 2 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[2], fileInput[2]) {
		fmt.Println("Test line 3 failed")
		t.Errorf("got\n %v, want %v", fileOutput[2], fileInput[2])
		return
	} else {
		fmt.Println(" line 3 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[3], fileInput[3]) {
		fmt.Println("Test line 4 failed")
		t.Errorf("got\n %v, want %v", fileOutput[3], fileInput[3])
		return
	} else {
		fmt.Println(" line 4 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[4], fileInput[4]) {
		fmt.Println("Test line 5 failed")
		t.Errorf("got\n %v, want %v", fileOutput[4], fileInput[4])
		return
	} else {
		fmt.Println(" line 5 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[5], fileInput[5]) {
		fmt.Println("Test line 6 failed")
		t.Errorf("got\n %v, want %v", fileOutput[5], fileInput[5])
		return
	} else {
		fmt.Println(" line 6 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[6], fileInput[6]) {
		fmt.Println("Test line 7 failed")
		t.Errorf("got\n %v, want %v", fileOutput[6], fileInput[6])
		return
	} else {
		fmt.Println(" line 7 Test passed successfully")
	}
	if !reflect.DeepEqual(fileOutput[7], fileInput[7]) {
		fmt.Println("Test  line 8 failed")
		t.Errorf("got\n %v, want %v", fileOutput[7], fileInput[7])
		return
	} else {
		fmt.Println(" line 8 Test passed successfully")
	}
}
