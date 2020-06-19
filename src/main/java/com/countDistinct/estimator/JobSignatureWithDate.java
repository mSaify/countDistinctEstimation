package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JobSignatureWithDate extends SketchDataSetBase implements ICountable {

    private JobsRecord[] jobs;

    public JobSignatureWithDate(JobsRecord[] jobs) {

        this.jobs = jobs;

        this.sketches = new ArrayList<>();

    }

    @Override
    public void ConstructDataStructure() {

        for (var month=1;month<=12;month++) {

            int mon = month;

            var perMonthRecords = Arrays.stream(jobs)
                    .filter(x->x.month== mon)
                    .map(x-> String.valueOf(x.JobSignature))
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



    @Override
    public long count() {
        return 0L;
    }

    @Override
    public long countWithFilter(FilterType filterType, String[] filterValues) {

        if(filterType == FilterType.SingleValue) {
            var sketch = this.sketches.get(Integer.parseInt(filterValues[0])-1);
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
                        .get(filter-1)
                        .CountDistinctDataStructure);
            }

            return this.UnionofDatasets(unionStructures).EstimateCount();

        }

        return 0;
    }


    @Override
    public void GroupBy(GroupType val) {

    }
}
