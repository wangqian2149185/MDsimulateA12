package resn.ui;

import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import resn.logging.Logger;
import resn.processor.Processor;

public class CommandLineUserInterface {

	
	protected Processor processor ;
	
	
	public CommandLineUserInterface(Processor processor) {
		this.processor = processor ;
		
	}
	
	// looks redundant but make both reader and programmer easy to understand and code
	public void start() {
		
		Scanner sc = new Scanner(System.in) ;
		
		String resn_type, resn_index, resn_chain, dis, resn_domain ;
		
		boolean run = true;
		
		while (run) {
			
			this.printMenu();
			
			String inputString = sc.next();
//			Logger.getInstance().log(inputString);
			
			if (!inputString.matches("^[0-7]$")) {
				System.out.println("Error: improper input integer value. Please select between 0-7.");
				System.out.flush();
				
				continue;
			}
			
			switch(inputString) {
			
			case "0" : // 0. Exit the program.
				run = false;
				break;
				
			case "1" : // 1. print frist model
				String results = this.processor.printFirstModel();
				System.out.println(results) ;
				System.out.flush();
				break;
				
			case "2" : // 2. find all residues around 5 angstrom of given residues
				this.processor.printPeptideSequence() ;
				System.out.print("\nWhat is this residue TYPE? Type \"N\" or \"Gln\" (case-insensitive) if you mean Glutamine \nType >> ");
				System.out.flush(); 
				resn_type = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhat is this residue INDEX? Type \"2\" if you mean second residue according to sequence above\nIndex >> ");
				System.out.flush(); 
				resn_index = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhich chain of this residue located? Type \"A\" (case-insensitive) if you mean 1st chain\nChain >> ");
				System.out.flush(); 
				resn_chain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				this.printMessage() ;
				
				String results2 = this.processor.findAtom5Aresn(resn_index, resn_type, resn_chain);
				System.out.println(results2) ;
				System.out.flush();
				
				this.writeResults(resn_type, resn_index, resn_chain, results2);
				
				break;
				
			case "6" : // 6. Find the residues within x angstrom of given residue.
				this.processor.printPeptideSequence() ;
				System.out.print("\nWhat is this residue TYPE? Type \"N\" or \"Gln\" (case-insensitive) if you mean Glutamine \nType >> ");
				System.out.flush(); 
				resn_type = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhat is this residue INDEX? Type \"2\" if you mean second residue according to sequence above\nIndex >> ");
				System.out.flush(); 
				resn_index = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhich chain of this residue located? Type \"A\" (case-insensitive) if you mean 1st chain\nChain >> ");
				System.out.flush(); 
				resn_chain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhat is the restrict distance? Type \"6\" if you mean 6 angstrom\nDistance >> ");
				System.out.flush(); 
				dis = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				this.printMessage() ;
				
				String results6 = this.processor.findAtom5Aresn(resn_index, resn_type, resn_chain, dis);
				System.out.println(results6) ;
				System.out.flush();
				
				this.writeResults(resn_type, resn_index, resn_chain, results6, dis);
				
				break;
				
			case "3" : // 3. Find the frequency of residues within 5 angstrom of given residue.
				this.processor.printPeptideSequence() ;
				System.out.print("\nWhat is this residue TYPE? Type \"N\" or \"Gln\" (case-insensitive) if you mean Glutamine \nType >> ");
				System.out.flush(); 
				resn_type = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhat is this residue INDEX? Type \"2\" if you mean second residue according to sequence above\nIndex >> ");
				System.out.flush(); 
				resn_index = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhich chain of this residue located? Type \"A\" (case-insensitive) if you mean 1st chain\nChain >> ");
				System.out.flush(); 
				resn_chain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				this.printMessage() ;
				
				String results3 = this.processor.frequencyOf5AResidues(resn_index, resn_type, resn_chain);
				System.out.println(results3) ;
				System.out.flush();
				
				this.writeResults(resn_type, resn_index, resn_chain, results3);
				
				break;
				
			case "5" : // 5. Find the frequency of residues within 5 angstrom of given domain.
				this.processor.printPeptideSequence() ;
				System.out.print("\nSelect residue index numbers of domain. \nE.g. type \"18-25\" or \"23, 39, 54\" or \"13-26, 43, 89\" \nType >> ");
				System.out.flush(); 
				resn_domain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhich chain? E.g. \"a, B, c\" means chain A, B and C. \nchain >> ");
				System.out.flush(); 
				resn_chain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				this.printMessage() ;
				
				String results5 = this.processor.frequencyof5ADomain(resn_domain,  resn_chain) ;
				System.out.println(results5) ;
				System.out.flush();
				
				this.writeResults(results5);
				
				break;
				
			case "4" : // 4. Find the residues within 5 angstrom of given domain.
				this.processor.printPeptideSequence() ;
				System.out.print("\nSelect residue index numbers of domain. \nE.g. type \"18-25\" or \"23, 39, 54\" or \"13-26, 43, 89\" \nType >> ");
				System.out.flush(); 
				resn_domain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				System.out.print("\nWhich chain? E.g. \"a, B, c\" means chain A, B and C. \nchain >> ");
				System.out.flush(); 
				resn_chain = sc.next().trim().toUpperCase(); 
				System.out.flush();
				
				this.printMessage() ;
				
				String results4 = this.processor.findResnOfDomain(resn_domain,  resn_chain) ;
				System.out.println(results4) ;
				System.out.flush();
				
				this.writeResults(results4);
				
				break;
				
			default :
				break;

			}

			


		}
		sc.close();
		
		Logger.getInstance().logClose();
	}
	
	
	private void printMessage() {
		
		Random rand = new Random() ;
		
		int number = rand.nextInt(100);
		
		String[] messages = {"\nCalculating...", "\nDigging...", "\nAnalyzing...", "\nSequeezing...", "\nProcessing...", "\nCooking...", "\nSmashing..."} ;
		
		System.out.println(messages[number%messages.length]) ;
		
		System.out.flush();
		
	}

	protected void writeResults(String results) {
		Logger lg = Logger.getInstance() ;
		
		lg.log("\n" + results + "\n");
	}
	
	
	// helper function to write results to file
	protected void writeResults(String resn_type, String resn_index, String resn_chain, String results) {
		Logger lg = Logger.getInstance() ;
		
		lg.log("\n***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****\n");
		lg.log("**\t" + "List of residues within " + "5A of aimed residue: " + resn_type + resn_index + " in chain " + resn_chain + "\t   **\n");
		lg.log("***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****\n");
		lg.log("\n" + results + "\n");
		lg.log("***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** ***** *****\n\n\n");
	}
	
	// helper function to write results to file
	protected void writeResults(String resn_type, String resn_index, String resn_chain, String results, String dis) {
		Logger lg = Logger.getInstance() ;
		
		lg.log("\n***** ***** ***** ***** ***** ***** *****\n");
		lg.log("**\t" + "List of residues within " + dis + "A of aimed resideu: "  + resn_type + resn_index + " in chain " + resn_chain +"\t   **\n");
		lg.log("***** ***** ***** ***** ***** ***** *****\n");
		lg.log("\n" +results + "\n");
		lg.log("***** ***** ***** ***** ***** ***** *****\n\n\n");
	}
	


	public static boolean argsCheck(String[] args) {
		// Logger
		Logger lg = Logger.getInstance() ;

		// check list: if any of them fail, terminate program
		String regex = "^--(?<name>.+?)=(?<value>.+)$";
		Pattern pattern = Pattern.compile(regex);
		
		// for later logging
		String arguments = "";
		
		// set for check if duplicate arguments used more than once
		Set<String> argSet = new HashSet<String>() ;
		
		// set for check if --log is input
		Set<String> nameSet = new HashSet<String>() ;
		
		for (int i = 0 ; i < args.length; i++) {
			
			// Any arguments to main do not match the form “--name=value”.
			Matcher matcher = pattern.matcher(args[i]);
			if (!matcher.matches()) {
				System.out.println("Error: arguments [" + args[i] + "] do not match the form \"--type=filename\".") ;
				return false;
			}
			
			// The name of an argument is not one of the names listed above.
			String name = args[i].split("=")[0] ;
			String fileName = args[i].split("=")[1] ;
			
			if ( 	   !name.equals("--pdb") 
					&& !name.equals("--log")) {
				System.out.println("Error: arguments [" + args[i] + "] is not one of the names expected.") ;
				return false;
			}
			
			// The name of an argument is used more than once (e.g., "--log=a.log --log=a.log").
			if (!argSet.add(args[i])) {
				System.out.println("Error: arguments [" + args[i] + "] is used more than once.") ;
				return false;
			}
			
			// The logger cannot be correctly initialized (e.g., the given log file cannot be opened for writing).
			if ( name.equals("--log")) {
				lg.setOutputDestination(fileName); // set output destination here
				if (!lg.canWrite()) {
					System.out.println("Error: arguments [" + fileName + "] cannot be opened and write.") ;
					return false;
				}
			}
			
			// The format of the COVID data file can not be determined from the filename extension (“csv” or “json”, case-insensitive).
			if (name.equals("--pdb")) {
				String extension = fileName.split("\\.")[1].toLowerCase() ;
				if (!extension.equals("pdb") && !extension.equals("txt")) {
					System.out.println("Error: format of [" + args[i] + "] is not pdb or txt version.") ;
					return false;
				}
			}
			
			// The specified input files do not exist or cannot be opened for reading (e.g., because of file permissions).
			File f = new File(fileName) ;
			if (!f.exists()) {
				System.out.println("The file " + fileName + " does not exist.");
				return false;
			} else if (!f.canRead()) {
				System.out.println("The file " + fileName + "cannot be read.");
				return false;
			}
			
			// add name to nameSet
			nameSet.add(args[i]);
			
			// if not terminated by args[i], then concatinate to arguments for later logging
			arguments = (arguments + " " + args[i]) ;
			
		}
		
		// check if there is --log file
		if ( !arguments.contains("--log") ) {
			lg.setOutputDestination("log.log");
		} 
		
		lg.log(arguments); // log the user input arguments
		
		return true ;
	}
	
	
	
	
	
	
	

	
	protected void printMenu() {
		System.out.println("0. Exit the program.");
		System.out.println("1. Print the first model.");
		System.out.println("2. Find the residues within 5 angstrom of given residue.");
		System.out.println("3. Find the frequency of residues within 5 angstrom of given residue.");
		System.out.println("4. Find the residues within 5 angstrom of given domain.");
		System.out.println("5. Find the frequency of residues within 5 angstrom of given domain.");
		System.out.println("6. Find the residues within x angstrom of given residue.");
		System.out.print("> ") ;
		System.out.flush();
	}
	
	
	
	protected boolean isDateGoodFormat(String date) {
		
		String regex = "[0-9]{4}-[0-9]{2}-[0-9]{2}" ;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		
		return matcher.find();
	}
	
	protected boolean isZipGoodFormat (String zip_code) {
		
		String regex = "[0-9]{5}" ;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(zip_code);
		
		return matcher.find();
	}
	
	protected void printResults (String results) {
		System.out.println("\nBEGIN OUTPUT") ;
		System.out.print(results);
		System.out.println("END OUTPUT\n") ;
	}
	
	

}
