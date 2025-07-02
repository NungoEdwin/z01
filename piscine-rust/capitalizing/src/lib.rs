pub fn capitalize_first(input: &str) -> String {
    let mut ch = input.chars();
    match ch.next() {
        None => String::new(),
        Some(f) => f.to_uppercase().collect::<String>() + ch.as_str(),
    }
}

pub fn title_case(input: &str) -> String {
    let mut result = String::new();
    let mut new_word = true;

    for c in input.chars() {
        if c.is_whitespace() {
            result.push(c);
            new_word = true;
        } else if new_word {
            result.extend(c.to_uppercase());
            new_word = false;
        } else {
            result.extend(c.to_lowercase());
        }
    }

    result
}

pub fn change_case(input: &str) -> String {
    input.chars().map(|ch| {
        if ch.is_lowercase() {
            ch.to_uppercase().to_string()
        } else {
            ch.to_lowercase().to_string()
        }
    }).collect()
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn it_works() {
        assert_eq!(capitalize_first("joe is missing"), "Joe is missing");
        assert_eq!(title_case("jill is leaving A"), "Jill Is Leaving A");
        assert_eq!(change_case("heLLo THere"), "HEllO thERE");
    }

    #[test]
    fn test_empty() {
        assert_eq!(capitalize_first(""), "");
        assert_eq!(title_case(""), "");
        assert_eq!(change_case(""), "");
    }

    #[test]
    fn test_capitalize_first() {
        assert_eq!(capitalize_first("hello"), "Hello");
        assert_eq!(capitalize_first("HELLO"), "HELLO");
    }

    #[test]
    fn test_title_case() {
        assert_eq!(title_case("hello my\t\tname is carl"), "Hello My\t\tName Is Carl");
    }

    #[test]
    fn test_change_case() {
        assert_eq!(change_case("Hello World"), "hELLO wORLD");
    }
}