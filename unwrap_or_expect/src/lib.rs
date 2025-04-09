pub enum Security {
    Unknown,
    Message,
    Warning,
    NotFound,
    UnexpectedUrl,
}

pub fn fetch_data(server: Result<&str, &str>, security_level: Security) -> String {
match security_level{
Security::Unknown=>server.unwrap().to_string(),
Security::Message=>server.expect("ERROR: program stops").to_string(),
Security::Warning=>match server{
    Ok(url)=>url.to_string(),
    Err(err)=>format!("WARNING: check the server"),
}
Security::NotFound=>match server{
    Ok(url)=>url.to_string(),
    Err(err)=>format!("Not found: {}",err),
}
Security::UnexpectedUrl=>match server{
    Ok(err)=>err.to_string(),
    Err(url)=>panic!("{url}")
}
    }
}   