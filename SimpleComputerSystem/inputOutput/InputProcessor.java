
package inputOutput;

import java.util.ArrayList;

/**
 * The InputProcessor
 * 
 * @author Yilin Zhao
 *
 */
public class InputProcessor {
  public String command;
  public String[] fullPath;
  String[] empty = new String[1];

  /**
   * constructor of the InputProcessor
   * 
   * @param command
   * @param fullPath
   * @param empty
   */
  public InputProcessor(String input) {

    ArrayList<String> cmd = processSPACE(input);
    if (cmd.isEmpty()) {
      // set command to null if there is no statement
      this.command = null;
    } else if (cmd.size() == 1) {
      empty[0] = null;

      // set the command fullPath as an empty string
      cmd = processRedir(cmd);
      if (cmd.size() == 1) {
        this.command = cmd.get(0);
        this.fullPath = empty;
      } else {
        this.command = cmd.get(0);
        cmd.remove(0);
        String[] argus = new String[cmd.size()];
        for (int i = 0; i < cmd.size(); i++)
          argus[i] = cmd.get(i);
        this.fullPath = argus;
      }
    } else {
      cmd = processRedir(cmd);
      this.command = cmd.get(0);
      cmd.remove(0);
      // set all the other statements to the fullPath
      String[] argus = new String[cmd.size()];
      for (int i = 0; i < cmd.size(); i++)
        argus[i] = cmd.get(i);
      this.fullPath = argus;
    }
  }


  /**
   * 
   * @param input
   * @return ArrayList processeCmd
   */
  protected static ArrayList<String> processSPACE(String input) {
    ArrayList<String> cmd = new ArrayList<String>();

    // split the input with " "
    String[] allArgs = input.split(" ");
    for (int i = 0; i < allArgs.length; i++) {
      if (allArgs[i].startsWith("\"")) {
        if (allArgs[i].lastIndexOf("\"") > 0)
          if (allArgs[i].endsWith("\"")) {
            cmd.add(allArgs[i]);
          } else {
            cmd.add(allArgs[i].substring(0, allArgs[i].indexOf("\"", 1) + 1));
            cmd.add(allArgs[i].substring(allArgs[i].indexOf("\"", 1) + 1));
          }
        else {
          String newStr = allArgs[i];
          if (i == allArgs.length || remain(i + 1, allArgs)) {
            cmd.add(newStr);
          } else {
            for (int j = i + 1; j < allArgs.length; j++) {
              if (allArgs[j].contains("\"")) {
                newStr = newStr + " " + allArgs[j];
                if (allArgs[j].endsWith("\"")) {
                  cmd.add(newStr);
                } else {
                  cmd.add(newStr.substring(0, newStr.indexOf("\"", 1) + 1));
                  cmd.add(newStr.substring(newStr.indexOf("\"", 1) + 1));
                }
                i = j;
                break;
              } else {
                newStr = newStr + " " + allArgs[j];
              }
              while (j + 1 == allArgs.length) {
                cmd.add(newStr);
                j++;
              }
              i = j;
            }
          }
        }
      } else {
        if (!allArgs[i].equals(""))
          cmd.add(allArgs[i]);
      }
    }
    return cmd;
  }

  /**
   * 
   * @param cmd
   * @return ArrayList
   */
  protected static ArrayList<String> processRedir(ArrayList<String> cmd) {
    String[] afterRedir = afterRedir(cmd);
    if (afterRedir[0] != "") {
      for (int i = 0; i < afterRedir.length; i++) {
        cmd = slashRe(afterRedir[i], cmd);
      }
    }
    return cmd;
  }

  protected static String[] afterRedir(ArrayList<String> cmd) {
    String[] result;
    int first = firstRedir(cmd);
    if (first < 0) {
      result = new String[1];
      result[0] = "";
    } else {
      result = new String[cmd.size() - first];
      for (int i = 0; i < result.length; i++) {
        result[i] = cmd.get(first);
        cmd.remove(first);
      }
    }
    return result;
  }

  protected static int firstRedir(ArrayList<String> cmd) {
    int first = -1;
    for (int i = 0; i < cmd.size(); i++) {
      if (cmd.get(i).contains(">"))
        return i;
    }
    return first;
  }

  protected static ArrayList<String> slashRe(String str, ArrayList<String> cmd) {
    String addPart = str.charAt(0) + "";
    if (str.length() == 1) {
      cmd.add(addPart);
    } else {
      if (!str.startsWith("\"") || !str.endsWith("\"")) {
        for (int i = 1; i < str.length(); i++) {
          if (String.valueOf(str.charAt(i - 1)).equals(">")) {
            if (!String.valueOf(str.charAt(i)).equals(">")) {
              cmd.add(addPart);
              addPart = str.charAt(i) + "";
              if(i==str.length()-1) cmd.add(addPart);
            } else {
              addPart = addPart + str.charAt(i);
              if(i==str.length()-1) cmd.add(addPart);
            }
          } else if (String.valueOf(str.charAt(i)).equals(">")) {
            cmd.add(addPart);
            addPart = str.charAt(i) + "";
          } else {
            addPart = addPart + str.charAt(i);
            if(i==str.length()-1) cmd.add(addPart);
          }
        }
      }else {
        cmd.add(str);
      }
    }
    return cmd;
  }

  /**
   *
   * @param i
   * @param a
   * @return boolean
   */
  protected static boolean remain(int i, String[] a) {
    for (int j = i; j < a.length; j++) {
      if (a[j] != "")
        return false;
    }
    return true;
  }
}
