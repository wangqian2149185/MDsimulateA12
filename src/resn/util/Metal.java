package resn.util;

public class Metal {
	protected String id ;
	protected String atom_type ;
	protected String metal_index ;
	protected String x_axis;
	protected String y_axis;
	protected String z_axis;
	protected String element_type ;
	protected String model ;
	
	public Metal (		
			String id , 
			String atom_type ,
			String metal_index ,
			String resn_index ,
			String chain ,
			String x_axis,
			String y_axis,
			String z_axis,
			String element_type ,
			String model ) {
		
		this.id = id ;
		this.atom_type = atom_type ;
		this.metal_index = metal_index ;
		this.x_axis = x_axis ;
		this.y_axis = y_axis ;
		this.z_axis = z_axis ;
		this.element_type = element_type ;
		this.model = model ;
		
	}
	
	@Override
	public String toString() {
		return ("[" + this.id + "]" + 
				"[" + this.atom_type + "]" +
				"[" + this.metal_index + "]" +
				"[" + this.x_axis + "]" +
				"[" + this.y_axis + "]" +
				"[" + this.z_axis + "]" +
				"[" + this.element_type + "]" +
				"[" + this.model + "]");
		
	}

	public String getId() {
		return id;
	}

	public String getAtom_type() {
		return atom_type;
	}

	public String getMetal_indexe() {
		return metal_index;
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

	public String getModel() {
		return model;
	}
	
	

}

