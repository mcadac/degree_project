package edu.mirror.face.recognition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.Size;
import org.opencv.face.FaceRecognizer;
import org.opencv.face.LBPHFaceRecognizer;
import org.opencv.imgproc.Imgproc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.mirror.face.detection.GenderType;
import edu.mirror.face.detection.Person;
import edu.mirror.training.WeightedStandardPixelTrainer;

/**
 * Face Recognition 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 *
 */
@Component
public class FaceRecognition {

	/** Train face image size */
	public static Size TRAIN_FACE_IMAGE_SIZE = new Size(160, 160);
	
	/** Trained data path */
	@Value("${trained.data.path}")
	private String trainedDataPath;
	
	/** Class to train the system */
	@Autowired
	private WeightedStandardPixelTrainer weightedStandardPixelTrainer;
	
	/** Face Recognizer from OpenCV */
	private FaceRecognizer faceRecognizer;
	
	/**
	 * Default constructor
	 */
	public FaceRecognition() {
		faceRecognizer = LBPHFaceRecognizer.create();
	}
	
	
	/**
	 * Recognizes user based on his detected face. Also performs gender detection.
	 */
	public Optional<Person> matchFace(Mat face) {
		
		Imgproc.cvtColor(face, face, Imgproc.COLOR_BGR2GRAY);
		Imgproc.resize(face, face, TRAIN_FACE_IMAGE_SIZE);

		int[] label = {0};
		double[] confidence = {0};

		//faceRecognizer.predict(face, label, confidence);
		
		final Optional<GenderType> genderOptional = GenderType.getGenderFromOrdinal( weightedStandardPixelTrainer.predict(face) );
		
		if (genderOptional.isPresent() && confidence[0] < 100){
			
			return Optional.of(new Person(genderOptional.get(), confidence[0]));
			
		}

		return Optional.empty();
	}
	
	
	public void train() {
		List<Person> users = Collections.emptyList();

		System.out.println("Training face recognizer...");

		// experience file
		weightedStandardPixelTrainer.loadTrainedData(trainedDataPath);


		List<Mat> faces = new ArrayList<>();
		List<Integer> labels = new ArrayList<>();

//		users.forEach(user -> {
//			System.out.println("\t" + user.name() + " " + user.id());
//
//			user.
//				listFaceFiles()
//				.stream()
//				.peek(file -> System.out.println(file.getName()))
//				.map(faceUserFile ->
//					Imgcodecs.imread(faceUserFile.getAbsolutePath(), Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE))
//				.peek(face ->
//					Imgproc.resize(face, face, TRAIN_FACE_IMAGE_SIZE)
//				)
//				.forEach(userFaceMat -> {
//					faces.add(userFaceMat);
//					labels.add(user.id());
//				});
//		});

		MatOfInt allLabels = new MatOfInt(new int[labels.size()]);
		for (int i = 0; i < labels.size(); i++) {
			allLabels.put(i, 0, labels.get(i));
		}
		//faceRecognizer.train(faces, allLabels);

		System.out.println("Training complete.");
	}
	
	
}
