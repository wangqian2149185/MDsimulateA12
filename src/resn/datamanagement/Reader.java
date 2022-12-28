package resn.datamanagement;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import resn.logging.Logger;
import resn.util.Model;

public abstract class Reader {
	
	protected int modelCount = 0 ;

	public enum FileType {PDB} ;

	private final BufferedReader reader ;


	// Logger
	Logger lg = Logger.getInstance() ;

	public Reader (String filename) throws IOException {

		this.reader = Files.newBufferedReader(Paths.get(filename)) ;

//		this.lg.log(filename);

	}





	// TODO abstract methods to read files
	public abstract Set<Model> getModelSet() throws IOException ;



	@SuppressWarnings("unchecked")
	public static <T extends Reader> T createFileReader(String[] args, FileType fileType) throws IOException {

//		Pattern pattern ;
//		Matcher matcher ;

		String[] filename = new String[2];

		switch(fileType ) {


		case PDB :
			for (int i = 0 ; i < args.length; i++) {
				filename = args[i].substring(2).toLowerCase().split("[=]") ;

				if (filename[0].equals("pdb")) {
					return (T) new PdbFileReader(filename[1].trim()) ;
				}
			}

			// if none of the file is properties file, then return null to this Reader
			if (filename[1] == null) return null ;
			
			

		default:
			return null ;

		}

	}




	public String[] readRow() throws IOException {
		
		String line = this.reader.readLine() ;
		
		if (line == null) {
			 String[] readline = { "ENDEND", "ENDEND" } ;
			 return readline ;
		}

		String [] readline = line.split("[ \t]+") ;

		return readline;
	}
	
	
	public int getModelCount() {
		return this.modelCount;
	}

}


