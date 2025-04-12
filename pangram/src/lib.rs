use std::collections::HashMap;
pub fn is_pangram(s: &str) -> bool {
    let mut pan=false;
    let s=s.to_string().to_lowercase();
    let mut map=HashMap::new();
    for v in s.chars(){
       if v.is_alphabetic(){
        map.insert(v,true);
            }
    }
   if map.len()==26{
       pan=true
   }
   pan
}