package edu.mirror.api;

/**
 * Face recognition service used to exposes its capabilities 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public interface IFaceRecognitionService {

	/**
	 * Enable camera 
	 */
	void enableCamera();
	
	/**
	 * Do face recognition using camera
	 */
	void doFaceRecognition();
	
	/**
	 * Disable camera
	 */
	void disableCamera();
	
	/**
	 * To train system to recognition gender
	 * 
	 * @param trainingFilesPath {@link String}
	 * @return true if the training process was successful
	 */
	boolean trainGender(String trainingFilesPath) throws Exception;

	
}
