function fusion(obj1,obj2){
// let obj={};
// obj={...obj1}
// let values=Object.values(obj2)
// let keys=Object.keys(obj2)
// //let datatype=Array.isArray(values[0])?'array':typeof values[0];

//     keys.map(key=>{
//         let datatype=Array.isArray(obj2[key])?'array':typeof obj2[key];
//         if(datatype=='string'){
//             if(Object.keys(obj).includes(key)){
//                 obj[key]=obj[key]+ ' '+obj2[key]
//             }else{
//                 obj[key]=obj2[key]
//             }
//         }else if(datatype=='array'){
//             if(Object.keys(obj).includes(key)){
//                 obj[key]=[...obj[key],...obj2[key]]
//             }else{
//                 obj[key]=obj2[key]
//             }
//         }else if(datatype=='object'){
//             if(Object.keys(obj).includes(key)){
//                let objkeys=Object.keys(obj[key])
//                let obj2keys=Object.keys(obj2[key])
//                obj2keys.map(key1=>{
//                   if(objkeys.includes(key1)){
//                     console.log(key,key1)
//                    obj[key][key1]=obj[key][key1]+obj2[key][key1]
//                   }

//                })

//                 obj[key]={...obj[key],...obj2[key]}
//             }else{
//                 obj[key]=obj2[key]
//             }
//         }else if(datatype=='number'){
//             if(Object.keys(obj).includes(key)){
//                 obj[key]=obj[key]+obj2[key]
//             }else{
//                 obj[key]=obj2[key]
//             }
//         }



//     })
   

//return obj
let newobj = {};

for (const key in obj1) {
  if (obj1.hasOwnProperty(key)) {
    if (Array.isArray(obj1[key]) && Array.isArray(obj2[key])) {
      newobj[key] = [...obj1[key], ...obj2[key]];
    } else if (typeof obj1[key] === "string" && typeof obj2[key] === "string") {
      newobj[key] = obj1[key] + " " + obj2[key];
    } else if (typeof obj1[key] === "number" && typeof obj2[key] === "number") {
      newobj[key] = obj1[key] + obj2[key];
    } else if (typeof obj1[key] === "object" && typeof obj2[key] === "object" && obj1[key] !== null && obj2[key] !== null) {
      newobj[key] = fusion(obj1[key], obj2[key]);
    } else if (typeof obj2[key] !== "undefined") {
      newobj[key] = obj2[key];
    } else {
      newobj[key] = obj1[key];
    }
  }
}
for (const key in obj2) {
  if (obj2.hasOwnProperty(key) && typeof obj1[key] === "undefined") {
    newobj[key] = obj2[key];
  }
}
return newobj;

}
console.log(fusion({ arr: [1, "2"] }, { arr: [2] })) // -> { arr: [ 1, '2', 2 ] })
console.log(fusion({ str: "salem" }, { str: "alem" })) // -> { str: 'salem alem' })
console.log(fusion({ a: 1, b: { c: "Salem" } }, { a: 10, x: [], b: { c: "alem" } })) // -> { a: 11, x: [], b: { c: 'Salem alem' } })