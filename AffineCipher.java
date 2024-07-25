public class AffineCipher extends CipherBase {
  public static String amap;

  protected String rotorI = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
  protected String rotorINotch = "Q";
  protected int offset;
  protected int counterOffset;
  protected String originalInputLocation;

  public AffineCipher(String inputValue) {
    this.input = inputValue.toUpperCase();
    this.originalInputLocation = super.alphabets;
  }

  // method to encrypt input using Affine Cipher logic
  public String encrypt() {
    String encryptedText = "";
    int inputLength = input.length();
    trace("Affine encryption started for input " + input);
    for (int count = 0; count < inputLength; count++) {
      int letterIndex = alphabets.indexOf(input.charAt(count));
      if (letterIndex == -1) {
        encryptedText += input.charAt(count);
      } else {
        encryptedText += amap.charAt(letterIndex);
      }
    }
    encryptedText = normalizeValue(encryptedText);
    trace("Affine encrypted value " + encryptedText);
    return encryptedText;
  }

  // method to decrypt input using Affine Cipher logic
  public String decrypt() {
    String decryptedText = "";
    int inputLength = input.length();
    trace("Affine decryption started for input " + input);
    for (int count = 0; count < inputLength; count++) {
      int letterIndex = amap.indexOf(input.charAt(count));
      if (letterIndex == -1) {
        decryptedText += input.charAt(count);
      } else {
        decryptedText += alphabets.charAt(letterIndex);
      }
    }
    decryptedText = normalizeValue(decryptedText);
    trace("Affine decrypted value " + decryptedText);
    return decryptedText;
  }

// method to return string based upon calculation using offset
//https://stackoverflow.com/questions/22690261/how-do-i-input-a-character-then-move-it-up-a-few-letters-in-the-english-alphabet
 public String addOffset(String s, int offset) {
    String result = "";
    for (int index = 0; index < s.length(); index++) {      
      int letter = (s.charAt(index) + offset - 'A') % 26 + 'A';
      result += (char)(letter);
    }
    return result;
  }
}
