package edu.mirror.training;

import static edu.mirror.training.util.MathematicalUtil.similarityMatDif;
import static edu.mirror.training.util.MathematicalUtil.toMedialMat;
import static edu.mirror.training.util.FaceRecognitionHelper.COMMA;
import static edu.mirror.training.util.FaceRecognitionHelper.LINE_BREAK;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.Validate;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;


/**
 * Weighted standard pixel trainer
 * 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 *
 */
public class WeightedStandardPixelTrainer {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(WeightedStandardPixelTrainer.class);
	
	/** Image size */
	private Size imageSize;
	
	/** Trained weighted data */
	private TrainedWeightedData trainedWeightedData;

	/**
	 * Default constructor. 
	 * Sets image size with default values 90 x 90
	 */
	public WeightedStandardPixelTrainer() {
		this(new Size(90, 90));
	}

	/**
	 * Constructor with a {@link Size} object.
	 * 
	 * @param imageSize
	 */
	public WeightedStandardPixelTrainer(final Size imageSize) {
		
		this.imageSize = imageSize;
		this.trainedWeightedData = new TrainedWeightedData();
	}
	
	/**
	 * Gets {@link TrainedWeightedData}
	 * 
	 * @return {@link TrainedWeightedData} object
	 */
	public TrainedWeightedData getTrainedWeightedData() {
		return trainedWeightedData;
	}

	/**
	 * Training model system
	 * 
	 * @param imageFilePaths
	 * @param ids
	 */
	public void train(final Map<Integer, String> imageFilesPath) {
		
		Validate.isTrue(CollectionUtils.isEmpty(imageFilesPath), "Images files path is empty");
		final Integer[] idsArray = (Integer[]) imageFilesPath.keySet().toArray();

		final int[] variety = appendVarietyOf(idsArray);
		final int types = variety[variety.length - 1];
		
		final int standardImageRow = (int) imageSize.width, standardImageCol = (int) imageSize.height;
		TrainedWeightedData trainedWeightedData = new TrainedWeightedData(types, imageSize);

		for (int i = 0; i < types; i++) {
			
			trainedWeightedData.addId(i, variety[i]);
			
		}

		int typeNo = 0; 
		int index = 0;
		
		for (final String imageFilePath : imageFilesPath.values()) {
			
			Mat mat = Imgcodecs.imread(imageFilePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			Imgproc.resize(mat, mat, imageSize);
			mat = toMedialMat(mat);

			for (int i = 0; i < types; i++) {
				
				if (trainedWeightedData.getId(i) == idsArray[index]) {
					typeNo = i;
					break;
				}
				
			}

			for (int row = 0; row < standardImageRow; row++) {
				
				for (int col = 0; col < standardImageCol; col++) {
					
					double sumValue = (trainedWeightedData.getStandardImage(typeNo, row, col) * trainedWeightedData.getWeight(typeNo)) + mat.get(row, col)[0];

					int value = (int) sumValue / (trainedWeightedData.getWeight(typeNo) + 1);

					trainedWeightedData.setStandardImage(typeNo, row, col, value);
				}
			}

			trainedWeightedData.incrementWeight(typeNo);

			index++;
		}

		this.trainedWeightedData = trainedWeightedData;
	}

	/**
	 * Append variety of images ids
	 * 
	 * @param imagesIds - {@link Set}
	 * @return Array with int values
	 */
	private int[] appendVarietyOf(final Integer[] imagesIds) {
		
		
		final int length = imagesIds.length;
		final int[] result = new int[imagesIds.length + 1];

		int variety = 0;

		for (int i = 0; i < length; i++) {
			
			result[i] = imagesIds[i];
			boolean flagMatched = false;

			for (int j = 0; j < variety; j++) {
				
				if (result[j] == result[i]) {
					
					flagMatched = true;
					break;
					
				}
			}

			if (!flagMatched) {
				
				result[variety] = result[i];
				variety++;
				
			}
		}

		result[length] = variety;

		return result;
	}

	
	
	/**
	 * Loads previously prepared training data.
	 * 
	 * @param filePath {@link String}
	 */
	public void loadTrainedData(final String filePath) {
		
		final String mainString = readFile(filePath);

		int startIndex; 
		int stopIndex;
		String key;

		// total image types
		key = "types:";
		startIndex = mainString.indexOf(key, 0);
		stopIndex = mainString.indexOf(LINE_BREAK, startIndex);
		final int types = Integer.parseInt(mainString.substring(startIndex + key.length(), stopIndex));

		// image size
		key = "size:";
		startIndex = mainString.indexOf(key, stopIndex);
		stopIndex = mainString.indexOf(COMMA, startIndex);
		final int width = Integer.parseInt(mainString.substring(startIndex + key.length(), stopIndex));

		startIndex = stopIndex + 1;
		stopIndex = mainString.indexOf(LINE_BREAK, startIndex);
		final int height = Integer.parseInt(mainString.substring(startIndex, stopIndex));

		imageSize = new Size(width, height);
		final TrainedWeightedData trainedWeightedData = new TrainedWeightedData(types, imageSize);

		stopIndex = mainString.indexOf("data\n", stopIndex);
		for (int i = 0; i < types; i++) {

			// image id
			key = "id:";
			startIndex = mainString.indexOf(key, stopIndex);
			stopIndex = mainString.indexOf(LINE_BREAK, startIndex);
			int id = Integer.parseInt(mainString.substring(startIndex + key.length(), stopIndex));
			trainedWeightedData.addId(i, id);

			// image weight
			key = "weight:";
			startIndex = mainString.indexOf(key, stopIndex);
			stopIndex = mainString.indexOf(LINE_BREAK, startIndex);
			int weight = Integer.parseInt(mainString.substring(startIndex + key.length(), stopIndex));
			trainedWeightedData.setWeight(i, weight);

			key = "image\n";
			startIndex = mainString.indexOf(key, stopIndex);
			stopIndex = startIndex + key.length();

			for (int row = 0, col; row < width; row++) {
				for (col = 0; col < height; col++) {
					// standard image data

					startIndex = stopIndex + 1;
					stopIndex = mainString.indexOf(COMMA, startIndex);

					int pixel = Integer.parseInt(mainString.substring(startIndex, stopIndex));
					trainedWeightedData.setStandardImage(i, row, col, pixel);
				}
				stopIndex++;
			}
		}

		this.trainedWeightedData = trainedWeightedData;
	}

	/**
	 * Reads file from file path supplied
	 * 
	 * @param filePath {@link String}
	 * @return {@link String} file as {@link String}
	 */
	private String readFile(String filePath) {
		
		final StringBuilder fileData = new StringBuilder();

		try ( final BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
			
			String tempString = bufferedReader.readLine();

			while (tempString != null) {
				
				fileData.append(tempString).append("\n");
				tempString = bufferedReader.readLine();
				
			}

		} catch (final Exception exception) {
			
			LOGGER.error("Exception reading trained file!");
		}

		return fileData.toString();
	}

	/**
	 * Returns predicted type on trained set, or -1 if not recognized.
	 */
	public int predict(Mat inputMat) {
		int id = -1;
		float similarity = 0;

		Imgproc.resize(inputMat, inputMat, imageSize);
		//inputMat = toMedialMat(inputMat);

		int types = trainedWeightedData.getTypes();

		for (int i = 0; i < types; i++) {
			float currentSimilarity =
				compareMatDiv(trainedWeightedData.getStandardImage(i), inputMat) +
				similarityMatDif(trainedWeightedData.getStandardImage(i), inputMat);

			if (currentSimilarity > similarity) {
				similarity = currentSimilarity;
				id = trainedWeightedData.getId(i);
			}
		}

		if (similarity < 20) {
			return -1;
		}

		return id;
	}



}
