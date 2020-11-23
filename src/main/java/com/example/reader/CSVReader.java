package com.example.reader;

import com.example.exception.AppException;
import com.example.reader.parser.Parser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads a CSV file from the given path and transforms the CSV contents into a
 * List of items of the given type using the given Parser.
 * @param <T> parameterized return type
 */
public class CSVReader<T> implements Reader<T> {
    /**
     * Path of the CSV file
     */
    private final String csvFilePath;

    /**
     * Parser for transforming CSV line into a Java object.
     */
    private final Parser<T> parser;

    public CSVReader(String csvFilePath, Parser<T> parser) {
        this.csvFilePath = csvFilePath;
        this.parser = parser;
    }

    /**
     * Read the CSV file and return a List of objects of given type.
     *
     * @return a list of objects of given type
     * @throws AppException custom exception for errors while reading the given file.
     */
    @Override
    public List<T> readFile() throws AppException {
        List<T> result = new ArrayList<>();
        Path filePath = Paths.get(csvFilePath);
        try {
            List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            for (String line : lines.subList(1, lines.size())) {
                result.add(parser.parse(line));
            }
        } catch (IOException e) {
            throw new AppException("Failed to read file: " + csvFilePath);
        }
        return result;
    }
}
