package edu.mirror.gui.view.api.impl.manager;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import edu.mirror.gui.view.api.AbstractModuleManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by McadaC on 5/19/18.
 */
public class NewsModuleManager extends AbstractModuleManager{

    /** Logger  */
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsModuleManager.class);

    /** List of {@link SyndEntry} */
    private final List<SyndEntry> entries = new ArrayList<>();

    /** News pages with RSS*/
    private static final List<String> RSS_PAGES;
    static {
        RSS_PAGES = Arrays.asList("http://feeds.reuters.com/reuters/topNews",
                "http://feeds.reuters.com/reuters/technologyNews",
                "http://feeds.reuters.com/reuters/businessNews");

    }

    /**
     * Constructor
     *
     * @param initModules
     */
    public NewsModuleManager(int initModules) {
        super(initModules);
    }

    /**
     * Time in milliseconds it will take for this model to onUpdate it's data
     *
     * @return a time in milliseconds
     */
    @Override
    protected int getInterval() {
        return 1000 * 3600 * 24;
    }


    /**
     * Updates the data of this model
     * <p>
     * do not call this method directly instead call the update() method
     */
    @Override
    protected void onUpdate() {

        entries.clear();
        RSS_PAGES.forEach( page -> addFromRss(page));

    }

    /**
     * Add froms rss behaivor
     *
     * @param address
     */
    private void addFromRss(final String address) {

        try {

            final SyndFeed feed = new SyndFeedInput()
                    .build(new XmlReader((HttpURLConnection)new URL(address)
                    .openConnection()));

            entries.addAll(feed.getEntries());

        } catch (final Exception e) {
            LOGGER.error("Error creating RSS protocol",e);
        }


    }


    /**
     * Gets head lines
     *
     * @return
     */
    public String[] getHeadlines() {

        Set<String> set = new TreeSet<>();
        entries.forEach( syndEntry -> set.add(syndEntry.getTitle()));

        return set.toArray(new String[set.size()]);
    }}
