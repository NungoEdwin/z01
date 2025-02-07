function slice(arrstr,start,end=arrstr.length){
    let arrstr1=[];
    let i;
    if (start<0){
        start=arrstr.length+start
    }
    if (end<0){
        end=arrstr.length+end
    }
    for(i=start;i<end;i++){
        if (typeof(arrstr)=='string'){
            arrstr1+=arrstr[i]
            
        }else{
            arrstr1.push(arrstr[i])
        }
    }
    return arrstr1;
};
console.log(slice(['arsgthk','baba','a','g','k'],-2,-1))