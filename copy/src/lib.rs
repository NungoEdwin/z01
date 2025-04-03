use std::f64;

pub fn nbr_function(c: i32) -> (i32, f64, f64) {
    let exp_c = (c as f64).exp(); // e^c
    let ln_c = (c.abs() as f64).ln(); // ln(|c|)
    
    (c, exp_c, ln_c)
}

pub fn str_function(a: String) -> (String, String) {
    let exp_values: Vec<String> = a
        .split_whitespace() // Split by spaces
        .filter_map(|s| s.parse::<f64>().ok()) // Convert to f64 if possible
        .map(|n| n.exp().to_string()) // Compute e^n and convert to String
        .collect();
    
    (a, exp_values.join(" ")) // Join back as a single string
}

pub fn vec_function(b: Vec<i32>) -> (Vec<i32>, Vec<f64>) {
    let ln_values: Vec<f64> = b
        .iter()
        .map(|&n| (n.abs() as f64).ln()) // Compute ln(|n|)
        .collect();
    
    (b, ln_values)
}