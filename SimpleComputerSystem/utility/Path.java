
package utility;

import java.util.ArrayList;

/**
 * The Path Class
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class Path {
  /**
   * change any path users input into the full path related to it
   * 
   * @param path, which is the path users input
   * @param currentWorking, which is the current working directory
   * @return an arrayList contains every directory in the full path related to the path users input
   *         in the same order as the original path has
   */
  public static ArrayList<String> returnFullPath(String path, String currentWorking) {
    // check whether the input path is the root
    // if the input path is the root, return the arrayList only contains it
    if (path.charAt(0) == '/') {
      return separatePath(path);
    } else {
      if (currentWorking.equals("/")) {
        String all = currentWorking + path;
        ArrayList<String> fin = new ArrayList<String>();
        fin = separatePath(all);
        return fin;
      } else {
        String current = currentWorking;
        String all = current + "/" + path;
        ArrayList<String> fin = new ArrayList<String>();
        fin = separatePath(all);
        return fin;
      }
    }
  }

  /**
   * separate any path by "/" symbol to an arrayList that contains different elements and has the
   * same order as the original path has
   * 
   * @param path, which is a path
   * @return an arrayList contains every directory in the path in the same order as the original
   *         path has
   */
  public static ArrayList<String> separatePath(String path) {
    ArrayList<String> dirsList = new ArrayList<String>();
    if (path.contains("//")) {
      dirsList.add("/");
      dirsList.add("/");
      dirsList.add("/");
    } else {
      dirsList.add("/");
      String[] allDirs = path.split("/");
      for (int i = 0; i < allDirs.length; i++) {
        if (allDirs[i].equals("") || allDirs[i].equals("."))
          continue;
        else if (allDirs[i].equals("..")) {
          if (dirsList.size() == 1) {
          } else {
            dirsList.remove(dirsList.size() - 1);
          }
        } else {
          dirsList.add(allDirs[i]);
        }
      }
    }
    return dirsList;
  }

  /**
   * 
   * @param inputs
   * @param currentDirec
   * @return
   */
  public static ArrayList<String> separate(String[] inputs, String currentDirec) {
    ArrayList<String> separatedPath = new ArrayList<String>();
    for (String input : inputs) {
      if (input == null)
        continue;
      if (input.charAt(0) != '\"' && input.charAt(0) != '>') {
        ArrayList<String> path = new ArrayList<String>();
        path = returnFullPath(input, currentDirec);
        if (separatedPath.size() > 0) {
          separatedPath.remove(separatedPath.size() - 1);
        }
        for (String item : path) {
          separatedPath.add(item);
        }
      } else {
        separatedPath.add(input);
      }
      separatedPath.add(" ");
    }
    return separatedPath;
  }
}
