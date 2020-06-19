package com.countDistinct.estimator;

public class CountDistinctAlgorithmSettings {

        private static final Object lock = new Object();
        private static volatile CountDistinctSketchAlgorithm algorithm;
        public static volatile Integer noOfHashFunctions;
        public static volatile int R;



        private CountDistinctAlgorithmSettings() {
            noOfHashFunctions=2;
            R=(int) Math.pow(2,20);
        }

        public static void withInitialValue(int noOfHashFunc, int Rval) {

            noOfHashFunctions = noOfHashFunc;
            R = Rval;
        }
    }
