package edu.mirror.gui.client;

import feign.Param;
import feign.RequestLine;

/**
 * Created by McadaC on 5/26/18.
 */
public interface RecommendationClient {


    /**
     * Gets recommensations from service
     *
     * @param temp
     * @return
     */
    @RequestLine("GET /temperature/{temp}")
    String getRecommendationByTemp(@Param("temp") float temp);

    /**
     * Gets recommendations by gender
     *
     * @param gender
     * @return
     */
    @RequestLine("GET /clothes/{gender}/{temp}")
    String getRecommendationByGender(@Param("gender") float gender, @Param("temp") final float temp);
}
