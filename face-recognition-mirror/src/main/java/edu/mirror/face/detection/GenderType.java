package edu.mirror.face.detection;

import java.util.Optional;

/**
 * Person gender type 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public enum GenderType {

	/** The person is a woman*/
	WOMAN,
	
	/** The person is a man*/
	MAN;
	
	/**
	 * Gets a {@link GenderType} using its ordinal value
	 *  
	 * @param value int
	 * @return {@link Optional} with the object or empty
	 */
	public static Optional<GenderType> getGenderFromOrdinal(final int value){
		
		for(final GenderType genderType : GenderType.values()){
			
			if(genderType.ordinal() == value){
				return Optional.of(genderType);
			}
			
		}
		
		return Optional.empty();
	}
	
}
