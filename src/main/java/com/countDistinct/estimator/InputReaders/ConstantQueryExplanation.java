package com.countDistinct.estimator.InputReaders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class ConstantQueryExplanation {

    private static HashMap<Integer, String> queries;

    private static void initialize() {

        queries = new HashMap<>();


//        1. Number of distinct Source IP addresses.
//        2. Number of distinct Job signatures.
//        3. Number of distinct Job signatures from a given cluster ID.
//        4. Number of distinct Job signatures from a given range [l, r] of cluster IDs (both l, r ∈
//        {0, . . . , 99}).
//        5. Number of distinct Job signatures in a given month MM.
//        6. Number of distinct Job signatures in a given range of months [l, r].
//        7. Number of distinct Job signatures from a given Geography.
//        8. Number of distinct Source IP addresses from a given Geography.
//        9. Number of distinct Job signatures from a given set of Geographies.
//        10. Number of distinct IP addresses from a given set of Geographies.

        queries.put(1, "Distinct IP Address");
        queries.put(2, "Distinct Job Signature");
        queries.put(3, "Distinct Job Signature from clusterId, %s");
        queries.put(4, "Distinct Job Signature from clusterId range [%s]");
        queries.put(5, "Distinct Job signatures in a given month %s");
        queries.put(6, "Distinct Job signatures in a given range of months [%s]");
        queries.put(7, "Distinct Job signatures from a given Geography %s");
        queries.put(8, "Distinct IP addresses from a given Geography %s");
        queries.put(9, "Distinct Job Signature from Geographies %s");
        queries.put(10, "Distinct IP addresses from Geographies %s");

    }

    public static String get(int id, String[] val, FilterType filterType) {

        if (queries == null) {
            initialize();
        }
        var query = queries.get(id);

        if (filterType == FilterType.Range) {
            var print = new String[2];
            print[0] = val[0];
            print[1] = val[val.length - 1];
            return String.format(query, String.join(",", print));
        }

        return String.format(query, String.join(",", val));
    }

    public static void  printReport(List<Long> results,
                                    Optional<Query[]> queries,
                                    Optional<JobsRecord[]> jobsRecords) {

        System.out.println("");
        System.out.println("");

        System.out.println("actual vs calculated result...");
        var counter = 1;
        for (var query : queries.get()) {


            var actual = getActualCount(counter, jobsRecords,query);
            var expected = results.get(counter - 1);
            var error=0.0;

            if(Math.abs(actual-expected)>0)
             error = Math.abs(actual-expected)*1.0/actual;

            System.out.println(String.format("query %s actual value %s - algorithm value %s - percentage error - % .2f",
                    counter,
                    actual,
                    expected,
                    error*100
            ));
            counter++;

        }


    }

    private static long getActualCount(int i, Optional<JobsRecord[]> jobsRecords,Query queries) {

        switch (i) {
            case 1:
                return Arrays.stream(jobsRecords.get()).map(x -> x.IPAddress).distinct().count();
            case 2:
                return Arrays.stream(jobsRecords.get()).map(x -> x.JobSignature).distinct().count();
            case 3:
                return  Arrays.stream(jobsRecords.get())
                        .filter(x->x.ClusterId==Integer.parseInt(queries.filterValues[0]))
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 4:
                Predicate<JobsRecord> hasGeo =
                        c -> Arrays.stream(expandValues(queries.filterValues))
                                .anyMatch(u -> u == c.ClusterId);


                return  Arrays.stream(jobsRecords.get())
                        .filter(hasGeo)
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 5:
                return  Arrays.stream(jobsRecords.get())
                        .filter(x->x.month==Integer.parseInt(queries.filterValues[0]))
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 6:
                Predicate<JobsRecord> hasMonthIP =
                        c -> Arrays.stream(expandValues(queries.filterValues))
                                .anyMatch(u -> u == c.month);


                return  Arrays.stream(jobsRecords.get())
                        .filter(hasMonthIP)
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 7:
                return  Arrays.stream(jobsRecords.get())
                        .filter(x->x.geography==Integer.parseInt(queries.filterValues[0]))
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 8:
                return  Arrays.stream(jobsRecords.get())
                        .filter(x->x.geography==Integer.parseInt(queries.filterValues[0]))
                        .map(x -> x.IPAddress)
                        .distinct()
                        .count();

            case 9:
                Predicate<JobsRecord> hasCluster =
                        c -> Arrays.stream(queries.filterValues)
                                .mapToInt(x->Integer.parseInt(x))
                            .anyMatch(u -> u == c.geography);

                return  Arrays.stream(jobsRecords.get())
                        .filter(hasCluster)
                        .map(x -> x.JobSignature)
                        .distinct()
                        .count();

            case 10:

                Predicate<JobsRecord> hasGeoIP =
                        c -> Arrays.stream(queries.filterValues)
                                .mapToInt(x-> Integer.parseInt(x))
                                .anyMatch(u -> u == c.geography);

                return  Arrays.stream(jobsRecords.get())
                        .filter(hasGeoIP)
                        .map(x -> x.IPAddress)
                        .distinct()
                        .count();

            default:
                return 0;
        }
    }

    private static int[] expandValues(String[] filterValues) {

        var start = Integer.parseInt(filterValues[0]);
        var end = Integer.parseInt(filterValues[1]);

        var filterVals = new int[end-start+1];

        var counter=0;
        for(int i=start; i<=end; i++) {

            filterVals[counter++]=i;
        }

        return filterVals;

    }
}
