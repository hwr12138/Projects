
package commands;

import java.util.ArrayList;
import utility.Path;
import error.PathError;

public class PopDirectory extends Command implements CmdInterface {

	/**
	 * constructor of the PopDirectory class.
	 * 
	 * @param command the input command with its information
	 */
	public PopDirectory(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys).stack(command.stack));
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return null or error message
	 */
	public String process() {
		if (this.stack == null || this.stack.directories.size() == 0) {
			String errorMessage = "ERROR:Stack is empty, cannot pop out";
			return errorMessage;
		} else {
			String lastname = this.stack.directories.get(this.stack.directories.size() - 1);
			// Check if the directory still exists
			ArrayList<String> path = Path.separatePath(lastname);
			PathError pathExist = new PathError(path, this.fileSys);
			if (!pathExist.checkPathExist()) {
				// if the directory has been deleted or removed
				return "ERROR:the directory has been deleted";
			} else {
				// if the directory exists
				this.stack.directories.remove(this.stack.directories.size() - 1);
				this.currentDirectory = lastname;
				return "";
			}
		}
	}

	/**
	 * @return the string of currentDirectory
	 */
	public String getWorkingDir() {
		return this.currentDirectory;
	}
}
