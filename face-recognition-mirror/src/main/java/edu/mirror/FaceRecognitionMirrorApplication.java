package edu.mirror;

import org.apache.commons.lang3.Validate;
import org.opencv.core.Core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import edu.mirror.api.IFaceRecognitionService;

/**
 * Main class to start face recognition component
 * 
 * @author Camilo Espitia - dcespitiam@unipanamericana.edu.co
 * @version 1.0
 */
@Configuration
@ComponentScan(basePackages = "edu.mirror")
public class FaceRecognitionMirrorApplication implements CommandLineRunner {

	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(FaceRecognitionMirrorApplication.class);

	/** Training files path*/
	@Value("${training.files.path}")
	private String trainingFilesPath;
	
	/**
	 * Main service of the component. 
	 * This service is the input point to all the Face recognition mirror capabilities
	 */
	@Autowired
	private IFaceRecognitionService faceRecognitionService;
	
	/** Load opencv libraries */
	static{
		LOGGER.info("Lib path configurated: {}", System.getProperty("java.library.path"));
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	

	/**
	 * <ul>Main method used by Spring boot to start application.
	 * <li>This method load the opencv native library e.g *.dylib files</li>
	 * </ul>
	 * 
	 * @param args 
	 */
	public static void main(final String[] args) {
		SpringApplication.run(FaceRecognitionMirrorApplication.class, args);
	}

	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.boot.CommandLineRunner#run(java.lang.String[])
	 */
	@SuppressWarnings("static-access")
	@Override
	public void run(final String... args) throws Exception {
	
		try {
			
			final Thread currentThread = Thread.currentThread();
			
			faceRecognitionService.enableCamera();
			Validate.isTrue( faceRecognitionService.trainGender(trainingFilesPath), "Gender training was not successfully");
			
			while(true){
				faceRecognitionService.doFaceRecognition();
				currentThread.sleep(2000);
			}
			
		} catch (final Exception exception) {
			
			LOGGER.error("Exception : ", exception);
			
		} finally {
			
			faceRecognitionService.disableCamera();
			
		}		
	}
	
}
