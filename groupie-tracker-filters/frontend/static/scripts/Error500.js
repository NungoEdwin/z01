// Create a trail effect
document.addEventListener("mousemove", function (e) {
  const trail = document.createElement("div");
  trail.className = "trail";
  trail.style.left = e.pageX + "px";
  trail.style.top = e.pageY + "px";
  document.body.appendChild(trail);

  setTimeout(() => {
    trail.remove();
  }, 1000);
});

// Add styles for the trail
const style = document.createElement("style");
style.textContent = `
      .trail {
        position: absolute;
        width: 5px;
        height: 5px;
        border-radius: 50%;
        background-color: rgba(255, 215, 0, 0.5);
        pointer-events: none;
        transition: 0.5s;
        animation: fade 1s linear;
      }

      @keyframes fade {
        to {
          opacity: 0;
          transform: scale(2);
        }
      }
    `;
document.head.appendChild(style);
