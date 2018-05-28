package edu.mirror.gui.client.api;

/**
 * Created by McadaC on 5/26/18.
 */
public interface RecommendationsService {

    /**
     * Gets recommendations by temperature
     *
     * @return String
     */
    String getRecommendationByTemp(float temp);

}
