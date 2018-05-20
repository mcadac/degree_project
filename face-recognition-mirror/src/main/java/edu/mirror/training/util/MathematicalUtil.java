package edu.mirror.training.util;

import org.opencv.core.Mat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Mathematical util used to training an recognition process
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
public final class MathematicalUtil {
	
	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(MathematicalUtil.class);
	
	/**
	 * Private constructor
	 */
	private MathematicalUtil() {
		//Avoid create an object in memory
	}
	
	
	/**
	 * Do comparison between two {@link Mat} objects for get the similarity at pixel
	 * 
	 * @param mat1 {@link Mat}
	 * @param mat2 {@link Mat}
	 * @return similarity in percentage
	 */
	public static float similarityMatdiv(final Mat mat1, final Mat mat2) {
		
		if (mat1 != null && mat2 != null && !mat1.size().equals(mat2.size())) {
			
			LOGGER.warn("Please validate params supplied because those has a different size or are null");
			return -1;
		}

		final int rows = mat1.rows();
		final int cols = mat1.cols();

		float sumOfSimilarityByColInRow = 0;

		for (int row = 0; row < rows; row++) {
			
			float sumOfSimilarityByRow = 0;

			for (int col = 0; col < cols; col++) {
				
				final float pixel1 = (float) mat1.get(row, col)[0];
				final float pixel2 = (float) mat2.get(row, col)[0];

				float pixelSimilarity;

				if (pixel1 == pixel2) {
					
					pixelSimilarity = 100;
					
				} else if (pixel1 > pixel2) {
					
					pixelSimilarity = ((255 + pixel2) / (255 + pixel1)) * 100;
					
				} else {
					
					pixelSimilarity = (pixel1 / pixel2) * 100;
					
				}

				sumOfSimilarityByRow = sumOfSimilarityByRow + pixelSimilarity;
			}

			sumOfSimilarityByRow = sumOfSimilarityByRow / cols;
			
			sumOfSimilarityByColInRow = sumOfSimilarityByColInRow + sumOfSimilarityByRow;
		}


		return getSimilarity( sumOfSimilarityByColInRow / rows );

	}

	
	
	/**
	 * Do comparison between two {@link Mat} objects for get the similarity at pixel
	 * 
	 * @param mat1 {@link Mat}
	 * @param mat2 {@link Mat}
	 * @return  similarity in percentage
	 */
	public static float similarityMatDif(final Mat mat1, final Mat mat2) {
		
		if (mat1 != null && mat2 != null && !mat1.size().equals(mat2.size())) {
			LOGGER.warn("Please validate params supplied because those has a different size or are null");
			return -1;
		}

		final int rows = mat1.rows();
		final int cols = mat1.cols();

		float sumOfSimilarityByColInRow = 0;

		for (int row = 0; row < rows; row++) {
			
			float sumOfSimilarityByRow = 0;

			for (int col = 0; col < cols; col++) {
			
				final float pixel1 = (float) mat1.get(row, col)[0];
				final float pixel2 = (float) mat2.get(row, col)[0];

				float pixelSimilarity;
				
				if (pixel1 == pixel2) {
					
					pixelSimilarity = 100;
					
				} else if (pixel1 < pixel2) {
					
					pixelSimilarity = (255 - pixel2 + pixel1) / 255 * 100;
					
				} else {
					
					pixelSimilarity = (255 - pixel1 + pixel2) / 255 * 100;
					
				}

				sumOfSimilarityByRow = sumOfSimilarityByRow + pixelSimilarity;
			}

			sumOfSimilarityByRow = sumOfSimilarityByRow / cols;
			
			sumOfSimilarityByColInRow = sumOfSimilarityByColInRow + sumOfSimilarityByRow;
		}


		return getSimilarity( sumOfSimilarityByColInRow / rows);

	}

	
	

	/**
	 * Calculates to medial mat, return a new {@link Mat}
	 *  
	 * @param mat
	 * @return {@link Mat}
	 */
	public static Mat toMedialMat(final Mat mat) {
		
		final Mat mat2 = new Mat(mat.size(), mat.type());

		final int rows = mat.rows();
		final int cols = mat.cols();

		double sumOfPixelByColInRow = 0;
		
		//Toma columnas y suma pixeles
		for (int x = 0; x < rows; x++) {
			
			double sumOfPixelByRow = 0;

			for (int y = 0; y < cols; y++) {
				
				// Canal azul
				sumOfPixelByRow = sumOfPixelByRow + mat.get(x, y)[0];
			}

			sumOfPixelByRow = sumOfPixelByRow / cols;
			sumOfPixelByColInRow = sumOfPixelByColInRow + sumOfPixelByRow;
		}

		double mediumPixel = sumOfPixelByColInRow / rows;

		int perfectMediumPixel = 255 / 2;

		for (int x = 0; x < rows; x++) {
			
			for (int y = 0; y < cols; y++) {
				
				final double pixelValue = (int) mat.get(x, y)[0];
				double mediumValue = (int) (pixelValue * perfectMediumPixel / mediumPixel);

				if (mediumValue > 255) {
					
					mediumValue = 255;
				}

				mat2.put(x, y, mediumValue);
			}
		}

		return mat2;
	}
	
	
	/**
	 * Gets similarity percentage 
	 * 
	 * @param similarity
	 * @return
	 */
	private static float getSimilarity(final float similarity) {
	
		return similarity > 50 ? similarity - 50 : 50 - similarity;
	}


	

}
