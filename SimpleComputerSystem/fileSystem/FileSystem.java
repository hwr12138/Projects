
package fileSystem;

import java.util.*;

public class FileSystem {
	public Directory root;
	// Constructor

	public FileSystem() {
		this.root = new Directory("/");
	}

	public Directory findDirec(ArrayList<String> separatedPath) {

		Directory dir = this.root;
		boolean mark = true;
		
		if (separatedPath.isEmpty()) {
			return dir;
		}
		if (separatedPath.get(separatedPath.size() - 1) == " ") {
			separatedPath.remove(separatedPath.size() - 1);
		}
		for (int i = 1; i <= separatedPath.size() - 1; i++) {
			mark = false;
			// go through all child directory to check if we can follow separated path
			for (Directory direc : dir.direcInside) {
				
				// follow the separated path
				if (direc.name.equals(separatedPath.get(i))) {
					dir = direc;
					mark = true;
					break;// find!
				}
			}
			if (mark == false) {
				return null;
			}
		}
		return dir;
	}

	public File findFile(ArrayList<String> separatedPath) {
		if (separatedPath.get(separatedPath.size() - 1) == " ") {
			separatedPath.remove(separatedPath.size() - 1);
		}
		Directory current = this.root;
		if (separatedPath.size() == 1)
			return null;
		for (int i = 1; i < separatedPath.size(); i++) {
			if (separatedPath.size() > 2) {
				for (Directory direc : current.direcInside) {
					if (direc.name.equals(separatedPath.get(i))) {
						current = direc;
						break;
					}
				}
			}

			if (i == separatedPath.size() - 1) {
				for (File file : current.fileInside) {
					if (file.name.equals(separatedPath.get(i))) {
						return file;
					}
				}
			}
		}

		return null;

	}

	public Directory findparentDirec(ArrayList<String> separatedPath) {
		// we cannot find the parent directory of root, return null in this case
		if (separatedPath == null || separatedPath.size() == 1) {
			return root;
		}

		ArrayList<String> parentSinglePath = new ArrayList<String>();
		parentSinglePath.addAll(separatedPath);
		parentSinglePath.remove(parentSinglePath.size() - 1);
		Directory parentDirec = findDirec(parentSinglePath);
		return parentDirec;
	}
}
