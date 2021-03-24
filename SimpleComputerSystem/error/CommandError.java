
package error;

import java.util.HashMap;

import utility.CommandLog;

public class CommandError {

	String command;

	/**
	 * consructor of the CommandError class
	 * 
	 * @param command
	 */
	public CommandError(String command) {
		this.command = command;
	}

	/**
	 * method that checks whether the command exists or not
	 * 
	 * @return true if command exists, false if it does not
	 */
	public boolean checkCmdExist() {
		HashMap<String, String> cmdHash = new HashMap<String, String>();
		CommandLog.populateHashMap(cmdHash);
		for (String item : cmdHash.keySet()) {
			if (item.equals(this.command)) {
				return true;
			}
		}
		return false;
	}
}
