
package commands;

import fileSystem.File;
import fileSystem.Directory;

import java.util.ArrayList;

import utility.Path;

/**
 * process a filesystem and return a list of files and directories
 */
public class List extends Command implements CmdInterface {

  /**
   * constructor of the List class.
   * 
   * @param command the input command with its information
   */
  public List(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  /**
   * Display all files and directories in the current working directory
   * @return the output of running ls command to singlePath or error message
   */
  public String process() {
    ArrayList<String> separatedPath = new ArrayList<String>();
    separatedPath = Path.separate(this.fullPath, this.currentDirectory);
    if (separatedPath.indexOf("-R") != separatedPath.lastIndexOf("-R")) {
      return "Error:multiple -R";
    }
    String totaloutput = "";
    int index = 0;
    String output;
    ArrayList<String> singlePath = new ArrayList<String>();
    if (fullPath.length == 0 || fullPath[0] == null || !fullPath[0].equals("-R")) {
      if (!(index < separatedPath.size() - 1)) {
        singlePath = Path.separatePath(currentDirectory);
      }
      while (index < separatedPath.size() - 1) {// go through all separate path
        if (separatedPath.get(index) == "/" && index != 0) {
          output = lsPerPath(singlePath);
          totaloutput += output;
          singlePath.clear();
          if (output.contains("ERROR:"))
            break;
        }
        singlePath.add(separatedPath.get(index));
        index++;
      }
      totaloutput += lsPerPath(singlePath);// run the last path
    } else if (fullPath[0].equals("-R")) {
      index = 3;// push the starting index 3 forwards since first two are [/,/,-R]
      if (!(index < separatedPath.size() - 1))
        singlePath = Path.separatePath(currentDirectory);
      while (index < separatedPath.size() - 1) {// go through all separate path
        if (separatedPath.get(index) == "/" && index != 3) {
          output = lsPerPath(singlePath);
          totaloutput += output;
          singlePath.clear();
          if (output.contains("ERROR:"))
            break;
        }
        singlePath.add(separatedPath.get(index));
        index++;
      }
      totaloutput += lsRPerPath(singlePath);// run the last path
    } else
      return "ERROR:Inappopriate Input";
    return totaloutput;
  }

  /**
   * 
   * do ls if no -R in command
   * 
   * @param singlePath one path among the input [Paths..]
   * @return the output of running ls command to singlePath or error message
   */
  public String lsPerPath(ArrayList<String> singlePath) {
    if (singlePath.size() == 0) {
      return "";
    }
    if (this.fileSys.findFile(singlePath) != null) {
      // if it's a specific file
      File file = this.fileSys.findFile(singlePath);
      return file.name;
    } else if (this.fileSys.findDirec(singlePath) != null) {
      // if it's a specific directory
      Directory direc = this.fileSys.findDirec(singlePath);
      String dirContent = "";

      dirContent += direc.name;// first show directory's name
      dirContent += ":\n";
      for (Directory dir : direc.direcInside) {// directory inside
        dirContent += dir.name;
        dirContent += " ";
      }
      for (File file : direc.fileInside) {// filename inside
        dirContent += file.name;
        dirContent += " ";
      }
      return dirContent + "\n";
    } else {
      // need to be replaced by error class
      String errorMessage = "\nERROR:List command get one error here because path doesnot exist.";
      return errorMessage;
    }
  }

  /**
   * do ls with -R in command
   * 
   * @param singlePath one path among the input [Paths..]
   * @return the output of running ls command to singlePath or error message
   */
  public String lsRPerPath(ArrayList<String> singlePath) {
    if (this.fileSys.findFile(singlePath) != null) {
      File file = this.fileSys.findFile(singlePath);
      return file.name + "\n";
    } else if (this.fileSys.findDirec(singlePath) != null) {
      Directory direc = this.fileSys.findDirec(singlePath);// if we reach a specific directory
      ArrayList<String> newSinglePath = new ArrayList<String>();
      newSinglePath.addAll(singlePath);
      String dirContent = "";
      boolean emptyMark = false;
      dirContent += direc.name;// first show directory's name
      dirContent += ":\n";
      for (Directory dir : direc.direcInside) {// directory inside
        dirContent += dir.name;
        dirContent += " ";
        emptyMark = true;
      }
      for (File file : direc.fileInside) {// filename inside
        dirContent += file.name;
        dirContent += " ";
        emptyMark = true;
      }
      if (emptyMark == true) {
        dirContent += "\n\n";
      }
      for (Directory dir : direc.direcInside) {// do this recursively
        newSinglePath.add(dir.name);
        dirContent += lsRPerPath(newSinglePath);
        dirContent += "\n";
        newSinglePath.remove(newSinglePath.size() - 1);
      }
      return dirContent;
    } else {
      String errorMessage = "\nERROR:List command get one error here because path doesnot exist.";
      return errorMessage; // return appropriate error message
    }
  }

  /**
   * @return the string of currentDirectory
   */
  public String getWorkingDir() {
    return this.currentDirectory;
  }

}
