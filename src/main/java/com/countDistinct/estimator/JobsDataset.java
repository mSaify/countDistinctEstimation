package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.ColumnType;
import com.countDistinct.estimator.InputReaders.JobsRecord;

public class JobsDataset implements IColumn<JobsDataset>, IQueryable {

    JobsRecord[] jobs;

    SourceIPAddress sourceIPAddressDS;

    JobSignature jobSignatureDS;


    public JobsDataset(JobsRecord [] jobsRecords) {
        jobs = jobsRecords;
    }

    public JobsDataset withSourceIPAddressDataSet() {
         sourceIPAddressDS = new SourceIPAddress(jobs);
         sourceIPAddressDS.ConstructDataStructure();
        return this;
    }

    public JobsDataset withJobSignatureDataSet() {
        jobSignatureDS = new JobSignature(jobs);
        jobSignatureDS.ConstructDataStructure();
        return this;
    }


    @Override
    public JobsDataset column(ColumnType columnType) {
        return null;
    }
}
