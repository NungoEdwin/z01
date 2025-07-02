pub fn sum(a: &[i32]) -> i32 {
    // a.iter().sum()
    let mut sum:i32 = 0;
    for i in a {
        sum += i;
    }

    sum
}

pub fn thirtytwo_tens() -> [i32; 32] {
    // [132; 32]
    let mut res:[i32; 32]=[0; 32];
    for i in  0..32 {
        res[i]=10;
    }
    res
}
