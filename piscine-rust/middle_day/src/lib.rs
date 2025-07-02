use chrono::{NaiveDate, Datelike};
pub use chrono::Weekday as wd;

pub fn middle_day(year: i32) -> Option<wd> {
    if (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0) {
        return None
    }
    
    let middle_day = 183;
    let date = NaiveDate::from_yo_opt(year, middle_day)?;
    
    Some(date.weekday())
}
