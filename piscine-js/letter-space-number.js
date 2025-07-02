function letterSpaceNumber(str){
    let arr=[]
    arr=str.match(/[a-zA-Z]\s{1}\d{1}\b/g);
    if(arr==null){
        return []
    }
   return arr
}
console.log(letterSpaceNumber('I like 7up.'))