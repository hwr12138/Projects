
package commands;

import java.io.File;
import java.io.FileWriter;

/**
 * save a document containing all previous user input and output to the local
 * computer
 * 
 * @author Haowen Rui
 */
public class SaveJShell extends Command {
  /**
   * constructor for SaveJShell
   * 
   * @param command contains all the needed input values
   */
  public SaveJShell(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).inputOutput(command.inputOutput));
  }

  /**
   * save all past user inputs and user outputs into a local file
   * 
   * @return ""
   */
  public String process() {
    if (this.fullPath.length != 1) {
      String param = "";
      for (String str : this.fullPath)
        param += str;
      return "ERROR: saveJShell: wrong number of parameters: " + param;
    } else {
      try {
        File file = new File(fullPath[0]);
        if (file.createNewFile()) {
          FileWriter fw = new FileWriter(file);
          for (String str : this.inputOutput) {
            fw.write(str + "\n");
          }
          fw.close();
        } else {
          FileWriter fw = new FileWriter(file);
          fw.flush();
          for (String str : this.inputOutput) {
            fw.write(str + "\n");
          }
          fw.close();
        }
      } catch (Exception e) {
        System.out.println("ERROR: saveJShell: error with file creation");
      }
      return "";
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
