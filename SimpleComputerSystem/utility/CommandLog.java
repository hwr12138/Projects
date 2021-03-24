
package utility;

import java.util.HashMap;

public class CommandLog {
  /**
   * populates a hashmap with detailed names of each command
   * 
   * @param cmdHash hashmap that needs to be populated
   */
  public static void populateHashMap(HashMap<String, String> cmdHash) {
    cmdHash.put("cd", "commands.ChangeDirectory");
    cmdHash.put("cp", "commands.Copy");
    cmdHash.put("cat", "commands.Concatenate");
    cmdHash.put("echo", "commands.Echo");
    cmdHash.put("exit", "commands.Exit");
    cmdHash.put("history", "commands.History");
    cmdHash.put("ls", "commands.List");
    cmdHash.put("mkdir", "commands.MakeDirectories");
    cmdHash.put("man", "commands.Man");
    cmdHash.put("mv", "commands.Move");
    cmdHash.put("popd", "commands.PopDirectory");
    cmdHash.put("pushd", "commands.PushDirectory");
    cmdHash.put("pwd", "commands.PrintWorkingDirectory");
    cmdHash.put("saveJShell", "commands.SaveJShell");
    cmdHash.put("loadJShell", "commands.LoadJShell");
    cmdHash.put("curl", "commands.Curl");
    cmdHash.put("search", "commands.Search");
    cmdHash.put("tree", "commands.Tree");
    cmdHash.put("rm", "commands.Remove");
  }
}
