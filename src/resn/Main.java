package resn;

import java.io.IOException;

import resn.datamanagement.Reader;
import resn.datamanagement.Reader.FileType;
import resn.processor.Processor;
import resn.ui.CommandLineUserInterface;



public class Main {

	public static void main(String[] args) throws IOException {


		boolean ready = CommandLineUserInterface.argsCheck(args); // check if configuration args are valid
		if (!ready) return ;
		
		System.out.println("Reading...") ;
		
		Reader pdb_reader 		= Reader.createFileReader(args, FileType.PDB) ;

		Processor processor = new Processor (pdb_reader) ;

		CommandLineUserInterface ui = new CommandLineUserInterface(processor) ;

		ui.start() ;



	}

}
