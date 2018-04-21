package edu.mirror.api;

import java.util.Optional;

import org.opencv.core.Mat;

/**
 * Interface to defines the camera controller basic methods
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public interface ICameraController {

	/**
	 * Starts camera component
	 */
	void start();
	
	/**
	 * Stop camera componet
	 */
	void stop();
	
	/**
	 * Validates if camera is opened
	 * 
	 * @return true when the camera start was successful 
	 */
	boolean isOpened();

	/**
	 * Reads a frame from the video
	 *  
	 * @return {@link Optional} with object mat frame
	 */
	Optional<Mat> readFrame();
	
}
