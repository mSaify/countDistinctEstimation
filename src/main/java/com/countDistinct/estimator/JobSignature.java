package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JobSignature implements  ICountable {

    private CountDistinctSketchAlgorithm sketch;

    private JobSignatureWithCluster withCluster;
    private JobSignatureWithGeography withGeography;
    private JobSignatureWithDate withDate;

    private JobsRecord[] jobs;

    private boolean generateGroupSets=true;

    private ICountable finalExecutor;

    public JobSignature(JobsRecord[] jobs) {

        this.jobs = jobs;

        finalExecutor=this;

        this.sketch = new CountDistinctSketchAlgorithm(
                CountDistinctAlgorithmSettings.noOfHashFunctions,
                CountDistinctAlgorithmSettings.R
        );

    }

    public JobSignature(JobsRecord[] jobs,Boolean generateGroupSets) {
        this.generateGroupSets  = generateGroupSets;
    }


    @Override
    public void ConstructDataStructure() {

        this.sketch.GenerateCountDistinctSketch(GetAllJobSignature());

        if(generateGroupSets==true) {

            withCluster =  new JobSignatureWithCluster(jobs);
            withCluster.ConstructDataStructure();

            withGeography = new JobSignatureWithGeography(jobs);
            withGeography.ConstructDataStructure();

            withDate =  new JobSignatureWithDate(jobs);
            withDate.ConstructDataStructure();
        }


    }


    private List<String> GetAllJobSignature() {

        return Arrays.stream(this.jobs).map(j ->  String.valueOf(j.JobSignature)).collect(Collectors.toList());
    }

    @Override
    public long count() {

        if(this.finalExecutor==this)
            return this.sketch.EstimateCount();

        return this.finalExecutor.count();

    }

    @Override
    public long countWithFilter(FilterType filterType, String[] filterValues) {

        if(this.finalExecutor==this)
            return this.sketch.EstimateCount();

        return this.finalExecutor.countWithFilter(filterType,filterValues);
    }

    @Override
    public void GroupBy(GroupType groupType) {

        switch (groupType) {

            case ClusterId: finalExecutor = withCluster; break;
            case Geogrpahy: finalExecutor = withGeography; break;
            case Month: finalExecutor = withDate; break;

        }

    }

}

