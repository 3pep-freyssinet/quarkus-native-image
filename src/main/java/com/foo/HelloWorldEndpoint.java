package com.foo;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import java.time.LocalDateTime;


@Path("/hello")
public class HelloWorldEndpoint {
   
    LocalDateTime dateTime;

    @GET
    @Path("/datetime")
    //@Produces(MediaType.TEXT_PLAIN)
    public String getTime() {
        return "hello the world, now it is : " + dateTime.now();
    }
}
