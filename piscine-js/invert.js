function invert(obj){
    const keys=Object.keys(obj);
const values=Object.values(obj);
   // console.log(values,keys.slice(1,keys.length))
    let obj1=Object.create({});
    // keys.map(n=>{

    //     let x =obj[n]
    //    obj1[x]=n
    // })
    // let i=0
    // for(let int of keys){
    //  // obj1[values]=key
    //  //let {key,value}={...int}
    //  obj1[values[i]]=int
    //  i++

    // }
    obj1=Object.fromEntries(Object.entries(obj).map(([key,val])=>[val,key]))
    return obj1
    
}
const obj={
    firstName: "John",
    lastName: "Doe",
    age: 50,
    eyeColor: "blue"
  };
console.log(invert(obj))