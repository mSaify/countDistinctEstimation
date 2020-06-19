package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SourceIPAddressWithDate extends SketchDataSetBase implements ICountable {

    private JobsRecord[] jobs;

    public SourceIPAddressWithDate(JobsRecord[] jobs) {

        this.jobs = jobs;

        this.sketches = new ArrayList<>();

    }

    @Override
    public void ConstructDataStructure() {

        for (var month=1;month<=12;month++) {

                int mon = month;

                var perMonthRecords = Arrays.stream(jobs)
                        .filter(x->x.month== mon)
                        .map(x->x.IPAddress)
                        .collect(Collectors.toList());

                if(perMonthRecords.size()>0) {

                    var sketch = new CountDistinctSketchAlgorithm(
                            CountDistinctAlgorithmSettings.noOfHashFunctions,
                            CountDistinctAlgorithmSettings.R
                    );

                    sketch.GenerateCountDistinctSketch(perMonthRecords);

                    sketches.add(sketch);
                }
        }
    }

    //default value to return is the 0th sketch count
    @Override
    public long count() {
        return this.sketches.get(0).EstimateCount();
    }



    @Override
    public void GroupBy(GroupType val) {

    }
}
