package Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Number.NumberUtility;
import Files.FileHandeler;

/**
 * This class is for split a file in different subFile with data from the original.
 * The data to be save in the subfiles are extracted in a random way. The objective 
 * of this class is being able of create different test and training data subset from 
 * a original annotated data. 
 * 
 * THIS CLASS REQUIERE THE FILE TO HAVE A HEADER
 * @author GL26163
 *
 */
public class RandomTestAndTrainingFileSpliter {

	private String outputDirName = "TestAndTrainingSubsets";
	private final String FILE_SEPARATOR = System.getProperty("file.separator");
	private final String CSV_SEPARATOR = ",";
	private final String TEST_FILE_NAME = "testSample";
	private final String TRAINING_FILE_NAME = "trainingSample";
	private final String CSV_FILE_EXTENSION = ".csv";
	/**
	 * Directory for saving the results
	 */
	private File mainResultDirectory;
	private String maindirectorypath;
	
	private String delimiter = CSV_SEPARATOR;

	private String fileExtension = CSV_FILE_EXTENSION;




	/**
	 * Directory for saving the test files
	 */
	private File testDirectory;
	



	/**
	 * Directory for saving the training files
	 */
	private File trainingDirectory;
	
	/**
	 * Amount of files to create for test and training
	 */
	private int 	numberOfSubFiles;
	
	/**
	 * Percentage of data to be extracted, from the original file, for a test subFile
	 */
	private int 	testPercentage;
	
	/**
	 * Input file
	 */
	private File inputFile;
	
	
	
	/**
	 * By default it would create  just one sample with 50% of the 
	 * data for training and the other 50% for testing
	 * @param inputFile
	 */
	public RandomTestAndTrainingFileSpliter(File inputFile){
		if(inputFile == null){
			throw new IllegalArgumentException("Input file canot be null");
		}
		this.inputFile = inputFile;
		numberOfSubFiles = 1;
		testPercentage = 50;
		maindirectorypath = System.getProperty("user.dir");
		
	}
	
	
	public RandomTestAndTrainingFileSpliter(int numberOfSubFiles,
			int testPercentage, File inputFile) {
		super();
		if(inputFile == null){
			throw new IllegalArgumentException("Input file canot be null");
		}
		this.numberOfSubFiles = numberOfSubFiles;
		this.testPercentage = testPercentage;
		this.inputFile = inputFile;
	}


	public void executeTestAndTrainingSpliterToFile() throws IOException{
		setFields();
		
		//Upload to memory the input file
		ArrayList<String> 	fileDataList = FileHandeler.getLinesInFile(inputFile);
		double 				linesForTest = (fileDataList.size() - 1) * (testPercentage/100.0); //se descuenta el header
		
		
		//Loop for wanted partitions
		for(int i = 0; i < numberOfSubFiles; i++){
			System.out.println("Working on sample number: " + i);
			
			//Extracting test and training data
			int amntOfLinesExtractedForTest = (int)linesForTest;
			ArrayList<String> trainingData	= new ArrayList<String>(fileDataList);  //a copy of the original data
			ArrayList<String> testData 		= new ArrayList<String>();
			
			String header = trainingData.remove(0); //remove header
			//set training and test sample
			setSampleData(amntOfLinesExtractedForTest, trainingData, testData);
			trainingData.add(0, header);
			testData.add(0, header);
			
			saveDataToFile(trainingData, testData, i);		
		}
				
		
	}
	
//	public LinkedHashMap<String, ArrayList<String>> executeTestAndTrainingSpliterToList() throws IOException{
//		setFields();
//		
//		LinkedHashMap<String, ArrayList<String>> result = new LinkedHashMap<String, ArrayList<String>>();
//		initializeHashList(result, numberOfSubFiles);
//		
//		//Upload to memory the input file
//		ArrayList<String> 	fileDataList = FileHandeler.getLinesInFile(inputFile);
//		double 				linesForTest = (fileDataList.size() - 1) * (testPercentage/100.0); //se descuenta el header
//		
//		
//		//Loop for wanted partitions
//		for(int i = 0; i < numberOfSubFiles; i++){
//			System.out.println("Working on sample number: " + i);
//			
//			//Extracting test and training data
//			int amntOfLinesExtractedForTest = (int)linesForTest;
//			ArrayList<String> trainingData	= new ArrayList<String>(fileDataList);  //a copy of the original data
//			ArrayList<String> testData 		= new ArrayList<String>();
//			
//			String header = trainingData.remove(0); //remove header
//			//set training and test sample
//			setSampleData(amntOfLinesExtractedForTest, trainingData, testData);
//			trainingData.add(0, header);
//			testData.add(0, header);
//			
//			saveDataToFile(trainingData, testData, i);		
//		}
//				
//		return result;
//	}
//	






	/**
	 * save the sample to a file
	 * @param trainingData
	 * @param testData
	 * @throws IOException 
	 */
	private void saveDataToFile(ArrayList<String> trainingData,
			ArrayList<String> testData, int sampleNumber) throws IOException {
		File tempTestFile = new File(testDirectory.getPath() + FILE_SEPARATOR + TEST_FILE_NAME + sampleNumber + fileExtension);
		File trainingTempFile = new File(trainingDirectory.getPath() + FILE_SEPARATOR + TRAINING_FILE_NAME + sampleNumber + fileExtension);
		
		tempTestFile.createNewFile();
		trainingTempFile.createNewFile();
		
		FileHandeler.writeListToFile(trainingData, trainingTempFile);
		FileHandeler.writeListToFile(testData, tempTestFile);
	}


	/**
	 * This method take the original data and divided into two random parts
	 * @param amntOfLinesExtractedForTest
	 * @param trainingData
	 * @param testData
	 */
	private void setSampleData(int amntOfLinesExtractedForTest,
			ArrayList<String> trainingData, ArrayList<String> testData) {
		while(amntOfLinesExtractedForTest > 0){
			int start 	= NumberUtility.getRandomInt(0, 	trainingData.size()); 
			int end = (start + amntOfLinesExtractedForTest < trainingData.size())? start + amntOfLinesExtractedForTest: trainingData.size();
			end		= NumberUtility.getRandomInt(start, end);			//start + lines for test se asegura de que de primera instancia no extraiga mas de lo pedido
			
			for(int j = start; j < end; j++){
				testData.add(trainingData.get(start));
				trainingData.remove(start);
			}
			
			amntOfLinesExtractedForTest -= end-start;
		}
		
	}


	public String getOutputDirName() {
		return outputDirName;
	}
	public String getFileExtension() {
		return fileExtension;
	}


	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	public void setOutputDirName(String outputDirName) {
		this.outputDirName = outputDirName;
	}


	public String getMainResultDirectoryPath() {
		return maindirectorypath;
	}


	public void setMainResultDirectoryPath(String mainResultDirectory) {
		this.maindirectorypath = mainResultDirectory;
	}


	public int getNumberOfSubFiles() {
		return numberOfSubFiles;
	}


	public void setNumberOfSubFiles(int numberOfSubFiles) {
		this.numberOfSubFiles = numberOfSubFiles;
	}


	public int getTestPercentage() {
		return testPercentage;
	}


	public void setTestPercentage(int testPercentage) {
		this.testPercentage = testPercentage;
	}


	public File getInputFile() {
		return inputFile;
	}


	public void setInputFile(File inputFile) {
		if(inputFile == null){
			throw new IllegalArgumentException("Input file cannot be null");
		}
		this.inputFile = inputFile;
	}

	public String getDelimiter() {
		return delimiter;
	}


	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public File getTestDirectory() {
		return testDirectory;
	}


	public void setTestDirectory(File testDirectory) {
		this.testDirectory = testDirectory;
	}


	public File getTrainingDirectory() {
		return trainingDirectory;
	}


	public void setTrainingDirectory(File trainingDirectory) {
		this.trainingDirectory = trainingDirectory;
	}
	private void setFields(){
		mainResultDirectory = new File(maindirectorypath + FILE_SEPARATOR + outputDirName);
		mainResultDirectory.mkdir();
		
		testDirectory 		= new File(mainResultDirectory.getPath() + FILE_SEPARATOR + "TestSubsets");
		trainingDirectory 	= new File(mainResultDirectory.getPath() + FILE_SEPARATOR + "TrainingSubsets");
		
		testDirectory.mkdir();
		trainingDirectory.mkdir();
	}
	
}
