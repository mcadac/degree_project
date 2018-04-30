package edu.mirror.face.detection;

import static edu.mirror.training.util.FaceRecognitionHelper.CLASSPATH;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import edu.mirror.training.GenderTraining;

/**
 * Class to manage the face detector
 *  
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@Component
public class FaceDetector {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenderTraining.class);
	
	/** {@link CascadeClassifier} */
	private CascadeClassifier faceCascadeClassifier;
	
	/**
	 * Constructor with {@link ResourceLoader} to get the frontal face detection file
	 * 
	 * @param resourceLoader
	 * @throws IOException 
	 */
	@Autowired
	public FaceDetector(final ResourceLoader resourceLoader, @Value("${frontal.face.detection.file}") final String frontalFaceDetectionFile) throws IOException {
		
		final Resource folderResource = resourceLoader.getResource(CLASSPATH + frontalFaceDetectionFile);
		final String haarCascadeFrontalFacePath = folderResource.getFile().getAbsolutePath();
		LOGGER.info("Frontal face detection file : {}", haarCascadeFrontalFacePath);
		
		faceCascadeClassifier = new CascadeClassifier();
		faceCascadeClassifier.load(haarCascadeFrontalFacePath);
	}
	
	

	/**
	 * Detects faces on the input and returns a list of rectangulars around each detected face.
	 * 
	 * @param frame
	 * @return
	 */
	public List<Rect> detectFace(final Mat frame) {
		Mat grayFrame = new Mat();

		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);

		// equalize the frame histogram to improve the result
		Imgproc.equalizeHist(grayFrame, grayFrame);

		// compute minimum face size (20% of the frame height, in our case)

		int minSize = 0;
		int height = grayFrame.rows();

		if (Math.round(height * 0.2f) > 0) {
			minSize = Math.round(height * 0.2f);
		}

		final MatOfRect detectedFacesRectangulars = new MatOfRect();

		this.faceCascadeClassifier.detectMultiScale(grayFrame,
			detectedFacesRectangulars,
			1.1,
			1,
			Objdetect.CASCADE_DO_CANNY_PRUNING,
			new Size(minSize, minSize),
			grayFrame.size());

		return detectedFacesRectangulars.toList();
	}
	


}
