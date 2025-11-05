public class Palindrome {
    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        String str=s.toLowerCase();
        int left  = 0;
        int right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
     public static void main(String[] args) {
        System.out.println(Palindrome.isPalindrome("ressasser"));  // expected true
        System.out.println(Palindrome.isPalindrome("Bonjour"));     // expected false
    }
}

