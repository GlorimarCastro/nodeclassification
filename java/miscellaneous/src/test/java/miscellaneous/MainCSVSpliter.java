package miscellaneous;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import Files.CSVFileSpliter;
import Interfaces.CSVSpliter;

public class MainCSVSpliter {

	public static void main(String[] args) throws IOException {
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		list.add(0);
		
		CSVFileSpliter spliter = new CSVFileSpliter(new File("H:\\EclipseWorkspace\\glorimar-wikidata-toolkit\\Runnable\\csvtest.csv"));
				//, true, null, list);
		spliter.setDefaultColumn(list);
		spliter.setDelimiterToUseInResult(" ");
		spliter.setFileExtForResult(".txt");
		spliter.setHasHeader(true);
		spliter.setShouldSaveHeader(false);
		spliter.excecuteCSVSplitToFile();

	}

}
