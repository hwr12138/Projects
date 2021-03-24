package commands;

import java.util.ArrayList;
import error.PathError;
import utility.Path;

/**
 * The ChangeDirectory command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class ChangeDirectory extends Command implements CmdInterface {

  /**
   * constructor of the ChangeDirectory class.
   * 
   * @param command the input command with its information
   */
  public ChangeDirectory(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  @Override
  /**
   * @return an empty string or error messages
   */
  public String process() {
    String result = "";
    if (this.fullPath.length > 1) {
      result = "ERROR: cd: too many arguments";
    } else {
      if (this.fullPath[0] == null) {
        this.currentDirectory = this.fileSys.root.name;
      } else {
        ArrayList<String> path = Path.returnFullPath(this.fullPath[0], this.currentDirectory);
        PathError pathExist = new PathError(path, this.fileSys);
        // check whether the path exists or not
        if (!pathExist.checkPathExist()) {
          result = "ERROR: cd: " + this.fullPath[0] + ": No such directory";
        } else {
          // check whether the path is to a file
          if (this.fileSys.findDirec(path) != null) {
            String newDir = "";
            if (path.size() == 1) {
              newDir = path.get(0);
            } else {
              newDir = "";
              for (int i = 1; i < path.size(); i++) {
                newDir = newDir + "/" + path.get(i);
              }
            }
            this.currentDirectory = newDir;
          } else if(this.fileSys.findFile(path) != null){
            result = "ERROR: cd: " + this.fullPath[0] + ": Not a directory";
          }
        }
      }
    }
    return result;
  }

  @Override
  public String getWorkingDir() {
    // TODO Auto-generated method stub
    return this.currentDirectory;
  }


}
