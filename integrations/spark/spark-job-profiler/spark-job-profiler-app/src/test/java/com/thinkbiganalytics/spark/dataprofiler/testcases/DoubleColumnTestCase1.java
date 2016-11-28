package com.thinkbiganalytics.spark.dataprofiler.testcases;

import com.thinkbiganalytics.spark.dataprofiler.columns.ColumnStatistics;
import com.thinkbiganalytics.spark.dataprofiler.columns.DoubleColumnStatistics;
import com.thinkbiganalytics.spark.dataprofiler.core.ProfilerTest;
import com.thinkbiganalytics.spark.dataprofiler.topn.TopNDataItem;
import com.thinkbiganalytics.spark.dataprofiler.topn.TopNDataList;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;


/**
 * Double Column Statistics Test Case
 * @author jagrut sharma
 *
 */
public class DoubleColumnTestCase1 extends ProfilerTest {
	
	private static ColumnStatistics columnStats;
	private static long nullCount;
	private static long totalCount;
	private static long uniqueCount;
	private static double percNullValues;
	private static double percUniqueValues;
	private static double percDuplicateValues;
	private static TopNDataList topNValues;
	private static double max;
	private static double min;
	private static double sum;
	private static double mean;
	private static double stddev;
	private static double variance;

	@Before
	public void setUp() {
		super.setUp();

		columnStats = columnStatsMap.get(5);	//height
		nullCount = 3L;
		totalCount = 10L;
		uniqueCount = 5L;
		percNullValues = 30.0d;
		percUniqueValues = 50.0d;
		percDuplicateValues = 50.0d;
		topNValues = columnStats.getTopNValues();
		max = 6.22d;
		min = 4.37d;
		sum = 38.87d;
		mean = 5.552857143d;
		stddev = 0.615298169d;
		variance = 0.378591837d;
	}
	
	@Test
	public void testDoubleNullCount() {
		assertEquals(nullCount, columnStats.getNullCount());
	}
	
	
	 @Test
	 public void testDoubleTotalCount() {
		 assertEquals(totalCount, columnStats.getTotalCount());
	 }


	 @Test
	 public void testDoubleUniqueCount() {
		 assertEquals(uniqueCount, columnStats.getUniqueCount());
	 }


	 @Test
	 public void testDoublePercNullValues() {
		 assertEquals(percNullValues, columnStats.getPercNullValues(), ProfilerTest.epsilon);
	 }


	 @Test
	 public void testDoublePercUniqueValues() {
		 assertEquals(percUniqueValues, columnStats.getPercUniqueValues(), ProfilerTest.epsilon);
	 }


	 @Test
	 public void testDoublePercDuplicateValues() {
		 assertEquals(percDuplicateValues, columnStats.getPercDuplicateValues(), ProfilerTest.epsilon);
	 }


	@Test
	public void testDoubleTopNValues() {
		TreeSet<TopNDataItem> items = topNValues.getTopNDataItemsForColumn();
		Iterator<TopNDataItem> iterator = items.descendingIterator();

		//Verify that there are 3 items
		assertEquals(3, items.size());

		//Verify the top 3 item counts
		int index = 1;
		while (iterator.hasNext()) {
			TopNDataItem item = iterator.next();
			if (index == 1) {
				assertEquals(5.85d, item.getValue());
				assertEquals(Long.valueOf(4L), item.getCount());
			}
			else if (index == 2) {
				assertEquals(null, item.getValue());
				assertEquals(Long.valueOf(3L), item.getCount());
			}
			else if (index == 3) {
				/*
                    Not checking value since it can be arbitrary.
                    All remaining values have count 1
                */
				assertEquals(Long.valueOf(1L), item.getCount());
			}

			index++;
		}
	}

	 @Test
	    public void testDoubleMax() {
	    	assertEquals(max, ((DoubleColumnStatistics)columnStats).getMax(), ProfilerTest.epsilon);
	    }
	    
	    
	    @Test
	    public void testDoubleMin() {
	    	assertEquals(min, ((DoubleColumnStatistics)columnStats).getMin(), ProfilerTest.epsilon);
	    }
	    
	    
	    @Test
	    public void testDoubleSum() {
	    	assertEquals(sum, ((DoubleColumnStatistics)columnStats).getSum(), ProfilerTest.epsilon);
	    }
	    
	    
	    @Test
	    public void testDoubleMean() {
	    	assertEquals(mean, ((DoubleColumnStatistics)columnStats).getMean(), ProfilerTest.epsilon);
	    }
	    
	    
	    @Test
	    public void testDoubleStddev() {
	    	assertEquals(stddev, ((DoubleColumnStatistics)columnStats).getStddev(), ProfilerTest.epsilon);
	    }
	    
	    
	    @Test
	    public void testDoubleVariance() {
	    	assertEquals(variance, ((DoubleColumnStatistics)columnStats).getVariance(), ProfilerTest.epsilon);
	    }
	    
	    
	    @AfterClass
	    public static void tearDownClass() {
	    	System.out.println("\t*** Completed run for DoubleColumnTestCase1 ***");
	    }
}   