function sign(n){
    if (n>0){
        return 1;
    }else if (n<0){
        return -1;
    }
    return 0;
};
function sameSign(n,p){
    if (sign(n)==sign(p)){
        return true
    }
    return false
}