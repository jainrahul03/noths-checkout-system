package com.example.reader;

import com.example.exception.AppException;

import java.util.List;

/**
 * A skeleton definition for a Reader.
 * @param <T> parameterized return type
 */
public interface Reader<T> {
    /**
     * Read file from the given source and return a List of objects of given type.
     *
     * @return a list of objects of given type
     * @throws AppException custom exception for errors while reading the given file
     */
    List<T> readFile() throws AppException;
}
