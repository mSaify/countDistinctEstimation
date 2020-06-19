package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SourceIPAddress implements ICountable  {


    private CountDistinctSketchAlgorithm sketch;

    private SourceIPAddressWithCluster withCluster;
    private SourceIPAddressWithGeography withGeography;
    private SourceIPAddressWithDate withDate;

    private JobsRecord[] jobs;

    private boolean generateGroupSets=true;

    private ICountable finalExecutor;

    public SourceIPAddress(JobsRecord[] jobs) {

        this.jobs = jobs;

        finalExecutor=this;

        this.sketch = new CountDistinctSketchAlgorithm(
                CountDistinctAlgorithmSettings.noOfHashFunctions,
                CountDistinctAlgorithmSettings.R
                );
    }

    public SourceIPAddress(JobsRecord[] jobs,Boolean generateGroupSets) {
        this.generateGroupSets  = generateGroupSets;
    }


    @Override
    public void ConstructDataStructure() {

        this.sketch.GenerateCountDistinctSketch(GetAllSourceIpAddress());

        if(generateGroupSets==true) {

            withCluster =  new SourceIPAddressWithCluster(jobs);
            withCluster.ConstructDataStructure();

            withGeography = new SourceIPAddressWithGeography(jobs);
            withGeography.ConstructDataStructure();

            withDate = new SourceIPAddressWithDate(jobs);
            withDate.ConstructDataStructure();
        }


    }


    private List<String> GetAllSourceIpAddress() {

        return Arrays.stream(this.jobs).map(j -> j.IPAddress).collect(Collectors.toList());
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
