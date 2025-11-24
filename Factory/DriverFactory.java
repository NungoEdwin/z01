public class DriverFactory {
    public static Driver getDriver(String type) {
        if (type == null){
              return null;
        }
        type = type.trim().toLowerCase();
        if (type.equals("car")) {
            return new CarDriver();
        } else if (type.equals("plane")) {
            return new PlaneDriver();
        }
        return null;
    }
}