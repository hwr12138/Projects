
package commands;

/**
 * The Exit command
 * 
 * @author Kexin Zhai
 *
 */
public class Exit extends Command implements CmdInterface {
	/**
	 * constructor of the Exit class.
	 * 
	 * @param command the input command with its information
	 */
	public Exit(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
	}

	@Override
	/**
	 * Stop the program
	 * @return an empty string
	 */
	public String process() {
		/**
		 * @param the int for showing the correctness for the command
		 */
		System.exit(0);
		return "";

	}

	@Override
	/**
	 * get the current working directory and send it to parent class.
	 */
	public String getWorkingDir() {
		return this.currentDirectory;
	}

}
