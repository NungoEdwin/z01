use std::collections::HashMap;

pub const SENTENCE: &str = "this is a very basic sentence with only a few repetitions. once again this is very basic but it should be enough for basic tests";

pub fn word_frequency_counter<'a>(words: &'a [&'a str]) -> HashMap<&'a str, usize> {
    let mut hmap = HashMap::new();

    for &word in words { 
        *hmap.entry(word).or_insert(0) += 1; 
    }

    hmap
}

pub fn nb_distinct_words(frequency_count: &HashMap<&str, usize>) -> usize {
    frequency_count.len()
}

