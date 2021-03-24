package commands;

/**
 * interface for all commands
 * 
 * @author Haowen Rui
 */
public interface CmdInterface {
  /**
   * the command interface that represent all individual commands
   */
  public String process();

  /**
   * get the current working directory and send it to parent class.
   */
  public String getWorkingDir();
}
