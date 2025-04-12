pub fn search(array: &[i32], key: i32) -> Option<usize> {
    let mut val:Option<usize>=Some(0);
        for (i,v)in array.iter().enumerate(){
            if *v==key{
                val=Some(i);
                break;
            }
        }
        val
    }