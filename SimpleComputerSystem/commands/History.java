
package commands;

/**
 * print a list of all or select past commands
 * 
 * @author Haowen Rui
 */
public class History extends Command {

  /**
   * constructor for all history class.
   * 
   * @param cmd the input command with its information
   */
  public History(Command cmd) {
    super(new Command.Builder(cmd.fullPath, cmd.currentDirectory).history(cmd.history));
  }

  /**
   * check if the first string has all integers
   * 
   * @param list a list of strings
   * @return true if the first string is all integers, false if not.
   */
  public static boolean stringIsDigit(String[] list) {
    for (int a = 0; a < list.length; a++) {
      char c = list[0].charAt(a);
      if (Character.isDigit(c) == false) {
        return false;
      }
    }
    return true;
  }

  /**
   * process input requirement and print out required amount of past shell inputs
   * 
   * @return any error messages
   */
  public String process() {
    String result = "";
    if (this.fullPath[0] == null) {
      for (int i = 0; i < history.size(); i++) {
        if (i < history.size() - 1)
          result = result + (i + 1 + ". " + history.get(i) + "\n");
        else
          result = result + (i + 1 + ". " + history.get(i));
      }
    } else if (this.fullPath.length == 1 && stringIsDigit(this.fullPath) == true) {
      int cmdToView = history.size() - Integer.parseInt(this.fullPath[0]);
      if (cmdToView < 0)
        cmdToView = 0;
      for (int i = 0; i < history.size() - cmdToView; i++) {
        int index = (cmdToView + i + 1);
        if (i < history.size() - cmdToView - 1) {
          result = result + (index + ". " + history.get(cmdToView + i) + "\n");
        } else
          result = result + (index + ". " + history.get(cmdToView + i));
      }
    } else {
      String param = "";
      for (String str : this.fullPath)
        param += str;
      result = ("ERROR: history: more than 1 parameter or parameter is none integer: " + param);
    }
    return result;
  }

  @Override
  /**
   * get the current working directory and send it to parent class.
   */
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
