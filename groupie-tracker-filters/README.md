# Groupie Tracker

Groupie Tracker is a web application that allows music enthusiasts to explore and discover information about various artists and bands. Our platform provides detailed data on artists' creation dates, first album releases, members, and upcoming concert locations.

With an interactive interface, users can search, filter, and visualize artist information, making it easier than ever to stay up-to-date with your favorite musicians and discover new ones.

## Features

- **Client-Server Communication**: The application triggers actions that communicate with the server to receive information.
- **Data Visualization**: Various methods to display artist information and concert details.
- **Event Handling**: The application responds to user actions and displays the relevant information.

## Technology Stack

- **Backend**: Go (Golang)
- **Frontend**: HTML, CSS, JavaScript

## Installation

Make sure to have golang setup inside your machine and follow the bellow steps
1. Clone the repository:

   ```bash
   git clone https://learn.zone01kisumu.ke/git/enungo/groupie-tracker-filters
   cd groupie-tracker
   ```

2. Run the Go server:

   ```bash
   cd cmd && go run .
   ```

3. Open your web browser and navigate to `http://localhost:8080`.

## Testing

Unit tests can be found in the `tests` directory. To run the tests, use:

```bash
go test ./tests
```