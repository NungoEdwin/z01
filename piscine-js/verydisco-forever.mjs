//const { argv } = require('node:process');
import process from 'node:process'
//import { writeFile } from 'node:fs';
import fs from 'fs'


process.argv.forEach((val, index) => {
    if(index==2){
        //console.log(`${index}: ${val}`);
        let arr=val.split('/')
        let string=arr[arr.length-1]
        let str1=string.split(' ')
        let str2=str1.map(n=>{
            if(n!=''&&n!=' '){
                let len=n.length
                if(len%2==0){
                    n=n.slice(len/2,len)+n.slice(0,len/2)
                    //console.log(n)
                }else{
                    n=n.slice(len/2 +1,len)+n.slice(0,len/2+1)
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
        //console.log(word)
        
//writing result to an outputfile
        fs.writeFile('verydisco-forever.txt', word, (err) => {

            // In case of a error throw err.
            if (err) throw err;
        })

}
});