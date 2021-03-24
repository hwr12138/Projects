
package commands;

import java.util.ArrayList;
import utility.Path;

/**
 * The Concatenate command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class Concatenate extends Command implements CmdInterface {
  /**
   * constructor of the Concatenate class.
   * 
   * @param command the input command with its information
   */
  public Concatenate(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  @Override
  /**
   * return content of a file
   * 
   * @return the contents of the files users input
   */
  public String process() {
    String result = "";
    if (this.fullPath[0] == null) {
      result = "ERROR: cat: Not the required number of arguments";
    } else {
      for (int i = 0; i < this.fullPath.length; i++) {
        ArrayList<String> path = new ArrayList<String>();
        path = Path.returnFullPath(this.fullPath[i], this.currentDirectory);
        if (this.fileSys.findFile(path) != null) {
          result = result + this.fileSys.findFile(path).content + "\n";
        } else if (this.fileSys.findDirec(path) != null) {
          result = "cat: " + this.fullPath[i] + ": Is a directory";
        } else {
          result = result + "error: cat: " + this.fullPath[i] + ": No such file or directory";
        }
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
