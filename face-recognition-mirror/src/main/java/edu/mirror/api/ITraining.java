package edu.mirror.api;

/**
 * It has the methods that should be used to train the system
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public interface ITraining {

	/**
	 * Train the system with path established
	 * 
	 * @param path
	 * @return true if the training was successful
	 */
	boolean train(final String path);
	
}
