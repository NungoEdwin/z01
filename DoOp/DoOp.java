public class DoOp{
public static String operate(String[] args){
	if(args.length!=3||args==null){
		return "Error";
	}
		try{	
		int left=Integer.parseInt(args[0]);
		int right=Integer.parseInt(args[2]);
		String sign=args[1];
		int tot=0;
	switch (sign){
			case "+":{
			tot=left + right;
			break;
			}
			case "-":{
			tot=left - right;
			break;
			}
			case "x":{
			tot=left * right;
			break;
			}
			case "/":{
			tot=left / right;
			break;
			}
			case "%":{
			tot= left % right;
			break;
			}
			default:{
			 return "it depend on the input";
			}			
	}
	return Integer.toString(tot);
}catch(ArithmeticException e){
	return "Error";
}
}
public static void main(String args[]){
 		System.out.println(DoOp.operate(new String[]{"1","+","2"}));
        System.out.println(DoOp.operate(new String[]{"1","-","1"}));
        System.out.println(DoOp.operate(new String[]{"1","%","0"}));
        System.out.println(DoOp.operate(args));
}

}
