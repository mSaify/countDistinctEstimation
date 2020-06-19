package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SourceIPAddressWithGeography extends SketchDataSetBase implements ICountable {

    private JobsRecord[] jobs;

    public SourceIPAddressWithGeography(JobsRecord[] jobs) {

        this.jobs = jobs;

        this.sketches = new ArrayList<>();

    }

    @Override
    public void ConstructDataStructure() {

        for(var i=0; i<10;i++) {

            int geoId = i;

            var geoValue = Arrays.stream(jobs)
                    .filter(x->x.geography== geoId)
                    .map(x->x.IPAddress)
                    .collect(Collectors.toList());

            var sketch = new CountDistinctSketchAlgorithm(
                    CountDistinctAlgorithmSettings.noOfHashFunctions,
                    CountDistinctAlgorithmSettings.R
            );

            sketch.GenerateCountDistinctSketch(geoValue);

            sketches.add(sketch);
        }

    }



    @Override
    public long count() {
        return 0L;
    }


    @Override
    public void GroupBy(GroupType val) {

    }
}
