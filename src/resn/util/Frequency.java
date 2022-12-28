package resn.util;

public class Frequency implements Comparable<Frequency>{
	
	protected String resn_type;
	protected String resn_index;
	protected String chain ;
	protected Double frequency ;
	protected Integer modelCount ;
	
	public Frequency (String resn_type, String resn_index, String chain, Double frequency) {
		this.resn_index = resn_index ;
		this.resn_type = resn_type ;
		this.chain = chain ;
		this.frequency = frequency ;
	}
	
	public Frequency (String resn_type, String resn_index, String chain) {
		this.resn_index = resn_index ;
		this.resn_type = resn_type ;
		this.chain = chain ;
		this.frequency = -0.10 ;
		this.modelCount = 0 ;
	}
	
	
	@Override
	public int compareTo(Frequency o) {
		
		if (this.getChain().compareTo(o.getChain()) < 0) return -1 ;
		else if (this.getChain().compareTo(o.getChain()) > 0) return 1 ;
		else return Integer.valueOf(this.resn_index) - Integer.valueOf(o.getResn_index()) ;
		
	}
	
	@Override
	public String toString() {
		
		String results = "" ;
		
		results += this.resn_type + "\t" + this.resn_index + "\t" + this.chain + "\t" + String.format("%.4f", this.frequency ) + "\n";
		
		return results;
	}
	
	
	
	
	
	
	

	public String getResn_type() {
		return resn_type;
	}

	public String getResn_index() {
		return resn_index;
	}

	public String getChain() {
		return chain;
	}

	public Double getFrequency() {
		return frequency;
	}

	public void setResn_type(String resn_type) {
		this.resn_type = resn_type;
	}

	public void setResn_index(String resn_index) {
		this.resn_index = resn_index;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public void setFrequency(Double frequency) {
		this.frequency = frequency;
	}
	
	

}
