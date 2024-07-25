public abstract class CipherBase {
  protected String input;
  protected String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static CipherBase cipher = null;
  public static String traceMode = "OFF"; // default

  public CipherBase() {
    CipherBase.cipher = this;
  }

  // abstract methods
  public abstract String encrypt();

  public abstract String decrypt();

  // method to remove special characters
  /*
   * https://stackoverflow.com/questions/7552253/how-to-remove-special-characters-
   * from-a-string#:~:text=That%20depends%20on%20what%20you%20define%20as%
   * 20special,or%20it%20would%20mean%20%22any%20but%20these%20characters%22.
   */
  public String normalizeValue(String input) {
    trace("Normalizing the input value " + input);
    return input.replaceAll("[\\-\\+\\.\\^:,'~!]", "");
  }

  // method to remove empty spaces
  public String replaceEmptySpaces(String input) {
    trace("Removing the empty space from input value " + input);
    return input.replaceAll("\\s+", "");
  }

  // method to report trace or not based upon mode
  public static void trace(String msg) {
    if (traceMode.trim().toUpperCase().equals("ON")) {
      System.out.println(msg);
    }
  }

}