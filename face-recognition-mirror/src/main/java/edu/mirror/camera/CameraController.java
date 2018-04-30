package edu.mirror.camera;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import edu.mirror.api.ICameraController;

/**
 * Camera controller
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@Component
public class CameraController implements ICameraController {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(CameraController.class);
	
	/** Default index camera for opencv native libraries */
	private static final int DEFAULT_CAMERA = 0;
	
	/** Opencv video capture used to start and manage camera*/
	private VideoCapture videoCapture;
	

	/**
	 * Default constructor
	 */
	public CameraController() {
		videoCapture = new VideoCapture();
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ICameraController#start()
	 */
	@Override
	public void start() {
		
		videoCapture.open(DEFAULT_CAMERA);
		Validate.isTrue( isOpened(), "The camera cannot be opened");
		LOGGER.info("Camera started!");
		
	}
	
	

	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ICameraController#stop()
	 */
	@Override
	public void stop() {
		
		if(isOpened()){
			videoCapture.release();
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ICameraController#getFrame()
	 */
	@Override
	public Optional<Mat> readFrame() {

		Validate.isTrue(isOpened(), "The camera is not opened");

		final Mat matFrame = new Mat();
		videoCapture.read(matFrame);

		return Optional.of(matFrame);
	}


	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ICameraController#isOpened()
	 */
	@Override
	public boolean isOpened() {
		
		return videoCapture != null && videoCapture.isOpened();
	}

	
	
}
