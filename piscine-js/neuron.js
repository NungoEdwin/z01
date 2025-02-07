const neuron = (data) => {
    if (data.length === 0) {
      return {};
    }
  
    const result = {};
  
    data.forEach(item => {
      const [typeAndQuery, response] = item.split(' - Response: ');
      const [type, ...queryParts] = typeAndQuery.split(': ');
      const query = queryParts.join(': '); // In case the query itself contains ': '
  
      const typeLower = type.toLowerCase();
      const key = query.toLowerCase().replace(/\s+/g, '_').replace(/[?!]/g, '');
  
      if (!result[typeLower]) {
        result[typeLower] = {};
      }
  
      if (!result[typeLower][key]) {
        result[typeLower][key] = {
          [typeLower.slice(0, -1)]: query, // Remove 's' from type for singular form
          responses: []
        };
      }
  
      result[typeLower][key].responses.push(response);
    });
  
    return result;
  }
  
const testData = [
    'Questions: what is ounces? - Response: Ounce, unit of weight in the avoirdupois system',
    'Questions: what is ounces? - Response: equal to 1/16 pound (437 1/2 grains)',
    'Questions: what is Mud dauber - Response: Mud dauber is a name commonly applied to a number of wasps',
    'Orders: shutdown! - Response: Yes Sr!',
    'Orders: Quote something! - Response: Pursue what catches your heart, not what catches your eyes.'
];

//console.log(JSON.stringify(neuron(testData), null, 2));