#[derive(Debug, Clone, Copy)]

pub struct Circle {
	pub center :Point,
	pub radius :f64
}

impl Circle {
    pub fn diameter(&self) -> i64 {
        (self.radius * 2.0) as i64
    }
    pub fn area(&self) -> f64 {
        std::f64::consts::PI * (self.radius *self.radius) as f64
    }
    pub fn intersect(&self, other: Circle) -> bool {
        let dx = self.center.0 - other.center.0;
        let dy = self.center.1 - other.center.1;
        let distance = ((dx * dx + dy * dy) as f64).sqrt() as f64;
        let radius_sum = self.radius.abs() + other.radius.abs();
        distance <= radius_sum
    } 
    pub fn new(x: f64, y:f64,  radius: f64) -> Self {
        Circle{
            center:Point(x,y), 
            radius: radius
        }
    }
}

//for the points
#[derive(Debug, Clone, Copy)]
pub struct Point(pub f64, pub f64);

    
impl Point {
    pub fn distance(&self, other: Point) -> f64 {
        let da = (self.0 - other.0) as f64;
        let db = (self.1 - other.1) as f64;
        (da * da + db * db).sqrt()
    }

}
