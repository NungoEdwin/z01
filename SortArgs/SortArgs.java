import java.util.Arrays;
import java.io.*;
public class SortArgs {
    public static void sort(String[] args) {
        if (args==null || args.length==0){
            System.out.println();
            return;
        }
        //convert String array to int Array
        int[] numbers = new int[args.length];
        for (int i = 0; i < args.length; i++) {
            numbers[i] = Integer.parseInt(args[i]);
        
        }
        //sort the array
        Arrays.sort(numbers);
        //print the sorted array with spaces and new line at the end
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i] );
            if (i < numbers.length - 1) {
                System.out.print(" ");
            }
        
        }
        System.out.println();

    }

    public static void main(String[] args)throws IOException{
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        var defaultOut = System.out;

        System.setOut(printStream);
        SortArgs.sort(new String[]{"4", "2", "1", "3"});
        System.setOut(defaultOut);

        String output = outputStream.toString();
        System.out.println(output.equals("1 2 3 4\n"));
    
    }
}
