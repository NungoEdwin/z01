function getURL(dataSet) {
    const pattern = /https?:\/\/\S+/g;
    return dataSet.match(pattern) || [];
}

function greedyQuery(dataSet) {
    const pattern = /https?:\/\/\S+\?\S+(?:&\S+){2,}/g;
    return dataSet.match(pattern) || [];
}

function notSoGreedy(dataSet) {
    const urls = getURL(dataSet);

    return urls.filter(url => {
        const queryStart = url.indexOf('?');
        if (queryStart === -1) return false; // No query parameters, exclude this URL

        const queryString = url.substring(queryStart + 1);
        const params = queryString.split('&');

        // Ensure there are 2 to 3 query parameters
        return params.length >= 2 && params.length <= 3;
    });
}