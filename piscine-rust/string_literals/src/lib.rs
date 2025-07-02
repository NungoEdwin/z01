pub fn is_empty(v: &str) -> bool {
    v.is_empty() // Directly checks if the string is empty
}

pub fn is_ascii(v: &str) -> bool {
    v.is_ascii() // Checks if all characters in the string are ASCII
}

pub fn contains(v: &str, pat: &str) -> bool {
    v.contains(pat) // Checks if `pat` exists within `v`
}

pub fn split_at(v: &str, index: usize) -> (&str, &str) {
    v.split_at(index) // Splits the string at the given index
}

pub fn find(v: &str, pat: char) -> usize {
    v.find(pat).unwrap_or(v.len()) // Returns the index or the length if not found
}
