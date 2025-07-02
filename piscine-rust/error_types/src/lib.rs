use chrono::Local;
pub use chrono::{NaiveDate, Utc};


#[derive(Debug, Eq, PartialEq)]
pub struct FormError {
   pub form_values: (&'static str, String),
   pub date: String,
   pub err: &'static str,
}

impl FormError {
    pub fn new(field_name: &'static str, field_value: String, err: &'static str) -> Self {
       let date = Local::now().format("%Y-%m-%d %H:%M:%S").to_string();
       Self {
        form_values: (field_name, field_value),
        date,
        err,
       }
    }
}

#[derive(Debug, Eq, PartialEq)]
pub struct Form {
    pub name: String,
    pub password: String,
}

impl Form {
    pub fn validate(&self) -> Result<(), FormError> {
        if self.name.trim().is_empty() {
            return Err(FormError::new("name", self.name.clone(), "Username is empty"));
        }

        if self.password.len() < 8 {
            return Err(FormError::new(
                "password",
                self.password.clone(),
                "Password should be at least 8 characters long",
            ));
        }

     
        let mut has_letter = false;
        let mut has_digit = false;
        let mut has_symbol = false;

        for c in self.password.chars() {
            if c.is_ascii_alphabetic() {
                has_letter = true;
            } else if c.is_ascii_digit() {
                has_digit = true;
            } else if c.is_ascii() && !c.is_ascii_alphanumeric() {
                has_symbol = true;
            }
        }

        if !(has_letter && has_digit && has_symbol) {
            return Err(FormError::new(
                "password",
                self.password.clone(),
                "Password should be a combination of ASCII numbers, letters and symbols",
            ));
        }

        Ok(())
    }
}