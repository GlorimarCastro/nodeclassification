Wdkt-ll-parent:
	Import this folder in Eclipse as a General Eclipse Project. Eclipse automatically will open it as a Maven Project and download all dependencies. When importing this project make sure to select the option �Search for nested projects� in the Options section in the the Import Properties box.  
	There is one dependency that is local and needs to be specified in the pom.xml file inside wdkt-ll-parent. This dependency uses the wdkt.jar. Set the systemPath for the dependency with goupID �org.wikidata.wdtk� to point to your local wdkt.jar file. Example:
		<dependency>
  			<groupId>org.wikidata.wdtk</groupId>
  			<artifactId>wdtk-parent</artifactId>
  			<version>0.5.0SNAPSHOT</version>
  			<scope>system</scope>
  			<systemPath> C:\Users\NodeClassificationExperiment\nodeclassification\java\wdkt.jar
			</systemPath>
  		</dependency>


