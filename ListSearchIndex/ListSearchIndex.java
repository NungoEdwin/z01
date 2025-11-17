import java.util.List;
import java.util.ArrayList;
public class ListSearchIndex{
public static Integer findLastIndex(List<Integer> list,Integer value){
if(list==null){
return null;
}
Integer index;
index=list.lastIndexOf(value);
index=index.equals(-1)?null:index;
return index;
}


public static Integer findFirstIndex(List<Integer> list,Integer value){
if(list==null){
return null;
}
	Integer index;
index=list.indexOf(value);
index=index.equals(-1)?null:index;
return index;
}
public static List<Integer> findAllIndexes(List<Integer> list,Integer value){
if(list==null){
return null;
}
List<Integer> ret= new ArrayList<Integer>();
for(int i=0;i<list.size();i++){
if(list.get(i).equals(value)){
ret.add(i);
}
}
return ret;
}
public static void main(String[] args) {
        System.out.println(ListSearchIndex.findLastIndex(List.of(9, 13, 89, 8, 23, 1, 0, 89), 89));
        System.out.println(ListSearchIndex.findFirstIndex(List.of(9, 13, 89, 8, 23, 1, 0, 89), 89));
        System.out.println(ListSearchIndex.findAllIndexes(List.of(9, 13, 89, 8, 23, 1, 0, 89), 89).toString());
    }
}
