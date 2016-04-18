package miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Files.CSVFileSpliter;
import Files.FileHandeler;
import Files.RandomTestAndTrainingFileSpliter;

public class TestAndTrainingGenerator {
	public static void main(String[] args) throws IOException{
		String FILE_SEPARATOR = System.getProperty("file.separator");
		
		//Create data separation
		RandomTestAndTrainingFileSpliter filter = new RandomTestAndTrainingFileSpliter(new File("Data/summer2015/NewYorkTimes2007/completeMetricsWithPoliticianRoleForNYT2007_NotIsolated.csv"));
		filter.setNumberOfSubFiles(100);
		filter.setTestPercentage(80);
		filter.setMainResultDirectoryPath("Data/summer2015/NewYorkTimes2007");
		filter.executeTestAndTrainingSpliterToFile();
		
		
		File testDirectory = filter.getTestDirectory();
		File trainingDirectory = filter.getTrainingDirectory();
		
		File[] testFiles = testDirectory.listFiles();
		File[] trainingFile = trainingDirectory.listFiles();
		
		
		//Divide the features
		ArrayList<Integer> defaultColumn = new ArrayList<Integer>();
		defaultColumn.add(1);
		for(File f : testFiles){
			if(f.isDirectory()){
				continue;
			}
			System.out.println(f.getName());
			
			File tempTestDirectory = new File(testDirectory + FILE_SEPARATOR + FileHandeler.getNameWithoutExt(f));
			tempTestDirectory.mkdir();
			
			CSVFileSpliter csvSpliter = new CSVFileSpliter(f, true, null, defaultColumn);
			csvSpliter.setDelimiterToUseInResult(" ");
			csvSpliter.setFileExtForResult(".txt");
			csvSpliter.setShouldSaveHeader(false);
			
			csvSpliter.setResultDir(tempTestDirectory);
			csvSpliter.excecuteCSVSplitToFile();
			
		}
		
		for(File f : trainingFile){
			if(f.isDirectory()){
				continue;
			}
			System.out.println(f.getName());
			
			File tempTrainingDirectory = new File(trainingDirectory + FILE_SEPARATOR + FileHandeler.getNameWithoutExt(f));
			tempTrainingDirectory.mkdir();
			
			CSVFileSpliter csvSpliter = new CSVFileSpliter(f, true, null, defaultColumn);
			csvSpliter.setDelimiterToUseInResult(" ");
			csvSpliter.setFileExtForResult(".txt");
			csvSpliter.setShouldSaveHeader(false);
			csvSpliter.setResultDir(tempTrainingDirectory);
			csvSpliter.excecuteCSVSplitToFile();
			
		}
	
	}
}
