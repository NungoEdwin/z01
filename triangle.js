function triangle(str, size) {
    let art1='';
    for (let i = 1; i <= size; i++) {
        let n = 0;
        let art = '';
        while (n < i) {
            art += str
            n++
        }
        if (i != size) {
            art1+=art + '\n'

        } else {
            art1+=art
        }
    }
    return art1
    
}
console.log(triangle('*', 5))