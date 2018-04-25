package edu.mirror.api.impl;

import java.util.Optional;

import org.apache.commons.lang3.Validate;
import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mirror.api.ICameraController;
import edu.mirror.api.IFaceRecognitionService;
import edu.mirror.api.ITraining;

/**
 * Implementation of {@link IFaceRecognitionService}
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@Service
public class FaceRecognitionService implements IFaceRecognitionService {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionService.class);
	
	/** Camera controller */
	@Autowired
	private ICameraController cameraController;
	
	/** Gender training */
	@Autowired
	private ITraining genderTraining;
	
	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.IFaceRecognitionService#enableCamera()
	 */
	@Override
	public void enableCamera() {
		
		cameraController.start();
		
	}

	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.IFaceRecognitionService#doFaceRecognition()
	 */
	@Override
	public void doFaceRecognition() {
		
		Validate.isTrue(cameraController.isOpened(), "The camera is not enable");
		final Optional<Mat> matFrameOptional = cameraController.readFrame();
		
		if(matFrameOptional.isPresent()){
			LOGGER.info("The frame reading was successful {}", matFrameOptional.get());
		}
		
	}

	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.IFaceRecognitionService#disableCamera()
	 */
	@Override
	public void disableCamera() {
		
		cameraController.stop();
		
	}

	
	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.IFaceRecognitionService#trainGender(java.lang.String)
	 */
	@Override
	public boolean trainGender(final String trainingFilesPath) {
		
		return genderTraining.train(trainingFilesPath);
		
	}

}
