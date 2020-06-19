package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JobSignatureWithCluster extends SketchDataSetBase implements ICountable {


    private JobsRecord[] jobs;


    public JobSignatureWithCluster(JobsRecord[] jobs) {

        this.jobs = jobs;

    }

    @Override
    public void ConstructDataStructure() {

        sketches =  new ArrayList<>();

        for(var i=0; i<100;i++) {

            int clusterId = i;

            var clusterVal = Arrays.stream(jobs)
                    .filter(x->x.ClusterId==clusterId)
                    .map(x-> Long.toString(x.JobSignature))
                    .collect(Collectors.toList());

            var sketch = new CountDistinctSketchAlgorithm(
                    CountDistinctAlgorithmSettings.noOfHashFunctions,
                    CountDistinctAlgorithmSettings.R
            );

            sketch.GenerateCountDistinctSketch(clusterVal);

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

            var filValuesInt = new int[filterValues.length];
            if(filterType == FilterType.Range) {

                filValuesInt = expandValues(filterValues);
            }
            else {
                filValuesInt = Arrays.stream(filterValues)
                        .mapToInt(x->Integer.parseInt(x))
                        .toArray();
            }

            var unionStructures = new ArrayList<CountDistinctDataStructure>();

            for(var filter : filValuesInt) {

                unionStructures.add(this.sketches
                        .get(filter)
                        .CountDistinctDataStructure);
            }

            return this.UnionofDatasets(unionStructures).EstimateCount();

        }

        return 0L;
    }


    @Override
    public void GroupBy(GroupType val) {

    }
}
