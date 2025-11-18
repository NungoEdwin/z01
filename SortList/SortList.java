import java.util.ArrayList;
import java.util.List;
public class SortList {

    public static List<Integer> sort(List<Integer> list) {
        if(list==null||list.isEmpty()) {
            if(list.isEmpty()) return list;
            return new ArrayList<Integer>();
        }
  List<Integer> copy=new ArrayList(list);
        copy.sort((a,b) -> {return a.compareTo(b);});
        //copy.sort(Integer::compareTo);
 return copy; 
   	}

    public static List<Integer> sortReverse(List<Integer> list) {
    if(list==null||list.isEmpty()) {
            if(list.isEmpty()) return list;
            return new ArrayList<Integer>();
        }
    List<Integer> copy=new ArrayList(list);
   copy.sort( (a,b) -> {return -1 * a.compareTo(b);});
return copy;
   	}
  	public static void main(String[] args) {
        System.out.println(SortList.sort(List.of(15, 1, 14, 18, 14, 98, 54, -1, 12)).toString());
        System.out.println(SortList.sortReverse(List.of(15, 1, 14, 18, 14, 98, 54, -1, 12)).toString());
    }
}
