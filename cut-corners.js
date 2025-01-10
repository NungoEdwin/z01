function round(num) {
  let count = 0;
  let isNeg = false;

  if (num < 0) {
    isNeg = true;
    num = -1 * num;
  }
  let steps = [100000, 10000, 1000, 100, 10, 1];

  for (let step of steps) {
    if (step > num) {
      continue;
    }
    for (let i = num; i >= step; i -= step) {
      num -= step;
      count += step;

      if (num < 1) {
        if (num >= 0.5) {
          count += 1;
          break;
        }
      }
    }
  }

  if (isNeg) {
    return -1 * count;
  }
  return count;
}

 function ceil(num){
    let count = 0;
  let isNeg = false;

  if (num < 0) {
    isNeg = true;
    num = -1 * num;
  }
  let steps = [100000, 10000, 1000, 100, 10, 1];

  for (let step of steps) {
    if (step > num) {
      continue;
    }
    for (let i = num; i >= step; i -= step) {
      num -= step;
      count += step;

      if (num < 1) {
        if (isNeg==true) {
          //count += 1;
          break;
        }else{
            count += 1;
        }
      }
    }
  }

  if (isNeg) {
    return -1 * count;
  }
  return count;

 };
 function floor(num){
    let count = 0;
  let isNeg = false;

  if (num < 0) {
    isNeg = true;
    num = -1 * num;
  }
  let steps = [100000, 10000, 1000, 100, 10, 1];

  for (let step of steps) {
    if (step > num) {
      continue;
    }
    for (let i = num; i >= step; i -= step) {
      num -= step;
      count += step;

      if (num < 1) {
        if (isNeg==true) {
          count += 1;
          break;
        }else{
            //count += 1;
        }
      }
    }
  }

  if (isNeg) {
    return -1 * count;
  }
  return count;

 };

function trunc(num){
    let count = 0;
    let isNeg = false;
  
    if (num < 0) {
      isNeg = true;
      num = -1 * num;
    }
    let steps = [100000, 10000, 1000, 100, 10, 1];
  
    for (let step of steps) {
      if (step > num) {
        continue;
      }
      for (let i = num; i >= step; i -= step) {
        num -= step;
        count += step;
  
        if (num < 1) {
            break;
        
        }
      }
    }
  
    if (isNeg) {
      return -1 * count;
    }
    return count;
  
};
//const nums = [3.7, -3.7, 3.1, -3.1]

//console.log(nums.map(ceil));