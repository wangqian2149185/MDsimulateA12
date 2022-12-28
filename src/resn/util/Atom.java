package resn.util;

public class Atom {
	
	protected String id ;
	protected String atom_type ;
	
	protected String x_axis;
	protected String y_axis;
	protected String z_axis;
	protected String element_type ;

	
	public Atom (		
			String id , 
			String atom_type ,
			
			String x_axis,
			String y_axis,
			String z_axis,
			String element_type ) {
		
		this.id = id ;
		this.atom_type = atom_type ;
		this.x_axis = x_axis ;
		this.y_axis = y_axis ;
		this.z_axis = z_axis ;
		this.element_type = element_type ;
	}
	
	@Override
	public String toString() {
		return ("[" + this.id + "]\t" + 
				"[" + this.atom_type + "]\t" +
				"[" + this.x_axis + "]\t" +
				"[" + this.y_axis + "]\t" +
				"[" + this.z_axis + "]\t" +
				"[" + this.element_type + "]\t");
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public String getId() {
		return id;
	}

	public String getAtom_type() {
		return atom_type;
	}


	public String getX_axis() {
		return x_axis;
	}

	public String getY_axis() {
		return y_axis;
	}

	public String getZ_axis() {
		return z_axis;
	}

	public String getElement_type() {
		return element_type;
	}

	
	

}
