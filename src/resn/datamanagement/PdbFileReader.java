package resn.datamanagement;

import resn.util.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import resn.util.Residue;

public class PdbFileReader extends Reader {
	
	
//	protected int modelCount = 0 ;
	

	public PdbFileReader(String filename) throws IOException {
		super(filename);
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	


	@Override
	public Set<Model> getModelSet() throws IOException {
		
		
		Set<Model> modelSet = new HashSet<Model>() ; 

//		Set<Metal> metalSet	= new HashSet<Metal>() ;

		String id ;
		String atom_type ;
		String resn_type ;
		String resn_index ;
		String chain ;
		String x_axis;
		String y_axis;
		String z_axis;
		String element_type ;

		
//		String metal_index ;
//		String metal_x_axis ;
//		String metal_y_axis ;
//		String metal_z_axis ;
//		String metal_element_type ;

		int index_id 					= 1 ;
		int index_atom_type 			= 2 ;
		int index_resn_type				= 3 ;
		int index_chain 				= 4 ;
		int index_resn_index			= 5 ;
		int index_x_axis				= 6 ;
		int index_y_axis				= 7 ;
		int index_z_axis				= 8 ;
		int index_element_type			=11 ;
		
//		int index_metal_index 			= 4 ;
//		int index_metal_x_axis			= 5 ;
//		int index_metal_y_axis			= 6 ;
//		int index_metal_z_axis			= 7 ;
//		int index_metal_element_type	=10 ;
		
		
		boolean run = true ;
		Atom atom = null ;
		Residue residue = null; 
		Model model = null ;
		int modelCount = 0 ;
		
		while (run) {
			String [] readRowList = readRow();
			
			switch (readRowList[0]) {
			
			case "MODEL" :
				model = new Model( readRowList[1] );
				modelCount++ ;
				break ;
				
			case "ATOM" :
				if (readRowList.length == 11) { // metal ions such calcium
//					id 					= readRowList[index_id] ;
//					atom_type 			= readRowList[index_atom_type] ;
//					metal_index 		= readRowList[index_metal_index] ;
//					metal_x_axis 		= readRowList[index_metal_x_axis] ;
//					metal_y_axis 		= readRowList[index_metal_y_axis] ;
//					metal_z_axis 		= readRowList[index_metal_z_axis] ;
//					metal_element_type 	= readRowList[index_metal_element_type] ;
//					
//					metalSet.add(new Atom(id, atom_type, resn_type, resn_index, chain, x_axis, y_axis, z_axis, element_type, model)) ;
					
				}
				else { // residue atoms
					id 				= readRowList[index_id] ;
					atom_type 		= readRowList[index_atom_type] ;
					x_axis 			= readRowList[index_x_axis] ;
					y_axis 			= readRowList[index_y_axis] ;
					z_axis 			= readRowList[index_z_axis] ;
					try {
						element_type 	= readRowList[index_element_type] ;
					} catch (ArrayIndexOutOfBoundsException e) {
//						System.out.println("id: " + id + "\natom_type: " + atom_type + "\nx_axis: " + x_axis + "\ny_axis: " + y_axis + "\nz_axis: " + z_axis) ;
//						return modelSet;
						continue ;
					}
					
					
					
					
					atom = new Atom(id, atom_type, x_axis, y_axis, z_axis, element_type) ;
					
					resn_type 		= readRowList[index_resn_type] ;
					resn_index 		= readRowList[index_resn_index] ;
					chain 			= readRowList[index_chain] ;
					
					if (residue == null ) { // if residue is not initialized or 
						residue = new Residue(resn_type, resn_index, chain , atom) ;
					} 
					else if (	residue.getResn_index().equals(resn_index) &&
							residue.getResn_type().equals(resn_type) &&
							residue.getChain().equals(chain)){
						residue.addAtomToResidue(atom);
					}
					else { 
						model.addResidue(residue);
						residue = new Residue(resn_type, resn_index, chain , atom) ;

					}


				}
				
				break ;
				
			case "ENDMDL" :
				model.addResidue(residue);
				modelSet.add(model);
				model = null;
				residue = null ;
				break ;
			
			case "ENDEND" :
				run = false ;
				this.modelCount = modelCount ;
				break;
				
			default :
				break ;
			}
		}

		return modelSet;

	}










	
	
	
}
