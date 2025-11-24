public class ConcatStrategy implements OperationStrategy {

    @Override
    public int execute(int a, int b) {
        // Convert to string, concatenate, convert back to int
        return Integer.parseInt("" + a + b);
    }
}
