public class CaesarCipher extends CipherBase {
  private int shiftNumber;

  public CaesarCipher(String inputValue, String offset) {
    this.input = inputValue.toUpperCase();
    if (offset == "") {
      shiftNumber = 1; // default offset
    } else {
      this.shiftNumber = Integer.parseInt(offset);
    }
  }

  /*
   * https://www.topcoder.com/thrive/articles/caesar-cipher-in-java-encryption-and
   * -decryption
   */
  // method to encrypt input using caesar cipher logic
  public String encrypt() {
    String encryptedText = "";
    int inputLength = input.length();
    trace("Caesar encryption started for input " + input);
    for (int count = 0; count < inputLength; count++) {
      int letterIndex = alphabets.indexOf(input.charAt(count));
      if (letterIndex == -1) {
        encryptedText += input.charAt(count);
      } else {
        int letterShiftIndex = (letterIndex + shiftNumber) % 26;
        encryptedText += alphabets.charAt(letterShiftIndex);
      }
    }
    encryptedText = normalizeValue(encryptedText);
    trace("Caesar encrypted value " + encryptedText);
    return encryptedText;
  }

  // method to decrypt input using caesar cipher logic
  public String decrypt() {
    String decryptedText = "";
    int inputLength = input.length();
    trace("Caesar decryption started for input " + input);
    for (int count = 0; count < inputLength; count++) {
      int letterIndex = alphabets.indexOf(input.charAt(count));
      if (letterIndex == -1) {
        decryptedText += input.charAt(count);
      } else {
        int letterShiftIndex = (letterIndex - shiftNumber) % 26;
        if (letterShiftIndex < 0) {
          letterShiftIndex = alphabets.length() + letterShiftIndex;
        }
        decryptedText += alphabets.charAt(letterShiftIndex);
      }
    }
    decryptedText = normalizeValue(decryptedText);
    trace("Caesar descrypted value " + decryptedText);
    return decryptedText;
  }

}
