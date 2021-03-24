
package commands;

import java.util.ArrayList;

import error.PathError;
import fileSystem.Directory;
import utility.Path;
/**
 * The MakeDirectories command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class MakeDirectories extends Command implements CmdInterface {
  /**
   * constructor of the MakeDirectories class.
   * 
   * @param command the input command with its information
   */
  public MakeDirectories(Command command) {
	   super(new Command.Builder(command.fullPath, command.currentDirectory).
	            fileSys(command.fileSys));
  }

  @Override
  /**
   * make new directories
   * @return an empty string or error messages
   */
  public String process() {
      // check whether the path to two directories have errors(ie: use Path and
      // PathError)
    if(this.fullPath[0]==null)return"ERROR: mkdir: missing operand";
      for (int i = 0; i < this.fullPath.length; i++) {
        // 1.1 Path gives the full path and we take the full path except the last entry
        ArrayList<String> path = Path.returnFullPath(this.fullPath[i], this.currentDirectory);
        String name = path.get(path.size() - 1);
        path.remove(path.size() - 1);
        // if PathError returns false, means the full path except the last entry doesn't
        // exist in FileSystem, returns the error message
        PathError pathExist = new PathError(path, this.fileSys);
        if (!pathExist.checkPathExist())
          return "ERROR: mkdir: cannot create directory: No such file or directory";
        else {
          path.add(name);
          PathError dirExist = new PathError(path, this.fileSys);
          if (dirExist.checkPathExist())
            return "ERROR: mkdir: cannot create directory : File exists";
          else {
            if (!validName(name))
              return "ERROR: mkdir: cannot create directory: Not valid name";
            else {
              Directory newDir = new Directory(name);
              path.remove(path.size() - 1);
              this.fileSys.findDirec(path).direcInside.add(newDir);
            }
          }
        }
      }
      return "";
  }

  
  /**
   * check whether the path user typed in is valid or not
   * @param name
   * @return
   */
  public boolean validName(String name) {
    char[] charOfName = name.toCharArray();
    for (char c : charOfName) {
      if (c == '!' || c == '@' || c == '#' || c == '$' || c == ' ' || c == '/' || c == '.' || c == '%' || c == '^'
          || c == '&' || c == '*' || c == '(' || c == ')' || c == '{' || c == '}' || c == '~' || c == '|' || c == '<'
          || c == '>' || c == '?' || c == '"') {
        return false;
      }
    }
    return true;
  }

  @Override
  public String getWorkingDir() {
    return this.currentDirectory;
  }

}
