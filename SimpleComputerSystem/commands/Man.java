
package commands;

import java.util.HashMap;
import utility.CmdDocumentation;

/**
 * The Man command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class Man extends Command implements CmdInterface {

  /**
   * constructor of the Man class.
   * 
   * @param command the input command with its information
   */
  public Man(Command command) {
	  super(new Command.Builder(command.fullPath, command.currentDirectory));
  }

  /**
   * @return documentation for the specific command
   */
  public String process() {
    HashMap<String, String> cmdHash = new HashMap<String, String>();
    CmdDocumentation.populateManHashMap(cmdHash);

    if (this.fullPath.length > 1) {
      return "ERROR: Man: too many args";
    } else {
      if (this.fullPath[0] == null) {
        // if the input of man command is null, return error messages
        return "ERROR: Man: What manual page do you want?\n" + "For example, try 'man man'.";
      } else {
        if (cmdHash.containsKey(this.fullPath[0])) {
          return "\n" + cmdHash.get(this.fullPath[0]);
        } else {
          return "ERROR: Man: No manual entry for " + this.fullPath[0] ;
        }
      }
    }
  }

  @Override
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
