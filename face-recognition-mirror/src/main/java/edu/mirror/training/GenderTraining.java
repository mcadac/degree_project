package edu.mirror.training;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import edu.mirror.api.ITraining;

/**
 * Class to train the system by gender
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@Component
public class GenderTraining implements ITraining {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(GenderTraining.class);
	
	/** Trained data path */
	@Value("{trained.data.path}")
	private String trainedDataPath;
	
	/** Resource loader*/
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ITraining#train(java.lang.String)
	 */
	@Override
	public boolean train(final String path) throws IOException {
		
		final Map<Integer, String> filesAbsolutePaths = getFilesPath( getSubFolders(path) );
		
		final WeightedStandardPixelTrainer weightedStandardPixelTrainer = new WeightedStandardPixelTrainer();
		weightedStandardPixelTrainer.train(filesAbsolutePaths);
		
		return weightedStandardPixelTrainer.saveTrainedData(trainedDataPath);
		
	}

	/**
	 * Gets sub folders where are the files to train the system
	 * 
	 * @param path 
	 * @return {@link File} array
	 * @throws IOException 
	 */
	private File[] getSubFolders(final String path) throws IOException {
		
		Validate.isTrue( StringUtils.isNotBlank(path), "The training path cannot be null");
		
		LOGGER.info(resourceLoader.getResource("classpath:" + path).getFilename());
		final File dataFolder = resourceLoader.getResource("classpath:" + path).getFile();

		return dataFolder.listFiles((current, name) -> new File(current, name).isDirectory());
	}
	
	
	/**
	 * Gets files path to train system
	 * 
	 * @param subFolders {@link File} array
	 * @return {@link Map} with an id and Absolute path
	 */
	private Map<Integer, String> getFilesPath(final File[] subFolders){
		
		if (subFolders == null){
			throw new IllegalArgumentException("Training sub folder is null");
		}
		
		final Map<Integer, String> filesAbsolutePaths = new HashMap<>();

		int fileId = 0;
		
		for (final File subfolder : subFolders) {
			
			LOGGER.info("File id : {} = {}", fileId, subfolder.getName());
			
			final File[] files = subfolder.listFiles();
			
			if (files == null) {
				continue;
			}
			
			for (final File file : files) {
				
				filesAbsolutePaths.put( Integer.valueOf(fileId), file.getAbsolutePath());
			}
	
			fileId ++;
		}
		
		return filesAbsolutePaths;
	}

}
