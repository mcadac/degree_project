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

	
}
