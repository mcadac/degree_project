package edu.mirror.face.detection;

/**
 * Information about person that was detected 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public class Person {

	/** Person's gender */
	private GenderType genderType;
	
	/** Confidence value */
	private double confidence;

	/**
	 * Default constructor
	 */
	public Person() {
		//Empty
	}

	/**
	 * Constructor with a {@link GenderType}
	 * 
	 * @param genderType
	 * @param confidence
	 */
	public Person(final GenderType genderType, final double confidence) {
		
		this.genderType = genderType; 
		this.confidence = confidence;
	}
	
	
	/**
	 * Gets gender type
	 * 
	 * @return {@link GenderType}
	 */
	public GenderType getGenderType() {
		return genderType;
	}

	/**
	 * Sets gender type
	 * 
	 * @param genderType
	 */
	public void setGenderType(final GenderType genderType) {
		this.genderType = genderType;
	}

	/**
	 * Gets confidence values
	 * 
	 * @return double
	 */
	public double getConfidence() {
		return confidence;
	}

	/**
	 * Sets confidence values 
	 * 
	 * @param confidence
	 */
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
}
