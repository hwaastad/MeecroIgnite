/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.meecroignite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.extern.log4j.Log4j2;
import org.primefaces.extensions.model.fluidgrid.FluidGridItem;
import org.waastad.meecroignite.qualifier.IgniteCache;

/**
 *
 * @author helge
 */
@ApplicationScoped
@Log4j2
@Path("/api")
public class RestResource {
    
    @Inject
    @IgniteCache(name = "xxx-cache")
    private Cache<String, Object> cache;
    
    @Inject
    @IgniteCache(name = "schema-cache")
    private Cache<String, Object> schemaCache;
    
    @POST
    public Response count(String name) {
        if (!cache.containsKey(name)) {
            log.info("Adding {} to cache", name);
            cache.put(name, UUID.randomUUID().toString());
        }
        return Response.ok(cache.get(name)).build();
    }
    
    @POST
    @Path("/schema")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchema(String name) {
        if (!schemaCache.containsKey(name)) {
            log.info("Adding {} to cache", name);
            FluidGridItem item = new FluidGridItem(UUID.randomUUID().toString());
            List<FluidGridItem> list = new ArrayList<>();
            list.add(item);
            schemaCache.put(name, list);
        }
        return Response.ok(schemaCache.get(name)).build();
    }
    
}
