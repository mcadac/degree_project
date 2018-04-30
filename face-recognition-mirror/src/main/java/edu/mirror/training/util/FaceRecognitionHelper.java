package edu.mirror.training.util;

import org.springframework.core.io.Resource;

/**
 * Helper class with constants and common methods
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public final class FaceRecognitionHelper {

	/** Line break constant*/
	public static final String LINE_BREAK = "\n";
	
	/** Comma constant */
	public static final String COMMA = ",";
	
	/** Classpath to be used by {@link Resource}*/
	public static final String CLASSPATH = "classpath:";
	
	/**
	 * Private constructor for not create an object
	 */
	private FaceRecognitionHelper(){
		//Private constructor
	}
	
}
