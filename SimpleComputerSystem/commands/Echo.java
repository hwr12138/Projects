
package commands;

import java.util.ArrayList;

/**
 * print a string given that the parameters are parenthesized correctly
 * 
 * @author Haowen Rui
 */
public class Echo extends Command {

  /**
   * constructor of the echo class.
   * 
   * @param command the input command with its information
   */
  public Echo(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory)// .sepPath(command.separatedPath)
        .fileSys(command.fileSys));
  }

  /**
   * check if the format of the input string is valid (surrounded by quotation
   * marks)
   * 
   * @param str an input string to be checked
   * @return true if the format is correct
   */
  public boolean checkFormat(String str) {

    if (str.length() > 1 && (str.charAt(0) != '\"' || str.charAt(str.length() - 1) != '\"')) {
      return false;
    }
    int counter = 0;
    for (int i = 0; i < str.length(); i++) {
      if (counter == 0) {
        if (str.charAt(i) == '\"')
          counter = 1;
        else
          return false;
      } else if (counter == 1 && str.charAt(i) == '\"')
        counter = 0;
    }
    return true;
  }

  /**
   * return a line of output given one or a few input strings
   * 
   * @return the resulting string without '\"'
   */
  public String process() {
    String result = "";
    if (this.fullPath[0] == null)
      return "";
    ArrayList<String> completeList = new ArrayList<>();
    for (int i = 0; i < this.fullPath.length; i++) {
      String str = this.fullPath[i];
      if (checkFormat(str) == false) {
        return "ERROR: echo: input parameter: '" + str + "' format is invalid";
      }
      String[] list = str.split("\"");
      for (String item : list) {
        completeList.add(item);
      }
      if (i != this.fullPath.length - 1 && str != "")
        completeList.add(" ");
    }
    if (completeList.isEmpty() || completeList.get(0) == "")
      return "ERROR: echo: parameter absent";
    for (int i = 0; i < completeList.size(); i++) {
      String str = completeList.get(i);
      if (str != "")
        result += str;
      else {
        result = "ERROR: echo: parameter absent or is not parenthesized correctly";
        break;
      }
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
