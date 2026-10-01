package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static int getInt() {
        do {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số nguyên.");
            }
        } while (true);
    }

    public static int getPositiveInt(String message) {
        do {
            try {
                int i = getInt();
                if (i < 0) {
                    System.out.println(message);
                } else {
                    return i;
                }
            } catch (NumberFormatException e) {
            }
        } while (true);
    }

    public static double getDouble() {
        do {
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập số.");
            }
        } while (true);
    }

    public static String getNonBlankString() {
        do {
            String s = scanner.nextLine();
            if (s.isBlank()) {
                System.out.println("Vui lòng nhập chuỗi có giá trị.");
            } else {
                return s;
            }
        } while (true);
    }

    public static String getString(String regex) {
        do {
            String s = getNonBlankString();
            if (s.matches(regex)) {
                return s;
            } else {
                System.out.println("Vui lòng nhập đúng địng dạng " + regex);
            }
        } while (true);

    }

    public static LocalDateTime getDateTime() {
        do {
            try {
                return LocalDateTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            } catch (DateTimeParseException e) {
                System.out.println("Vui lòng nhập đúng địng dạng ngày giờ.");
            }
        } while (true);
    }
}
