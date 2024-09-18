import java.io.*;
import java.util.Scanner;

public class Main {
    private static final char[] ALPHABET = {
            'а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з',
            'и','к', 'л', 'м', 'н', 'о', 'п', 'р', 'с', 'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь', 'э', 'я', '.', ',', '«', '»', '"', '\'', ':', '!', '?', ' '
    };

    private static final int ALPHABET_SIZE = ALPHABET.length;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Программа для шифрования и расшифровки текста \n"+"-".repeat(20));
        System.out.println("Введите путь к файлу для обработки: ");
        String filePath = scanner.nextLine();
        System.out.println("Введите сдвиг: ");
        int shift = scanner.nextInt();
        System.out.println("Выберите режим работы \n 1 - Шифрование \n 2 - Расшифровка \n 3 - Выход из программы ");
        int mode = scanner.nextInt();
        scanner.nextLine();

        try {switch (mode) {
            case 1:
                processFile(filePath, shift, true);
            case 2:
                processFile(filePath, shift, true);
            case 3:
                System.exit(0);
            default:
                System.out.println("Такого режима нет.");
        }}  catch (IOException e) {
           e.printStackTrace();
       } finally {
            scanner.close();
        }
    }

    public static void processFile(String filePath, int shift, boolean encrypt) throws IOException {
        File inputFile = new File(filePath);
        File outputFile = new File("result.txt");

        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new FileNotFoundException("Такого файла нет, перепроверьте путь");
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine = encrypt ? encrypt(line.toLowerCase(), shift) : decrypt(line.toLowerCase(), shift);
                writer.write(processedLine);
                writer.newLine();
            }
        }
        System.out.println("Обработка завершена! Результат записан в result.txt");
    }

    private static String encrypt(String text, int shift) {
        return processText(text, shift);
    }

    private static String decrypt(String text, int shift) {
        return processText(text, -shift);
    }

    private static String processText(String text, int shift) {
        StringBuilder result = new StringBuilder();

        for (char c : text.toCharArray()) {
            int index = indexOf(c);
            if (index != -1) {
                int newIndex = (index + shift) % ALPHABET_SIZE;
                if (newIndex < 0) {
                    newIndex += ALPHABET_SIZE;
                }
                result.append(ALPHABET[newIndex]);
            } else {
                result.append(c); // Если символа нет в списке, то не трогаем его
            }
        }

        return result.toString();
    }

    private static int indexOf(char c) {
        for (int i = 0; i < ALPHABET.length; i++) {
            if (ALPHABET[i] == c) {
                return i;
            }
        }
        return -1; // Символ не найден
    }
}