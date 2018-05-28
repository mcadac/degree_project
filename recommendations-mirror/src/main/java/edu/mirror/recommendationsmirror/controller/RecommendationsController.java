package edu.mirror.recommendationsmirror.controller;

import edu.mirror.recommendationsmirror.temperature.TemperatureRank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Recommendations controller
 *
 * @author dcespitiam@unipanamericana.edu.co
 */
@RestController
public class RecommendationsController {

    /** Logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendationsController.class);

    /**
     * Validates status
     * @return
     */
    @RequestMapping("/status")
    public String status(){
        return "ok";
    }


    /**
     * Gets clothes recommendations
     *
     * @param gender
     * @param temperature
     * @return
     */
    @RequestMapping("/clothes/{gender}/{temp}")
    public String recommendationsClothes(@PathVariable("gender") String gender, @PathVariable("temp") float temperature){

        LOGGER.info("Gender : {}, Temp : {}", gender, temperature);
        return TemperatureRank.getMessage(temperature);
    }

    /**
     * Recomendations using temperature
     *
     * @param temperature
     * @return
     */
    @RequestMapping("/temperature/{temp}")
    public String recommendationsTemp(@PathVariable("temp") float temperature){

        LOGGER.info("Temp : {}", temperature);
        return TemperatureRank.getMessage(temperature);

    }
}
