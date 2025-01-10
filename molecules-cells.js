function RNA(str){
    let rna=''
for(let char of str){
    if (char=='G'){
rna+='C'
    }else if(char=='C'){
rna+="G"
    }else if(char=='T'){
rna+='A'
    }else if(char=='A'){
        rna+='U'
    }
}
return rna;
};
// DNA | RNA
//  G  -  C
//  C  -  G
//  T  -  A
//  A  -  U
function DNA(str){
    let dna=''
for(let char of str){
    if (char=='C'){
dna+='G'
    }else if(char=='G'){
dna+="C"
    }else if(char=='A'){
dna+='T'
    }else if(char=='U'){
        dna+='A'
    };
}
return dna;
};
console.log(RNA('GCTA'))