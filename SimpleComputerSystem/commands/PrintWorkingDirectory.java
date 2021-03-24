
package commands;

/**
 * The PrintWorkingDirectory command
 * 
 * @author Chang Liu
 * @author Kexin Zhai
 *
 */
public class PrintWorkingDirectory extends Command implements CmdInterface {

	/**
	 * constructor of the PrintWorkingDirectory class.
	 * 
	 * @param command the input command with its information
	 */
	public PrintWorkingDirectory(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
	}

	/**
	 * @return the string of currentDirectory
	 */
	public String process() {
		return this.currentDirectory;
	}

	/**
	 * @return the string of currentDirectory
	 */
	public String getWorkingDir() {
		return this.currentDirectory;
	}
}
