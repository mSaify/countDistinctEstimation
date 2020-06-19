package com.countDistinct.estimator;

import com.countDistinct.estimator.InputReaders.ColumnType;

public interface IColumn<T> {

    public T column(ColumnType columnType);
}
