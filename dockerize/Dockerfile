FROM golang:1.22.2
WORKDIR /stylize
COPY . .
RUN go build -o art-dockerize

EXPOSE 8080
# CMD ./main
CMD [ "./art-dockerize" ]