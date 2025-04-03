pub fn first_subword(mut s: String) -> String {
    let mut index = s.len(); // Default index to length of string

    for (i, c) in s.chars().enumerate() {
        if i > 0 && (c.is_uppercase() || c == '_') {
            index = i;
            break;
        }
    }

    s.truncate(index); // Mutate the original string to keep only the first sub-word
    s
}
