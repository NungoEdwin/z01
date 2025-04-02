use std::io;
fn main() {
    let mut input=String::new();
    let mut c=0;
    loop{
        println!("Riddle: I am the beginning of the end, and the end of time and space. I am essential to creation, and I surround every place. What am I?");
        let _=io::stdin().read_line(&mut input);
        if input=="e"{
         break println!("{}",c)
        }
        c+=1
    }
}
