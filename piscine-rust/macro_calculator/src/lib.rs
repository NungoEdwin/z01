use json::{object, JsonValue};

pub fn add(left: u64, right: u64) -> u64 {
    left + right
}
#[derive(Debug, Clone)]
pub struct Food {
    pub name:String,
    pub calories:[String;2],
    pub proteins:f64,
    pub fats:f64,
    pub carbs:f64,
    pub nbr_of_portions:f64,
}

fn round_value(value: f64) -> f64 {
    let rounded = (value * 100.0).round() / 100.0;
    if (rounded * 10.0) % 1.0 == 0.0 {
        ((rounded * 10.0).round()) / 10.0
    } else {
        rounded
    }
}
fn extract_number(s: &str) -> Option<f64> {
    let number: String = s.chars().filter(|c| c.is_digit(10) || *c == '.').collect();
    number.parse::<f64>().ok()
}


pub fn calculate_macros(foods: Vec<Food>) -> JsonValue {
    let mut total_cals: f64 = 0.0;
    let mut total_fats: f64 = 0.0;
    let mut total_carbs: f64 = 0.0;
    let mut total_proteins: f64 = 0.0;

    for food in foods {
        if let Some(kcal) = extract_number(&food.calories[1]) {
            let multiplier = food.nbr_of_portions;
            total_cals += kcal * multiplier;
            total_fats += food.fats * multiplier;
            total_carbs += food.carbs * multiplier;
            total_proteins += food.proteins * multiplier;
        }
    }
    

    object! {
        "cals"    => round_value(total_cals),
        "carbs"   => round_value(total_carbs),
        "proteins"=> round_value(total_proteins),
        "fats"    => round_value(total_fats)
    }
}