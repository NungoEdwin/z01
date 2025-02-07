const escapeStr=' "js\'s" way of doing things is , \\d \/ret "\`sara\'" '
const arr =[4,'2'];
const obj={
    str:'nana',
    num:12,
    bool:true,
    undef:undefined

};
const nested={
    arr:[4,undefined,'2'],
    obj:{
        str:'str',
        num:12,
        bool:false
    }
};

Object.freeze(nested)
Object.freeze(nested.arr)
Object.freeze(nested.obj)