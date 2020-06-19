package com.countDistinct.estimator.InputReaders;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FastJobsDataReader implements InputReader<JobsRecord> {

    public Optional<JobsRecord[]> process(String inputFilePath) {
        List<JobsRecord> inputList = new ArrayList<JobsRecord>();
        try{
            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            inputList = br.lines().map(JobsRecord::new).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {

        }

        System.out.println(String.format("input of 1st record read as ipAddr %s, clusterId %s, jobId %s, month %s, geo  %s",
                inputList.get(0).IPAddress,
                inputList.get(0).ClusterId,
                inputList.get(0).JobSignature,
                inputList.get(0).month,
                inputList.get(0).geography));

        JobsRecord[] resultQueries = new JobsRecord[inputList.size()];
        return Optional.ofNullable(inputList.toArray(resultQueries));

    }
}
