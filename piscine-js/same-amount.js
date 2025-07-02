function sameAmount(str,reg1,reg2){
    //actually adding the global variable passes it in the regex
    reg1=new RegExp(reg1,'g');
    reg2=new RegExp(reg2,'g')
let match1=str.match(reg1);
let match2=str.match(reg2);
if(match1==null&&match2==null){
    return true
}else if(match1==null||match2==null){
    return false
}

   if(match1.length==match2.length){
      return true
    }
    return false
}
console.log(sameAmount("absq ght",/q /, /qqqqqqq/))