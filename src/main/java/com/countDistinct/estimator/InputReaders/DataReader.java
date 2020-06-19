package com.countDistinct.estimator.InputReaders;


import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public final class DataReader implements InputReader<JobsRecord> {
    @Override
    public Optional<JobsRecord[]> process(String filePath) throws IOException {
        FileInputStream inputStream = null;
        Scanner sc = null;
        ArrayList<JobsRecord> jobRecords = new ArrayList<>();

        try {
            inputStream = new FileInputStream(filePath);
            sc = new Scanner(inputStream, "UTF-8");
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                jobRecords.add(new JobsRecord(line));
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
        JobsRecord[] resultQueries = new JobsRecord[jobRecords.size()];
        return Optional.ofNullable(jobRecords.toArray(resultQueries));
    }
}
