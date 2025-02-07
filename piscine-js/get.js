function get(obj,path){
    let arr1=path.split('.')
    let count=0;
    var bas;
    for(let cont of arr1){
if(count==0){
    
    bas=obj[cont]
    if(typeof bas=='undefined'){
        break;
    }

}else{
    if(typeof bas=='undefined'){
        break;
    }
    bas=bas[cont]
}
count++
    }
    return bas
    //obj['nested']['key'];
};
console.log(get({ nested: { key: 'value' } }, 'key.key'))