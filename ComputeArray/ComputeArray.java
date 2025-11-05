public class ComputeArray {
    public static int[] computeArray(int[] array) {
        if (array == null) {
            return null;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int x = array[i];
            int mod3 = x % 3;
            if (mod3 < 0) {
                mod3 += 3;
            }
            if (mod3 == 0) {
                result[i] = x * 5;
            } else if (mod3 == 1) {
                result[i] = x + 7;
            } else {
                // mod3 == 2
                result[i] = x;
            }
        }
        return result;
    }


      public static void main(String[] args) {
        int[] array = ComputeArray.computeArray(new int[]{9, 13, 8, 23, 1, 0, 89});
        for (int i : array) {
            System.out.print(i + " ");
        }
        System.out.println("\n");
    }
}
