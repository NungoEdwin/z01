function pick(obj,strarr){
const keys=Object.keys(obj)
var str=false
if(typeof strarr =='string'){
    str=true
}
//alternatively
const Pick = Array.isArray(strarr) ? strarr : [strarr];
let obj1={}
// keys.map(()=>{
//     if(str&&keys.includes(strarr)){
//         obj1[strarr]=obj[strarr]
//     }else if(!str){
//         for(let s of strarr){
//             if(keys.includes(s)){
//                 obj1[s]=obj[s]
//             }
//         }
//     }
// })
for(let key in obj){
if(obj.hasOwnProperty(key)&&Pick.includes(key)){
    obj1[key]=obj[key]
}
}
return obj1

};
function omit(obj,strarr){
    const keys=Object.keys(obj)
    var str=false
    if(typeof strarr =='string'){
        str=true
    }
    //alternatively
    const Pick = Array.isArray(strarr) ? strarr : [strarr];
    let obj1={}//interesting found out using Object.create({}) was causing the notorious reference not equal error in the intra testcases
    
   /* keys.map(key=>{
        if(str&&key!=strarr){
            obj1[key]=obj[key]
        }else if(!str){
            // for(let s of strarr){
            //     if(Object.is(key,s)){
            //         console.log('2')
            //         //obj1[key]=obj[key]
                    
            //     }else{
            //         console.log('1')
            //        obj1[key]=obj[key]
            //     }
            // }
            if(!(strarr.includes(key))){
                obj1[key]=obj[key]
            }

            
        }
    })*/
        for(let key in obj){
            if(obj.hasOwnProperty(key)&&!Pick.includes(key)){
                obj1[key]=obj[key]
            }
            }

    return obj1
    
};
const obj={
    firstName: "John",
    lastName: "Doe",
    age: 50,
    eyeColor: "blue"
  };
console.log(omit(obj,['firstName','lastName']))
