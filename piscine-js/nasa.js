function nasa(N){
    let str='';
    for(let i=1;i<=N;i++){
        if(i%3==0&&i%5==0){
         
          if(i!=N){
            str+="NASA"+' '
        }else{
            str+="NASA"
        }  
        }else if(i%3==0){
            if(i!=N){
                str+="NA"+' '
            }else{
                str+="NA"
            }
        }else if(i%5==0){
         if(i!=N){
            str+="SA"+' '
        }else{
            str+="SA"
        }
        }else{
            if(i!=N){
                str+=''+i+' '
            }else{
                str+=i+''
            }
        }
    }
    return str;
}
//console.log(nasa(15))