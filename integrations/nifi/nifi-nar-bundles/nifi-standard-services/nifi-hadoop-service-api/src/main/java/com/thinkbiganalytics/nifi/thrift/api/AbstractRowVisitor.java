package com.thinkbiganalytics.nifi.thrift.api;

import java.sql.ResultSet;
import java.util.Date;

/**
 * Created by matthutton on 2/1/16.
 */
public class AbstractRowVisitor implements RowVisitor {

    @Override
    public void visitRow(ResultSet row) {

    }

    @Override
    public void visitColumn(String columnName, int colType, Date value) {

    }

    @Override
    public void visitColumn(String columnName, int colType, String value) {

    }
}
