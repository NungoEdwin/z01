pub fn search(array: &[i32], key: i32) -> Option<usize> {
    let mut val:Option<usize>=Some(0);
    let mut found =false;
        for (i,v)in array.iter().enumerate(){
            if *v==key{
                val=Some(i);
                found=true;
                break;
            }
        }
        if !found{
            val=None;
        }
        val
    }