pub enum Security {
    Unknown,
    Message,
    Warning,
    NotFound,
    UnexpectedUrl,
}

pub fn fetch_data(server: Result<&str, &str>, security_level: Security) -> String {
  match server{
    Ok(url)=>url.to_string(),
    Err(err)=>match security_level{
Security::Unknown=>panic!("{err}"),
Security::Message=>format!("ERROR : program stops"),
Security::Warning=>format!("WARNING : check the server"),
Security::NotFound=>format!("Not found : {}",err),
Security::UnexpectedUrl=>panic!("{err}")
    }

  }   
}