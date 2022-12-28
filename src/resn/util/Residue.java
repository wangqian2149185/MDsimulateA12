package resn.util;

import java.util.HashSet;
import java.util.Set;

public class Residue implements Comparable<Residue> {
	
	protected Set<Atom> atoms = new HashSet<Atom>();

	protected String resn_type ;
	protected String resn_index ;
	protected String chain ;

	
	public Residue (String resn_type ,String resn_index ,String chain, Atom atom  ) {
		

		this.resn_type = resn_type ;
		this.resn_index = resn_index ;
		this.chain = chain ;
		
		this.atoms.add(atom) ;
	}
	
	public Residue() {}
	
	public void addAtomToResidue(Atom atom) {
		this.atoms.add(atom) ;
	}
	
	
	
	@Override
	public int compareTo(Residue o) {
		
		if (this.getChain().compareTo(o.getChain()) < 0) return -1 ;
		else if (this.getChain().compareTo(o.getChain()) > 0) return 1 ;
		else return Integer.valueOf(this.resn_index) - Integer.valueOf(o.getResn_index()) ;
		
	}
	

    // Two employees are equal if their IDs are equal
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Residue employee = (Residue) o;
        return ( Integer.valueOf(this.resn_index) == Integer.valueOf(employee.getResn_index()) && this.getChain().equals(employee.getChain()));
    }
	
	
	
	
	
	
	
	@Override
	public String toString() {
		
		String results = "" ;
		
		for (Atom a : this.atoms) {
			results += ( "[" + this.chain + "]\t" + 
					"[" + this.resn_index + "]\t" +
					"[" + this.resn_type + "]\t" + a + "\n") ;
		}
		
		return results;
		
	}
	
	
	
	
	
	
	// getters and setters
	public String getResn_type() {
		return resn_type;
	}

	public String getResn_index() {
		return resn_index;
	}

	public String getChain() {
		return chain;
	}


	public Set<Atom> getAtoms() {
		return atoms;
	}


	
	
	
	
}
