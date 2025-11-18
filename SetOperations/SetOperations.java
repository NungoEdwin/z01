import java.util.HashSet;
import java.util.Set;

public class SetOperations {
    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        if(set1==null&&set2==null){
		 return null;
		}else if(set1==null){
		return set2;
		}else if(set2==null){
		return set1;
		}
    Set<Integer> union=new HashSet<>();
	set1.forEach(a ->{ union.add(a);});
    set2.forEach(a ->{ union.add(a);});
	return union;
    }

    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        if(set1==null&&set2==null){
		 return null;
		}else if(set1==null||set2==null){
		return new HashSet<>();
		}
        Set<Integer> inter=new HashSet<>();
        set1.forEach(a->{if(set2.contains(a)){inter.add(a);}});
        return inter;
    }
public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<>();
        set1.add(1);
        set1.add(2);
        set1.add(3);

        Set<Integer> set2 = new HashSet<>();
        set2.add(2);
        set2.add(3);
        set2.add(4);

        Set<Integer> unionSet = SetOperations.union(set1, set2);
        System.out.println(unionSet); // Expected Output: [1, 2, 3, 4]

        Set<Integer> intersectionSet = SetOperations.intersection(set1, set2);
        System.out.println(intersectionSet); // Expected Output: [2, 3]
    }
}
