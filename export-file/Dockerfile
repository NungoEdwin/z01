# Build from 
FROM golang:1.22.1-alpine As builder

# Command to build bash
RUN apk add --no-cache bash

# Set the directory that the container starts in
WORKDIR /ascii-art-web-dockerize

# Copy everything in the current directory
COPY . .

# Command to build the project
RUN go build -o ascii

# Step 2

FROM alpine:latest

WORKDIR /ascii-art-web-dockerize

COPY --from=builder /ascii-art-web-dockerize/ascii .

COPY --from=builder /ascii-art-web-dockerize/templates /ascii-art-web-dockerize/templates 
COPY --from=builder /ascii-art-web-dockerize/static /ascii-art-web-dockerize/static
COPY --from=builder /ascii-art-web-dockerize/banner /ascii-art-web-dockerize/banner

EXPOSE 5000

CMD ["/ascii-art-web-dockerize/ascii"]

LABEL \
    maintainer="Benard Opiyo <benardopiyo13@gmail.com.com>" \
    maintainer="Fena Onditi <[Fena] (https://github.com/konditi1)>"\
    version="1.0" \
    description="A simple ASCII art web application which provides a web interface for generating ASCII art using different banner styles." \
    repository="https://github.com/benardopiyo/ascii-art-web" \
    license="MIT"
