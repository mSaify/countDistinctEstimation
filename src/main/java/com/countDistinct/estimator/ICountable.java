package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.FilterType;

public interface ICountable extends IGroupBy {

    void ConstructDataStructure();

    long count();

    long countWithFilter(FilterType filterType, String[] filterValues);
}
