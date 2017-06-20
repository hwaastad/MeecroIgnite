/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.meecroignite;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.feature.LoggingFeature;
import org.apache.meecrowave.Meecrowave;
import org.junit.Test;

/**
 *
 * @author helge
 */
public class MainIT {

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testApi() {
        System.out.println("api");
        try (final Meecrowave meecrowave = new Meecrowave(new Meecrowave.Builder().randomHttpPort()).bake()) {
            String base = String.format("http://%s:%d", meecrowave.getConfiguration().getHost(), meecrowave.getConfiguration().getHttpPort());
            WebTarget target = ClientBuilder.newBuilder().build().register(LoggingFeature.class).target(base);
            Invocation.Builder inv = target.path("/api").request().accept(MediaType.APPLICATION_JSON_TYPE);
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
        }
    }
    
     @Test
    public void testAchema() {
        System.out.println("schema");
        try (final Meecrowave meecrowave = new Meecrowave(new Meecrowave.Builder().randomHttpPort()).bake()) {
            String base = String.format("http://%s:%d", meecrowave.getConfiguration().getHost(), meecrowave.getConfiguration().getHttpPort());
            WebTarget target = ClientBuilder.newBuilder().build().register(LoggingFeature.class).target(base);

            Invocation.Builder inv = target.path("/api/schema").request().accept(MediaType.APPLICATION_JSON_TYPE);
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
            inv.post(Entity.entity("xxx", MediaType.TEXT_PLAIN));
        }
    }

}
