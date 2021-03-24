
package driver;

import java.util.*;
import utility.Stack;
import commands.Command;
import fileSystem.FileSystem;
import inputOutput.InputProcessor;
import inputOutput.OutputProcessor;
import utility.Redirection;

/**
 * 
 * @author Yilin Zhao
 *
 */
public class JShell {
  public FileSystem fileSys = new FileSystem();
  public String cwDir;
  public Stack directories = new Stack();
  public ArrayList<String> history = new ArrayList<>();
  public ArrayList<String> inputOutput = new ArrayList<>();
  public ArrayList<String> redir = new ArrayList<>();

  public JShell() {
    this.cwDir = "/";
    this.fileSys.root.name = "/";
  }


  /**
   * 
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub
    Scanner sc = new Scanner(System.in);
    JShell j = new JShell();
    do {
      System.out.print(j.cwDir + ":");
      String input = sc.nextLine();
      j.inputOutput.add(j.cwDir + ":" + input);
      InputProcessor inPut = new InputProcessor(input);
      if (inPut.command == null) {
      } else {
        j.history.add(input);
        // if we have the ">" or ">>", we need to redirect our output to target file
        j.redir = Redirection.redirPart(inPut.fullPath);
        inPut.fullPath = Redirection.cmdPart(inPut.fullPath);
        Command newCommand =
            new Command.Builder(inPut.fullPath, j.cwDir).fileSys(j.fileSys).command(inPut.command)
                .history(j.history).stack(j.directories).inputOutput(j.inputOutput).build();
        String result = newCommand.process();
        j.cwDir = newCommand.getWorkingDir();
        OutputProcessor output =
            new OutputProcessor(Redirection.processRedir(j.redir, j.fileSys, result, j.cwDir));
        output.output();
        j.inputOutput.add(output.output);
      }
    } while (true);
  }
}

