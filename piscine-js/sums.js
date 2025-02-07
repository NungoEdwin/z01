/*Create a function named sums that accepts a number and returns its partitions.

A partition is a group of numbers, where the sum of the partition is equal to the number argument. Duplicate partitions are not allowed. [1, 2] and [2, 1] are considered duplicates. The array of partitions must be sorted.

Example:

sums(4) // [ [1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2] ]
*/

function sums(n) {
    const result = [];
  
    function findPartitions(target, currentPartition, lastNumber) {
      if (target === 0) {
        result.push([...currentPartition]);
        return;
      }
  
      for (let i = lastNumber; i <= target; i++) {
        currentPartition.push(i);
        findPartitions(target - i, currentPartition, i);
        currentPartition.pop();
      }
    }
  
    findPartitions(n, [], 1);
    return result.slice(0, -1 );
  }
  
  console.log(sums(1)); // Output: [ [1, 1, 1, 1], [1, 1, 2], [1, 3], [2, 2] ]