use std::io;
fn main() {
    let mut gotcha=false;
    let mut c=0;
    while !gotcha{
        println!("I am the beginning of the end, and the end of time and space. I am essential to creation, and I surround every place. What am I?");
    	let mut input=String::new();
        let _=io::stdin().read_line(&mut input);
       input =input.trim().parse().expect("failed parsing &str to str");
        c+=1;
        if input=="The letter e"{
          println!("Number of trials {}",c);
          gotcha=true;
        }
    }
}
