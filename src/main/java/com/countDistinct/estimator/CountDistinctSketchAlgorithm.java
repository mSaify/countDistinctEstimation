package com.countDistinct.estimator;

import java.util.*;
import java.util.stream.IntStream;


public class CountDistinctSketchAlgorithm {

    private HashWrapper hashWrapper;
    private Integer noOfHashFunctions;
    public CountDistinctDataStructure CountDistinctDataStructure;
    private Integer R;

    public  CountDistinctSketchAlgorithm(int noOfHashFunctions,int R) {

        this.noOfHashFunctions=noOfHashFunctions;
        this.R = R;

        hashWrapper = new HashWrapper(this.noOfHashFunctions);
        CountDistinctDataStructure = new CountDistinctDataStructure(this.noOfHashFunctions, this.R);
    }

    // for testing static methods
    public  CountDistinctSketchAlgorithm() {

    }


    public void GenerateCountDistinctSketch(List<String> values) {
        IntStream
                .range(0, noOfHashFunctions)
                .forEach(hashFuncIndex ->
                    GenerateCountSketchForEachHash(hashFuncIndex,values) );

    }

    private void GenerateCountSketchForEachHash(Integer hashIndex, List<String> values) {
        final int[] count = {0};
        values.stream()
                .forEach(val-> {
                    count[0]++;
                    var z = GetHashValue(hashIndex,val);
                    var wi = CountDistinctDataStructure.SketchUpdateValues(z,R);

                    CountDistinctDataStructure.
                            get(hashIndex)
                            .get(wi[0])
                            .set(wi[1]);
                    });
    }

    public long EstimateCount() {
        return CountDistinctDataStructure.EstimateCount();
    }

    private Long GetHashValue(int hashIndex, String value) {
        return hashWrapper.getHashValue(hashIndex, value);
    }


}
