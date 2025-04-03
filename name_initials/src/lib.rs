pub fn initials(names: Vec<&str>) -> Vec<String> {
    names
        .iter()
        .map(|name| {
            name.split_whitespace() // Split the name by spaces
                .filter_map(|word| word.chars().next()) // Get the first letter of each word
                .map(|c| format!("{}.", c.to_ascii_uppercase())) // Convert to uppercase and append "."
                .collect::<Vec<String>>() // Collect initials as a vector of strings
                .join(" ") // Join with spaces
        })
        .collect() // Collect into a vector of strings
}