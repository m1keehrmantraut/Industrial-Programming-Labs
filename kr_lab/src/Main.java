import java.io.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        String str1;
        String str2;
        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            str1 = scanner.nextLine();
            str2 = scanner.nextLine();

            try (PrintWriter writer = new PrintWriter("output.txt")) {
                List<Integer> positions = new ArrayList<>();
                while (str1.lastIndexOf(str2) != -1) {
                    int pos = str1.lastIndexOf(str2);
                    positions.add(pos);
                    str1 = str1.substring(0, pos);
                }
                positions = positions.reversed();
                for (int i : positions) {
                    writer.println(i);
                }
            }
            catch (FileNotFoundException exception) {
                throw exception;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}