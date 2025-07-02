function indexOf(array, n,i=0) {
  let arraylen=array.length;
    if (i<0){
    arraylen=i+arraylen;
  }
  for (i; i < arraylen; i++) {
    if (array[i] === n) {
      return i;
    };
}
return -1;
}
function lastIndexOf(arrayz, n,i=arrayz.length-1) {
    let arraylen=arrayz.length;
  if (i<0){
    i=Math.max(i+arraylen,0);
  };
  let index=-1;
  //console.log(i,arraylen)
  for (i; i >=0; i--) {
    if (arrayz[i] === n) {
      index = i;
      return index;
    }
  };
  return index;
}
function includes(array, n,i=0) {
    let arraylen=array.length;
    if (i<0){
    arraylen=i+arraylen;
  }
    for (i; i < arraylen; i++) {
      if (array[i] === n) {
        return true
      };
      
    };
    return false;
};
console.log(lastIndexOf(['t', 0, 0, 't'], 't', 2))
