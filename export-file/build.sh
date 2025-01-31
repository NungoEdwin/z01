#!/bin/bash

# Build the Docker image
docker build -t ascii-art-web-dockerize .

# Run the Docker container
docker run -p 5000:5000 ascii-art-web-dockerize
