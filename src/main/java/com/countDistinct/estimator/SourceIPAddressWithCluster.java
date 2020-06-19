package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SourceIPAddressWithCluster extends SketchDataSetBase  implements ICountable {


    private JobsRecord[] jobs;

    public SourceIPAddressWithCluster(JobsRecord[] jobs) {

        this.jobs = jobs;

    }

    @Override
    public void ConstructDataStructure() {

        sketches =  new ArrayList<>();

        for(var i=0; i<100;i++) {

            int clusterId = i;

            var geoValue = Arrays.stream(jobs)
                    .filter(x->x.ClusterId== clusterId)
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
        return 0;
    }

    @Override
    public long countWithFilter(FilterType filterType, String[] filterValues) {

        if(filterType == FilterType.SingleValue) {
            var sketch = this.sketches.get(Integer.parseInt(filterValues[0]));
            return sketch.EstimateCount();

        }
        else if(filterType == FilterType.MultipleValue || filterType == FilterType.Range) {

            var filteredSketches = new ArrayList<>();

            for(var filter : filterValues) {




                // need to figure out how to get union of sketches
                //var sketch = this.sketches.get(Integer.parseInt(filterValues[0]));
                // return sketch.EstimateCount();

            }

        }

        return 0L;
    }


    @Override
    public void GroupBy(GroupType val) {

    }
}
