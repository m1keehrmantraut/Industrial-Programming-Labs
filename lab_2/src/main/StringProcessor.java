package main;

import java.util.*;
import java.text.*;
import java.util.regex.*;

public class StringProcessor {
    private String inputString;
    private final String delimiters;
    private final String initialString;

    public StringProcessor(String inputString, String delimiters) {
        this.inputString = inputString;
        this.delimiters = delimiters;
        this.initialString = inputString;
    }

    public String process() {
        System.out.println(this.inputString);

        List<String> exponentialLexemes = this.extractExponential();
        List<String> timeLexemes = this.extractTime();
        List<Double> numStrings = this.extractNumbers();
        List<String> stringLexemes = new ArrayList<>();

        this.addRandomNumber();

        StringTokenizer tokenizer = new StringTokenizer(inputString, delimiters);

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            stringLexemes.add(token);
        }
        return resultProcessing(exponentialLexemes, timeLexemes, stringLexemes, numStrings);
    }

    private String resultProcessing(List<String> exponentialLexemes, List<String> timeLexemes, List<String> stringLexemes, List<Double> numStrings) {
        StringBuilder result = new StringBuilder();

        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        DecimalFormat percentFormat = new DecimalFormat("0%");
        DecimalFormat currencyFormat = new DecimalFormat("0.00 ₽");

        decimalFormat.setGroupingUsed(false);
        percentFormat.setGroupingUsed(false);
        currencyFormat.setGroupingUsed(false);

        result.append("Исходная строка: ").append(initialString).append("\n");
        result.append("Разделители: ").append(delimiters).append("\n");
        result.append("Подстроки: ").append(stringLexemes).append("\n");
        result.append("Вещественные числа: ").append(exponentialLexemes).append("\n");
        result.append("Время (ЧЧ-ММ): ").append(timeLexemes).append("\n");

        if (!numStrings.isEmpty()) {
            result.append("Форматированные числа:\n");
            for (Double num : numStrings) {
                result.append("  - ").append(decimalFormat.format(num))
                        .append(" (").append(percentFormat.format(num / 100))
                        .append(", ").append(currencyFormat.format(num))
                        .append(")\n");
            }
        }

        if (!timeLexemes.isEmpty()) {
            timeLexemes.sort(Comparator.naturalOrder());
            result.append("Отсортированное время: ").append(timeLexemes).append("\n");
        }

        addRandomNumber();
        result.append("Строка со случайным числом: ").append(inputString).append("\n");

        result.append("После удаления подстрок: ").append(removeSmallestSubstring()).append("\n");

        return result.toString();
    }

    public String removeSmallestSubstring() {
        StringBuilder result = new StringBuilder(initialString);

        Pattern pattern = Pattern.compile("[а-яА-Я][^" + Pattern.quote(delimiters) + "]*?[0-9]");
        Matcher matcher = pattern.matcher(initialString);

        List<MatchResult> strToDelete = new ArrayList<>();
        while (matcher.find()) {
            strToDelete.add(matcher.toMatchResult());
        }

        strToDelete.sort(Comparator.comparingInt(mr -> mr.group().length()));

        if (! strToDelete.isEmpty()) {
            MatchResult smallest = strToDelete.get(0);
            String toRemoveStr = smallest.group();

            int index = result.indexOf(toRemoveStr);
            if (index != -1) {
                result.delete(index, index + toRemoveStr.length());
            }

        }
        return result.toString();
    }

    private void removeSubstrings(List<String> substrings) {
        for (String substring : substrings) {
            this.inputString = this.inputString.replaceFirst(substring, "");
        }
    }

    public List<String> extractExponential() {
        List<String> substrings = new ArrayList<>();

        Pattern pattern = Pattern.compile("-?\\d*\\.?\\d+[eE][-+]?\\d+");
        Matcher matcher = pattern.matcher(this.inputString);

        while (matcher.find()) {
            substrings.add(matcher.group());
        }

        this.removeSubstrings(substrings);

        return substrings;
    }

    public boolean validateTimeLexeme(String timeLexeme) {
        if (timeLexeme == null || !timeLexeme.matches("\\d{1,2}-\\d{1,2}")) {
            return false;
        }

        String[] parts = timeLexeme.split("-");
        if (parts.length != 2) {
            return false;
        }

        try {
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);

            return hours >= 0 && hours <= 23 && minutes >= 0 && minutes <= 59;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private List<String> extractTime() {
        List<String> substrings = new ArrayList<>();

        Pattern pattern = Pattern.compile("\\d+-\\d+");
        Matcher matcher = pattern.matcher(this.inputString);

        while (matcher.find()) {
            String matched = matcher.group();
            if (this.validateTimeLexeme(matched)) {
                substrings.add(matched);
            }
        }

        this.removeSubstrings(substrings);

        return substrings;
    }

    public List<Double> extractNumbers() {
        List<Double> numbers = new ArrayList<>();

        Pattern pattern = Pattern.compile("-?\\d+(?:\\.\\d+)?");
        Matcher matcher = pattern.matcher(this.inputString);

        while (matcher.find()) {
            String matched = matcher.group();
            try {
                Double parsed = Double.parseDouble(matched);
                numbers.add(parsed);
            } catch (NumberFormatException e) {
                System.out.println("Failed to parse: " + matched);
            }
        }

        return numbers;
    }

    private void addRandomNumber() {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(this.inputString);
        Random random = new Random();
        int randomNum = random.nextInt(10);

        if (matcher.find()) {
            String decimalNumber = matcher.group();
            this.inputString = this.inputString.replaceFirst(decimalNumber, decimalNumber+randomNum);
        }
        else {
            int center = this.inputString.length() / 2;
            this.inputString = this.inputString.substring(0, center) + randomNum + this.inputString.substring(center);
        }
    }
}