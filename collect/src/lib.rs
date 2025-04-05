pub fn bubble_sort(arr: &mut [i32]) {
    let n = arr.len();

    for i in 0..n {
        for j in i + 1..n {
            if arr[i] > arr[j] {
                arr.swap(j,i);
            }
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        let mut v = [3, 2, 4, 5, 1, 7];
        bubble_sort(&mut v);
        assert_eq!(v, [1, 2, 3, 4, 5, 7]);
    }
}