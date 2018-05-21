package edu.mirror.api.impl;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;

import edu.mirror.api.GuiMirrorService;
import org.apache.commons.lang3.Validate;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.mirror.api.ICameraController;
import edu.mirror.api.IFaceRecognitionService;
import edu.mirror.api.ITraining;
import edu.mirror.face.detection.FaceDetector;
import edu.mirror.face.detection.Person;
import edu.mirror.face.recognition.FaceRecognition;

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
	
	/** Face detector */
	@Autowired
	private FaceDetector faceDetector;
	
	/** Face recognition */
	@Autowired
	private FaceRecognition faceRecognition;

	/** GuiMirror service*/
	@Autowired
	private GuiMirrorService guiMirrorService;
	 
	
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

		if (matFrameOptional.isPresent()) {

			final Mat matFrame = matFrameOptional.get();
			doFaceDetection(matFrame);

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
	public boolean trainGender(final String trainingFilesPath) throws Exception {
		
		LOGGER.info("Traigning with files from : {}", trainingFilesPath);
		final boolean isTrained =  genderTraining.train(trainingFilesPath);
		faceRecognition.train();
		return isTrained;
		
	}

	
	private void doFaceDetection(final Mat frame) {

		List<Rect> faces = faceDetector.detectFace(frame);

		faces.forEach(face -> {

			final Rect cropFace = new Rect(face.x, face.y, face.width, face.height);
			final Mat cropedFace = new Mat(frame, cropFace);
			final Optional<Person> optional = faceRecognition.matchFace(cropedFace);

			if (optional.isPresent()) {

				final Person personRecognized = optional.get();
				LOGGER.info("Person recognized : {}", personRecognized.getGenderType());
				guiMirrorService.notifyPersonRecognized(personRecognized.getGenderType().name());

			} else {
				LOGGER.warn("Person not recognized");
			}

		});

	}
}
