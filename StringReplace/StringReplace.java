public class StringReplace {
public static String replace(String s,char target,char replacement){
     if (s==null)return null;
		return s.replace(target,replacement);
}
public static String replace(String s, String target,String replacement){
 if (s==null)return null;
 return s.replaceAll(target,replacement);
}
public static void main(String args[]){
System.out.println(replace("javatpoint is a very good website", 'a', 'e'));
        System.out.println(replace("my name is khan my name is java", "is","was"));
        System.out.println(replace("hey i'am java", "I'am","was"));

}

}
