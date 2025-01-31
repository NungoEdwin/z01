package asciiart

import (
	"crypto/sha256"
	"errors"
	"fmt"
	"os"
	"strings"
)

func GetHash(filedata []byte) string {
	hash := sha256.Sum256(filedata)
	return fmt.Sprintf("%x", hash)
}

func ReadFile(banner string) ([]string, error) {
	var fileName string
	switch banner {
	case "shadow":
		fileName = "./banner/" + banner + ".txt"
	case "standard":
		fileName = "./banner/" + banner + ".txt"
	case "thinkertoy":
		fileName = "./banner/" + banner + ".txt"
	default:
		return nil, errors.New("error: unsupported banner type")
	}

	fileContent, err := os.ReadFile(fileName)
	if err != nil {
		return nil, err
	}
	// validating the file
	generateHash := GetHash(fileContent)

	validHash := map[string]string{
		"shadow":     "26b94d0b134b77e9fd23e0360bfd81740f80fb7f6541d1d8c5d85e73ee550f73",
		"standard":   "ff376634f909e02b69976e75d68580f30f37b5e524c4e4c8fe1e566e60064905",
		"thinkertoy": "c081c44a7f93acd7bf10a611256bfe84be4e63a6b9aaf9e947b51a2c7937fe52",
	}

	if bannerHash, ok := validHash[banner]; ok {
		if bannerHash != generateHash {
			return nil, errors.New("error: modified file")
		}
	} else {
		return nil, errors.New("no such file")
	}

	// Defining a empty file error
	if len(fileContent) == 0 {
		return nil, errors.New("empty file error")
	}

	strArray := strings.Split(string(fileContent), "\n")
	return strArray, nil
}
