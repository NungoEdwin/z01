// Create twinkling stars
function createStars() {
  const numStars = 50;
  const container = document.body;

  for (let i = 0; i < numStars; i++) {
    const star = document.createElement("div");
    star.classList.add("star");
    star.style.width = `${Math.random() * 3 + 1}px`;
    star.style.height = star.style.width;
    star.style.left = `${Math.random() * 100}vw`;
    star.style.top = `${Math.random() * 100}vh`;
    star.style.animationDelay = `${Math.random() * 2}s`;
    container.appendChild(star);
  }
}

createStars();

// Handle search
const searchBtn = document.querySelector(".search-btn");
const searchInput = document.querySelector(".search-input");

searchBtn.addEventListener("click", () => {
  const query = searchInput.value.trim();
  if (query) {
    window.location.href = `/search?q=${encodeURIComponent(query)}`;
  }
});

searchInput.addEventListener("keypress", (e) => {
  if (e.key === "Enter") {
    searchBtn.click();
  }
});
