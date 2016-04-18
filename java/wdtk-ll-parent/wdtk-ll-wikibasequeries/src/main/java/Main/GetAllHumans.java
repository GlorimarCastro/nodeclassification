package Main;

import java.io.IOException;
import org.wikidata.wdkt.enums.Entity;
import org.wikidata.wdtk.examples.ExampleHelpers;
import org.wikidata.wdtk.examples.JsonSerializationInstanceOfProcessor;


/**
 * Save on OUTPUT_FILE_NAME all the people found in dumpFileDirectory (a wiki project json dump file).
 * @author gl26163
 *
 */
public class GetAllHumans {
	//Dumpfiles parent directory path. 
	//It is important that the json dump file to be use is saved in a directory with the next format:
	//		[dumpFilesParentDirectoryPath]\dumpfiles\[wikimedia project name]\json-[date]
	//Where:
	//	wikimedia project 		-	is the wiki project from where the dump file were downloaded (e.g wikidatawiki)
	//	json-date				-	is the json dump file name, which fallow the next format json-[date], where the date have to be YYYYMMDD (e.g json-20150608)
	static final String dumpFileDirectory = "H:\\EclipseWorkspace\\RolesAnnotation\\wdtk-parent\\wdtk-examples"; 
	
	//Results file name
	//static final String OUTPUT_FILE_NAME = "json-wikidata-allpeople.json.gz";
	
	//Project name
	static final String PROJECT_NAME = "wikidatawiki";
	
	
	
	//***************************************************************
	//							MAIN METHOD
	//***************************************************************
	public static void main(String[] input) throws IOException{
		
		//Set Logger configuration.
		ExampleHelpers.configureLogging();
		
		//Print basic documentation in the console
		printDocumentation();
		
		//processEntitiesFromWikidataDump connect to Wikidata and create a dump with all the data specified by an EntityDocumentProcessor
		//JsonSerializationInstanceOfProcessor is an EntityDocumentProcessor which select wikidata entities if they are instance of a class
		JsonSerializationInstanceOfProcessor jsonSerializationProcessor = JsonSerializationInstanceOfProcessor.getInstanceOf(Entity.HUMAN.toString());
		jsonSerializationProcessor.openJsonSerializer();
		//If a proxy is used this method should be modified
		ExampleHelpers.processEntitiesFromWikidataDump(jsonSerializationProcessor);
		jsonSerializationProcessor.closeJsonSerializer();

		

		printcompletedMessage();
		
		
	}
	
	
	
	
	/**
	 * Prints some basic documentation about this program.
	 */
	public static void printDocumentation() {
		System.out
				.println("********************************************************************");
		System.out.println("*** Wikidata Toolkit: GetAllPersons");
		System.out.println("*** ");
		System.out
				.println("*** This program will extract all persons from a local dumps from Wikidata.");
		System.out
				.println("*** It will filter the data and store the results in a new JSON file.");
		System.out.println("*** See source code for further details.");
		System.out
				.println("********************************************************************");
	}
	
	/**
	 * Prints completed message.
	 */
	public static void printcompletedMessage() {
		System.out
				.println("\n\n********************************************************************");
		System.out.println("*** Wikidata Toolkit: GetAllPersons");
		System.out.println("*** ");
		System.out
				.println("*** Process completed");
		System.out.println("*** ");
		System.out
				.println("********************************************************************");
	}
	

	

}


