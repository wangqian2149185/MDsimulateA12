package resn.processor;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import resn.datamanagement.Reader;
import resn.util.Atom;
import resn.util.Frequency;
import resn.util.Model;
import resn.util.Residue;

public class Processor {
	
	protected Reader pdb_reader;
	
	protected Set<Model> modelSet;
	
	protected double modelCount = 0 ;
	
	protected Map<String, HashSet<Residue>> residues5A = new HashMap<String, HashSet<Residue>>() ;
	protected Map<Residue, Double> modelNumber5A = new TreeMap<Residue, Double>() ;

	
	public Processor(Reader pdb_reader)
			throws IOException {

		this.pdb_reader = pdb_reader;

		try {

			this.modelSet = pdb_reader.getModelSet();
			
			this.modelCount = pdb_reader.getModelCount() ;
			
		} catch (NullPointerException e) {
		} // if files missed in beginning configuration arguments,null will be produced
		// and ignored

	}
	


	// 1. print the first 10 line
	public String printFirstModel() {
		int i = 1;
		String results = "";
		
		for (Model m : modelSet) {
			if (i == 0) break ;
			results += m.toString();
			i-- ;
		}
		
		return results; 
	}
	
	
	// 2. find all of the residues within 5A of given residue
	public String findAtom5Aresn(String resn_index, String resn_type, String resn_chain) {
		
		String results = "Model\tresn_type\tresn_index\tresn_chain\taimed_resn_chain\n" ;
		String resn_index_trim = resn_index.trim() ;
		String resn_type_threeLetter = this.getThreeLetter(resn_type.trim());
		String resn_chain_trim = resn_chain.trim().toUpperCase() ;
		
		if (resn_type_threeLetter == null) {
			results = "The residue type is not correct!";
			return results ;
		}
		
		
		Residue r_aim = new Residue() ;
		
		for (Model m : this.modelSet) {
			
			r_aim = new Residue() ;
			
			for (Residue r : m.getPeptide()) {
				// if found the aimed residue
				if (r.getResn_index().equals(resn_index_trim) && r.getResn_type().equals(resn_type_threeLetter) && r.getChain().equals(resn_chain_trim)) {
					r_aim = r;
					break;
				}
				
				
			}
			
			if (r_aim.getAtoms().isEmpty()) continue;
			
			// check two atom set if they are overlapped within 5A
			for (Residue r : m.getPeptide()) {
				if (this.compareTwoResiduesWithin5A(r, r_aim)) {
					results += m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + this.getOneLetter(resn_type_threeLetter) + resn_index_trim + "_" + resn_chain_trim + "\n";
				}
			}
		}
		
		if (r_aim.getAtoms().isEmpty()) {
			results = "The residue of " + resn_type + " " + resn_index + " does not exist!"; 
			return results;
		}
		
		return results ;
	}

	// 3. find all of the residues within 5A of given residue
	public String findAtom5Aresn(String resn_index, String resn_type, String resn_chain, String angstrom) {

		String results = "" ;
		String resn_index_trim = resn_index.trim() ;
		String resn_type_threeLetter = this.getThreeLetter(resn_type.trim());
		String resn_chain_trim = resn_chain.trim().toUpperCase() ;

		if (resn_type_threeLetter == null) {
			results = "The residue type is not correct!";
			return results ;
		}
		
		Residue r_aim = new Residue() ;

		for (Model m : this.modelSet) {

			r_aim = new Residue() ;

			for (Residue r : m.getPeptide()) {
				// if found the aimed residue
				if (r.getResn_index().equals(resn_index_trim) && r.getResn_type().equals(resn_type_threeLetter) && r.getChain().equals(resn_chain_trim)) {
					r_aim = r;
					break;
				}
				if (r_aim.getAtoms().isEmpty()) continue;
				
			}

			// check two atom set if they are overlapped within 5A
			for (Residue r : m.getPeptide()) {
				if (this.compareTwoResiduesWithin5A(r, r_aim, angstrom)) {
					results +=  m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + this.getOneLetter(resn_type_threeLetter) + resn_index_trim + "_" + resn_chain_trim + "\n";
				}
			}
		}
		
		if (r_aim.getAtoms().isEmpty()) {
			results = "The residue of " + resn_type + " " + resn_index + " does not exist!"; 
			return results;
		}
		return results ;
	}
	
	// helper function to compare two
	public boolean compareTwoResiduesWithin5A(Residue r1, Residue r2) {
		
		
		for (Atom a1 : r1.getAtoms()) {
			for (Atom a2 : r2.getAtoms()) {
				double dis = Math.sqrt(Math.pow( Double.valueOf(a1.getX_axis())- Double.valueOf(a2.getX_axis())  , 2 )
						+(Math.pow( Double.valueOf(a1.getY_axis())- Double.valueOf(a2.getY_axis())  , 2 ))
						+(Math.pow( Double.valueOf(a1.getZ_axis())- Double.valueOf(a2.getZ_axis())  , 2 )));
				
				if (dis <= 5.0) return true;
				
			}
		}
		
		return false ;
	}
	
	// helper function to compare two
	public boolean compareTwoResiduesWithin5A(Residue r1, Residue r2, String angstrom) {
		
		Double range = Double.valueOf(angstrom.trim()) ;

		for (Atom a1 : r1.getAtoms()) {
			for (Atom a2 : r2.getAtoms()) {
				double dis = Math.sqrt(Math.pow( Double.valueOf(a1.getX_axis())- Double.valueOf(a2.getX_axis())  , 2 )
						+(Math.pow( Double.valueOf(a1.getY_axis())- Double.valueOf(a2.getY_axis())  , 2 ))
						+(Math.pow( Double.valueOf(a1.getZ_axis())- Double.valueOf(a2.getZ_axis())  , 2 )));

				if (dis <= range) return true;

			}
		}

		return false ;
	}
	
	// helper function to print peptide sequence
	public void printPeptideSequence() {
		
		Map<Integer, String> peptideSequence = new TreeMap<Integer, String>() ;
		
		for (Model m : this.modelSet) {
			for (Residue r : m.getPeptide()) {
				peptideSequence.put(Integer.valueOf(r.getResn_index()), this.getOneLetter(r.getResn_type())) ;
			}
			
			break;
		}
	
		
		int size = peptideSequence.size() ;
		
		for (int i = 0; i < size ; i++) {
			if (i % 5 == 0) System.out.print(i+1 + "\t");
		}
		
		System.out.println();
		
		int j = 1 ;
		for (Integer index : peptideSequence.keySet()) {
			System.out.print(peptideSequence.get(index));
			if (j % 5 == 0) System.out.print("\t");
			j++ ;
		}
		
		
	}
	
	// helper function
	public String getThreeLetter(String oneLetterCode) {
		
		// if already three letters
		if (oneLetterCode.length() == 3) return oneLetterCode.toUpperCase() ;
		
		switch (oneLetterCode.toUpperCase()) {

		case "M" :
			return "MET" ;

		case "A" :
			return "ALA" ;

		case "R" :
			return "ARG" ;

		case "N" :
			return "ASN" ;

		case "D" :
			return "ASP" ;

		case "C" :
			return "CYS" ;

		case "Q" :
			return "GLN" ;

		case "E" :
			return "GLU" ;

		case "G" :
			return "GLY" ;

		case "H" :
			return "HIS" ;

		case "I" :
			return "ILE" ;

		case "L" :
			return "LEU" ;

		case "K" :
			return "LYS" ;

		case "F" :
			return "PHE" ;

		case "P" :
			return "PRO" ;

		case "O" :
			return "PYL" ;

		case "S" :
			return "SER" ;

		case "U" :
			return "SEC" ;

		case "T" :
			return "THR" ;

		case "W" :
			return "TRP" ;

		case "Y" :
			return "TYR" ;

		case "V" :
			return "VAL" ;

		default:
			return null;
		}
	}

	// helper function
	// return the current residue type in one letter
	public String getOneLetter(String threeLetterCode) {
		switch (threeLetterCode) {

		case "MET" :
			return "M" ;

		case "ALA" :
			return "A" ;

		case "ARG" :
			return "R" ;

		case "ASN" :
			return "N" ;

		case "ASP" :
			return "D" ;

		case "CYS" :
			return "C" ;

		case "GLN" :
			return "Q" ;

		case "GLU" :
			return "E" ;

		case "GLY" :
			return "G" ;

		case "HIS" :
			return "H" ;

		case "ILE" :
			return "I" ;

		case "LEU" :
			return "L" ;


		case "LYS" :
			return "K" ;

		case "PHE" :
			return "F" ;

		case "PRO" :
			return "P" ;

		case "PYL" :
			return "O" ;

		case "SER" :
			return "S" ;

		case "SEC" :
			return "U" ;

		case "THR" :
			return "T" ;

		case "TRP" :
			return "W" ;

		case "TYR" :
			return "Y" ;

		case "VAL" :
			return "V" ;

		default:
			return null;
		}
	}



	public String findResnOfDomain(String resn_domain, String resn_chain) {
		
		String results = "" ;
		
		String[] resnArray = resn_domain.split("[, ]+") ;
		String[] chainArray = resn_chain.split("[, ]+") ;
		
		for (String resn_index : resnArray) {
			if (resn_index.contains("-")) {
				String[] resn  = resn_index.split("[-]") ; // if 18-25
				
				for (int i = Integer.valueOf(resn[0]) ; i <= Integer.valueOf(resn[1]) ; i++ ) {
					for (String c : chainArray) {
						results += this.findAtom5Aresn(Integer.toString(i), c) ;
					}
				}
			}
			else {
				for (String c : chainArray) {
					results += this.findAtom5Aresn(resn_index, c.toUpperCase()) ;
				}
			}
		}
		
		return results;
	}

	public String findAtom5Aresn(String resn_index, String resn_chain) {

		String results = "Model\tresn_type\tresn_index\tresn_chain\taimed_resn_chain\n" ;
		String resn_index_trim = resn_index.trim() ;

		String resn_chain_trim = resn_chain.trim().toUpperCase() ;


		Residue r_aim = new Residue() ;

		for (Model m : this.modelSet) {

			r_aim = new Residue() ;

			for (Residue r : m.getPeptide()) {
				// if found the aimed residue
				if (r.getResn_index().equals(resn_index_trim) && r.getChain().equals(resn_chain_trim)) {
					r_aim = r;
					break;
				}


			}

			if (r_aim.getAtoms().isEmpty()) continue;

			// check two atom set if they are overlapped within 5A
			for (Residue r : m.getPeptide()) {
				if (this.compareTwoResiduesWithin5A(r, r_aim)) {
					results += m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t"  + resn_index_trim + "_" + resn_chain_trim + "\n";
				}
			}
		}

		if (r_aim.getAtoms().isEmpty()) {
			results = "The residue of "  + resn_index + " does not exist!"; 
			return results;
		}

		return results ;
	}


	
	
	// Find the frequency of residues within 5 angstrom of given residue.
	public String frequencyOf5AResidues(String resn_index, String resn_type, String resn_chain) {
		
		String results = "\nList of residues within 5A of "+ resn_type + resn_index+ " in chain "+ resn_chain + "\ntresn_type\tresn_index\tresn_chain\tmodel_frequency\n" ;
		String resn_index_trim = resn_index.trim() ;
		String resn_type_threeLetter = this.getThreeLetter(resn_type.trim());
		String resn_chain_trim = resn_chain.trim().toUpperCase() ;
		
		this.residues5A.clear();
		this.modelNumber5A.clear();
		
		if (resn_type_threeLetter == null) {
			results = "The residue type is not correct!";
			return results ;
		}
		
		
		Residue r_aim = new Residue() ;
		
		for (Model m : this.modelSet) {
			
			r_aim = new Residue() ;
			
			for (Residue r : m.getPeptide()) {
				// if found the aimed residue
				if (r.getResn_index().equals(resn_index_trim) && r.getResn_type().equals(resn_type_threeLetter) && r.getChain().equals(resn_chain_trim)) {
					r_aim = r;
					break;
				}
				
				
			}
			
			if (r_aim.getAtoms().isEmpty()) continue;
			
			// check two atom set if they are overlapped within 5A
			for (Residue r : m.getPeptide()) {
				if (this.compareTwoResiduesWithin5A(r, r_aim)) {
					// if residue5A contians the model, then put it, or add a new model
					if (this.residues5A.containsKey(m.getModelNumber())) {
						this.residues5A.get(m.getModelNumber()).add(r) ;
					}
					else {
						this.residues5A.put(m.getModelNumber(), new HashSet<Residue>()) ;
						this.residues5A.get(m.getModelNumber()).add(r) ;
					}
//					results += m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + this.getOneLetter(resn_type_threeLetter) + resn_index_trim + "_" + resn_chain_trim + "\n";
				}
			}
		}
		
		if (r_aim.getAtoms().isEmpty()) {
			results = "The residue of " + resn_type + " " + resn_index + " does not exist!"; 
			return results;
		}
		
		// if found the aimed residue, then go ahead
		for (String s : this.residues5A.keySet()) {
			for (Residue r : this.residues5A.get(s)) {
				if (this.modelNumber5A.containsKey(r)) {
					this.modelNumber5A.put(r, this.modelNumber5A.get(r)+1.0) ;
				} else this.modelNumber5A.put(r, 1.0) ;
				
			}
		}
		
		// print results
		for (Residue r : this.modelNumber5A.keySet()) {
			results += r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + String.format("%.4f", modelNumber5A.get(r)/this.modelCount ) + "\n";
		}
		
		return results ;
	}



	public String frequencyof5ADomain(String resn_domain, String resn_chain) {

		String results = "\nList of residues within 5A of "+ resn_domain + " in chain "+ resn_chain + "\ntresn_type\tresn_index\tresn_chain\tmodel_frequency\n" ;
		
		String[] resnArray = resn_domain.split("[, ]+") ;
		String[] chainArray = resn_chain.split("[, ]+") ;
		
		this.residues5A.clear();
		this.modelNumber5A.clear();
		

		for (String resn_index : resnArray) {
			if (resn_index.contains("-")) { // if element is a domain by "-"
				String[] resn  = resn_index.split("[-]") ; // if 18-25

				for (int i = Integer.valueOf(resn[0]) ; i <= Integer.valueOf(resn[1]) ; i++ ) {
					for (String c : chainArray) {
//						results += this.findAtom5Aresn(Integer.toString(i), c) ;
						Residue r_aim = new Residue() ;
						
						for (Model m : this.modelSet) {
							
							r_aim = new Residue() ;
							
							for (Residue r : m.getPeptide()) {
								// if found the aimed residue
								if (r.getResn_index().equals(Integer.toString(i)) && r.getChain().equals(c)) {
									r_aim = r;
									break;
								}
								
								
							}
							
							if (r_aim.getAtoms().isEmpty()) continue;
							
							// check two atom set if they are overlapped within 5A
							for (Residue r : m.getPeptide()) {
								if (this.compareTwoResiduesWithin5A(r, r_aim)) {
									// if residue5A contians the model, then put it, or add a new model
									if (this.residues5A.containsKey(m.getModelNumber())) {
										this.residues5A.get(m.getModelNumber()).add(r) ;
									}
									else {
										this.residues5A.put(m.getModelNumber(), new HashSet<Residue>()) ;
										this.residues5A.get(m.getModelNumber()).add(r) ;
									}
//									results += m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + this.getOneLetter(resn_type_threeLetter) + resn_index_trim + "_" + resn_chain_trim + "\n";
								}
							}
						}
						
						
						
						
						
					}
				}
			}
			else { // if element is not a domain, but single residue
				for (String c : chainArray) {
//					results += this.findAtom5Aresn(resn_index, c.toUpperCase()) ;
					Residue r_aim = new Residue() ;
					
					for (Model m : this.modelSet) {
						
						r_aim = new Residue() ;
						
						for (Residue r : m.getPeptide()) {
							// if found the aimed residue
							if (r.getResn_index().equals(resn_index) && r.getChain().equals(c)) {
								r_aim = r;
								break;
							}
							
							
						}
						
						if (r_aim.getAtoms().isEmpty()) continue;
						
						// check two atom set if they are overlapped within 5A
						for (Residue r : m.getPeptide()) {
							if (this.compareTwoResiduesWithin5A(r, r_aim)) {
								// if residue5A contians the model, then put it, or add a new model
								if (this.residues5A.containsKey(m.getModelNumber())) {
									this.residues5A.get(m.getModelNumber()).add(r) ;
								}
								else {
									this.residues5A.put(m.getModelNumber(), new HashSet<Residue>()) ;
									this.residues5A.get(m.getModelNumber()).add(r) ;
								}
//								results += m.getModelNumber() + "\t" + r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + this.getOneLetter(resn_type_threeLetter) + resn_index_trim + "_" + resn_chain_trim + "\n";
							}
						}
					}
					
					
					
					
					
					
					
				}
			}
		}
		// if found the aimed residue, then go ahead
		for (String s : this.residues5A.keySet()) {
			for (Residue r : this.residues5A.get(s)) {
				if (this.modelNumber5A.containsKey(r)) {
					this.modelNumber5A.put(r, this.modelNumber5A.get(r)+1.0) ;
				} else this.modelNumber5A.put(r, 1.0) ;
				
			}
		}
		
		// extract the index and chain only, for return the proper format of results
		Map<Residue, Frequency> peptideSequence = new TreeMap<>() ;

		for (Model m : this.modelSet) {
			for (Residue r : m.getPeptide()) {
				peptideSequence.put(r, new Frequency(r.getResn_type(), r.getResn_index(), r.getChain())) ;

			}

			break;
		}
		
		// update the freq of found residues
		for (Residue r : this.modelNumber5A.keySet()) {
			 peptideSequence.get(r).setFrequency(this.modelNumber5A.get(r)/this.modelCount) ;
		}
		
		for (Residue r : peptideSequence.keySet()) {
			results += peptideSequence.get(r).toString() ;
		}
		
//		// print results
//		for (Residue r : this.modelNumber5A.keySet()) {
//			results += r.getResn_type() + "\t" + r.getResn_index() + "\t" + r.getChain() + "\t" + String.format("%.4f", modelNumber5A.get(r)/this.modelCount ) + "\n";
//		}
		
		return results;
		
	}




	
}
