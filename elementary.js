function multiply(a,b){
    let sign='';
    if ((a>=0&&b>=0)||(a<0&&b<0)){
    sign=''
    }else{
        sign='-'
    };
    a=Math.abs(a);
    b=Math.abs(b);

    let result=0;
    for (let i=0;i<b;i++){
result+=a;
    }
    return  parseFloat(sign+result);
};
//let count=0;
function divide(a,b){
    let sign='';
    if ((a>=0&&b>=0)||(a<0&&b<0)){
    sign='';
    }else{
        sign='-'
    };
    a=Math.abs(a);
    b=Math.abs(b);

//     if (a < b) {
//         return 0;
//       } else {
//     return 1 + parseFloat(sign+divide(a - b, b));

// };
let count=0;
while (a>=b){
    a=a-b;
    count++
};
return parseFloat(sign+count);



};
function modulo(a,b){
    let sign='';
    if ((a>=0&&b>=0)){
    sign='';
    }else if ((a<0)||(a<0&&b<0)){
        sign='-'
    };
    a=Math.abs(a);
    b=Math.abs(b);
    
while(a>=b){
    a=a-b
};
return parseFloat(sign+a);
    // if (a <b) {
    //     return a;
    //   } else {
    //    return modulo(a - b, b);
    //   };
    

};
console.log(modulo(-123,-22));