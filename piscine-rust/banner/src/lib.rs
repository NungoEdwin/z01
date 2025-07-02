use std::{collections::HashMap, num::ParseFloatError};

#[derive(Debug)]
pub struct Flag<'a> {
    pub short_hand: String,
    pub long_hand: String,
    pub desc: &'a str,
}

impl<'a> Flag<'a> {
    pub fn opt_flag(name: &'a str, d: &'a str) -> Self {
        let short_hand = name.chars().next().map_or(String::new(), |c| format!("-{}", c));
        let long_hand = format!("--{}", name);
        Flag {
            short_hand,
            long_hand,
            desc: d,
        }
    }
}

pub type Callback = fn(&str, &str) -> Result<String, ParseFloatError>;

#[derive(Debug)]
pub struct FlagsHandler {
    pub flags: HashMap<(String, String), Callback>,
}

impl FlagsHandler {
    pub fn add_flag(&mut self, flag: Flag, func: Callback) {
        self.flags.insert((flag.short_hand, flag.long_hand), func);
    }

    pub fn exec_func(&self, input: &str, argv: &[&str]) -> Result<String, String> {
        if let Some((_, func)) = self.flags
            .iter()
            .find(|(key, _)| key.0 == input || key.1 == input)    
        {
            if argv.len() < 2 {
                return Err("Insufficient arguments".to_string());
            }
            func(argv[0], argv[1]).map_err(|e| format!("{}", e))
        } else {
            Err("Flag not found".to_string())
        }
    }
}

pub fn div(a: &str, b: &str) -> Result<String, ParseFloatError> {
    let a: f64 = a.parse()?;
    let b: f64 = b.parse()?;
    Ok((a / b).to_string())
}

pub fn rem(a: &str, b: &str) -> Result<String, ParseFloatError> {
    let a: f64 = a.parse()?;
    let b: f64 = b.parse()?;
    Ok((a % b).to_string())
}