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
import org.linkeddev.abstdev.client.AirConditionerClient;
import org.linkeddev.ws.representations.AirConditioner;

@Path("/air_conditioner")
@Produces(MediaType.APPLICATION_JSON)
public class AirConditionerResource {
	private static AirConditionerClient ACC;
	
	public AirConditionerResource() {
		this.ACC = new AirConditionerClient();
		ACC.run();
	}
	
	@GET
	public Response getAirConditionerRunningStatus() throws BusException {
		int onOff = ACC.mACInterface.getOnOff();
		int temp = ACC.mACInterface.getTemp();
		int windLevel = ACC.mACInterface.getWindLevel();
		String condition = ACC.mACInterface.getCondition();
		return Response.ok(new AirConditioner(onOff, temp, windLevel, condition)).build();
	}
	
	@POST
	public Response turnOn() throws BusException {
		ACC.mACInterface.turnOn();
		return Response.created(null).build();
	}
	
	@DELETE
	public Response turnOff() throws BusException {
		ACC.mACInterface.turnOff();
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/set_temp")
	public Response setTemp(AirConditioner ac) throws BusException {
		ACC.mACInterface.setTemp(ac.getTemp());
		return Response.ok().build();
	}
	
	@PUT
	@Path("/increase_wind")
	public Response increaseWind() throws BusException {
		ACC.mACInterface.increaseWind();
		return Response.ok().build();
	}
	
	@PUT
	@Path("/decrease_wind")
	public Response decreaseWind() throws BusException {
		ACC.mACInterface.decreaseWind();
		return Response.ok().build();
	}
	
	@PUT
	@Path("/cooling")
	public Response cooling() throws BusException {
		ACC.mACInterface.cooling();
		return Response.ok().build();
	}
	
	@PUT
	@Path("/heating")
	public Response heating() throws BusException {
		ACC.mACInterface.heating();
		return Response.ok().build();
	}
	
	@PUT
    @Path("/blowing")
    public Response blowing() throws BusException {
        ACC.mACInterface.blowing();
        return Response.ok().build();
    }
}
