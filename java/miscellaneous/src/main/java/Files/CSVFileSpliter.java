package Files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Interfaces.CSVSpliter;

public class CSVFileSpliter implements CSVSpliter{
	private final String DEFAULT_FILE_NAMES = "value";
	private final String DELIMITER = ",";
	private final String FILE_SEPARATOR = System.getProperty("file.separator");
	private final String FILE_EXT = ".csv";
	
	/**
	 * Boolean variable for header availability
	 */
	boolean hasHeader;
	boolean shouldSaveHeader = true;

	private String delimiterToUseInResult = DELIMITER;
	private String fileExtForResult = FILE_EXT;




	/**
	 * To increase performance the user can specify how many column the csv file have.
	 */
	Integer amntColumns;
	
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
	Collection<Integer> defaultColumn;
	
	/**
	 * This directory is where all the result files are going to be saved.
	 */
	File resultDir = null;
	


	/**
	 * File to be splitted 
	 */
	File csvFile;
	//===================================================
	//==============CONSTRUCTOR==========================
	//===================================================
	/**
	 * By default the header availability is set to false,
	 * the amnt of columns is set to null, as the default columns
	 */
	public CSVFileSpliter(File csvFile){
		if(csvFile == null){
			throw new IllegalArgumentException("CSV File cannot be null");
		}
		this.csvFile 	= csvFile; 
		hasHeader 		= false;
		amntColumns		= null;
		defaultColumn 	= null;
	}

	/**
	 * 
	 * @param hasHeader 
	 * @param amntColumns
	 * @param defaultColumn
	 */
	public CSVFileSpliter(File csvFile, boolean hasHeader, Integer amntColumns,
			Collection<Integer> defaultColumn) {
		super();
		if(csvFile == null){
			throw new IllegalArgumentException("CSV File cannot be null");
		}
		this.csvFile 		= csvFile; 
		this.hasHeader 		= hasHeader;
		this.amntColumns 	= amntColumns;
		this.defaultColumn 	= defaultColumn;
	}


	
	//===================================================
	//======PUBLIC METHODS==========PUBLIC METHODS=======
	//===================================================
	
	/**
	 * This methos execute the csv column division and save the result in 
	 * separete files
	 * @throws IOException 
	 */
	public void excecuteCSVSplitToFile() throws IOException{
		String[] 					header 	= null;
		BufferedReader 				reader 	= new BufferedReader(new FileReader(csvFile));
		ArrayList<BufferedWriter> 	writers = new ArrayList<BufferedWriter>();
		
		initializeResultDir();
		
		if(hasHeader){
			//try to read header
			header = readheader(reader);
			
			//set writers
			for(int i = 0; i < header.length; i++){
				//create file for the each column
				File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
						+ header[i] + fileExtForResult);
				tempFile.createNewFile();
				
				//add writer
				writers.add(new BufferedWriter(new FileWriter(tempFile)));
			}
			
			//write header in each file
			if(shouldSaveHeader){
				writeHeader(header, writers);
			}
			
			
			//read line and save data
			String line = reader.readLine();
			while(line != null){
				System.out.println("Working with: " + line);
				String[] values = line.split(DELIMITER);
				writeValue(values, writers, line = reader.readLine());
			}
		}else{
			//set writers
			String line = reader.readLine();
			if(amntColumns == null){
				
				if(line == null){
					System.out.println("Empty File");
					return;
				}
				
				String[] tempValues = line.split(DELIMITER);
				for(int i = 0; i < tempValues.length; i++){
					//create file for the each column
					File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
							+ DEFAULT_FILE_NAMES + i +  fileExtForResult);
					tempFile.createNewFile();
					
					//add writer
					writers.add(new BufferedWriter(new FileWriter(tempFile)));
				}
				writeValue(tempValues, writers, line = reader.readLine());
				
			}else{
				for(int i = 0; i < amntColumns; i++){
					//create file for the each column
					File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
							+ DEFAULT_FILE_NAMES + i +  fileExtForResult);
					tempFile.createNewFile();
					
					//add writer
					writers.add(new BufferedWriter(new FileWriter(tempFile)));
				}
			}

			
			//read line and save data
			
			while(line != null){
				System.out.println("Working with: " + line);
				String[] values = line.split(DELIMITER);
				writeValue(values, writers, (line = reader.readLine()));
			}
		}
		
		closeWriters(writers);
	}
	


	public boolean isHasHeader() {
		return hasHeader;
	}

	public void setHasHeader(boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	public Integer getAmntColumns() {
		return amntColumns;
	}

	public void setAmntColumns(Integer amntColumns) {
		this.amntColumns = amntColumns;
	}

	public Collection<Integer> getDefaultColumn() {
		return defaultColumn;
	}

	public void setDefaultColumn(Collection<Integer> defaultColumn) {
		this.defaultColumn = defaultColumn;
	}

	public boolean isShouldSaveHeader() {
		return shouldSaveHeader;
	}

	public void setShouldSaveHeader(boolean shouldSaveHeader) {
		this.shouldSaveHeader = shouldSaveHeader;
	}
	
	/**
	 * 
	 */
	public List<List<String>> executeCSVSplitToList() {
		//Not implemented
		return null;
//		String[] 					header 	= null;
//		BufferedReader 				reader 	= new BufferedReader(new FileReader(csvFile));
//		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
//		initializeResultDir();
//		
//		if(hasHeader){
//			//try to read header
//			header = readheader(reader);
//			
//			//set writers
//			for(int i = 0; i < header.length; i++){
//				//create file for the each column
//				File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
//						+ header[i] + fileExtForResult);
//				tempFile.createNewFile();
//				
//				//add writer
//				writers.add(new BufferedWriter(new FileWriter(tempFile)));
//			}
//			
//			//write header in each file
//			if(shouldSaveHeader){
//				writeHeader(header, writers);
//			}
//			
//			
//			//read line and save data
//			String line;
//			while((line = reader.readLine()) != null){
//				System.out.println("Working with: " + line);
//				String[] values = line.split(DELIMITER);
//				writeValue(values, writers);
//			}
//		}else{
//			//set writers
//			if(amntColumns == null){
//				String tempLine = reader.readLine();
//				if(tempLine == null){
//					System.out.println("Empty File");
//					return;
//				}
//				
//				String[] tempValues = tempLine.split(DELIMITER);
//				for(int i = 0; i < tempValues.length; i++){
//					//create file for the each column
//					File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
//							+ DEFAULT_FILE_NAMES + i +  fileExtForResult);
//					tempFile.createNewFile();
//					
//					//add writer
//					writers.add(new BufferedWriter(new FileWriter(tempFile)));
//				}
//				writeValue(tempValues, writers);
//				
//			}else{
//				for(int i = 0; i < amntColumns; i++){
//					//create file for the each column
//					File tempFile = new File(resultDir.getPath() + FILE_SEPARATOR 
//							+ DEFAULT_FILE_NAMES + i +  fileExtForResult);
//					tempFile.createNewFile();
//					
//					//add writer
//					writers.add(new BufferedWriter(new FileWriter(tempFile)));
//				}
//			}
//
//			
//			//read line and save data
//			String line;
//			while((line = reader.readLine()) != null){
//				System.out.println("Working with: " + line);
//				String[] values = line.split(DELIMITER);
//				writeValue(values, writers);
//			}
//		}
//		
//		closeWriters(writers);
	}
	
	
	public File getResultDir() {
		return resultDir;
	}

	public void setResultDir(File resultDir) {
		this.resultDir = resultDir;
	}
	public String getDelimiterToUseInResult() {
		return delimiterToUseInResult;
	}

	public void setDelimiterToUseInResult(String delimiterToUseInResult) {
		this.delimiterToUseInResult = delimiterToUseInResult;
	}
	
	public String getFileExtForResult() {
		return fileExtForResult;
	}

	public void setFileExtForResult(String fileExtForResult) {
		this.fileExtForResult = fileExtForResult;
	}
	//===================================================
	//=====PRIVATE METHODS=========PRIVATE METHODS=======
	//===================================================
	private void initializeResultDir(){
		if(resultDir == null){
			resultDir = new File(System.getProperty("user.dir") + FILE_SEPARATOR + "results");
			System.out.println("Result dir creado: " + resultDir.mkdir());
			System.out.println("En: " + resultDir.getPath());
		}//else already initialize
	}
	
	private String[] readheader(BufferedReader reader) throws IOException{
		String temp;
		String[] header = null;
		if((temp = reader.readLine()) != null){
			header = temp.split(DELIMITER);
		}else{
			System.err.println("Empty File");
			System.exit(0);
		}
		return header;
	}
	
	private void writeHeader(String[] header, ArrayList<BufferedWriter> writers) throws IOException {
		
		if(defaultColumn == null){
			for(int i = 0; i < header.length; i++){
				writers.get(i).write(header[i]);
				writers.get(i).newLine();
				writers.get(i).flush();
			}
		}else{ //attach the header for each default column
			for(int i = 0; i < header.length; i++){
				//write the deafult header
				for(Integer col : defaultColumn){
					writers.get(i).write(header[col] + delimiterToUseInResult);
				}
				writers.get(i).write(header[i]);
				writers.get(i).newLine();
				writers.get(i).flush();
			}
		}
		
	}
	
	private void writeValue(String[] values, List<BufferedWriter> writers, String hasNext) throws IOException{
		if(defaultColumn == null){
			for(int i = 0; i < writers.size(); i++){
				writers.get(i).write(values[i]);
				if(hasNext != null){
					writers.get(i).newLine();
				}
				writers.get(i).flush();
			}
		}else{ //attach the header for each default column
			for(int i = 0; i < writers.size(); i++){
				//write the deafult header
				for(Integer col : defaultColumn){
					writers.get(i).write(values[col] + delimiterToUseInResult);
				}
				writers.get(i).write(values[i]);
				if(hasNext != null){
					writers.get(i).newLine();
				}
				writers.get(i).flush();
			}
		}
	}
	
	private void closeWriters(ArrayList<BufferedWriter> writers) throws IOException{
		for(BufferedWriter writer : writers){
			writer.close();
		}
	}
}
