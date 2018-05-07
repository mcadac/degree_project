package edu.mirror.training;

import static edu.mirror.training.util.FaceRecognitionHelper.CLASSPATH;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import edu.mirror.api.ITraining;
import edu.mirror.face.detection.GenderType;

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
	@Value("${trained.data.path}")
	private String trainedDataPath;
	
	/** Resource loader*/
	@Autowired
	private ResourceLoader resourceLoader;
	
	/** Class to train the system */
	@Autowired
	private WeightedStandardPixelTrainer weightedStandardPixelTrainer;
	
	
	/*
	 * (non-Javadoc)
	 * @see edu.mirror.api.ITraining#train(java.lang.String)
	 */
	@Override
	public boolean train(final String path) throws IOException {
		
		final Pair<Integer[], String[]> filesAbsolutePaths = getFilesPath( getSubFolders(path) );
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
		final Resource folderResource = resourceLoader.getResource(CLASSPATH + path);
		
		LOGGER.info(folderResource.getFilename());
		final File dataFolder = folderResource.getFile();

		return dataFolder.listFiles((current, name) -> new File(current, name).isDirectory());
	}
	
	
	/**
	 * Gets files path to train system
	 * 
	 * @param subFolders {@link File} array
	 * @return {@link Map} with an id and Absolute path
	 */
	private Pair<Integer[], String[]> getFilesPath(final File[] subFolders){
		
		if (subFolders == null){
			throw new IllegalArgumentException("Training sub folder is null");
		}
		
		final ArrayList<String> filePathList = new ArrayList<>();
		final ArrayList<Integer> idList = new ArrayList<>();

		
		for (final File subfolder : subFolders) {
			
			final GenderType genderFolder = GenderType.valueOfFromString(subfolder.getName());
			
			if (genderFolder != null){
				
				LOGGER.info("File id : {} = {}", genderFolder.getId(), subfolder.getName());
				
				final File[] files = subfolder.listFiles();
				
				if (files == null) {
					continue;
				}
				
				for (final File file : files) {
					
					filePathList.add(file.getAbsolutePath());
					idList.add(genderFolder.getId());
				}
				
			}
			
		}

		String[] filePaths = new String[filePathList.size()];
		filePathList.toArray(filePaths);

		Integer[] ids = new Integer[idList.size()];
		idList.toArray(ids);
		
		return new ImmutablePair<Integer[], String[]>(ids, filePaths);
	}

}
