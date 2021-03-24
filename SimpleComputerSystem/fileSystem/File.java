package fileSystem;

public class File {
  public String name;
  public String content;

  public File(String fileName, String fileContent) {
    this.name = fileName;
    this.content = fileContent;
  }
}
