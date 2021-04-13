package com.github.catapan.ifood.order;

import java.time.LocalDateTime;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

@Path("/log")
public class LogResource {

    private static final Logger LOG = Logger.getLogger(LogResource.class);

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        LOG.info("Olá Quarkus");
        LOG.infov("Olá Quarkus {0} ", LocalDateTime.now());
        return "hello log";
    }
}
