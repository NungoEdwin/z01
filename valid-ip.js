/*function findIP(dataSet) {
    // Regular expression to match valid IP addresses with optional ports
    const pattern = /\b(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)(?::\d{1,5})?\b/g;
    
    // Match all occurrences in the dataSet
    const matches = dataSet.match(pattern) || [];
    
    // Further filter to ensure valid port range (0-65535) if a port is present
    return matches.filter(ip => {
        const parts = ip.split(':');
        if (parts.length === 2) {
            const port = parseInt(parts[1], 10);
            return port >= 0 && port <= 65535;
        }
        return true;
    });
};
// const dataSet = "Here are some IPs: 192.168.1.1, 10.0.0.1:80, and 256.100.50.0. Check this IP: 123.45.67.89:65535 and this one: 192.168.0.0:99999";
// const validIPs = findIP(dataSet);
// console.log(validIPs);
*/
function findIP(input) {
    const ipPattern =
      /(?!(((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4}):(?!(?![7-9]\d\d\d\d)(?!6[6-9]\d\d\d)(?!65[6-9]\d\d)(?!655[4-9]\d)(?!6553[6-9])(?!0+)(\d{1,5})))((((25[0-5]|(2[0-4]|1\d|[1-9]|)\d)\.?\b){4})(?::(?![7-9]\d\d\d\d)(?!6[6-9]\d\d\d)(?!65[6-9]\d\d)(?!655[4-9]\d)(?!6553[6-9])(?!0+)(\d{1,5}))?)/g;
  
    const matches = input.match(ipPattern);
  
    return matches || [];
  }