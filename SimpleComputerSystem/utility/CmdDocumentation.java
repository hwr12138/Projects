
package utility;

import java.util.HashMap;

/**
 * 
 * @author Yilin Zhao
 *
 */
public class CmdDocumentation {
	  /**
	   * HashMap for CmdDocumentation
	   * 
	   * @param a HashMap
	   */
	  public static void populateManHashMap(HashMap<String, String> cmdHash) {
	    cd(cmdHash);
	    cat(cmdHash);
	    echo(cmdHash);
	    exit(cmdHash);
	    man(cmdHash);
	    history(cmdHash);
	    ls(cmdHash);
	    mkdir(cmdHash);
	    pwd(cmdHash);
	    popd(cmdHash);
	    pushd(cmdHash);
	    rm(cmdHash);
	    mv(cmdHash);
	    cp(cmdHash);
	    curl(cmdHash);
	    saveJShell(cmdHash);
	    loadJShell(cmdHash);
	    search(cmdHash);
	    tree(cmdHash);
	  }

	  /**
	   * Add cd documentation
	   * @param a HashMap
	   */
	  protected static void cd(HashMap<String, String> cmdHash) {
	    cmdHash.put("cd",
	        "cd DIR\n\n"
	            + "Change directory to DIR, which may be relative to the current directory or\n"
	            + "may be a full path. As with Unix, .. means a parent directory and a . means\n"
	            + "the current directory. The directory must be /, the forward slash. The foot \n"
	            + "of the file system is a single slash: /.\n");
	  }

	  /**
	   *  Add cat documentation
	   * @param a HashMap
	   */
	  protected static void cat(HashMap<String, String> cmdHash) {
	    cmdHash.put("cat", "cat FILE1 [FILE2 ...]\n\n"
	        + "Display the contents of FILE1 and other files (i.e. File2 ....) concatenated in\n"
	        + "the shell. You may want to use three line breaks to separate the contents of one file\n"
	        + "from the other file.\n");
	  }

	  /**
	   * Add echo documentation
	   * @param a HashMap
	   */
	  protected static void echo(HashMap<String, String> cmdHash) {
	    cmdHash.put("echo", "echo STRING\n\n" + "Print Stirng\n");
	  }

	  /**
	   * Add exit documentation
	   * @param a HashMap
	   */
	  protected static void exit(HashMap<String, String> cmdHash) {
	    cmdHash.put("exit", "exit\n\n" + "Quit the program\n");
	  }

	  /**
	   * Add man documentation
	   * @param a HashMap
	   */
	  protected static void man(HashMap<String, String> cmdHash) {
	    cmdHash.put("man", "man CMD\n\n"
	        + "Print documentation for CMD.\n");
	  }

	  /**
	   * Add history documentation
	   * @param a HashMap
	   */
	  protected static void history(HashMap<String, String> cmdHash) {
	    cmdHash.put("history", "history [number]\n\n"
	        + "This command will print out recent commands, one command per line. i.e.\n"
	        + "    1.cd ..\n" + "    2.mkdir textFolder\n" + "    3.echo \"Hello World\"\n"
	        + "    4.fsjhdfks\n" + "    5.history\n"
	        + "The above output from history has two columns. The first column is\n"
	        + "numbered such that the line with the highest number is the most recent command.\n"
	        + "The most recent command is history.  The second column contains the actual\n"
	        + "command. " + "Note: Your output should also contain as output any syntactical errors\n"
	        + "typed by the user as seen on line 4.\n" + "\n"
	        + "We can truncate the output by specifying a number (>=0) after the command.\n"
	        + "For instance, if we want to only see the last 3 commands typed, we can type the\n"
	        + "following on the command line:\n" + "    history 3\n"
	        + "And the output will be as follows:\n" + "    4.fsjhdfks\n" + "    5.history\n"
	        + "    6.history 3\n");
	  }

	  /**
	   * Add ls documentation
	   * @param a HashMap
	   */
	  protected static void ls(HashMap<String, String> cmdHash) {
	    cmdHash.put("ls",
	        "ls [-R] [PATH . . . ]\n\n" + "If -R is present, recursively list all subdirectories."
	            + "If no paths are given, print the contents (file or directory) of the current\n"
	            + "directory, with a new line following each of the content (file or directory).\n"
	            + "\n" + "Otherwise, for each path p, the order listed:\n"
	            + "    1.If p speci:ies a file, print p\n"
	            + "    2.If p speci:ies a directory, print p, a colon, then the contents of that\n"
	            + "      directory, then an extra new line.\n"
	            + "    3.If p does not exist, print a suitable message.\n");
	  }

	  /**
	   * Add cat documentation
	   * @param a HashMap
	   */
	  protected static void mkdir(HashMap<String, String> cmdHash) {
	    cmdHash.put("mkdir", "mkdir DIR ...\n\n"
	        + "Create directories, each of which may be relative to the current directory or maybe a full path.\n"
	        + "mkdir can now take in more than one DIR.\n");
	  }

	  /**
	   * Add pwd documentation
	   * @param a HashMap
	   */
	  protected static void pwd(HashMap<String, String> cmdHash) {
	    cmdHash.put("pwd", "pwd\n\n" + "Print the current working directory (including).\n");
	  }

	  /**
	   * Add popd documentation
	   * @param a HashMap
	   */
	  protected static void popd(HashMap<String, String> cmdHash) {
	    cmdHash.put("popd",
	        "popd\n\n" + "Remove the top entry from the directory stack, and cd into it. The removal\n"
	            + "must be consistent as per the LIFO behavior of a stack. The popd command\n"
	            + "removes the top most directory from the directory stack and makes it the\n"
	            + "current working directory. If there is no directory onto the stack, then give\n"
	            + "appropriate error message.\n");
	  }

	  /**
	   * Add pushd documentation
	   * @param a HashMap
	   */
	  protected static void pushd(HashMap<String, String> cmdHash) {
	    cmdHash.put("pushd",
	        "pushd DIR\n\n"
	            + "Saves the current working directory by pushing onto directory stack and then\n"
	            + "changes the new current working directory to DIR. The push must be \n"
	            + "consistent as per the LIFO behavior of a stack. The pushd command\n"
	            + "the old current working directory in directory stack so that it can be returned\n"
	            + "to at any time (via the popd command). The size of the directory stack is\n"
	            + "dynamic and dependent on the pushd and the popd commands.\n");
	  }

	  /**
	   * Add rm documentation
	   * @param a HashMap
	   */
	  protected static void rm(HashMap<String, String> cmdHash) {
	    cmdHash.put("rm", "rm DIR\n\n"
	        + "removes the DIR from the file system. This also removes all the children of DIR (i.e.\n"
	        + "it actsrecursively).\n");
	  }

	  /**
	   * Add mv documentation
	   * @param a HashMap
	   */
	  protected static void mv(HashMap<String, String> cmdHash) {
	    cmdHash.put("mv", "mv OLDPATH NEWPATH\n\n"
	        + "Move item OLDPATH to NEWPATH. Both OLDPATH and NEWPATH may be relative to the current \n"
	        + "directory or may be full paths. If NEWPATH is a directory, move the item into the \n"
	        + "directory.\n");
	  }

	  /**
	   * Add cp documentation
	   * @param a HashMap
	   */
	  protected static void cp(HashMap<String, String> cmdHash) {
	    cmdHash.put("cp",
	        "cp OLDPATH NEWPATH\n\n"
	            + "Like mv, but don\'t remove OLDPATH. If OLDPATH is a directory, recursively copy "
	            + "the contents\n");
	  }

	  /**
	   * Add curl documentation
	   * @param a HashMap
	   */
	  protected static void curl(HashMap<String, String> cmdHash) {
	    cmdHash.put("curl", "curl URL\n\n"
	        + "Retrieve the file at that URLand add it to the current working directory.\n");
	  }

	  /**
	   * Add saveJShell documentation
	   * @param a HashMap
	   */
	  protected static void saveJShell(HashMap<String, String> cmdHash) {
	    cmdHash.put("saveJShell",
	        "saveJShell FileName\n\n" + "Save current JShell session to FileName.\n");
	  }



	  /**
	   * Add loadJShell documentation
	   * @param a HashMap
	   */
	  protected static void loadJShell(HashMap<String, String> cmdHash) {
	    cmdHash.put("loadJShell",
	        "loadJShell FileName\n\n"
	            + "Load the contents of the FileName and reinitialize everything that was previously\n"
	            + "saved into the FileName\n");
	  }

	  /**
	   * Add search documentation
	   * @param a HashMap
	   */
	  protected static void search(HashMap<String, String> cmdHash) {
	    cmdHash.put("search", "search PATH ... -[f|d] -name EXPRESSION\n\n"
	        + "Search PATH ... and search all directories or files (indicates by type d or f) that\n"
	        + "have the exactly name as EXPRESSION\n");
	  }

	  /**
	   * Add tree documentation
	   * @param a HashMap
	   */
	  protected static void tree(HashMap<String, String> cmdHash) {
	    cmdHash.put("tree", "tree\n\n" + "Display the entire filesystem as a tree.\n"
	        + "For every level of the tree, you must indent by a tab character.\n");
	  }
}
