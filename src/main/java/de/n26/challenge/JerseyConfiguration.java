package de.n26.challenge;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author felix
 */
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        packages("de.n26.challenge");
        register(JacksonFeature.class);
    }
    
}
