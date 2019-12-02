package de.juliandrees.battlearena.model;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public class GameMenu implements Closeable {

    private Scanner scanner;
    private InputStream inputStream;
    private OutputStream outputStream;

    public GameMenu(final InputStream inputStream, final OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.scanner = new Scanner(this.inputStream, StandardCharsets.UTF_8);
    }

    public Optional<String> readString(final String prefix) throws IOException {
        print(prefix + ": ");
        return Optional.of(scanner.nextLine());
    }

    public Optional<Integer> readInteger(final String prefix) throws IOException {
        Optional<String> optionalInput = this.readString(prefix);
        if (!optionalInput.isPresent()) {
            return Optional.empty();
        }
        try {
            int number = Integer.parseInt(optionalInput.get());
            return Optional.of(number);
        } catch (NumberFormatException ex) {
            this.println("Bitte gebe eine Zahl ein.");
            return Optional.empty();
        }
    }

    public void print(String message) throws IOException {
        outputStream.write(message.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
    }

    public void println(final String message) throws IOException {
        print(message + System.lineSeparator());
    }

    public void emptyLine() throws IOException {
        println("");
    }

    @Override
    public void close() throws IOException {
        scanner.close();
        inputStream.close();
        outputStream.close();
    }

}
