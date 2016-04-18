package Interfaces;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import  org.wikidata.wdtk.datamodel.interfaces.EntityDocumentProcessor;


public interface EntityDocumentProcessorExtended extends EntityDocumentProcessor{
	
	public List<String> getCSVListResult();


	 public ArrayList<String> getCSVListResultForAllDataInFile();


	public void getCSVListResult(File file);
}
