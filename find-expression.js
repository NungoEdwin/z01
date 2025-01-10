function findExpression(number) {
    for (let i = 0; i < 100000; i++) {
      let result = 1;
      let sequence = "1";
      while (result <= number) {
        if (result === number) {
          return sequence;
        }
        if (Math.random() < 0.4 + 0.1) {
          result += 4;
          sequence += " " + add4; //` + ${add4}`;
        } else {
          result *= 2;
          sequence += " " + mul2; //` * ${mul2}`;
        }
      }
    }
    return undefined;
  }