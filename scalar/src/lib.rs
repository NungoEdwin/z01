pub fn sum(left: u8, right: u8) -> u8 {
    left + right
}
pub fn diff(a: i32, b: i32) -> i32 {
a-b
}

pub fn pro(a: i8, b: i8) -> i8 {
a*b
}

pub fn quo(a: i32, b: i32) -> i32{
a/b
}

pub fn rem(a: i32, b: i32) -> i32 {
a%b
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        let result = sum(2, 2);
        assert_eq!(result, 4);
    }
}
