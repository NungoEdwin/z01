pub struct Message {
    content: String,
    user: String,
}

impl Message {
    pub fn new(content: String, user: String) -> Self {
        Message { content, user }
    }

    pub fn send_ms(&self) -> Option<&str> {
        if self.content.is_empty() || self.content.contains("stupid") {
            None
        } else {
            Some(&self.content)
        }
    }
}

pub fn check_ms(message: &Message) -> Result<String, String> {
    match message.send_ms() {
        Some(content) => Ok(content.to_string()),
        None => Err("ERROR: illegal".to_string()),
    }
}