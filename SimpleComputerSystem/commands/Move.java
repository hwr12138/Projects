
package commands;

import java.util.ArrayList;

import error.PathError;
import fileSystem.Directory;
import fileSystem.File;
import utility.Path;

/**
 * The Move command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class Move extends Command implements CmdInterface {
  /**
   * constructor of the Move class.
   * 
   * @param command the input command with its information
   */
  public Move(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  @Override
  /**
   * move file and directory
   * @return an empty string or error messages
   */
  public String process() {
    String result = null;
    if (this.fullPath.length != 2) {
      result = "ERROR: mv: invalid input";
    } else {
      ArrayList<String> path = Path.returnFullPath(this.fullPath[0], this.currentDirectory);
      ArrayList<String> dir = Path.returnFullPath(this.fullPath[1], this.currentDirectory);
      String name = dir.get(dir.size() - 1);
      // Move dir(1, 3, 4)
      if (this.fileSys.findDirec(path) != null) {
        String last = path.get(path.size() - 1);
        if (this.fileSys.findparentDirec(path) == null) {
          result = "ERROR: mv: cannot stat" + " \'" + this.fullPath[0] + "\'";
        } else {
          Directory dir1 = this.fileSys.findparentDirec(path);
          result = moveDir(path, last, dir1, dir, name);
        }
      }
      // Move file (1, 2, 3, 4)
      else if (this.fileSys.findFile(path) != null) {
        Directory dir1 = this.fileSys.findparentDirec(path);
        result = moveFile(path, dir1, dir, name);
      } else {
        result =
            "ERROR: mv: cannot stat" + " \'" + this.fullPath[0] + "\' : No such file or directory";
      }
    }
    return result;
  }

  /**
   * check if the name is valid or not
   * 
   * @param name
   * @return boolean to check whether the name meets the requirement or not
   */
  private boolean validName(String name) {
    char[] charOfName = name.toCharArray();
    for (char c : charOfName) {
      if (c == '!' || c == '@' || c == '#' || c == '$' || c == ' ' || c == '/' || c == '.'
          || c == '%' || c == '^' || c == '&' || c == '*' || c == '(' || c == ')' || c == '{'
          || c == '}' || c == '~' || c == '|' || c == '<' || c == '>' || c == '?' || c == '"') {
        return false;
      }
    }
    return true;
  }

  /**
   * convert a path to a string
   * 
   * @param path
   * @return a string that corresponds to an arraylist
   */
  private String arrayListToString(ArrayList<String> path) {
    String newDir = "";
    if (path.size() == 1) {
      newDir = path.get(0);
    } else {
      newDir = "";
      for (int i = 1; i < path.size(); i++) {
        newDir = newDir + "/" + path.get(i);
      }
    }
    return newDir;
  }

  /**
   * 
   * move the directory to target new Directory
   * 
   * @param path
   * @param last
   * @param dir
   * @param name
   * @param dir1
   * @return error messages
   */
  private String moveDirPart(ArrayList<String> path, String last, ArrayList<String> dir,
      String name, Directory dir1) {
    String result = "";
    PathError newExist = new PathError(dir, this.fileSys);
    if (newExist.checkPathExist()) {
      Directory dir2 = this.fileSys.findDirec(path);
      Directory dir3 = this.fileSys.findDirec(dir);
      if (dir3.direcInside.isEmpty()) {
        dir1.direcInside.remove(dir2);
        dir3.direcInside.add(dir2);
      } else {
        for (int i = 0; i < dir3.direcInside.size(); i++) {
          if (dir3.direcInside.get(i).name == dir2.name) {
            result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'"
                + this.fullPath[1] + "/" + last + "\': Directory not empty";
          } else {
            dir1.direcInside.remove(dir2);
            dir3.direcInside.add(dir2);
          }
        }
      }
    } else {
      if (!validName(name)) {
        result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'"
            + this.fullPath[1] + "\' : Not a valid name";
      } else {
        Directory dir2 = this.fileSys.findDirec(path);
        Directory dir3 = this.fileSys.findparentDirec(dir);
        dir1.direcInside.remove(dir2);
        dir3.direcInside.add(dir2);
        dir2.name = name;
      }
    }
    return result;
  }

  /**
   * 
   * check if the directory we will do mv command is appropriate
   * 
   * @param path
   * @param last
   * @param dir1
   * @return error messages
   */
  private String moveDir(ArrayList<String> path, String last, Directory dir1, ArrayList<String> dir,
      String name) {
    String result = "";
    if (this.fileSys.findFile(dir) != null) {
      result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : Not a directory";
    } else if (dir.size() == 1 || this.fileSys.findparentDirec(dir) != null) {
      String newPath = arrayListToString(path);
      String newDir = arrayListToString(dir);
      if (newDir.contains(newPath) || newDir == newPath) {
        result = "ERROR: mv: cannot move" + " \'" + this.fullPath[0]
            + "\' to a subdirectory of itself, \'" + this.fullPath[1] +  "/" + last + "\'";
      } else if (newPath.contains(newDir) && path.size() == dir.size() + 1) {
        result = "ERROR: mv: \'" + this.fullPath[0] + "\' and \'" + this.fullPath[0]
            + "\' are the same file";
      } else {
        result = moveDirPart(path, last, dir, name, dir1);
      }
    } else {
      result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : Not a directory";
    }
    return result;
  }

  /**
   * move the File to target new Directory
   * 
   * @param path
   * @param dir
   * @param name
   * @param dir1
   * @return error messages
   */
  private String moveFilePart(ArrayList<String> path, ArrayList<String> dir, String name,
      Directory dir1) {
    String result = "";
    PathError fileExist = new PathError(dir, this.fileSys);
    if (!fileExist.checkPathExist()) {
      if (!validName(name)) {
        result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'"
            + this.fullPath[1] + "\' : Not a valid name";
      } else {
        File file1 = this.fileSys.findFile(path);
        Directory dir3 = this.fileSys.findparentDirec(dir);
        dir3.fileInside.add(file1);
        dir1.fileInside.remove(file1);
        file1.name = name;
      }
    } else {
      File file1 = this.fileSys.findFile(path);
      File file2 = this.fileSys.findFile(dir);
      file2.content=file1.content;
      dir1.fileInside.remove(file1);
    }
    return result;
  }

  /**
   * check if the file we will do mv command is appropriate
   * 
   * @param path
   * @param dir1
   * @return error messages
   */
  private String moveFile(ArrayList<String> path, Directory dir1, ArrayList<String> dir,
      String name) {
    String result = "";
    if (this.fileSys.findDirec(dir) != null) {
      String newPath = arrayListToString(path);
      String newDir = arrayListToString(dir);
      if (newPath.contains(newDir) && path.size() == dir.size() + 1) {
        result = "ERROR: mv: \'" + this.fullPath[0] + "\' and \'" + this.fullPath[0]
            + "\' are the same file";
      } else {
        File file1 = this.fileSys.findFile(path);
        Directory dir3 = this.fileSys.findDirec(dir);
        dir1.fileInside.remove(file1);
        dir3.fileInside.add(file1);
      }
    } else if (this.fileSys.findparentDirec(dir) != null) {
      if (path.equals(dir)) {
        result =
            "ERROR: mv: " + this.fullPath[0] + "\' and \'" + this.fullPath[1] + "\' are identical";
      } else {
        result = moveFilePart(path, dir, name, dir1);
      }
    } else {
      result = "ERROR: mv: cannot move " + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : No such file or directory";
    }
    return result;
  }

  @Override
  /**
	 * @return the string of currentDirectory
	 */
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
