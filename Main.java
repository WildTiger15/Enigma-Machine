import java.util.*;

// https://www.101computing.net/enigma-machine-emulator/
//   rotors = II II I
//   offset = N D O
//   plugboard = AZ BY CX DW EV FU GT HS IR JQ
//
//  if command has no '='', and does not start with '>' or '<' 
//     then just use Enigma Machine to encrypt/decrypt
//
//   BARRELROLL    // encrpt the text using settings
//   RQGMLJZFCE    // encrpt the text using settings
//   Hello World   // Will also removes spaces. converts to upper case.
//   Hello, World  // removes punctuation, too.
//
// Finally, if there are two sections of text, assume one is plain text and the other
// is Enigma Text. The goal is to crack the machine and identify the rotors, offsets and plugboard.
//   VIMQUICKBROWNFOXESJUMPEDOVERTHELAZYDOG = QHYVRDMEIDSCMKCJNUFBYRPFADZSGROMGDZGBX

public class Main {

  private String offset = "";
  private String inputText = "";

  public static Scanner console = new Scanner(System.in);

  public static void main(String[] args) {
    Main program = new Main();
    program.runInputLoop();
  }
  // method to process a given command
  public String processCommand(String command) {
    String result = "";
    //reset the input and offset variables before command processing
    resetInputAndOffset();
    
    if (command.contains("=")) {
      parseCommandArguments2(command);
    } else if (command.contains("<") || command.contains(">"))  {
      String commandArguments = command.substring(2);
      // parse the command arguments
      parseCommandArguments(commandArguments);
    } else {
      inputText = command;
    }

    if (command.toUpperCase().contains("<C")) {
      CipherBase.cipher = new CaesarCipher(inputText, offset);
      result = CipherBase.cipher.decrypt();
    } else if (command.toUpperCase().contains(">C")) {
      CipherBase.cipher = new CaesarCipher(inputText, offset);
      result = CipherBase.cipher.encrypt();
    } else if (command.toUpperCase().contains("<A")) {
      CipherBase.cipher = new AffineCipher(inputText);
      result = CipherBase.cipher.decrypt();
    } else if (command.toUpperCase().contains(">A")) {
      CipherBase.cipher = new AffineCipher(inputText);
      result = CipherBase.cipher.encrypt();
    } else if (command.toUpperCase().contains("AMAP")) {
      AffineCipher.amap = inputText;
    } else if (command.toUpperCase().contains(">R") || command.toUpperCase().contains("<R")) {
      RotorCipher rotorCipher = new RotorCipher(inputText, offset);
       if(command.toUpperCase().contains(">R")){
       result = rotorCipher.rotorEncrypt();
       } else{
       result = rotorCipher.rotorDecrypt();
       }     
    } else if (command.toUpperCase().contains(">E") || command.toUpperCase().contains("<E")) {
      EnigmaCipher.resetEnigmaSupport();
      EnigmaCipher enigmaCipher = new EnigmaCipher(inputText, offset);
     if(command.toUpperCase().contains(">E")){
       result = enigmaCipher.enigmaRotorEncrypt();
     } else{
       result = enigmaCipher.enigmaRotorDecrypt();
     }
    } else if (command.toUpperCase().contains("ROTORS")) {
      EnigmaCipher.rotorsList = inputText;
    } else if (command.toUpperCase().contains("OFFSET")) {
      EnigmaCipher.offsetCharList = inputText;
    } else if (command.toUpperCase().contains("PLUGBOARD")) {
      EnigmaCipher.plugboard = inputText;
    } else if (command.toUpperCase().contains("TRACE")) {
      CipherBase.traceMode = inputText;
    } else {
      EnigmaCipher fullEnigmaCipher = new EnigmaCipher(inputText,"");
      result = fullEnigmaCipher.performCompleteEnigmaOperations();
    } 
    return result;
  }

  private void parseCommandArguments(String commandArguments) {
    int firstSpaceCharIndex = commandArguments.indexOf(' ');
    if (firstSpaceCharIndex == 0) {
      inputText = commandArguments.substring(firstSpaceCharIndex + 1);
    } else {
      offset = commandArguments.substring(0, firstSpaceCharIndex);
      inputText = commandArguments.substring(firstSpaceCharIndex + 1);
    }
    System.out.println("Offset is " + offset);
    System.out.println("text is " + inputText);
  }

  private void parseCommandArguments2(String command) {
    int indexOfEqual = command.indexOf('=');
    inputText = command.substring(indexOfEqual + 2);
  }

  private void resetInputAndOffset() {
    inputText = "";
    offset = "";
  }

  private void runInputLoop() {
    boolean done = false;
    while (!done) {
      System.out.print("Enter: ");
      String input = console.nextLine();
      input = input.trim();
      if (input.equalsIgnoreCase("quit")) {
        done = true;
      } else if (input.length() > 0 && !UnitTestRunner.processCommand(input, this::processCommand)) {
        String result = processCommand(input);
        System.out.println(result);
      }
    }
  }
}