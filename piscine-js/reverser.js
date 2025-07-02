function reverse(arrstr) {
  let temp = "";
  let arr = [];
  let string = false;
  for (let i = arrstr.length - 1; i >= 0; i--) {
    if (typeof arrstr == "string") {
      string = true;
      temp += arrstr[i];
    } else {
      arr.push(arrstr[i]);
    }
  }
  if (string) {
    return temp;
  } else {
    return arr;
  }
}
//console.log(reverse("abcd"));
