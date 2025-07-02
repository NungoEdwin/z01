function isValid(date){
let datez=new Date(date);
if (new Date(date).toString() === "Invalid Date") {
    return false;
}
if (!(date instanceof Date) && typeof date !== "number") {
        return false;
    }
    return true;//getMonth()
if(datez.getFullYear()%4==0){
    if(datez.getMonth()==1&&datez.getDate()>29){
        return false
    }else if(datez.getMonth()==7){
        return true
    }else if(datez.getMonth()%2==0&&datez.getDate()>30){
        return false
    }
    return true
}else{
    if(datez.getMonth()==1&&datez.getDate()>28){
        return false;
    }else if(datez.getMonth()==7){
        return true;
    }else if(datez.getMonth()%2==0&&datez.getDate()>30){
        return false
    }
    return true;


}

};
function isAfter(date1,date2){
    if (!isValid(date1) || !isValid(date2)) return false;
    return date1 > date2;
}
function isBefore(date1,date2){
    if (!isValid(date1) || !isValid(date2)) return false;
    return date1 < date2;
}
function isFuture(date){
    if (!isValid(date)) return false;
    return date > Date.now();
}
function isPast(date) {
    if (!isValid(date)) return false;
    const now = new Date();
    return date < now;
}
//console.log(isValid('2016-02-30'))
//console.log(isValid(2016,02,30))