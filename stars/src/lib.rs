pub fn stars(n: u32) -> String {
let mut star="".to_string();
let result=2_i32.pow(n);
for _i in 1..=result{
 star.push('*');
}
star
}