pub fn num_to_ordinal(x: u32) -> String {
    let mut a :String=x.to_string();
   if x%10==1{
       a.push_str("st");
   }else if x%10==2{
       a.push_str("nd");
   }else if x%10==3{
       a.push_str("rd");
   }else{
       a.push_str("th");
   }
   a

}