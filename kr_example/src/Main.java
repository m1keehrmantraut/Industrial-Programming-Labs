import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.sqrt;

public class Main {
    public static void main(String[] args) {
        String str = "hello world";
        str = str + '!';
        str = str.replace('h', 'H');
        System.out.println(str.substring(0, 5));

        StringBuilder sb = new StringBuilder();
        sb.append("jaba");
        sb.replace(2, 4, "ba is shit");
        sb.insert(12, '!');
        sb.delete(0, 2);
        System.out.println(sb);

        StringBuffer strb = new StringBuffer(sb);
        strb.insert(2, "ratrum");
        System.out.println(strb);

        try (FileWriter fl = new FileWriter("output.txt")) {
            for (int i = 0; i <= 120; i++) {
                fl.write(i * i + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (PrintWriter fl = new PrintWriter("outputer.txt")) {
            for (int i = 0; i <= 120; i++) {
                fl.write(sqrt(i) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                System.out.println(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String date = "2021:2:29";
        System.out.println(DateValidation.validate_date(date, "yyyy:MM:dd"));
        System.out.println(DateValidation.validate_calendar(2012, 8, 9));

        String number = "+374-(44)-485-42-94, +374-(29)-432-52-04";
        Pattern pattern = Pattern.compile("\\+\\d{3}-\\(\\d{2}\\)-\\d{3}-\\d{2}-\\d{2}");
        Matcher matcher = pattern.matcher(number);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

        Student[] students = {
                new Student("Петр", 20, 4.5),
                new Student("Анна", 19, 4.8),
                new Student("Иван", 21, 4.5),
                new Student("Мария", 20, 4.9)
        };
        Arrays.sort(students);
        for (Student s : students) {
            System.out.println(s);
        }

        String text = "hell yeah,hello world";
        StringTokenizer tokenizer = new StringTokenizer(text, ",");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            System.out.println(token);
        }
    }
}