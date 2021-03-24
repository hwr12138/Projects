
package commands;

import java.util.ArrayList;

import error.PathError;
import utility.Path;

/**
 * The PushDirectory command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class PushDirectory extends Command implements CmdInterface {


	/**
	 * constructor of the PushDirectory class.
     @param command the input command with its information 
	 */
  public PushDirectory(Command command) {
	  super(new Command.Builder(command.fullPath, command.currentDirectory).
	            fileSys(command.fileSys).stack(command.stack));
  }

  @Override
  /**
   * @return an empty string or error messages
   */
  public String process() {
	// check whether the input path is null
    if (this.fullPath[0] == null) {
      return "ERROR: pushd: no other directory";
    // check whether the length of the input path is larger than 1
    } else if (this.fullPath.length > 1) {
      return "ERROR: pushd: too many arguments";
    } else {
      ArrayList<String> path = 
    		  Path.returnFullPath(this.fullPath[0], this.currentDirectory);
      PathError pathExist = new PathError(path, this.fileSys);
      // check whether the path users input exists
      // if the path users input doesn't exist, return error messages
      if (!pathExist.checkPathExist()) {
        return "ERROR: pushd: No such file or directory";
      // if the path users input exists, add the current working directory to the stack
      } else {
    	if(this.fileSys.findDirec(path)!=null){
    	  this.stack.directories.add(this.getWorkingDir());
    	  /** change the current working directory to the directory corresponding
    	   * to the path users input
    	   */
    	  String updateCurr = "";
    	  if (path.size() == 1) {
    	    this.currentDirectory = path.get(0);
    	    return null;
    	    } else {
    	      for (int i = 1; i < path.size(); i++) {
    	    	updateCurr = updateCurr + "/" + path.get(i);
    	    }
    	    this.currentDirectory = updateCurr;
    	          return null;
    	        }
    	  }else {
    		  return "ERROR: pushd: Not a directory";
    	  }        
      }
    }
  }
	@Override
	public String getWorkingDir() {
		    return this.currentDirectory;
		  }

}
