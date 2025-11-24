public class TransportFactory{
    public static Transport getTransport(String type){
     //   if (type == null) {
       //     return null;
       // }
        //type = type.trim().toLowerCase();
        if(type.equals("car")){
            return new Car();
        }
        else if(type.equals("plane")){
            return new Plane();
        }
        return null;
    }
}