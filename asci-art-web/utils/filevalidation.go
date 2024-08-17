package utils

import (
	"fmt"
	"os"
)

// FileValidation checks incase of any modification done on standard text
// Incase of modification the byte size will be not equal to the expected size, triggering a re-download
func FileValidation(fileName string, fileSize int64) error {
	f, err1 := os.Stat(fileName)
	if os.IsNotExist(err1) || f.Size() != fileSize {
		stdFile, err := os.Create(fileName)
		if err != nil {
			return err
		}
		defer stdFile.Close()
		fileInfo, err := os.Stat(fileName)
		if err != nil {
			return err
		}
		if fileInfo.Size() != fileSize {
			err := DownloadStd(fileName, stdFile)
			if err != nil {
				fmt.Println(err.Error())
				return err
			}
		}

	}

	return nil
}
