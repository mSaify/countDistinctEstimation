package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.*;

import java.util.ArrayList;
import java.util.Optional;

public class executor {

    static String path = "1000IpAddr.tsv";
    static String queryFile = "queryFile.tsv";
    static Optional<JobsRecord[]> jobsRecords;

    public  static void main(String[] args) throws Exception {

        setArgs(args);

        jobsRecords =  new FastJobsDataReader().process(path);
        var queries =  new QueryReader().process(queryFile);

        CountDistinctAlgorithmSettings
                .withInitialValue(2,(int) Math.pow(2,17));


        System.out.println("constructing all Count Distinct Sketches .... ");

        var jobsDataSet = new JobsDataset(jobsRecords
                                        .orElse(new JobsRecord[0]))
                                        .withSourceIPAddressDataSet()
                                        .withJobSignatureDataSet();

        QueryExecutor(queries, jobsDataSet);

    }

    private static void setArgs(String[] args) {
        if(args.length>=1) {
            path = args[0];
            System.out.println(String.format("data file  %s", path));
        }
        if(args.length>=2) {
            queryFile = args[1];
            System.out.println(String.format("query file %s", queryFile));
        }
    }

    private static void QueryExecutor(Optional<Query[]> queries, JobsDataset jobsDataSet) throws Exception {
        var counter=1;

        var queryBuilder = new QueryBuilder()
                .onDataSet(jobsDataSet);

        ArrayList<Long> results = new ArrayList<>();

        for(var query: queries.get()) {


            if(query.columnType == ColumnType.IPAddress)
                queryBuilder.column(ColumnType.IPAddress);
            else
                queryBuilder.column(ColumnType.JobSignature);


            if(query.filterType!= FilterType.None) {

                queryBuilder.filter(query.filterType);

                if(query.filterValues.length>1) {

                    if(query.filterType == FilterType.Range)
                        queryBuilder.filterValues(query.filterValues[0],query.filterValues[1]);
                    else
                        queryBuilder.filterValues(query.filterValues);
                }
                else
                    queryBuilder.filterValues(query.filterValues[0]);
            }

            if(query.groupBy != GroupType.None)
                queryBuilder.groupBy(query.groupBy);

            var res = queryBuilder.countDistinct();
            results.add(res);

            System.out.println(String.format("for query %s. '%s' - %s",
                    counter++,
                    query.querydetails,
                    res
            ));
        }

        ConstantQueryExplanation.printReport(results,queries,jobsRecords);
    }
}
