public class WordleChar {
  private char letter;
  private String color;

  public WordleChar(char letter, String color) {
    this.letter = letter;
    this.color = color;
  }

  public char getLetter() { return this.letter; }
  public String getColor() { return this.color; }
}
