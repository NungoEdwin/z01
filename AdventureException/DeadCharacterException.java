public class DeadCharacterException extends Exception{
  private Character character;

  public DeadCharacterException(Character character){
     this.character = character;
  }

  @Override
  public String getMessage() {
    String type = character.getClass().getSimpleName().toLowerCase();
    return "The " + type + " " + character.getName() + " is dead.";
  }
}