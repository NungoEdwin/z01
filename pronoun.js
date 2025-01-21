function pronoun(text){
let obj={};
text=text.replaceAll("\n",' ');
text=text.replaceAll('  ',' ');
text=text.replaceAll(/[,?!-:;_]/g,'');


let pron=['i','you','he','she','it','they','we'];
let fields=text.split(' ')
pron.map((pro)=>{
    let match=0;
    fields.map((word,j)=>{
        if(pro.toLowerCase()==word.toLowerCase()){
            if(match>0){
                match++
                if(pron.includes(fields[j+1])){
                    obj[pro]['count']+=1
                }else{
                    obj[pro]['word'].push(fields[j+1])
                     obj[pro]['count']+=1

                }
            }else{
                match++;
                if(j==fields.length-1||pron.includes(fields[j+1])){
                    obj[pro]={'word':[],'count':match}
                }else{
                    
                    obj[pro]={'word':[fields[j+1]],'count':match}
                }
                
            }
        }
    


    })
})
return obj

}
let ex = 'Using Array Destructuring, you you can iterate through objects easily.'
console.log(pronoun(ex))
//{ you: { word: [ 'can' ], count: 2 } }

const ex1 = 'If he you want to buy something you have to pay.'
//console.log(pronoun(ex1))
/*{
  he: { word: [], count: 1}
  you: { word: [ 'want', 'have' ], count: 2 }
}*/
