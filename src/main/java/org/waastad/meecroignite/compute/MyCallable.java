/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.waastad.meecroignite.compute;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ignite.lang.IgniteCallable;

/**
 *
 * @author helge
 */
//@Log4j2
public class MyCallable implements IgniteCallable<Integer> {

    private static final long serialVersionUID = 3990951433563884942L;

    @Override
    public Integer call() throws Exception {
        System.out.println("Running job on thread: "+Thread.currentThread().getName());
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://www.vg.no");
        CloseableHttpResponse response1 = client.execute(get);
        Integer res = 0;
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            String toString = EntityUtils.toString(entity1);
            res = toString.length();
            System.out.println("Size is: " + res);
            EntityUtils.consume(entity1);
            Thread.sleep(3000);
        } finally {
            response1.close();
        }
        return res;
    }

}
