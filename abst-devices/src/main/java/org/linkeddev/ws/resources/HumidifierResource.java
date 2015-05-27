package org.linkeddev.ws.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.alljoyn.bus.BusException;
import org.linkeddev.abstdev.client.HumidifierClient;
import org.linkeddev.ws.representations.AirConditioner;
import org.linkeddev.ws.representations.Humidifier;

@Path("humidifier")
@Produces(MediaType.APPLICATION_JSON)
public class HumidifierResource {
    private static HumidifierClient HC;
    
    public HumidifierResource() {
        this.HC = new HumidifierClient();
        HC.run();
    }
    
    @GET
    public Response getHumidifierRunningStatus() throws BusException {
        int onOff = HC.mHumidifier.getOnOff();
        int humidity = HC.mHumidifier.getHumidity();
        return Response.ok(new Humidifier(onOff,humidity)).build();
    }
    
    @POST
    public Response turnOn() throws BusException {
        HC.mHumidifier.turnOn();
        return Response.created(null).build();
    }
    
    @DELETE
    public Response turnOff() throws BusException {
        HC.mHumidifier.turnOff();
        return Response.noContent().build();
    }
    
    @PUT
    @Path("/set_humidity")
    public Response setHumidity(Humidifier humi) throws BusException {
        HC.mHumidifier.setHumidity(humi.getHumidity());
        return Response.ok().build();
    }
}
