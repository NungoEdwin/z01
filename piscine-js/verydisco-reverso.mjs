import process from 'process'
import fs from 'fs'
import resolve from 'path'
const readFile=fs.promises.readFile
//let reader=new FileReader()
// let input=fs.readFile('verydisco-forever.txt',(err,data) => {

//     // In case of a error throw err.
//     if (err) throw err;
//     console.log(data.data)
//     return data
// })
//console.log (input)
let sa='a'

fs.readFile(`${process.argv[2]}`,(err,data)=>{
    if(err||data=='undefined') throw err
    sa= data.toString();
    //console.log(sa)
    let arr=sa.split('/')
        let string=arr[arr.length-1]
        let str1=string.split(' ')
        let str2=str1.map(n=>{
            if(n!=''&&n!=' '){
                let len=n.length
                if(len%2==0){
                    n=n.slice(len/2,len)+n.slice(0,len/2)
                    //console.log(n)
                }else{
                    n=n.slice(len/2,len)+n.slice(0,len/2)
                }
                return n

            }
        })
        let word=''
        str2.map((n,index)=>{
            if(str2.length -1!=index){
                word+=n+ ' '
            }else{
                word+=n
            }
        })
        console.log(word)
        
    })
   // console.log(sa)
