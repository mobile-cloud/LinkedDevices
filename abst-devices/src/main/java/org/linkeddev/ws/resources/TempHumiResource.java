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
import org.linkeddev.ws.representations.TempHumi;
import org.linkeddev.abstdev.client.TempAndRHClient;

@Path("/temp_humi")
@Produces(MediaType.APPLICATION_JSON)
public class TempHumiResource {
	private static TempAndRHClient TEMP_HUMI;
	
	public TempHumiResource() {
		this.TEMP_HUMI = new TempAndRHClient();
		TEMP_HUMI.run();
	}
	
	@GET
	public Response getTempHumi() throws BusException{
		TempHumi th = new TempHumi(TEMP_HUMI.mTempAndRHInterface.getTemp(),TEMP_HUMI.mTempAndRHInterface.getRH());
		return Response.ok(th).build();
	}
}
