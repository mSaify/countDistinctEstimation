package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.ColumnType;
import com.countDistinct.estimator.InputReaders.FilterType;
import com.countDistinct.estimator.InputReaders.GroupType;

public class QueryBuilder implements IColumn<QueryBuilder> {

    private JobsDataset dataset;

    private FilterType filterType;

    private ICountable countableAttr;

    private String[] filterValues;

    private GroupType groupType;


    public QueryBuilder() {


    }

    public long countDistinct() {

        return countableAttr.countWithFilter(filterType,filterValues);

    }

    public QueryBuilder groupBy(GroupType type) {
        countableAttr.GroupBy(type);
        return this;
    }



    public QueryBuilder(JobsDataset jobsDataset) {
        dataset=jobsDataset;

    }


    public QueryBuilder onDataSet(JobsDataset jobsDataset) {

        dataset=jobsDataset;

        return this;
    }

    @Override
    public QueryBuilder column(ColumnType columnType) {



        switch (columnType) {

            case IPAddress:  countableAttr  = dataset.sourceIPAddressDS; break;

            case JobSignature: countableAttr =  dataset.jobSignatureDS; break;
        }

        return this;
    }

    public QueryBuilder filter(FilterType filterType) {

        this.filterType=filterType;

        return this;
    }

    public QueryBuilder filterValues(String[] arr) throws Exception {
        if(filterType != FilterType.MultipleValue)
            throw new Exception("filter type values don't match");

        filterValues = arr;

        return this;
    }

    public QueryBuilder filterValues(String arr1, String arr2) throws Exception {

        if(filterType != FilterType.Range)
            throw new Exception("filter type values don't match");

        filterValues = new String[] {arr1,arr2};
        return this;
    }

    public QueryBuilder filterValues(String arr) throws Exception {

        if(filterType != FilterType.SingleValue)
            throw new Exception("filter type values don't match");

        filterValues = new String[] {arr};

        return this;
    }
}
