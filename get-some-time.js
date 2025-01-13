// function firstDayWeek(week,year){
// let weeks=0;
// let firstday=new Date(Number(year),0,1)
// let diff=firstday.getDay()-1//since its day 5 friday n we want day 1 monday.default 0 is sunday for 2016 case
// //console.log(diff)
// let start=new Date(Number(year),0,1).setDate(firstday.getDate()-diff)//00 denotes january 01 basically
// let startday=new Date(start)
// let weekz=[]
// let newStart=startday
// if(startday>firstday){
//    weekz.push(startday)
// }else{
//    weekz.push(firstday)
// }
// while(weeks<54){
//    for(let i=0;i<35;i+=1){
//       if(i%7==0){
//          weeks+=1;
//          newStart=new Date(newStart.setDate(newStart.getDate()+7));
//          // console.log(newStart.toString());
//          weekz[weeks]=newStart
//       }
//    }
// }
// // console.log(weeks)//weekz.length)
// return weekz[Number(week)-1].toDateString();
//  const day=weekz[week-1].getDate().toString().padStart(2,'0');
//  const month=(weekz[week-1].getMonth()+1).toString().padStart(2,'0')//adding one coz 00 is jan basically 01
//  const year1=weekz[week-1].getFullYear().toString().padStart(4,'0')
//  return `${day}-${month}-${year1}` 
// }
// //fucking proud of this code yeeeih!!
//console.log(firstDayWeek(3,'2024'))//why is it skipping 2nd element yawa!
function firstDayWeek(week, year) {
   // Parse the year as an integer
   const parsedYear = parseInt(year, 10);
 
   // Ensure week is between 1 and 53
   week = Math.max(1, Math.min(53, week));
 
   // Function to create date, handling years below 100
   function createDate(year, month, day) {
     const date = new Date(year, month, day);
     if (year < 100) {
       date.setFullYear(year);
     }
     return date;
   }
 
   // Create a Date object for January 1st of the given year
   let time = createDate(parsedYear, 0, 1);
 
   // Special case for Week 1 starting on January 1st
   if (week === 1) {
     return formattedDate(time);
   }
 
   // Calculate the number of days to add to reach the desired week
   let dayPlus = (week - 1) * 7;
   time.setDate(time.getDate() + dayPlus);
 
   // Find the first Monday of the desired week
   while (getWeekDay(time) !== 'Monday') {
     time.setDate(time.getDate() - 1);
   }
 
   // If the calculated date is in the previous year, return January 1st
   if (time.getFullYear() < parsedYear) {
     return formattedDate(createDate(parsedYear, 0, 1));
   }
 
   return formattedDate(time);
 }
 
 function getWeekDay(date) {
   const days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
   return days[date.getDay()];
 }
 
 function formattedDate(d) {
   let month = String(d.getMonth() + 1).padStart(2, '0');
   let day = String(d.getDate()).padStart(2, '0');
   let year = String(d.getFullYear()).padStart(4, '0');
   return `${day}-${month}-${year}`;
 }
