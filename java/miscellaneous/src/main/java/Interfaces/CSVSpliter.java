package Interfaces;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface CSVSpliter {

	/**
	 * Boolean variable for header availability
	 */
	boolean hasHeader = false;
	
	/**
	 * To increase performance the user can specify how many column the csv file have.
	 */
	Integer amntColumns = null;
	
	/**
	 * This is a collection of integers that indicate default columns to be with
	 * each of the other columns of the csv file. Example:
	 * 	123, 5, 8, 3
	 *  5, 123, 159, 59
	 *  If this collection specify 2, 3, then the result would be something like
	 *  8, 3, 123                   8, 3, 5
	 *  159, 59, 5                  159,59, 123 
	 *  
	 */
	Collection<Integer> defaultColumn = null;
	
	/**
	 * This directory is where all the result files are going to be saved.
	 */
	File resultDir = null;

	
	/**
	 * This methos execute the csv column division and save the result in 
	 * separete files
	 * @throws IOException 
	 */
	public void excecuteCSVSplitToFile() throws IOException;
	
	/**
	 * Return a list containing all the divisions
	 * @return
	 */
	public List<List<String>> executeCSVSplitToList();
	
	public boolean isHasHeader();

	public void setHasHeader(boolean hasHeader);

	public Integer getAmntColumns();

	public void setAmntColumns(Integer amntColumns);

	public Collection<Integer> getDefaultColumn();

	public void setDefaultColumn(Collection<Integer> defaultColumn);
}
