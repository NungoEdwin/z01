public class ComputeArray {
    public static int[] computeArray(int[] array) {
        if (array == null) {
            return null;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int x = array[i];
            int rem = x % 3;  // remainder can be 0, 1, 2, or negative (‐1, ‐2) for negative x

            if (rem == 0) {
              result[i] = x * 5;
                
            } else if (rem == 1 || rem==-1) {
                result[i] = x + 7;
            }else if (rem==2 || rem ==-2){
                result[i] = x;
            }
        }
        return result;
    }

      public static void main(String[] args) {
        int[] array = ComputeArray.computeArray(new int[]{20, 65, 30, -67, 75, 11, -1, -30, 17});
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
