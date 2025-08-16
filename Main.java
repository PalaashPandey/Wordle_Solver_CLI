import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
  public static String[] words;
  public static WordleChar[] solvedWord;

  public static String[] shuffleArray(String[] array) {
    int max = array.length - 1;
    for (int i = 0; i < array.length - 1; i++) {
      int min = i + 1;
      int range = max - min;
      int j = (int) (Math.random() * (range + 1)) + min;
      String temp = array[i];
      array[i] = array[j];
      array[j] = temp;
    }
    return array;
  }

  public static String[] getWords() {
    final int LENGTH_OF_WORDS = 3158;
    String[] words = new String[LENGTH_OF_WORDS];
    
    try {
      BufferedReader wordFileReader = new BufferedReader(new FileReader("words.txt"));
      int i = 0;
      String currLine = wordFileReader.readLine();
      while (currLine != null) {
        words[i] = currLine.trim();
        i++;
        currLine = wordFileReader.readLine();
      }
      wordFileReader.close();
      return shuffleArray(words);
    }
    catch (IOException error) {
      error.printStackTrace();
      return new String[0];
    }
  }

  public static String getPromptString(int pos, String color) {
    String suffix = "";
    if (pos == 1) { suffix = "st"; }
    else if (pos == 2) { suffix = "nd"; }
    else if (pos == 1) { suffix = "rd"; }
    else { suffix = "th"; }
    return "Enter the " + pos + suffix + " position (1-5) from the left of a " + color + " character (-1 for none): ";
  }

  public static boolean isOtherIndexNotAvailable(String word, char target) {
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == target && solvedWord[i] == null) {
        return false;
      }
    }
    return true;
  }

  public static boolean charFrequencyExceedsUserInput(String word, String userInput, char target) {
    int userInputCount = 0;
    for (int i = 0; i < userInput.length(); i++) {
      if (userInput.charAt(i) == target) {
        userInputCount++;
      }
    }
    int wordCharCount = 0;
    for (int i = 0; i < word.length(); i++) {
      if (word.charAt(i) == target) {
        wordCharCount++;
      }
    }
    return userInputCount - 1 < wordCharCount;
  }

  public static int filterWordsAndGetRemaining(String userInput, int index, String color) {
    int remaining = 0;
    for (int i = 0; i < words.length; i++) {
      if (words[i] == null) { 
        continue; 
      }

      // Invalid Green: words[i].charAt(index) != userInput.charAt(index)
      if (color.equals("green")) {
        solvedWord[index] = new WordleChar(userInput.charAt(index), color);
      }
      if (color.equals("green") && words[i].charAt(index) != userInput.charAt(index)) {
        words[i] = null;
        continue;
      }

      // Invalid Yellow: words[i].charAt(index) == userInput.charAt(index) or no other valid placement for char
      if (color.equals("yellow") && (words[i].charAt(index) == userInput.charAt(index) || isOtherIndexNotAvailable(words[i], userInput.charAt(index)))) {
        words[i] = null;
        continue;
      }

      // Invalid Gray: word has too many of char
      if (color.equals("gray") && charFrequencyExceedsUserInput(words[i], userInput, userInput.charAt(index))) {
        words[i] = null;
        continue;
      }

      remaining++;
    }
    return remaining;
  }

  public static String getRecommendation() {
    for (String s: words) {
      if (s != null) {
        return s;
      }
    }
    return "";
  }

  public static String[] getFirstTwoRecs() {
    String[] recs = new String[2];
    int curr = 0;
    for (String s: words) {
      if (curr > 1) { break; }
      if (s != null) {
        recs[curr] = s;
        curr++;
      }
    }
    return recs;
  }

  public static String getUserAction(Scanner scan, int remaining) {
    String nextAction;
    if (remaining == 0) {
      System.out.print("Word could not be guessed! Play again? (y/n): ");
      nextAction = scan.nextLine().toLowerCase();
    }
    else if (remaining == 1) {
      System.out.print("The wordle is likely " + getRecommendation() + "! Play again? (y/n): ");
      nextAction = scan.nextLine().toLowerCase();
    }
    else {
      String[] firstTwoRecs = getFirstTwoRecs();
      System.out.print("The wordle is likely " + firstTwoRecs[0] + " or " + firstTwoRecs[1] + "! Play again? (y/n): ");
      nextAction = scan.nextLine().toLowerCase();
    }
    return nextAction;
  }

  // MAIN
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    String wordInput;

    while (true) {
      words = getWords();
      solvedWord = new WordleChar[5];
      if (words.length == 0) {
        System.out.println("Error retrieving words");
        scan.close();
        return;
      }
      System.out.print("\033[H\033[2J");
      System.out.flush();
      System.out.print("Enter your starting word in here and wordle! (Q to quit): ");
      wordInput = scan.nextLine().toLowerCase();
      if (wordInput.equals("q")) {
        break;
      }

      int remaining = 0;

      while (true) {
        // Green
        int indexInput = 0;
        int currPos = 1;
        while (true) {
          System.out.print(getPromptString(currPos, "green"));
          indexInput = scan.nextInt();
          scan.nextLine();
          if (indexInput == -1) { break; }
          filterWordsAndGetRemaining(wordInput, indexInput - 1, "green");
          currPos++;
        }

        // Yellow
        indexInput = 0;
        currPos = 1;
        while (true) {
          System.out.print(getPromptString(currPos, "yellow"));
          indexInput = scan.nextInt();
          scan.nextLine();
          if (indexInput == -1) { break; }
          filterWordsAndGetRemaining(wordInput, indexInput - 1, "yellow");
          currPos++;
        }

        // Gray
        indexInput = 0;
        currPos = 1;
        while (true) {
          System.out.print(getPromptString(currPos, "gray"));
          indexInput = scan.nextInt();
          scan.nextLine();
          if (indexInput == -1) { break; }
          remaining = filterWordsAndGetRemaining(wordInput, indexInput - 1, "gray");
          currPos++;
        }
        
        
        
        if (remaining <= 2) {
          String nextAction = getUserAction(scan, remaining);
          if (nextAction.equals("y")) { break; }
          
          System.out.println("Thanks for playing!");
          scan.close();
          return;
        }

        System.out.print("\033[H\033[2J");
        System.out.flush();
        String newRec = getRecommendation();
        System.out.println("Remaining candidates: " + remaining);
        System.out.println("Recommended word to try on Wordle: " + newRec);
        
        System.out.print("Get another? (y/n): ");
        String tryAgain = scan.nextLine();
        while (tryAgain.toLowerCase().equals("y")) {
          String prevRec = new String(newRec);
          while (prevRec.equals(newRec)) {
            prevRec = new String(newRec);
            words = shuffleArray(words);
            newRec = getRecommendation();
          }
          System.out.println("Recommended word to try on Wordle: " + newRec);
          System.out.print("Get another? (y/n): ");
          tryAgain = scan.nextLine();
        }
        wordInput = newRec;
      }
    }
    System.out.println("Thanks for playing!");
    scan.close();
  }
}