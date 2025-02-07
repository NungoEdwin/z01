function chunk(arr,size){
    let subarray=[];
    let array=[];
    let count=0
    for (let cont of arr){
        if (count==size){
            array.push(subarray)
            subarray=[]
            count=0
        }
         if (subarray.length>=0&&subarray.length<=size){
            subarray.push(cont)
            count++
        }
        
    }
    if (subarray.length>0){
       array.push(subarray)
       subarray=[]
   }
    return array
};
console.log(chunk([1,2,3,4,5,6,7,8,9,0],3))