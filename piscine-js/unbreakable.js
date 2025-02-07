function split(str,sep='',limit=500){
    let arrstr=[];let word='';let count =0;
    if (!str.includes(sep)){
         arrstr.push(str);
         return arrstr
    }
    if(sep.length>1){
        str=str.replaceAll(sep,'✋')
        sep='✋'

    }
    for(let char of str){
        if (char==sep||(sep==''&&word.length>0)){
            if (count==limit){
                return arrstr
            }
            arrstr.push(word);
            count++
            word=''
            if(sep!=''){

                continue;
            }
        }
        word+=char;
        
    };
    if (word.length>0||str[str.length-1]==sep){
        if (count==limit){
            return arrstr
        }
       arrstr.push(word)
       count++;
       word=''
    }
    return arrstr;
};
function join(arr,sep=','){
    let joinz='';
    let count=0;
for(let cont of arr){
if (count!=arr.length-1&&!(count==0&&arr.length==1)){
    joinz+=cont+sep;

}else{
    joinz+=cont;
}
count++
    }
    return joinz;
};
// console.log(join(['adabra','bfrsd'],';'));
 //console.log(split('ggg - ddd - b', ' - '));
 console.log(split('', 'Riad'))