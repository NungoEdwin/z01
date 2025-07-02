const getAcceleration = (n) => {
    if (n.f !== undefined && n.m !== undefined) {
        return Number(n.f/n.m);
    }

    if (n.Δv !== undefined && n.Δt !== undefined) {
        return Number(n.Δv/n.Δt);
    }

    if (n.d !== undefined && n.t !== undefined) {
        return Number((2*n.d)/(n.t**2));
    }
    return "impossible"
}