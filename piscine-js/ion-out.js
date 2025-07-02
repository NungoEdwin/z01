function ionOut(str){
    let match=[];
    let match1=[]
    match=str.match(/\b[\w]*(?<=t)ion[\w]*\b/g);
    if(match==null){
        return []
    };
   match1= match.map((n)=>n.replace("ion",''))
   return match1
};
console.log(ionOut('t gionrage is t gion place tionnb iontk a tibulation tzzion'))