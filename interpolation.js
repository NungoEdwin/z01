function interpolation({step = 0, start = 0, end = 0,
    callback = () => {},
    duration = 0,
} = {}) {
    const delta = (end - start) / step;
    let current = start;
    let iteration = 0;

    const intervalDuration = duration / step;

    const timer = setInterval(() => {
        if (iteration < step) {
            callback([current, intervalDuration * (iteration + 1)]);
            current += delta;
            iteration++;
        } else {
            clearInterval(timer);
        }
    }, intervalDuration);
}

// const step = 5;
// const start = 0;
// const end = 1;
// const duration = 1000;

// interpolation({
//     step,
//     start,
//     end,
//     duration,
//     callback: (point) => console.log('Interpolated point:', point)
// });