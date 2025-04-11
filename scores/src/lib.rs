fn score(str:&str)->u64{
    let ownedstr=str.to_string().to_uppercase();
        let mut score:u64=0;
        for l in ownedstr.chars(){
            match l{
                'A'|'E'|'I'|'O'|'U'|'L'|'N'|'R'|'S'|'T'=>score+=1,
                'D'|'G'=>score+=2,
                'B'|'C'|'M'|'P'=>score+=3,
                'F'|'H'|'V'|'W'|'Y'=>score+=4,
                'K' =>score+=5,
                'J'|'X'=>score+=8,
                'Q'|'Z'=>score+=10,
                _ =>score+=0,
    
            }
        }
        score
    }