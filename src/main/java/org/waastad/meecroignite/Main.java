/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.meecroignite;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.meecrowave.Meecrowave;

/**
 *
 * @author helge
 */
@Log4j2
public class Main {

    public static void main(String[] args) {
        log.info("STARTING APP....");
        log.info("Setting org.waastad trace level");
        Configurator.setLevel("org.waastad", Level.TRACE);
        new Meecrowave(new Meecrowave.Builder() {
            {
                setHttpPort(8080);
                setTomcatScanning(true);
                setTomcatAutoSetup(true);
                setScanningPackageExcludes("io.crate,com.sun.jna,org.apache.ignite");
            }
        })
                .bake()
                .await();
    }
}
