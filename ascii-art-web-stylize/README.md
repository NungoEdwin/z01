## ASCII-ART-WEB-STYLIZE
The ASCII Art Web Stylize is a program written to generate ASCII art from a given string or its specified substring and writes the output to a webpage
The format of the input received as arguments is:
```
Usage: go run .
```
## Key Features

- Supports multiple ASCII art banners for customization
- Handles HTTP requests and responses appropriately 
- Generates ASCII art based on user input and selected banner
- Displays the ASCII art output on the web page
- Follows good coding practices and project structure

## Usage

1. **Clone the repository**:

    ```bash
    git clone https://learn.zone01kisumu.ke/git/gonyango/ascii-art-web-stylize.git
    ```

2. **Navigate to the project directory**:

    ```bash
    cd ascii-art-web-stylize
    ```

3. **Run the server**:

    ```bash
    go run main.go
    ```

4. **Open a web browser and visit** `http://localhost:8080`

5. **Enter text, select a banner, and click "Generate"**

6. **The ASCII art output will be displayed on the page**

## Implementation Details

The server uses the following key components:

- **HTTP handlers** for processing GET and POST requests
- **HTML templates** for rendering the web page
- **ASCII art generation logic** based on user input and selected banner
- **Appropriate HTTP status codes** for handling errors and success cases
## Dependencies
This program requires Go (Golang) to be installed on your system. You can download and install it from the [official Go website](https://golang.org/dl/).

## Contributing
Contributions to this project are welcome! If you'd like to contribute, please fork the repository and submit a pull request with your changes.

## Contributors
<body>
<div style="display: flex !important; justify-content: center !important;">
    <div style="margin: 10px;">
        <img src="images/enungo.png" style="border-radius: 50% !important; width: 150px !important; height: 150px; !important" alt="Granton">
        <p style="text-align: center;"><b><i>Edwin</i></b></p>
    </div>
</div>
<div style="display: flex !important; justify-content: center !important;">
    <div style="margin: 10px;">
        <img src="images/gonyango.png" style="border-radius: 50% !important; width: 150px !important; height: 150px; !important" alt="Granton">
        <p style="text-align: center;"><b><i>Granton</i></b></p>
    </div>
</div>
</body>


## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.



