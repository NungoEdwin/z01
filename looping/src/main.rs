use std::io;
fn main() {
    let mut gotcha=false
    let mut input=String::new();
    let mut c=0;
    while !gotcha{
        println!("I am the beginning of the end, and the end of time and space. I am essential to creation, and I surround every place. What am I?");
        let _=io::stdin().read_line(&mut input);
        c+=1;
        if input=="The letter e"{
          println!("Number of trials {}",c);
          gotcha=true;
        }
    }
}
