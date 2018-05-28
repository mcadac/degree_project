package edu.mirror.gui.client.api.impl;

import edu.mirror.gui.client.RecommendationClient;
import edu.mirror.gui.client.api.RecommendationsService;
import feign.Feign;
import feign.okhttp.OkHttpClient;
import org.springframework.stereotype.Service;

/**
 * Created by McadaC on 5/26/18.
 */
@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    /** Recommendation client */
    private RecommendationClient recommendationClient;

    /**
     * Constructor
     */
    public RecommendationsServiceImpl(){

        recommendationClient = Feign.builder()
                .client(new OkHttpClient())
                .target(RecommendationClient.class, "https://recommendations-mirror.herokuapp.com");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRecommendationByTemp(final float temp) {

        return recommendationClient.getRecommendationByTemp(temp);
    }
}
