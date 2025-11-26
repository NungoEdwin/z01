public class StringContains{
public static boolean isStringContainedIn(String subString,String s){

if (subString==null || s==null){return false;}
return s.contains(subString);
}

public static void main(String args[]){
System.out.println(isStringContainedIn("Hell", "Highway to Hell"));
        System.out.println(isStringContainedIn("Hell", "Hello World !"));
        System.out.println(isStringContainedIn("Bonjour", "hello World !"));

}
}
