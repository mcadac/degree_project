package edu.mirror.face.detection;

import org.opencv.objdetect.CascadeClassifier;

/**
 * Class to manage the face detector
 *  
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public class FaceDetector {
	
	/** {@link CascadeClassifier} */
	private CascadeClassifier faceCascadeClassifier;
	
	/** Flag to use LBP classifier */
	private boolean lbpClassifier = false;
	
	/** Flag to use HAAR classfier */
	private boolean haarClassifier = true;


}
