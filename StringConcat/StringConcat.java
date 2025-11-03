public class StringConcat{
public static String concat(String s1,String s2){
 if (s1==null){
 return s2;
 }else if(s2==null){
 return s1;}
 return s1 + s2;
}
public static void main(String args[]){
System.out.println(StringConcat.concat("Hello ", "étudiant !"));
 System.out.println(StringConcat.concat("", "Hello World !"));

}

}
