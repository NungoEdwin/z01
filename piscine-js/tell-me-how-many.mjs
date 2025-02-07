import fs from 'fs'
import process from 'process'


let small=process.argv
if(small.length>=3){
let slic=small.slice(2,small.length)
slic.map(arg=>{


    fs.readdir(arg,(_,files)=>{
        let count=0
    
        for(let file of files){
            count++
        }
        console.log(count)
    
    })





})
}else{
fs.readdir('.',(err,files)=>{
    let count=0
    for(let file of files){
        count++
    }
    console.log(count)
})
}