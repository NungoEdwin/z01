//const is={};
is.num=(n)=>{
    if (typeof (n)==='number'){
        return true
    }
    return false
};
is.nan=(n)=>Number.isNaN(n);
is.str=(n)=>{
    if (typeof (n)==='string'){
        return true;
    }
    return false;
};
is.bool=(n)=>{
    if (typeof n==='boolean'){
        return true;
    }
    return false;
};
is.undef=(n)=>{
    if (typeof (n)==='undefined'){
        return true
    }
    return false
};
is.def=(n)=>{
    if (typeof (n)!='undefined'){
        return true
    }
    return false
};
is.arr=(n)=>Array.isArray(n);
is.obj=(n)=>{
    if (typeof (n)==='object'&& !is.fun(n) && !is.arr(n) && n !== null){
        return true
    }
    return false
};
is.fun=(n)=>{
    if (typeof (n)==='function'){
        return true
    }
    return false
};
is.truthy=(n)=>!!n;
is.falsy=(n)=>!n;
// let Nan;// =[];
// console.log(is.def(Nan));
// console.log(typeof Nan);