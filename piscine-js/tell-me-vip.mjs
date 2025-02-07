// import fs from 'fs'
// import process from 'process'


// let small=process.argv
// if(small.length>=3){
// let slic=small.slice(2,small.length)
// slic.map(arg=>{


//     fs.readdir(arg,(_,files)=>{
    
//         let arr=[]
//         let slit=[]
//         files.forEach((file,index)=>{

//             fs.readFile(file,(_,data)=>{

//                 //data.answer=='yes'?slit.push(file):'none';
//                 console.log(data.toString())
//             })
        


//             let filecont=file.replace(/(.json)/g,'').split('_')
//             arr.push(filecont[1].concat(' ',filecont[0]))
//             arr.sort()
//         })
//        console.log(slit.length)
//         arr.map((file1,i)=>{
//             //console.log(`${i+1}. ${file1}`)
        
            
//         })
        
    
//     })

// })
// }else{
//     s.readdir('.',(_,files)=>{
    
//         let arr=[]
//         files.forEach((file,index)=>{
//             let filecont=file.replace(/(.json)/g,'').split('_')
//             arr.push(filecont[1].concat(' ',filecont[0]))
//             arr.sort()
//         })
//        // console.log(arr)
//         arr.map((file1,i)=>{
//             console.log(`${i+1}. ${file1}`)
        
            
//         })
        
    
//     })
// }
import fs from "fs/promises";
import path from "path";

let arg = process.argv[2];
if (arg === undefined) {
  arg = process.argv[1];
}

try {
  const data = await fs.readdir(arg, "utf8");
  let arr = [];
  for (let i = 0; i < data.length; i++) {
    if (await saidYes(path.join(arg, data[i]))) {
      let s = data[i].replace(/\.json$/, "");
      let [lastName, firstName] = s.split("_");
      arr.push(`${firstName} ${lastName}`);
    }
  }
  arr.sort()

  let s = arr.map((name, index) => `${index + 1}. ${name}`).join("\n");

  await fs.writeFile("vip.txt", s);
  console.log(s); // Print the result to console
} catch (err) {
  console.error("Error:", err);
}

async function saidYes(filename) {
  try {
    const data = await fs.readFile(filename, "utf8");
    const jsonData = JSON.parse(data);
    return jsonData.answer === "yes";
  } catch (err) {
    console.error("Error reading/parsing file:", filename, err);
    return false;
  }
}