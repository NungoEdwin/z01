public class Chifoumi{
 public static ChifoumiAction getActionBeatenBy(ChifoumiAction chifoumiAction){
		 ChifoumiAction action=chifoumiAction;
 switch (chifoumiAction){
		 case ROCK:{
		 action= ChifoumiAction.SCISSOR;
		 break;
		 }
		 case PAPER:{
		 action=ChifoumiAction.ROCK;
		 break;
		 }
		 case SCISSOR:{
		 action=ChifoumiAction.PAPER;
		 break;
		}
 }
 return action;
 }

 public static void main(String[] args) {
        System.out.println(Chifoumi.getActionBeatenBy(ChifoumiAction.ROCK));
        System.out.println(Chifoumi.getActionBeatenBy(ChifoumiAction.PAPER));
        System.out.println(Chifoumi.getActionBeatenBy(ChifoumiAction.SCISSOR));
    }


}
