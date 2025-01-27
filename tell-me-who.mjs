import fs from 'fs'
import process from 'process'


let small=process.argv
if(small.length>=3){
let slic=small.slice(2,small.length)
slic.map(arg=>{


    fs.readdir(arg,(_,files)=>{
    
        let arr=[]
        files.forEach((file,index)=>{
            let filecont=file.replace(/(.json)/g,'').split('_')
            arr.push(filecont[1].concat(' ',filecont[0]))
            arr.sort()
        })
       // console.log(arr)
        arr.map((file1,i)=>{
            console.log(`${i+1}. ${file1}`)
        
            
        })
        
    
    })

})
}else{
    s.readdir('.',(_,files)=>{
    
        let arr=[]
        files.forEach((file,index)=>{
            let filecont=file.replace(/(.json)/g,'').split('_')
            arr.push(filecont[1].concat(' ',filecont[0]))
            arr.sort()
        })
       // console.log(arr)
        arr.map((file1,i)=>{
            console.log(`${i+1}. ${file1}`)
        
            
        })
        
    
    })
}