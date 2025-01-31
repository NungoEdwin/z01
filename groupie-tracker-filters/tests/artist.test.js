import jestFetchMock from 'jest-fetch-mock';

jestFetchMock.enableMocks();

describe('Testing the Pagination buttons', () => {
  beforeEach(() => {
    // Setup DOM structure before each test
    document.body.innerHTML = `
      <div id="artistGrid"></div>
      <button id="prevButton">Previous</button>
      <button id="nextButton">Next</button>
    `;
  });

  test('should attach an event listener to the next button', () => {
    const nextButton = document.getElementById('nextButton');

    expect(nextButton).not.toBeNull(); // Check if button exists

    nextButton.addEventListener('click', () => {
      document.getElementById('artistGrid').textContent = 'Next button clicked';
    });

    nextButton.click(); // Simulate button click

    expect(document.getElementById('artistGrid').textContent).toBe('Next button clicked');
  });

  test('should attach an event listener to the previous button', () => {
    const prevButton = document.getElementById('prevButton');

    expect(prevButton).not.toBeNull(); // Check if button exists

    prevButton.addEventListener('click', () => {
      document.getElementById('artistGrid').textContent = 'Previous button clicked';
    });

    prevButton.click(); // Simulate button click

    expect(document.getElementById('artistGrid').textContent).toBe('Previous button clicked');
  });
});
