
package commands;

import java.io.File;
import java.util.Scanner;

/**
 * load a document containing all previous user input and output to the currect
 * JShell
 * 
 * @author Haowen Rui
 */
public class LoadJShell extends Command {
  /**
   * constructor for LoadJShell
   * 
   * @param command contains all the needed input values
   */
  public LoadJShell(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).inputOutput(command.inputOutput));
  }

  /**
   * load all past user inputs and user outputs from a local file
   * 
   * @return ""
   */
  public String process() {
    if (this.fullPath.length != 1) {
      String param = "";
      for (String str : this.fullPath)
        param += str;
      return "ERROR: loadJShell: wrong number of parameters: " + param;
    } else if (this.inputOutput.size() > 1)
      return "ERROR: loadJShell disabled: there exist previous input";
    else {
      String result = "";
      try {
        File file = new File(fullPath[0]);
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
          result += (sc.nextLine() + "\n");
        }
        sc.close();
      } catch (Exception e) {
        System.out.println("ERROR: loadJShell: file does not exist");
      }
      return result.substring(0, result.length() - 1);
    }
  }

  @Override
  /**
   * get the current working directory and send it to parent class.
   */
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
