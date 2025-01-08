function words(n){
return n.split(" ");
};
function sentence(array){
 return array.join(" ");
};
function yell(n){
return n.toUpperCase();
};
function whisper(n){
return '*'+n.toLowerCase()+'*';
};
function capitalize(n){
return n.charAt(0).toUpperCase()+n.slice(1).toLowerCase();
};
// console.log(words("net fr net"));
// console.log(sentence(['ara','hytiu','jkli']));
 console.log(capitalize('STR'))