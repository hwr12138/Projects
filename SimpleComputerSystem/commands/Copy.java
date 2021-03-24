
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
public class Copy extends Command implements CmdInterface {
  /**
   * constructor of the Copy class.
   * 
   * @param command the input command with its information
   */
  public Copy(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  @Override
  /**
   * copy file and directory
   * @return an empty string or error messages
   */
  public String process() {
    String result = null;
    if (this.fullPath.length != 2) {
      result = "ERROR: cp: invalid input";
    } else {
      ArrayList<String> path = Path.returnFullPath(this.fullPath[0], this.currentDirectory);
      ArrayList<String> dir = Path.returnFullPath(this.fullPath[1], this.currentDirectory);
      String name = dir.get(dir.size() - 1);
      // Copy dir(1, 3, 4)
      if (this.fileSys.findDirec(path) != null) {
        String last = path.get(path.size() - 1);
        if (this.fileSys.findparentDirec(path) == null) {
          result = "ERROR: cp: cannot stat" + " \'" + this.fullPath[0] + "\'";
        } else {
          Directory dir1 = this.fileSys.findparentDirec(path);
          result = copyDir(path, last, dir1, dir, name);
        }
      }
      // Copy file (1, 2, 3, 4)
      else if (this.fileSys.findFile(path) != null) {
        Directory dir1 = this.fileSys.findparentDirec(path);
        result = copyFile(path, dir1, dir, name);
      } else {
        result =
            "ERROR: cp: cannot stat" + " \'" + this.fullPath[0] + "\' : No such file or directory";
      }
    }
    return result;
  }

  /**
   * check name valid
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
   * change arraylist to string
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
   * copy content in directory
   * @param old
   * @return a copy of the target directory
   */
  public static Directory dirCopy(Directory old) {
    Directory aft = new Directory(old.name);
    if (old.direcInside.size() != 0) {
      for (int i = 0; i < old.direcInside.size(); i++) {
        aft.direcInside.add(dirCopy(old.direcInside.get(i)));
      }
    }
    if (old.fileInside.size() != 0) {
      for (int i = 0; i < old.fileInside.size(); i++) {
        aft.fileInside.add(fileCopy(old.fileInside.get(i)));
      }
    }
    return aft;
  }

  /**
   * copy content in file
   * @param old
   * @return a copy of the target file
   */
  public static File fileCopy(File old) {
    File aft = new File(old.name, old.content);
    return aft;
  }

  /**
   * the directory that contains the directories and files from both two diretcories
   * @param old
   * @param aft
   * @return the directory that contains the directories and files from both two diretcories
   */
  public static Directory specialDir(Directory old, Directory aft) {
    if (!old.direcInside.isEmpty()) {
      for (int i = 0; i < old.direcInside.size(); i++) {
        aft.direcInside.add(dirCopy(old.direcInside.get(i)));
      }
    }
    if (!old.fileInside.isEmpty()) {
      for (int i = 0; i < old.fileInside.size(); i++) {
        aft.fileInside.add(fileCopy(old.fileInside.get(i)));
      }
    }
    return aft;
  }

  /**
   * the file that keeps the file name and content
   * @param old
   * @param aft
   * @return the file that keeps the file name and content
   */
  public static File specialFile(File old, File aft) {
    aft.content = old.content;
    return aft;
  }

  /**
   * part of process dir
   * @param path
   * @param dir
   * @param name
   * @return error messages
   */
  private String copyDirPart(ArrayList<String> path, ArrayList<String> dir, String name) {
    String result = "";
    PathError newExist = new PathError(dir, this.fileSys);
    if (newExist.checkPathExist()) {
      Directory dir2 = this.fileSys.findDirec(path);
      Directory dir3 = this.fileSys.findDirec(dir);
      if (dir3.direcInside.isEmpty()) {
        dir3.direcInside.add(dirCopy(dir2));
      } else {
        for (int i = 0; i < dir3.direcInside.size(); i++) {
          if (dir3.direcInside.get(i).name == dir2.name) {
            dir.add(dir3.direcInside.get(i).name);
            Directory dir4 = this.fileSys.findDirec(dir);
            specialDir(dir2, dir4);
            return "";
          }          
        }
        dir3.direcInside.add(dirCopy(dir2));
      }

    } else {
      if (!validName(name)) {
        result = "ERROR: cp: cannot copy " + " \'" + this.fullPath[0] + "\' to \'"
            + this.fullPath[1] + "\' : Not a valid name";
      } else {
        Directory dir2 = this.fileSys.findDirec(path);
        Directory dir3 = this.fileSys.findparentDirec(dir);
        Directory dir4 = dirCopy(dir2);
        dir4.name = name;
        dir3.direcInside.add(dir4);
      }
    }
    return result;
  }

  /**
   * process dir
   * @param path
   * @param last
   * @param dir1
   * @return error messages
   */


  private String copyDir(ArrayList<String> path, String last, Directory dir1, ArrayList<String> dir,
      String name) {
    String result = "";
    if (this.fileSys.findFile(dir) != null) {
      result = "ERROR: cp: cannot copy" + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : Not a directory";
    } else if (dir.size() == 1 || this.fileSys.findparentDirec(dir) != null) {
      String newPath = arrayListToString(path);
      String newDir = arrayListToString(dir);
      if (newDir.contains(newPath) || newDir == newPath) {
        result = "ERROR: cp: name to long (not copied)";
      } else if (newPath.contains(newDir) && path.size() == dir.size() + 1) {
        result = "ERROR: cp: \'" + this.fullPath[0] + "\' and \'" + this.fullPath[0]
            + "\' are identical (not copied)";
      } else {
        result = copyDirPart(path, dir, name);
      }
    } else {
      result = "ERROR: cp: cannot copy " + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : Not a directory";
    }
    return result;
  }

  /**
   * part of process file
   * @param path
   * @param dir
   * @param name
   * @param dir1
   */
  private String copyFilePart(ArrayList<String> path, ArrayList<String> dir, String name,
      Directory dir1) {
    String result = "";
    PathError fileExist = new PathError(dir, this.fileSys);
    if (!fileExist.checkPathExist()) {
      if (!validName(name)) {
        result = "ERROR: cp: cannot copy " + " \'" + this.fullPath[0] + "\' to \'"
            + this.fullPath[1] + "\' : Not a valid name";
      } else {
        File file1 = this.fileSys.findFile(path);
        Directory dir3 = this.fileSys.findparentDirec(dir);
        File file2 = fileCopy(file1);
        file2.name = name;
        dir3.fileInside.add(file2);
      }
    } else {
      File file1 = this.fileSys.findFile(path);
      File file2 = this.fileSys.findFile(dir);
      specialFile(file1, file2);
    }
    return result;
  }

  /**
   * process file
   * @param path
   * @param dir1
   * @return error messages
   */
  private String copyFile(ArrayList<String> path, Directory dir1, ArrayList<String> dir,
      String name) {
    String result = "";
    if (this.fileSys.findDirec(dir) != null) {
      String newPath = arrayListToString(path);
      String newDir = arrayListToString(dir);
      if (newPath.contains(newDir) && path.size() == dir.size() + 1) {
        result = "ERROR: cp: \'" + this.fullPath[0] + "\' and \'" + this.fullPath[0]
            + "\' are the same file";
      } else {
        File file1 = this.fileSys.findFile(path);
        Directory dir3 = this.fileSys.findDirec(dir);
        dir3.fileInside.add(fileCopy(file1));
      }
    } else if (this.fileSys.findparentDirec(dir) != null) {
      if (path.equals(dir)) {
        result = "ERROR: cp: " + this.fullPath[0] + "\' and \'" + this.fullPath[1]
            + "\' are identical (not copied)";
      } else {
        result = copyFilePart(path, dir, name, dir1);
      }
    } else {
      result = "ERROR: cp: cannot copy " + " \'" + this.fullPath[0] + "\' to \'" + this.fullPath[1]
          + "\' : No such file or directory";
    }
    return result;
  }

  @Override
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
