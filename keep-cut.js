function cutFirst(str){
    let i;let str1='';
    for(i=0;i<str.length;i++){
    if (i!=0&&i!=1){
        str1+=str[i]
    }
}
str=str1;
return str
};
function cutLast(str){
    let i,str1='';
    for(i=0;i<str.length;i++){
    if (i!=str.length-1&&i!=str.length-2){
        str1+=str[i]
    }
}
str=str1;
return str
};
function cutFirstLast(str){
    let i,str1='';
    for(i=0;i<str.length;i++){
    if (i!=0&&i!=1&&i!=str.length-1&&i!=str.length-2){
        str1+=str[i]
    }
}
str=str1;
return str
};
function keepFirst(str){
    let i,str1='';
    for(i=0;i<str.length;i++){
    if (i==0||i==1){
        str1+=str[i]
    }
}
str=str1;
return str
};
function keepLast(str){
    let i,str1='';
    for(i=0;i<str.length;i++){
    if (i==str.length-1||i==str.length-2){
        str1+=str[i]
    }
}
str=str1;
return str
};
function keepFirstLast(str){
    let i,str1='';
    for(i=0;i<str.length;i++){
    if (i==0||i==1||i==str.length-1||i==str.length-2){
        str1+=str[i]
    }
}
str=str1;
return str
};
//console.log(cutFirstLast('abara'))