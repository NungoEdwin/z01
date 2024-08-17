package utils

import (
	"fmt"
	"io"
	"net/http"
	"os"
)

/*
DownloadStd function downloads a file from a specified URL and saves it as "standard.txt" in the local file system.
It handles errors that may occur during the process of creating the file, downloading the content,
checking the HTTP response status, and copying the content to the file.
*/
func DownloadStd(downloadFile string, stdFile *os.File) error {
	var stdDownload *http.Response

	if downloadFile == "groupBanner.txt" {
		stdDownload, _ = http.Get("https://raw.githubusercontent.com/WycliffeAlphus/groupBanner/main/groupBanner.txt")

	} else {
		stdDownload, _ = http.Get("https://learn.zone01kisumu.ke/git/root/public/raw/branch/master/subjects/ascii-art/" + downloadFile)
	}
	defer stdDownload.Body.Close()

	if stdDownload.StatusCode != http.StatusOK {
		return fmt.Errorf("bad status %s", stdDownload.Status)
	}

	_, err2 := io.Copy(stdFile, stdDownload.Body)
	if err2 != nil {
		return err2
	}

	return nil

}
