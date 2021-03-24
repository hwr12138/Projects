
package fileSystem;

import java.util.ArrayList;

public class Directory {
  public String name;
  public ArrayList<File> fileInside;
  public ArrayList<Directory> direcInside;

  public Directory(String name) {
    this.name = name;
    this.fileInside = new ArrayList<File>();
    this.direcInside = new ArrayList<Directory>();
  }
}
