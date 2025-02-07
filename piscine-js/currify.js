function currify(func) {
    return function currified(...args) {
        return args.length >= func.length
            ? func(...args)
            : currified.bind(null, ...args);
    };
}