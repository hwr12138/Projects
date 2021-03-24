
package commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import fileSystem.File;
import utility.Path;

/**
 * The curl command
 * 
 * @author Yilin Zhao
 * @author Kexin Zhai
 *
 */
public class Curl extends Command implements CmdInterface {

  /**
   * constructor of curl class
   * 
   * @param command
   */
  public Curl(Command command) {
    super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
  }

  @Override
  /**
   * download a file from the given correct url
   */
  public String process() {
    String result = "";
    URL url;
    if (fullPath.length > 1 || fullPath[0] == null) {
      return "ERROR: Invalid input";
    }
    try {
      String name = fileName(fullPath[0]);
      ArrayList<String> filePath = new ArrayList<String>();
      filePath = Path.returnFullPath(name, this.currentDirectory);
      if (this.fileSys.findFile(filePath) != null) {
        result = "ERROR: File already exists";
      } else {
        url = new URL(fullPath[0]);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuffer sb = new StringBuffer();
        String content = br.readLine();
        while (content != null) {
          sb.append(content).append("\n");
          content = br.readLine();
        }
        content = sb.toString();
        content = sb.toString();
        File curl = new File(name, content);
        ArrayList<String> path = new ArrayList<String>();
        path = Path.returnFullPath(this.currentDirectory, this.currentDirectory);
        this.fileSys.findDirec(path).fileInside.add(curl);
      }
    } catch (MalformedURLException e) {
      result = "ERROR: Not a valid URL";
    } catch (IOException e) {
      result = "ERROR: Not valid contents";
    }
    return result;
  }

  /**
   * process for the correct filename
   * 
   * @param url
   * @return
   */
  protected static String fileName(String url) {
    String name = "";
    if (url.endsWith("/")) {
      url = url.substring(0, url.lastIndexOf("/"));
      name = url.substring(url.lastIndexOf("/") + 1);
    } else {
      name = url.substring(url.lastIndexOf("/") + 1);
    }
    return name;
  }

  @Override
  /**
   * get the current working directory and send it to parent class.
   */
  public String getWorkingDir() {
    return this.currentDirectory;
  }

}
