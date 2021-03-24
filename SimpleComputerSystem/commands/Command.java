
package commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;

import error.CommandError;
import fileSystem.FileSystem;
import utility.CommandLog;
import utility.Stack;

/**
 * a general command class that parse input and redirect information to
 * individual command classes
 * 
 * @author Haowen Rui
 */
public class Command implements CmdInterface {

  String command;
  String[] fullPath;
  String currentDirectory;
  FileSystem fileSys;
  ArrayList<String> history;
  Stack stack;
  ArrayList<String> inputOutput;

  /**
   * builder class that helps build a command object
   * 
   * @author Haowen Rui
   */
  public static class Builder {
    String command;
    String[] fullPath;
    String currentDirectory;
    FileSystem fileSys;
    ArrayList<String> history;
    Stack stack;
    ArrayList<String> inputOutput;

    /**
     * builder of a command object designed for individual commands, contains the
     * mandatory parameters
     * 
     * @param fullPath         the full input split by " "
     * @param currentDirectory the current working directory
     */
    public Builder(String[] fullPath, String currentDirectory) {
      this.fullPath = fullPath;
      this.currentDirectory = currentDirectory;
    }

    /**
     * sets the command variable for the builder object
     * 
     * @param cmd the string indicates what the user wants to do
     * @return a builder object with the command set to cmd
     */
    public Builder command(String cmd) {
      this.command = cmd;
      return this;
    }

    /**
     * sets the filesystem variable for the builder object
     * 
     * @param fileSys root of the fileSystem
     * @return a builder object with the filesystem set to fileSys
     */
    public Builder fileSys(FileSystem fileSys) {
      this.fileSys = fileSys;
      return this;
    }

    /**
     * sets the history variable for the builder object
     * 
     * @param history the arrayList of past input commands
     * @return a builder object with the history set to history in builder
     */
    public Builder history(ArrayList<String> history) {
      this.history = history;
      return this;
    }

    /**
     * sets the stack variable for the builder object
     * 
     * @param stack a stack that contains saved paths
     * @return a builder object with the stack set to stack in builder
     */
    public Builder stack(Stack stack) {
      this.stack = stack;
      return this;
    }

    /**
     * sets the inputOutput variable for the builder object
     * 
     * @param inputOutput an arraylist of strings containing previous user inputs
     *                    and machine outputs
     * @return a builder object with the inputOutput set to inputOutput
     */
    public Builder inputOutput(ArrayList<String> inputOutput) {
      this.inputOutput = inputOutput;
      return this;
    }

    /**
     * builds the Builder object into a Command object
     * 
     * @return a command object with the same variables as builder
     */
    public Command build() {
      return new Command(this);
    }
  }

  /**
   * builds a Command object from a Builder object
   * 
   * @param b the Builder object with similar variables as a Command object
   */
  public Command(Builder b) {
    this.command = b.command;
    this.fullPath = b.fullPath;
    this.currentDirectory = b.currentDirectory;
    this.fileSys = b.fileSys;
    this.history = b.history;
    this.stack = b.stack;
    this.inputOutput = b.inputOutput;
  }

  /**
   * the main command processing method that sends needed information to the
   * appropriate command classes.
   * 
   * @return a string that contains error messages
   */
  public String process() {
    String result = "";
    CommandError cmdErr = new CommandError(this.command);
    if (cmdErr.checkCmdExist() == true) {
      HashMap<String, String> cmdHash = new HashMap<String, String>();
      CommandLog.populateHashMap(cmdHash);
      String className = cmdHash.get(this.command);
      try {
        Class<?> cls = Class.forName(className);
        CmdInterface cmd = (CmdInterface) cls.getDeclaredConstructor(Command.class).newInstance(this);
        result = cmd.process();
        this.currentDirectory = cmd.getWorkingDir();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
    } else {
      return "ERROR: JShell: command not found: " + this.command;
    }
    return result;
  }

  @Override
  /**
   * get the current working directory and send it to parent class.
   */
  public String getWorkingDir() {
    return this.currentDirectory;
  }
}
