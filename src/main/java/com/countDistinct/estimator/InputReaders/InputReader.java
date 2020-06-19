package com.countDistinct.estimator.InputReaders;

import java.io.IOException;
import java.util.Optional;

public interface InputReader<T> {

    Optional<T[]> process(String  filePath) throws IOException;
}

