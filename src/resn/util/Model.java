package resn.util;

import java.util.HashSet;
import java.util.Set;

public class Model {
	
	protected Set<Residue> peptide = new HashSet<Residue>();
	
	protected String modelNumber ;
	
	
	
	public Model (String number) {
		this.modelNumber = number ;
	}
	
	
	public void addResidue(Residue resn) {
		this.peptide.add(resn) ;
	}
	
	
	@Override
	public String toString() {
		
		String results = "" ;
		
		for (Residue r : this.peptide) {
			results += ( this.modelNumber + "\n" + r.toString()  ) ;
		}
		
		return results ;
	}


	public Set<Residue> getPeptide() {
		return peptide;
	}


	public String getModelNumber() {
		return modelNumber;
	}
	
	
	
}
