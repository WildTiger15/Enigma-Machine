public class RotorCipher extends AffineCipher {
  
  public RotorCipher(String input, String offsetChar) {
    super(input);
    trace("Offset char for rotor is " + offsetChar);
    if (offsetChar.length() == 0) {
      offset = 0;
      counterOffset = 0;
    } else {
      offset = offsetChar.charAt(offsetChar.length()-1) - 65; // ASCII code - 'A' ASCII code 
      counterOffset = 26 - offset;
    }
    amap = rotorI;
  }
  // method to encrypt using rotorcipher logic
  // offset is added before performing encryption (same as Affinecipher)
  // later encrypted string is moved back by offset value
  public String rotorEncrypt() {
    trace("Rotor encrypt " + super.input + " with offset " + offset);
    super.input = addOffset(super.input, offset);
    super.input = super.encrypt();
    String returnValue = addOffset(super.input, counterOffset);
    trace("Rotor encrypted value " + returnValue);
    return returnValue;
  }

  // method to decrypt using rotorcipher logic
  // offset is added before performing decryption (same as Affinecipher)
  // later decrypted string is moved back by offset value
  public String rotorDecrypt() {
    trace("Rotor decrypt " + super.input + " with offset " + offset);
    super.input = addOffset(super.input, offset);
    super.input = super.decrypt();
    String returnValue = addOffset(super.input, counterOffset);
    trace("Rotor decrypted value " + returnValue);
    return returnValue;
  }
}