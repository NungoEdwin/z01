pub fn transform_and_save_on_heap(s: String) -> Box<Vec<u32>> {
        let u=&s[..];
        let arr=u.split_whitespace();
        let mut arr1:Vec<&str>= vec![];
        let mut arr2:Vec<u32>=vec![];
        for word in arr{
            arr1.push(word);
        }
        let _=&arr1.iter_mut().map(|word|
        if word.contains("k"){
            let x=word.replace("k","");
            let num:f32=x.parse().expect("not valid number");
           arr2.push( (&num * 1000.0) as u32);
            
        }else{
            let num:u32=word.parse().expect("invalid number");
            arr2.push(num);
        }
        ).collect::<Vec<_>>();
        Box::new(arr2)
        
        
}
pub fn take_value_ownership(a: Box<Vec<u32>>) -> Vec<u32> {
    *a

}
