
package inputOutput;

public class OutputProcessor {
  public String output;

  /**
   * constructor of the OutputProcessor
   * 
   * @param output
   * 
   */
  public OutputProcessor(String output) {
    this.output = output;
  }

  /**
   * print the output
   */
  public void output() {
    // check whether there is any output
    if (this.output == ""||this.output==null);
      // print
    else
    	System.out.println(output);
  }

 
}
