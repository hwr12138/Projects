
package error;

import java.util.ArrayList;

import fileSystem.Directory;
import fileSystem.File;
import fileSystem.FileSystem;

public class PathError {

  ArrayList<String> fullPath;
  FileSystem fileSys;

  /**
   * constructor for the PathError class. contains an arraylist of one full path
   * and the root of the file system
   * 
   * @param inputPath a full path separated by the /.
   * @param fileSys   the root of the file system
   */
  public PathError(ArrayList<String> inputPath, FileSystem fileSys) {
    this.fullPath = inputPath;
    this.fileSys = fileSys;
  }

  /**
   * search the current directory for existing file or directory
   * 
   * @param current  the current directory to be searched
   * @param fullPath the arraylist of full path used
   * @param i        the current index we are at in the full path
   * @return true if indicated file or directory exists
   */
  public static boolean search(Directory current, ArrayList<String> fullPath, int i) {
    for (File file : current.fileInside) {
      if (file.name.equals(fullPath.get(i)))
        return true;
    }
    for (Directory direc : current.direcInside) {
      if (direc.name.equals(fullPath.get(i)))
        return true;
    }
    return false;
  }

  /**
   * Process to check if this full path exists, can process one full path at a
   * time
   * 
   * @return true if path exists, and false if it does not
   */
  public boolean checkPathExist() {
    Directory current = this.fileSys.root;
    if (this.fullPath.size() == 1)
      return true;
    for (int i = 1; i < this.fullPath.size(); i++) {
      if (this.fullPath.size() == 2) {
        if (search(current, fullPath, i) == true)
          return true;
        else
          return false;
      } else {
        for (int j = 0; j < current.direcInside.size(); j++) {
          Directory direc = current.direcInside.get(j);
          if (j == current.direcInside.size() - 1 && direc.name.equals(fullPath.get(i)) == false) {
            return false;
          } else {
            if (direc.name.equals(fullPath.get(i))) {
              current = direc;
              if (i == this.fullPath.size() - 2)
                if (search(current, fullPath, i + 1) == true)
                  return true;
                else
                  return false;
              break;
            }
          }
        }
      }
    }
    return false;
  }

}
