package edu.mirror.gui.view.api.impl.manager;

import edu.mirror.gui.config.Icons;
import edu.mirror.gui.view.api.AbstractModuleManager;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.HourlyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;

import java.util.Date;

import static edu.mirror.gui.config.Constant.EMPTY;

/**
 * Created by McadaC on 5/19/18.
 */
public class WeatherModuleManager extends AbstractModuleManager {

    /** Api key to weather service */
    private static final String API_KEY = "607ce4032bdb7c7d41ceb85dc820725f";

    /** LONGITUD used to ubication  */
    private static final float LONGITUD =  -74.0817500f;

    /** LATITUD used to ubication */
    private static final float LATITUD = 4.6097100F;

    /** Day */
    private static final String DAY = "day_";

    /** Night */
    private static final String NIGHT = "night_";

    /** Current weather */
    private CurrentWeather currentWeather;

    /** Daily forecast */
    private DailyForecast dailyForecast;

    /** Hourly forecast */
    private HourlyForecast hourlyForecast;

    /**
     * Constructor
     *
     * @param initModules
     */
    public WeatherModuleManager(int initModules) {
        super(initModules);
    }


    /**
     * Time in milliseconds it will take for this model to onUpdate it's data
     *
     * @return a time in milliseconds
     */
    @Override
    protected int getInterval() {
        return 1000 * 1800;
    }

    /**
     * Updates the data of this model
     */
    @Override
    protected void onUpdate() {

        final OpenWeatherMap clientService = new OpenWeatherMap(OpenWeatherMap.Units.METRIC, API_KEY);

        this.currentWeather = clientService.currentWeatherByCoordinates(LATITUD, LONGITUD);
        this.dailyForecast = clientService.dailyForecastByCoordinates(LATITUD, LONGITUD, (byte) 5);
        this.hourlyForecast = clientService.hourlyForecastByCoordinates(LATITUD, LONGITUD);

        invalidate();
    }

    /**
     * the currentWeather weather code
     *
     * @return
     */
    public String getCurrentCode() {
        return iconFromCode(currentWeather.getWeatherInstance(0).getWeatherCode());
    }

    /**
     * the currentWeather temperature
     *
     * @return
     */
    public float getCurrentTemp() {
        return currentWeather.getMainInstance().getTemperature();
    }

    /**
     * the weather codes for the dailyForecast forecast
     *
     * @return
     */
    public String[] getDailyCodes() {

        final String[] res = new String[dailyForecast.getForecastCount()];

        for (int i = 0; i < res.length; i++) {

            res[i] = new StringBuilder(EMPTY)
                    .append( iconFromCode(dailyForecast.getForecastInstance(i).getWeatherInstance(0).getWeatherCode(), true))
                    .toString();
        }
        return res;
    }

    /**
     * The temperature for the dailyForecast forecast
     *
     * @return
     */
    public float[] getDailyTemps() {

        float[] res = new float[dailyForecast.getForecastCount()];

        for (int i = 0; i < res.length; i++) {
            res[i] = dailyForecast.getForecastInstance(i).getTemperatureInstance().getDayTemperature();
        }
        return res;
    }

    /**
     * the weather codes for the hourlyForecast forecast
     *
     * @return
     */
    public String[] getHourlyCodes() {

        final Date[] times = getHourlyTimes();
        final Date rise = getSunRise();
        final Date set = getSunSet();

        final String[] res = new String[hourlyForecast.getForecastCount()];
        for (int i = 0; i < res.length; i++) {

            res[i] = new StringBuilder(EMPTY)
                    .append(iconFromCode(hourlyForecast.getForecastInstance(i).getWeatherInstance(0).getWeatherCode(), times[i].after(rise) && times[i].before(set)))
                    .toString();
        }
        return res;
    }

    /**
     * the temperature for the hourlyForecast forecast
     *
     * @return
     */
    public float[] getHourlyTemps() {

        final float[] res = new float[hourlyForecast.getForecastCount()];

        for (int i = 0; i < res.length; i++) {

            res[i] = hourlyForecast.getForecastInstance(i).getMainInstance().getTemperature();
        }

        return res;
    }

    /**
     * the times for each weather instance for the dailyForecast forecast
     *
     * @return
     */
    public Date[] getDailyTimes() {

        final Date[] res = new Date[dailyForecast.getForecastCount()];

        for (int i = 0; i < res.length; i++) {

            res[i] = dailyForecast.getForecastInstance(i).getDateTime();

        }
        return res;
    }

    /**
     * the times for each weather instance for the hourlyForecast forecast
     *
     * @return
     */
    public Date[] getHourlyTimes() {

        final Date[] res = new Date[hourlyForecast.getForecastCount()];

        for (int i = 0; i < res.length; i++) {

            res[i] = hourlyForecast.getForecastInstance(i).getDateTime();

        }
        return res;
    }

    /**
     * the time of the sun rise
     *
     * @return
     */
    public Date getSunRise() {
        return currentWeather.getSysInstance().getSunriseTime();
    }

    /**
     * the time of the sun set
     *
     * @return
     */
    public Date getSunSet() {
        return currentWeather.getSysInstance().getSunsetTime();
    }

    /**
     * Loads icon from code
     *
     * @param code
     * @param day
     * @return
     */
    private String iconFromCode(int code, boolean day) {

        return Icons.Weather.getIcon((day ? DAY : NIGHT) + code);
    }

    /**
     * Loads icon from code
     * @param code
     * @return
     */
    private String iconFromCode(int code) {

        return Icons.Weather.getIcon((isDay() ? DAY : NIGHT) + code);
    }

    /**
     * Validates if is day
     *
     * @return true is default value
     */
    private boolean isDay() {

        try {

            final Date curr = new Date();
            return curr.before( getSunSet()) && curr.after( getSunRise());

        } catch (Exception e) {

            return true;
        }
    }


}
