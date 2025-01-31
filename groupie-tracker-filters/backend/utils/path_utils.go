package utils

import (
	"os"
	"path/filepath"
)

func FindProjectRoot(dir, dir2 string) string {
	cwd, _ := os.Getwd()
	basedir := findRoot(cwd)
	return filepath.Join(basedir, dir, dir2)
}

func findRoot(cwd string) string {
	for {
		if _, err := os.Stat(filepath.Join(cwd, "go.mod")); os.IsNotExist(err) {
			parentDir := filepath.Dir(cwd)
			if parentDir == cwd {
				return cwd
			}
			cwd = parentDir
		} else {
			return cwd
		}
	}
}
