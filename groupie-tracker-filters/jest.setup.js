// Inside your test setup file or at the beginning of your test file:
beforeEach(() => {
    document.body.innerHTML = `
      <div id="artistGrid"></div>
      <div id="loading"></div>
      <div id="skeletonContainer"></div>
      <input id="search" />
      <select id="filter"></select>
      <div id="overlay"></div>
      <div id="artistModal"></div>
      <div id="modalArtistContent"></div>
      <button id="closeModal"></button>
      <button id="prevPage"></button>
      <button id="nextPage"></button>
      <span id="currentPage"></span>
    `;
  });
  