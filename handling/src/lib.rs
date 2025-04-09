use std::fs::OpenOptions;
use std::io::Write;
use std::path::Path;

pub fn open_or_create<P: AsRef<Path>>(path: &P, content: &str) {
    // Try to open the file with options:
    // - create it if it doesn't exist
    // - append to it (don’t erase what's inside)
    let mut file = OpenOptions::new()
        .create(true)  // create the file if it doesn't exist
        .append(true)  // append to the file
        .open(path)    // open the file
        .expect("❌ Failed to open or create the file");

    // Try to write the content into the file
    write!(file, "{}", content).expect("❌ Failed to write to the file");
}