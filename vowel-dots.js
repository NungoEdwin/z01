let vowels=/[aeiou]/gi;
function vowelDots(str){
    let st='';
    if(str=='')return '';
    let matches=str.match(vowels);
    if(matches==null)return str;
   for(let match of matches){
    str= str.replaceAll(match,match+".")
   }
   return str.replaceAll(/[.]+/g,".")
   //return str
}
//console.log(vowelDots("absidraau"))
console.log(vowelDots('gtyl'))