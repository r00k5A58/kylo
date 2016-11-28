package com.thinkbiganalytics.spark.dataprofiler.columns;

import com.thinkbiganalytics.spark.dataprofiler.model.MetricType;
import com.thinkbiganalytics.spark.dataprofiler.output.OutputRow;
import org.apache.spark.sql.types.StructField;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Class to hold profile statistics for columns of date data type <br>
 * [Hive data type: DATE]
 * @author jagrut sharma
 *
 */

@SuppressWarnings("serial")
public class DateColumnStatistics extends ColumnStatistics {

	/* Date specific metrics */
	private Date maxDate;
	private Date minDate;
	
	/* Other variables */
	private Date columnDateValue;


	/**
	 * One-argument constructor
	 * @param columnField field schema
	 */
	public DateColumnStatistics(StructField columnField) {
		
		super(columnField);

		String MIN_DATE = "1000-01-01";
		maxDate = Date.valueOf(MIN_DATE);
		String MAX_DATE = "9999-12-12";
		minDate = Date.valueOf(MAX_DATE);
		
	}

	/**
	 * Calculate date-specific statistics by accommodating the value and frequency/count
	 */
	@Override
	public void accomodate(Object columnValue, Long columnCount) {
		
		accomodateCommon(columnValue, columnCount);
		
		if (columnValue != null) {
			columnDateValue = Date.valueOf(String.valueOf(columnValue));
		}
		if(columnDateValue != null) {
			if (maxDate.before(columnDateValue)) {
				maxDate = columnDateValue;
			}

			if (minDate.after(columnDateValue)) {
				minDate = columnDateValue;
			}
		}
		
	}

	
	/**
	 * Combine with another column statistics 
	 */
	@Override
	public void combine(ColumnStatistics v_columnStatistics) {
		
		combineCommon(v_columnStatistics);
		
		DateColumnStatistics vDate_columnStatistics = (DateColumnStatistics) v_columnStatistics;

		if (maxDate.before(vDate_columnStatistics.maxDate)) {
			maxDate = vDate_columnStatistics.maxDate;
		}
		
		if (minDate.after(vDate_columnStatistics.minDate)) {
			minDate = vDate_columnStatistics.minDate;
		}
	}

	
	/**
	 * Print statistics to console
	 */
	@Override
	public String getVerboseStatistics() {

		return "{\n" + getVerboseStatisticsCommon()
		+ "\n"
		+ "DateColumnStatistics ["
		+ "maxDate=" + maxDate
		+ ", minDate=" + minDate
		+ "]\n}";
	}

	
	/**
	 * Write statistics for output result table
	 */
	@Override
	public void writeStatistics() {
		
		writeStatisticsCommon();
		
		rows = new ArrayList<>();
		rows.add(new OutputRow(columnField.name(), String.valueOf(MetricType.MAX_DATE), String.valueOf(maxDate)));
		rows.add(new OutputRow(columnField.name(), String.valueOf(MetricType.MIN_DATE), String.valueOf(minDate)));
		outputWriter.addRows(rows);
	}

	
	/**
	 * Get latest date
	 * @return latest date
	 */
	public Date getMaxDate() {
		return maxDate;
	}

	
	/**
	 * Get earliest date
	 * @return earliest date
	 */
	public Date getMinDate() {
		return minDate;
	}
	
}