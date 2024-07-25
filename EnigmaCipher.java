public class EnigmaCipher extends AffineCipher {
  public static String rotorsList = "I II III"; // default
  public static String offsetCharList = "A A Z"; // default, no offset
  public static String plugboard = "AA"; // default, no mapping

  // rotor strings
  private String rotorII = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
  private String rotorIINotch = "E";
  private String rotorIII = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
  private String rotorIIINotch = "V";
  private String rotorIV = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
  private String rotorIVNotch = "J";
  private String rotorV = "VZBRGITYUPSDNHLXAWMJQOFECK";
  private String rotorVNotch = "Z";
  private String rightRotorNotch;
  private String middleRotorNotch;
  private String leftRotorNotch;

  // https://codereview.stackexchange.com/questions/225412/the-enigma-machine-cli-in-java
  private String reflectorB = "YRUHQSLDPXNGOKMIEBFZCWVJAT";

  // retor specific
  private String leftRotor;
  private String middleRotor;
  private String rightRotor;

  // offset specific
  private int offset1 = 0;
  private int counterOffset1 = 0;
  private int offset2 = 0;
  private int counterOffset2 = 0;
  private int offset3 = 0;
  private int counterOffset3 = 0;
  private char offsetChar1 = ' ';
  private char offsetChar2 = ' ';
  private char offsetChar3 = ' ';

  private String inputValue;

  public EnigmaCipher(String input, String offsetChars) {
    super(input);
    this.inputValue = input;
    
    if (offsetChars.toUpperCase().equals("A A Z") || offsetChars.toUpperCase().equals("AAZ")) {
      // default offset
    } else {
      if (offsetChars.length() > 0) {
        offsetChar1 = offsetChars.charAt(0);
        offsetChar2 = offsetChars.charAt(1);
        offsetChar3 = offsetChars.charAt(2);
        // set offset details
        setOffsetAndCounterOffset();
      }
    }
    
  }
  // method to set offset value based upon offset character
  private void setOffsetAndCounterOffset() {
    offset1 = offsetChar1 - 65; // ASCII code - 'A' ASCII code
    counterOffset1 = 26 - offset1;
    offset2 = offsetChar2 - 65; // ASCII code - 'A' ASCII code
    counterOffset2 = 26 - offset2;
    offset3 = offsetChar3 - 65; // ASCII code - 'A' ASCII code
    counterOffset3 = 26 - offset3;

    trace("offsetChar1 " + offsetChar1 + " offset1 " + offset1 + ", counterOffset1 " + counterOffset1);

    trace("offsetChar2 " + offsetChar2 + " offset2 " + offset2 + ", counterOffset2 " + counterOffset2);

    trace("offsetChar3 " + offsetChar3 + " offset3 " + offset3 + ", counterOffset3 " + counterOffset3);
  }
  
  // method to set 3 rotors used for engima
  private void setRotors() {
    String[] rotorArray = rotorsList.trim().split("\\s+");
    setLeftRotor(rotorArray[0]);
    setMiddleRotor(rotorArray[1]);
    setRightRotor(rotorArray[2]);
  }

  // method to set left rotor used for engima
  private void setLeftRotor(String rotorNumber) {
    trace("rotor number to compare " + rotorNumber);
    if (rotorNumber.trim().toUpperCase().equals("I")) {
      leftRotor = rotorI;
      leftRotorNotch = rotorINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("II")) {
      leftRotor = rotorII;
      leftRotorNotch = rotorIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("III")) {
      leftRotor = rotorIII;
      leftRotorNotch = rotorIIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("IV")) {
      leftRotor = rotorIV;
      leftRotorNotch = rotorIVNotch;
    } else {
      leftRotor = rotorV;
      leftRotorNotch = rotorVNotch;
    }
    trace("left rotor is " + leftRotor + " notch is " + leftRotorNotch);
  }

  // method to set middle rotor used for engima
  private void setMiddleRotor(String rotorNumber) {
    trace("rotor number to compare " + rotorNumber);
    if (rotorNumber.trim().toUpperCase().equals("I")) {
      middleRotor = rotorI;
      middleRotorNotch = rotorINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("II")) {
      middleRotor = rotorII;
      middleRotorNotch = rotorIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("III")) {
      middleRotor = rotorIII;
      middleRotorNotch = rotorIIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("IV")) {
      middleRotor = rotorIV;
      middleRotorNotch = rotorIVNotch;
    } else {
      middleRotor = rotorV;
      middleRotorNotch = rotorVNotch;
    }
    trace("middle rotor is " + middleRotor + " notch is " + middleRotorNotch);
  }

  // method to set right rotor used for engima
  private void setRightRotor(String rotorNumber) {
    trace("rotor number to compare " + rotorNumber);
    if (rotorNumber.trim().toUpperCase().equals("I")) {
      rightRotor = rotorI;
      rightRotorNotch = rotorINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("II")) {
      rightRotor = rotorII;
      rightRotorNotch = rotorIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("III")) {
      rightRotor = rotorIII;
      rightRotorNotch = rotorIIINotch;
    } else if (rotorNumber.trim().toUpperCase().equals("IV")) {
      rightRotor = rotorIV;
      rightRotorNotch = rotorIVNotch;
    } else {
      rightRotor = rotorV;
      rightRotorNotch = rotorVNotch;
    }
    trace("right rotor is " + rightRotor + " notch is " + rightRotorNotch);
  }

  // offset = ABC
  // offset = A B C
  private void setOffset() {
    if (offsetCharList.indexOf(' ') > 0) {
      String[] offsetArray = offsetCharList.trim().toUpperCase().split("\\s+");
      offsetChar1 = offsetArray[0].toUpperCase().charAt(0);
      offsetChar2 = offsetArray[1].toUpperCase().charAt(0);
      offsetChar3 = offsetArray[2].toUpperCase().charAt(0);
    } else {
      offsetChar1 = offsetCharList.toUpperCase().charAt(0);
      offsetChar2 = offsetCharList.toUpperCase().charAt(1);
      offsetChar3 = offsetCharList.toUpperCase().charAt(2);
    }
    trace("Original Offset1Char " + offsetChar1 + " Original Offset2Char " + offsetChar2 + " Original Offset3Char " + offsetChar3);
  }

  // method to increment offset character3 during enigma action
  private void incrementOffsetChar3() {
    if (offsetChar3 == 'Z') {
      offsetChar3 = 'A';
    } else {
      offsetChar3 += 1;
    }
  }
  
  // method to increment offset character2 during enigma action
  private void incrementOffsetChar2() {
    if (offsetChar2 == 'Z') {
      offsetChar2 = 'A';
    } else {
      offsetChar2 += 1;
    }
  }

  // method to increment offset character1 during enigma action  
  private void incrementOffsetChar1() {
    if (offsetChar1 == 'Z') {
      offsetChar1 = 'A';
    } else {
      offsetChar1 += 1;
    }
  }

  // method to swap characters based upon plugboard mapping
  private char swapPlugboardValue(char charToCheck) {
    trace("Plugboard value is " + plugboard);
    String[] plugboardArray = plugboard.trim().toUpperCase().split("\\s+");
    for (int loopIndex = 0; loopIndex < plugboardArray.length; loopIndex++) {
      trace("Plugboard mapping at index " + loopIndex + " is " + plugboardArray[loopIndex]);
      if (plugboardArray[loopIndex].charAt(0) == charToCheck) {
        return plugboardArray[loopIndex].charAt(1);
      }
      if (plugboardArray[loopIndex].charAt(1) == charToCheck) {
        return plugboardArray[loopIndex].charAt(0);
      }
    }
    return charToCheck;
  }

  /*
   * method to perform complete enigma actions
   * Sequence of operations (Total 9)
   * 1. Plugboard //1
   * 2. 3 Rotors (Right, Middle, Left) //1+4
   * 3. Reflector //5
   * 4. 3 Rotors //5+3
   * 5. plugboard //9
   */
  public String performCompleteEnigmaOperations() {
    // assume plugboard is default
    String result = "";
    String initialInput = this.inputValue.toUpperCase(); // set in constructor
    initialInput = normalizeValue(initialInput);
    initialInput = replaceEmptySpaces(initialInput);
    trace("Normalized value before running through enigma machine " + initialInput);
    Boolean performDoubleStepping = false;
    Boolean incrementLeftRotor = false;
    // initial offset and counter offset info
    setOffset();

    // set the rotors
    setRotors();

    for (int loopIndex = 0; loopIndex < initialInput.length(); loopIndex++) {
      // offsetChar3 is incremented for each iteration
      incrementOffsetChar3();
      trace("Offset char " + offsetChar3 + " rightNotch " + rightRotorNotch);
      
      if (performDoubleStepping == true) {
        incrementOffsetChar2();
        trace("Performed double stepping, offsetChar2 is " + offsetChar2);
        performDoubleStepping = false;
      }

      // increment offsetChar2 if right rotor notch passed
      if (offsetChar3 == rightRotorNotch.charAt(0) + 1) {
        incrementOffsetChar2();
        incrementLeftRotor = true;
        // if the rotor is at its notch location, it should be moved next time
        if (offsetChar2 == middleRotorNotch.charAt(0)) {
          trace("Going to perform double stepping");
          performDoubleStepping = true;
        }
      }

       trace("Offset char2 " + offsetChar2 + " middleNotch " + middleRotorNotch);
      // increment offsetChar1 if middle rotor notch passed
      if ((offsetChar2 == (middleRotorNotch.charAt(0) + 1)) && incrementLeftRotor) {
        incrementOffsetChar1();
        incrementLeftRotor = false;
      }
      
      // set the offset and counterOffset details
      setOffsetAndCounterOffset();

      char charToProcess = initialInput.charAt(loopIndex);
      // check plugboard mapping
      charToProcess = swapPlugboardValue(charToProcess);
      trace("Value after plugboard swap check " + charToProcess);
      super.input = String.valueOf(charToProcess);

      // perform encryption
      super.input = enigmaRotorEncrypt();

      trace("Value after 3 rotors encryption " + super.input);
      // reflector
      charToProcess = super.input.charAt(0);
      super.input = String.valueOf(getReflectorB(charToProcess));
      trace("Value after reflector B " + super.input);
      // decrypt
      super.input = enigmaRotorDecrypt();

      trace("Value after 3 rotors decryption " + super.input);

      // check second plugboard mapping
      charToProcess = super.input.charAt(0);
      charToProcess = swapPlugboardValue(charToProcess);
      trace("Value after 2nd plugboard swap check " + charToProcess);
      super.input = String.valueOf(charToProcess);
      result += super.input;
    }
    return result;
  }

  // method to perform rotor encryption for enigma
  public String enigmaRotorEncrypt() {
    // set the rotors
    setRotors();

    trace("output before rotor encryption " + super.input);
    super.input = rightRotorEncrypt();
    trace("output after right rotor encryption " + super.input);
    super.input = middleRotorEncrypt();
    trace("output after middle rotor encryption " + super.input);
    super.input = leftRotorEncrypt();
    trace("output after left rotor encryption " + super.input);
    return super.input;
  }

  private String leftRotorEncrypt() {
    amap = leftRotor;
    super.offset = offset1;
    super.counterOffset = counterOffset1;
    super.input = addOffset(super.input, offset1);
    super.input = super.encrypt();
    return addOffset(super.input, counterOffset1);
  }

  private String middleRotorEncrypt() {
    amap = middleRotor;
    super.offset = offset2;
    super.counterOffset = counterOffset2;
    super.input = addOffset(super.input, offset2);
    super.input = super.encrypt();
    return addOffset(super.input, counterOffset2);
  }

  private String rightRotorEncrypt() {
    amap = rightRotor;
    super.offset = offset3;
    super.counterOffset = counterOffset3;
    super.input = addOffset(super.input, offset3);
    super.input = super.encrypt();
    return addOffset(super.input, counterOffset3);
  }

  public char getReflectorB(char charToMap) {
    int reflectorIndex = alphabets.indexOf(charToMap);
    return reflectorB.charAt(reflectorIndex);
  }

  // method to perform rotor decryption for enigma
  public String enigmaRotorDecrypt() {
    // set the rotors
    setRotors();

    trace("output before rotor decryption " + super.input);
    super.input = leftRotorDecrypt();
    trace("output after left rotor decryption " + super.input);
    super.input = middleRotorDecrypt();
    trace("output after middle rotor decryption " + super.input);
    super.input = rightRotorDecrypt();
    trace("output after right rotor decryption " + super.input);
    return super.input;
  }

  public static void resetEnigmaSupport() {
    rotorsList = "I II III"; // default
    offsetCharList = "A A Z"; // default, no offset
    plugboard = "AA"; 
  }
  
  private String leftRotorDecrypt() {
    amap = leftRotor;
    super.offset = offset1;
    super.counterOffset = counterOffset1;
    super.input = addOffset(super.input, offset1);
    super.input = super.decrypt();
    return addOffset(super.input, counterOffset1);
  }

  private String middleRotorDecrypt() {
    amap = middleRotor;
    super.offset = offset2;
    super.counterOffset = counterOffset2;
    super.input = addOffset(super.input, offset2);
    super.input = super.decrypt();
    return addOffset(super.input, counterOffset2);
  }

  private String rightRotorDecrypt() {
    amap = rightRotor;
    super.offset = offset3;
    super.counterOffset = counterOffset3;
    super.input = addOffset(super.input, offset3);
    super.input = super.decrypt();
    return addOffset(super.input, counterOffset3);
  }
}