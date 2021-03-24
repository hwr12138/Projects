
package utility;

import java.util.ArrayList;
import error.PathError;
import fileSystem.File;
import fileSystem.FileSystem;

/**
 * 
 * @author Yilin Zhao
 *
 */
public class Redirection {

  /**
   * 
   * @param original
   * @return
   */
  public static String[] cmdPart(String[] original) {
    int length;
    if (firstRedir(original) >= 0) {
      length = firstRedir(original);
    } else {
      length = original.length;
    }
    String[] cmd;
    if (length == 0) {
      cmd = new String[1];
      cmd[0] = null;
    } else {
      cmd = new String[length];
      for (int i = 0; i < cmd.length; i++) {
        cmd[i] = original[i];
      }
    }
    return cmd;
  }

  /**
   * 
   * @param original
   * @return
   */
  public static ArrayList<String> redirPart(String[] original) {
    ArrayList<String> redir = new ArrayList<String>();
    int first = firstRedir(original);
    if (first >= 0) {
      for (int i = first; i < original.length; i++) {
        redir.add(original[i]);
      }
    }
    return redir;
  }

  /**
   * 
   * @param symbol
   * @param relative
   * @param path
   * @param fileSys
   * @param result
   * @return
   */
  public static String processRedir(ArrayList<String> redir, FileSystem fileSys, String result,
      String cwDir) {
    if (!redir.isEmpty() && !result.contains("ERROR:")) {
      if (redir.size() != 2 || redir.get(0).length() > 2) {
        result = "ERROR: Invalid input for redirection.";
      } else {
        if (result.contains("error: cat: ")) {
          if (result.startsWith("error: cat: ")) {
            result = "ERROR: " + result.substring(result.indexOf("c"));
          } else {
            String input = result.substring(result.indexOf("error: cat: "));
            input ="ERROR: " + input.substring(input.indexOf("c"));
            result = input + "\n"
                + mkFile(fileSys, redir.get(0), redir.get(1),
                    result.substring(0, result.indexOf("error: cat: ")), cwDir);
          }
        } else {
          result = mkFile(fileSys, redir.get(0), redir.get(1), result, cwDir);
        }
      }
    }
    return result;

  }

  /**
   * @param fileSys
   * @param symbol
   * @param givenFile
   * @param result
   * @param cwDir
   * @return
   */
  protected static String mkFile(FileSystem fileSys, String symbol, String givenFile, String result,
      String cwDir) {
    if(result.equals("")) return "";
    ArrayList<String> path = Path.returnFullPath(givenFile, cwDir);
    String file = path.get(path.size() - 1);
    PathError pathCheck = new PathError(path, fileSys);
    if (pathCheck.checkPathExist()) {
      if (fileSys.findDirec(path) != null) {
        return "ERROR: " + givenFile + ": Is a directory";
      } else {
        if (symbol.equals(">")) {
          fileSys.findFile(path).content = result;
        } else if (symbol.equals(">>")) {
          fileSys.findFile(path).content = fileSys.findFile(path).content + result;
        }
        return "";
      }
    } else if (path.size() > 1) {
      path.remove(path.size() - 1);
      if (fileSys.findFile(path) != null) {
        return "ERROR: " + givenFile + ": Not a directory";
      } else if (fileSys.findDirec(path) != null) {
        if (!validName(file))
          return "ERROR: " + givenFile + ": InvalidFileName";
        File newFile = new File(file, result);
        fileSys.findDirec(path).fileInside.add(newFile);
        return "";
      } else {
        return "ERROR: " + givenFile + ": No such file or directory";
      }
    } else {
      return "ERROR: " + givenFile + "Permission denied";
    }
  }

  /**
   * @param fullPath
   * @return
   */
  protected static int firstRedir(String[] fullPath) {
    int first = -1;
    if (fullPath[0] == null)
      return first;
    for (int i = 0; i < fullPath.length; i++) {
      if (fullPath[i].contains(">"))
        return i;
    }
    return first;
  }

  /**
   * @param name
   * @return
   */
  protected static boolean validName(String name) {
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

}
