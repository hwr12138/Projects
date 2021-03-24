
package commands;

import java.util.ArrayList;

import utility.Path;
import fileSystem.Directory;
import fileSystem.File;

public class Search extends Command implements CmdInterface {

	/**
	 * constructor of the Search class.
	 * 
	 * @param command the input command with its information
	 */
	public Search(Command command) {
		super(new Command.Builder(command.fullPath, command.currentDirectory).fileSys(command.fileSys));
	}

	/**
	 * @return the output of running search command or error message
	 */
	public String process() {
		ArrayList<String> separatedPath = new ArrayList<String>();
		separatedPath = Path.separate(this.fullPath, this.currentDirectory);
		if (separatedPath.size() < 10) {
			return "ERROR:missing arguments for search command:\ninput format should be: \nsearch path ...  -type [f|d] -name expression.\"";
		}

		int mark_name = separatedPath.indexOf("-name");
		int mark_type = separatedPath.indexOf("-type");
		int mark_last = separatedPath.lastIndexOf("/"); // solve the problem of treating "-type" and "-name" as path
		// Need to fixed the problem of mistaking everything input as a path
		// if the last "/" is behind "-type", then we have problems of mistaking "-name"
		// as path, the similar case to "-type"
		int separateMark = 0;
		if (mark_last > mark_type) {
			separateMark = mark_name - mark_last + 1;
		}

		// review mark of type by separateMark because we will take [f|d] as a path
		String type = separatedPath.get(mark_type + separateMark);
		if (!type.equals("f") && !type.equals("d")) {
			return "ERROR:Type should be f or d for file or directory, not " + type;
		}
		// revise mark of name by 1 because name an empty ahead
		String targetName = separatedPath.get(mark_name + 2);
		if (targetName.indexOf("\"") == targetName.lastIndexOf("\"")) {
			return "ERROR:Name is missing quotation marks, should be \"" + targetName + "\" " + ",not " + targetName;
		}
		targetName = targetName.substring(1, targetName.length() - 1);

		String totaloutput = "";
		totaloutput = searchPrepare(totaloutput, type, targetName, mark_type, mark_name, separateMark, separatedPath);

		if (totaloutput.equals("")) {
			return "ERROR: \""+targetName + "\" not found";
		} else {
			return totaloutput;
		}
	}

	/**
	 * 
	 * separate input to different paths and do search individually
	 * 
	 * @param totaloutput   store the outputs of different paths
	 * @param type          tell which type to search, Directory or File
	 * @param targetName    tell the name we want to search for
	 * @param mark_type     the index of the type we want to search for in
	 *                      separatedPath
	 * @param mark_name     the index of the name we want to search for in
	 *                      separaedPath
	 * @param separatedMark correct the input
	 * @param separatedPath the input
	 * @return the output of running search command or error message
	 */
	public String searchPrepare(String totaloutput, String type, String targetName, int mark_type, int mark_name,
			int separateMark, ArrayList<String> separatedPath) {
		ArrayList<String> singlePath = new ArrayList<String>();
		Directory inputDirec;
		String output = "";
		int index = 0;
		singlePath.add("/");

		while (index < mark_type - separateMark + 1) {
			index++;
			// reach the end of a path(singlePath is full and complete)
			if (separatedPath.get(index) == "/") {
				// check if the path exists, if it exists, get the path by each
				if (this.fileSys.findDirec(singlePath) == null) {
					return "ERROR: \""+singlePath + "\" does not exist";
				} else {
					inputDirec = this.fileSys.findDirec(singlePath);
				}
				// check if we will look for a file or a directory
				if (type.equals("f")) {
					output = searchDetailFile(inputDirec, singlePath, targetName, output);
				} else {
					output = searchDetailDirec(inputDirec, singlePath, targetName, output); // we checked ahead that
																							// it's either f or d.
				}
				if (!output.equals("")) {
					totaloutput += output;
				}
				// get ready for storing the next path
				singlePath.clear();
				singlePath.add("/");
				output = "";
			} else {
				singlePath.add(separatedPath.get(index));
			}
		}
		return totaloutput;
	}

	/**
	 * 
	 * search for a file in given Directory
	 * 
	 * @param Direc      the specific directory we want to do the search
	 * @param singlePath the specific path we want to do the search
	 * @param targetName tell the name we want to search for
	 * @param output     we information of if we locate the target File
	 * @return location of the file we want or empty if we don't find it in given
	 *         directory
	 */
	public String searchDetailFile(Directory Direc, ArrayList<String> singlePath, String targetName, String output) {
		for (File file : Direc.fileInside) {
			if (file.name.equals(targetName)) {
				String fileLocation = "";
				for (String str : singlePath) {
					fileLocation += str + "/";
				}
				// we don't want / at the beginning and / at the end
				if (fileLocation.length() > 2) {
					fileLocation = fileLocation.substring(1, fileLocation.length() - 1);
				} else {
					// if we find it at root
					fileLocation = "/";
				}
				output += "find file:\"" + targetName + "\" in " + fileLocation;
			}
		}
		for (Directory dir : Direc.direcInside) {
			ArrayList<String> newSinglePath = new ArrayList<String>();
			newSinglePath.addAll(singlePath);
			newSinglePath.add(dir.name);
			String newOutPut = searchDetailFile(dir, newSinglePath, targetName, output);
			if (!newOutPut.equals(output)) {
				output = newOutPut;
			}
			newSinglePath.remove(newSinglePath.size() - 1);
		}
		return output;
	}

	/**
	 * 
	 * search for a directory in given Directory
	 * 
	 * @param Direc      the specific directory we want to do the search
	 * @param singlePath the specific path we want to do the search
	 * @param targetName tell the name we want to search for
	 * @param output     we information of if we locate the target directory
	 * @return location of the directory we want or empty if we don't find it in
	 *         given directory
	 */
	public String searchDetailDirec(Directory Direc, ArrayList<String> singlePath, String targetName, String output) {
		for (Directory dir : Direc.direcInside) {
			if (dir.name.equals(targetName)) {
				String dirLocation = "";
				for (String str : singlePath) {
					dirLocation += str + "/";
				}
				// we don't want / at the beginning and / at the end
				if (dirLocation.length() > 2) {
					// if it is not root
					dirLocation = dirLocation.substring(1, dirLocation.length() - 1);
				} else {
					// if we find it at root
					dirLocation = "/";
				}

				output += "find directory:\"" + targetName + "\" in " + dirLocation + "\n";
			}
			ArrayList<String> newSinglePath = new ArrayList<String>();
			newSinglePath.addAll(singlePath);
			newSinglePath.add(dir.name);
			String newOutPut = searchDetailDirec(dir, newSinglePath, targetName, output);
			if (!newOutPut.equals(output)) {
				output = newOutPut;
			}
			newSinglePath.remove(newSinglePath.size() - 1);
		}
		return output;
	}

	/**
	 * @return the string of currentDirectory
	 */
	public String getWorkingDir() {
		return this.currentDirectory;
	}
}
