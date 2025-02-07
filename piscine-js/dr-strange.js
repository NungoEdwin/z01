function addWeek(date){
    const days=[
     'Monday',
     'Tuesday',
     'Wednesday',
     'Thursday',
     'Friday',
     'Saturday',
     'Sunday',
     'secondMonday',
     'secondTuesday',
     'secondWednesday',
    'secondThursday',
     'secondFriday',
     'secondSaturday',
    'secondSunday'
    ];

    const start=new Date('0001-01-01')
    let timediff=(new Date(date)-start)/(1000*60*60*24)
    if(timediff<14){
        return days[timediff]
    }else{
         return days[timediff%14]
    }

};
function timeTravel(date){
    date.date.setHours(date.hour);
    date.date.setMinutes(date.minute);
    date.date.setSeconds(date.second);
    //console.log(typeof(date.date))
   return date.date

}
//console.log(addWeek(new Date('0001-01-01')) )// Output: Monday
// addWeek(new Date('0001-01-02')) // Output: Tuesday
// addWeek(new Date('0001-01-07')) // Output: Sunday
// addWeek(new Date('0001-01-08')) // Output: secondMonday
// addWeek(new Date('0001-01-14')) // Output: secondTuesday

// console.log(timeTravel({
//     date: new Date('2020-05-29 23:25:22'),
//     hour: 21,
//     minute: 22,
//     second: 22,
//   }).toString())
  
  // Output: Fri May 29 2020 21:22:22 GMT+0100 (Western European Summer Time)
  
