function isPositive(n){
    if (n >0){
        return true
    }else if (n<0){
       return false
    }
};
function abs(n){
    if ((!isPositive(n))&&(n!=0)){
        return n * -1
    }
    return n

}
//console.log(!isPositive(0))
//console.log(abs(5))
//console.log(Math.abs(-23))
