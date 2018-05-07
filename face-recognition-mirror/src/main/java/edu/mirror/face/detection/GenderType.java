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
	WOMAN(0),
	
	/** The person is a man*/
	MAN(1);
	
	/** Gender type id*/
	private int id;
	
	/** Constructor */
	private GenderType(final int id) {
		this.id = id;
	}
	
	/**
	 * Gets a {@link GenderType} using its ordinal value
	 *  
	 * @param value int
	 * @return {@link Optional} with the object or empty
	 */
	public static Optional<GenderType> getGenderFromOrdinal(final int value){
		
		for(final GenderType genderType : GenderType.values()){
			
			if(genderType.getId() == value){
				return Optional.of(genderType);
			}
			
		}
		
		return Optional.empty();
	}
	
	/**
	 * Get gender
	 * 
	 * @param genderName
	 * @return null if not exist
	 */
	public static GenderType valueOfFromString(final String genderName){
		
		try{
			return genderName != null ? GenderType.valueOf(genderName.toUpperCase()) : null;
		} catch (final Exception e) {
			return null;
		}
	}
	
	/**
	 * Get gender type id
	 * 
	 * @return int
	 */
	public int getId() {
		return id;
	}

}
