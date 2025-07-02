#[derive(Debug, Clone, Eq, PartialEq)]
pub struct GameSession {
    pub id: u32,
    pub p1: (String, u16),
    pub p2: (String, u16),
    pub nb_games: u16
}

impl GameSession {
    pub fn new(id: u32, p1_name: String, p2_name: String, nb_games: u16) -> Box<GameSession> {
      let p1:u16= "0".parse().expect("invalid ");
     Box::new(Self{
           id,
           p1:(p1_name,p1),
           p2:(p2_name,p1),
           nb_games,
       })
       
    }
     pub fn read_winner(&self) -> (String, u16) {
     let p1=&self.p1;
     let p2=&self.p2;
     let mut stri=(String::new(),p1.1);
     if p1.1>p2.1{
         stri.0=p1.0.clone();
         stri.1=p1.1;
     }else if p2.1>p1.1{
         stri.0=p2.0.clone();
         stri.1=p2.1
     }else{
         stri.0=String::from("Same score! tied");
         stri.1=p2.1;
     }
     stri
     
     }
     pub fn update_score(&mut self, user_name: String) {
     if self.p1.1>self.nb_games/2 ||self.p2.1>self.nb_games{
         return;
     }
     if self.p1.0==user_name{
         self.p1.1+=1;
     }else if self.p2.0==user_name{
         self.p2.1+=1;
     }
        
     }
     pub fn delete(self) -> String {
        let id=self.id;
          drop(self);
          format!("game deleted: id -> {}",id)
    }
}