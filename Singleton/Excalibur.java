public class Excalibur {
    // hold the single instance — note the static
    private static Excalibur INSTANCE;

    // the name field
    private final String name;

    // private constructor: prevents external instantiation
    private Excalibur(String name) {
        this.name = name;
    }

    // public method to get the name
    public String getName() {
        return name;
    }

    // public static method to get the singleton instance
    public static Excalibur getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Excalibur("Sword");
        }
        return INSTANCE;
    }
 public static void main(String[] args)  {
        System.out.println(Excalibur.getInstance().getName());
    }
}

