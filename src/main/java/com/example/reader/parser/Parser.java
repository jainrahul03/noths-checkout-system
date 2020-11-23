package com.example.reader.parser;

/**
 * A skeleton definition for a Parser.
 * @param <T> parameterized return type
 */
public interface Parser<T> {
    /**
     * Parse a string and transform it into an object of given type.
     *
     * @param line a string representation of a single line in a file
     * @return object of given type.
     */
    T parse(String line);
}
