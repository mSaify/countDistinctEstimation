package com.countDistinct.estimator.InputReaders;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public final class QueryReader implements InputReader<Query> {


    @Override
    public Optional<Query[]> process(String filePath) throws IOException {

        FileInputStream inputStream = null;
        Scanner sc = null;
        ArrayList<Query> inputQueries = new ArrayList<>();

        try {
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if(!line.isEmpty())
                    inputQueries.add(new Query(line));
            }

            if (sc.ioException() != null) {
                throw sc.ioException();
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (sc != null) {
                sc.close();
            }
        }
        Query[] resultQueries = new Query[inputQueries.size()];
        return Optional.ofNullable(inputQueries.toArray(resultQueries));
    }
}
