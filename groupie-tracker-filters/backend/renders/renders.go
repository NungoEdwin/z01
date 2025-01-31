package renders

import (
	"fmt"
	"net/http"
	"text/template"
)

func RenderServerErrorTemplate(w http.ResponseWriter, statusCode int, errMsg string) {
	const errorTemplateHTML = `
	<html><head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>404 - Page Not Found | Groupie Trackers</title>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap');

  :root {
    --bg-color: #0a2a4a;
    --text-color: #f0f0f0;
    --accent-color: #ffd700;
    --button-bg: #1c5d99;
    --button-hover: #3a7ca5;
  }

  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  body {
    font-family: 'Roboto', sans-serif;
    background-color: var(--bg-color);
    color: var(--text-color);
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    height: 100vh;
    overflow: hidden;
  }

  .container {
    text-align: center;
    padding: 2rem;
    max-width: 600px;
  }

  h1 {
    font-family: 'Roboto', sans-serif;
    font-size: 4rem;
    font-weight: 700;
    color: var(--accent-color);
    margin-bottom: 1rem;
    text-shadow: 0 0 10px rgba(255, 215, 0, 0.5);
  }

  p {
    font-size: 1.2rem;
    margin-bottom: 2rem;
  }

  .btn {
    display: inline-block;
    padding: 0.8rem 1.5rem;
    background-color: var(--button-bg);
    color: var(--text-color);
    text-decoration: none;
    border-radius: 25px;
    font-family: 'Roboto', sans-serif;
    font-weight: 700;
    transition: background-color 0.3s ease, transform 0.3s ease;
  }

  .btn:hover {
    background-color: var(--button-hover);
    transform: translateY(-2px);
  }

  .search-container {
    margin-top: 2rem;
  }

  .search-input {
    padding: 0.5rem;
    width: 200px;
    border: none;
    border-radius: 25px 0 0 25px;
    font-family: 'Roboto', sans-serif;
  }

  .search-btn {
    padding: 0.5rem 1rem;
    background-color: var(--accent-color);
    color: var(--bg-color);
    border: none;
    border-radius: 0 25px 25px 0;
    cursor: pointer;
    font-family: 'Roboto', sans-serif;
    font-weight: 700;
  }

  .error-illustration {
    margin-bottom: 2rem;
  }

  .star {
    position: absolute;
    background-color: var(--accent-color);
    border-radius: 50%;
    animation: twinkle 2s infinite alternate;
  }

  @keyframes twinkle {
    0% { opacity: 0.2; }
    100% { opacity: 1; }
  }

  @media (max-width: 768px) {
    h1 {
      font-size: 3rem;
    }

    p {
      font-size: 1rem;
    }

    .search-input {
      width: 150px;
    }
  }
</style>
</head>
<body>
  <div class="container">
    <svg class="error-illustration" width="200" height="200" viewBox="0 0 200 200" fill="none" xmlns="http://www.w3.org/2000/svg">
      <circle cx="100" cy="100" r="90" stroke="#FFD700" stroke-width="4"/>
      <path d="M60 70L140 130" stroke="#FFD700" stroke-width="4"/>
      <path d="M140 70L60 130" stroke="#FFD700" stroke-width="4"/>
      <circle cx="70" cy="80" r="10" fill="#FFD700"/>
      <circle cx="130" cy="80" r="10" fill="#FFD700"/>
      <path d="M70 130Q100 150 130 130" stroke="#FFD700" stroke-width="4" fill="none"/>
    </svg>

    <h1>404</h1>
    <p>Oops! Looks like this track doesn't exist in our playlist.</p>
    <a href="/" class="btn">Return to Homepage</a>
  </div>

</body>
</html>
	`

	t, err := template.New("error").Parse(errorTemplateHTML)
	if err != nil {
		http.Error(w, fmt.Sprintf("Internal Server Error %v", err), http.StatusInternalServerError)
		return
	}

	data := struct {
		StatusCode int
		Error      string
	}{
		StatusCode: statusCode,
		Error:      errMsg,
	}

	w.Header().Set("Content-Type", "text/html; charset=utf-8")
	w.WriteHeader(statusCode)
	t.Execute(w, data)
}
