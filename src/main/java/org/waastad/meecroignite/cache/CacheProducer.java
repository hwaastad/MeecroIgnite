/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.meecroignite.cache;

import java.util.Arrays;
import javax.cache.Cache;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import lombok.extern.log4j.Log4j2;
import org.apache.ignite.Ignition;
import org.apache.ignite.binary.BinaryBasicIdMapper;
import org.apache.ignite.binary.BinaryBasicNameMapper;
import org.apache.ignite.binary.BinaryReflectiveSerializer;
import org.apache.ignite.binary.BinaryTypeConfiguration;
import org.apache.ignite.cache.eviction.lru.LruEvictionPolicy;
import org.apache.ignite.configuration.BinaryConfiguration;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.NearCacheConfiguration;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.vm.TcpDiscoveryVmIpFinder;
import org.waastad.meecroignite.qualifier.IgniteCache;

/**
 *
 * @author helge
 */
@Dependent
@Log4j2
public class CacheProducer {

    @Produces
    @IgniteCache
    public Cache<String, Object> getCache(InjectionPoint ip) {
        String cacheName = ip.getAnnotated().getAnnotation(IgniteCache.class).name();
        log.info("Producing cache {}..., loader: {}", cacheName,Thread.currentThread().getContextClassLoader());
        CacheConfiguration<String, Object> cfg = new CacheConfiguration<>();
        cfg.setName(cacheName);

        NearCacheConfiguration<String, Object> nearCacheConfiguration = new NearCacheConfiguration<>();
        nearCacheConfiguration.setNearEvictionPolicy(new LruEvictionPolicy<>(100));
        return Ignition.ignite().getOrCreateCache(cfg);
        //return Ignition.ignite().getOrCreateNearCache(cacheName, nearCacheConfiguration);
    }

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        log.info("Initializing cache.....loader: {}",Thread.currentThread().getContextClassLoader());
        Ignition.setClientMode(true);
        IgniteConfiguration ic = new IgniteConfiguration();
        ic.setPeerClassLoadingEnabled(true);
        ic.setClassLoader(Thread.currentThread().getContextClassLoader());
        TcpDiscoverySpi discoverySpi = new TcpDiscoverySpi();
        TcpDiscoveryVmIpFinder ipFinder = new TcpDiscoveryVmIpFinder();
        ipFinder.setAddresses(Arrays.asList("127.0.0.1"));
        discoverySpi.setIpFinder(ipFinder);
        ic.setDiscoverySpi(discoverySpi);
        Ignition.start(ic);
    }

    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        log.info("Destroying cache.....");
        Ignition.stopAll(true);
    }
}
