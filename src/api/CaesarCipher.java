package api;

import java.io.*;
import java.util.*;

public class CaesarCipher {
    private static final String ALPHABET = "абвгдежзийклмнопрстуфхцчшщъыьэюя.,\"\":-!? ";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Оберіть режим:");
        System.out.println("1. Шифрування");
        System.out.println("2. Розшифрування");
        System.out.println("3. Криптоаналіз");
        int mode = scanner.nextInt();

        switch (mode) {
            case 1:
                encrypt();
                break;
            case 2:
                decrypt();
                break;
            case 3:
                bruteForce();
                break;
            default:
                System.out.println("Невірний вибір режиму.");
        }
    }

    private static void encrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть текст для шифрування:");
        String plaintext = scanner.nextLine();
        System.out.println("Введіть ключ шифрування (ціле число):");
        int key = scanner.nextInt();

        String ciphertext = "";
        for (int i = 0; i < plaintext.length(); i++) {
            char ch = plaintext.charAt(i);
            int index = (ALPHABET.indexOf(ch) + key) % ALPHABET.length();
            ciphertext += ALPHABET.charAt(index);
        }

        saveToFile(ciphertext, "encrypted.txt");
        System.out.println("Текст було успішно зашифровано і збережено в файл encrypted.txt.");
    }

    private static void decrypt() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть шлях до файлу з зашифрованим текстом:");
        String filePath = scanner.nextLine();
        System.out.println("Введіть ключ шифрування (ціле число):");
        int key = scanner.nextInt();

        String ciphertext = readFromFile(filePath);
        String plaintext = "";
        for (int i = 0; i < ciphertext.length(); i++) {
            char ch = ciphertext.charAt(i);
            int index = (ALPHABET.indexOf(ch) - key + ALPHABET.length()) % ALPHABET.length();
            plaintext += ALPHABET.charAt(index);
        }

        System.out.println("Розшифрований текст:");
        System.out.println(plaintext);
    }

    private static void bruteForce() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть шлях до файлу з зашифрованим текстом:");
        String filePath = scanner.nextLine();

        String ciphertext = readFromFile(filePath);
        String decryptedText = "";

        for (int key = 1; key < ALPHABET.length(); key++) {
            for (int i = 0; i < ciphertext.length(); i++) {
                char ch = ciphertext.charAt(i);
                int index = (ALPHABET.indexOf(ch) - key + ALPHABET.length()) % ALPHABET.length();
                decryptedText += ALPHABET.charAt(index);
            }

            System.out.println("Ключ " + key + ":");
            System.out.println(decryptedText);
            System.out.println("---------------------");

            decryptedText = ""; // Очищуємо розшифрований текст
        }
    }

    private static void saveToFile(String text, String filePath) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println(text);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні файлу.");
            e.printStackTrace();
        }
    }

    private static String readFromFile(String filePath) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                text.append(line);
            }
        } catch (IOException e) {
            System.out.println("Помилка при читанні файлу.");
            e.printStackTrace();
        }

        return text.toString();
    }
}
