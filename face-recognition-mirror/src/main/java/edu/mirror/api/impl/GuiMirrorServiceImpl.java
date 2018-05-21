package edu.mirror.api.impl;

import edu.mirror.FaceRecognitionMirrorApplication;
import edu.mirror.api.GuiMirrorClient;
import edu.mirror.api.GuiMirrorService;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by McadaC on 5/20/18.
 */
@Service
public class GuiMirrorServiceImpl implements GuiMirrorService {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(GuiMirrorServiceImpl.class);

    /** Gui mirror client*/
    private GuiMirrorClient guiMirrorClient;

    /**
     * Constructor
     */
    public GuiMirrorServiceImpl(){

        guiMirrorClient = Feign.builder()
                .client(new OkHttpClient())
                .target(GuiMirrorClient.class, "http://localhost:8091");

    }



    /**
     * {@inheritDoc}
     */
    @Override
    public String notifyPersonRecognized(final String genderRecognized) {

        try {
            String response = guiMirrorClient.notifyRecognition(genderRecognized);
            LOGGER.info("Message Response : {}", response);
            return response;
        }catch (final Exception exception){

            LOGGER.error("Exception sending person recognition : {} ", exception);
            return StringUtils.EMPTY;
        }
    }
}
