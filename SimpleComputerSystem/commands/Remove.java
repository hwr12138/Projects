
package commands;

import java.util.ArrayList;

import utility.Path;
import fileSystem.Directory;
import fileSystem.File;

public class Remove extends Command implements CmdInterface {

	/**
	 * constructor of the Remove class.
	 * 
	 * @param command the input command with its information
	 */
	public Remove(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
	}

	@Override
	/**
	 * @return an empty string or error messages
	 */
	public String process() {
		// check whether the number of the arguments for this command is right
		if (this.fullPath[0]==null || this.fullPath.length == 0) {
			return "ERROR:No path for remove action";
		}
		if (this.fullPath.length>1) {
			return "ERROR:Too many arguments";
		}
		// if the path or file exists
		ArrayList<String> separatedPath = new ArrayList<String>();
		separatedPath = Path.separate(this.fullPath, this.currentDirectory);
		if (separatedPath.get(separatedPath.size() - 1) == " ") {
			separatedPath.remove(separatedPath.size() - 1);
		}
		ArrayList<String> cwDir=new ArrayList<String>();
		cwDir=Path.separatePath(this.currentDirectory);
		if (cwDir.containsAll(separatedPath)) {
			return "ERROR:we cannot delete the current working directory";
		}
		
		if (separatedPath.size() == 1) {
			return "ERROR:we cannot delete the root";
		}
		
		if (this.fileSys.findDirec(separatedPath) != null || this.fileSys.findFile(separatedPath) != null) {
			rmAndGoInside(separatedPath);
		} else {
			return "ERROR:rm cannot work because DIR doesn't exist";
		}
		return "";
	}

	/**
	 * do remove action recursively
	 * 
	 * @param singlePath one path we will work on
	 */
	public void rmAndGoInside(ArrayList<String> singlePath) {
		if (this.fileSys.findFile(singlePath) != null) {
			// if we reach a specific file, no need for recursive run
			File file = this.fileSys.findFile(singlePath);
			file.name = "";
			file.content = "";
		} else if (this.fileSys.findDirec(singlePath) != null) {
			// get its parent directory
			Directory parentDirec = this.fileSys.findparentDirec(singlePath);
			// if we reach a specific directory
			Directory direc = this.fileSys.findDirec(singlePath);
			// if we reach the end of a sub path
			if (direc.direcInside.isEmpty()) {
				// delete it from its parent directory's direcInside list
				ArrayList<Directory> newParentList = new ArrayList<Directory>();
				for (Directory dir : parentDirec.direcInside) {
					if (dir.name != direc.name) {
						newParentList.add(dir);
					}
				}
				parentDirec.direcInside = newParentList;
				direc.name = "";
			} else {
				// go deeper of a sub path if we haven't reached its end
				ArrayList<String> newSinglePath = new ArrayList<String>();
				newSinglePath.addAll(singlePath);

				for (Directory dir : direc.direcInside) {
					newSinglePath.add(dir.name);
					rmAndGoInside(newSinglePath);
					newSinglePath.remove(newSinglePath.size() - 1);
				}
				direc.name = "";
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
