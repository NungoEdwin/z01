use rand::Rng;

#[derive(PartialEq, Debug,Copy, Clone)]
pub enum Suit {
    Heart,
    Diamond,
    Spade,
    Club,
}

impl Suit {
    pub fn random() -> Suit {
        let mut rng =  rand::thread_rng();
        let suit = [Suit::Heart, Suit::Diamond, Suit::Spade, Suit::Club];
        let random_index : u8 =rng.gen_range(1..suit.len()).try_into().unwrap();
        Suit::translate(random_index)
    }

    pub fn translate(value: u8) -> Suit {
        match value{
            1 => Suit::Heart,
            2 => Suit::Diamond,
            3 => Suit::Spade,
            4 => Suit::Club,
            _ => panic!("Incalid")
        }
    }
}

#[derive(PartialEq, Debug, Copy, Clone)]
pub enum Rank {
    Ace,
    King,
    Queen,
    Jack,
    Number(u8)
}

impl Rank {
    pub fn random() -> Rank {
        let mut rng =  rand::thread_rng();
        // let rank = [Rank::Ace, Rank::King, Rank::Queen, Rank::Jack];
        let random_index:u8 = rng.gen_range(1..=13).try_into().unwrap();
        Rank::translate(random_index)
    }   

    pub fn translate(value: u8) -> Rank {
        match value {
            1 => Rank::Ace,
            13 => Rank::King,
            12 => Rank::Queen,
            11 => Rank::Jack,
            2..11 =>Rank::Number(value),
            _ => panic!("Incalid")
        }
    }
}

#[derive(Debug, PartialEq, Copy, Clone)]
pub struct Card {
    pub suit: Suit,
    pub rank: Rank,
}

pub fn winner_card(card: Card) -> bool {
    card.suit == Suit::Spade && matches!(card.rank, Rank::Ace)
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Suit::Club;
    use crate::Rank::Ace;

    #[test]
    fn it_works() {
        let your_card = Card {
            rank: Rank::random(),
            suit: Suit::random(),
        };
        assert_eq!(your_card, Card { suit: Club, rank: Ace });
    }
}
