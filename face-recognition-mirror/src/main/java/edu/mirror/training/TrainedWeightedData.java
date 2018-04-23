package edu.mirror.training;

import static edu.mirror.training.util.FaceRecognitionHelper.COMMA;
import static edu.mirror.training.util.FaceRecognitionHelper.LINE_BREAK;

import java.io.FileWriter;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * Used to save trained weighted data 
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 *
 */
public class TrainedWeightedData {
	
	/** Data types */
	private int types;
	
	/** Images size */
	private Size size;
	
	/** Images identifications*/
	private int[] ids;
	
	/** Data weights */
	private int[] weights;
	
	/** Images at {@link Mat} object */
	private Mat[] standardImages;
	
	/**
	 * Constructor to set types and image size
	 * 
	 * @param types int
	 * @param size {@link Size}
	 */
	public TrainedWeightedData(final int types, final Size size) {
		
		this.types = types;
		this.size = size;
		this.ids = new int[types];
		this.weights = new int[types];
		this.standardImages = new Mat[types];
		
		for (int i = 0; i < types; i++) {
			
			standardImages[i] = new Mat(size, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
			
		}
	}

	/**
	 * Default constructor
	 */
	public TrainedWeightedData() {
		this(0, new Size(90, 90));
	}

	/**
	 * Gets types
	 * 
	 * @return int
	 */
	public int getTypes() {
		return types;
	}

	/**
	 * Sets types
	 * 
	 * @param types int
	 */
	public void setTypes(int types) {
		this.types = types;
	}

	/**
	 * Gets image size
	 * 
	 * @return {@link Size}
	 */
	public Size getSize() {
		return size;
	}

	/**
	 * Sets Image size
	 * 
	 * @param size {@link Size}
	 */
	public void setSize(Size size) {
		this.size = size;
	}

	/**
	 * Gets images identifications
	 * 
	 * @return int arrays
	 */
	public int[] getIds() {
		return ids;
	}

	/**
	 * Sets images identifications
	 * 
	 * @param ids int arrays
	 */
	public void setIds(int[] ids) {
		this.ids = ids;
	}

	/**
	 * Gets images weights
	 * 
	 * @return int array
	 */
	public int[] getWeights() {
		return weights;
	}

	
	/**
	 * Sets images weights
	 * 
	 * @param weights int arrays
	 */
	public void setWeights(int[] weights) {
		this.weights = weights;
	}

	/**
	 * Gets images like {@link Mat} object
	 * @return {@link Mat} array
	 */
	public Mat[] getStandardImages() {
		return standardImages;
	}

	/**
	 * Sets images like {@link Mat} object
	 * 
	 * @param standardImages {@link Mat} arrays
	 */
	public void setStandardImages(Mat[] standardImages) {
		this.standardImages = standardImages;
	}
	
	/**
	 * Saves trained data to a file.
	 * 
	 * @param filePath {@link String}
	 * @throws IOException 
	 */
	public void saveTrainedData(final String filePath) throws IOException {

		FileWriter fileWriter = new FileWriter(filePath);
		fileWriter.write(generateTrainingData());
		fileWriter.close();

	}

	/**
	 * Generates training data.
	 */
	public String generateTrainingData() {
		
		int rows = (int) size.height, cols = (int) size.width;

		final StringBuilder content = createBasicContent(rows, cols);
		

		for (int i = 0; i < types; i++) {
			
			final StringBuilder imageType = new StringBuilder()
				.append("id:").append(ids[i]).append(LINE_BREAK)
				.append("weight:").append(weights[i]).append(LINE_BREAK)
				.append("image").append(LINE_BREAK);

			for (int row = 0; row < rows; row++) {
				
				final StringBuilder line = new StringBuilder();
				
				for (int col = 0; col < cols; col++) {
					
					line.append((int) standardImages[i].get(row, col)[0]).append(",");
					
				}
				
				imageType.append(line).append(LINE_BREAK);
				
			}

			content.append(imageType).append(LINE_BREAK);
		}

		return content.toString();
	}

	/**
	 * Creates the basic content to generate training data
	 * 
	 * @param rows int
	 * @param cols int
	 * @return {@link StringBuilder}
	 */
	private StringBuilder createBasicContent(final int rows, final int cols) {
		
		return new StringBuilder()
				.append("types:").append(types).append(LINE_BREAK)
				.append("size:").append(rows).append(COMMA).append(cols).append(LINE_BREAK)
				.append("data").append(LINE_BREAK);
		
	}
	
	/**
	 * Adds new id 
	 * 
	 * @param index int
	 * @param newId int
	 */
	public void addId(final int index, final int newId){
		ids[index] = newId;
	}

}
