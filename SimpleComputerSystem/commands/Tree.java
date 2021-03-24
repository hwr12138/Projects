
package commands;

import fileSystem.Directory;
import fileSystem.File;

public class Tree extends Command implements CmdInterface {

	/**
	 * constructor of the Tree class.
	 * 
	 * @param command the input command with its information
	 */
	public Tree(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
	}

	/**
	 * @return show the fileSystem in tree model with a tab as indentation 
	 */
	public String process() {
		// initialize the output to \ and space to " ",
		//suppose there's no empty space at the beginning
		String totaloutput = "/\n";
		String space = "\t";
		Directory direc = this.fileSys.root;
		int spaceNum = 1;

		totaloutput += Target(direc, space, spaceNum);

		return totaloutput;
	}

	/**
	 * 
	 * build tree model in fileSystem 
	 * 
	 * @param direc Directory we are currently adding to tree model
	 * @param space the indentation we want to use
	 * @param spaceNum the number indicating how many space we should have for every directory
	 * @return the tree model beginning from root
	 */
	public String Target(Directory direc, String space, int spaceNum) {
		String subOutPut = "";

		// go through all directories inside the current target directory
		for (Directory dir : direc.direcInside) {
			subOutPut += space.repeat(spaceNum) + dir.name + "\n";
			if (dir.direcInside != null) {
				subOutPut += Target(dir, space, spaceNum + 1);
			}
		}
		
		// go through all files inside the current target directory
		for (File file : direc.fileInside) {
			subOutPut += space.repeat(spaceNum) + file.name + "\n";
		}
		return subOutPut;
	}

	/**
	 * @return the string of currentDirectory
	 */
	public String getWorkingDir() {
		return this.currentDirectory;
	}

}
