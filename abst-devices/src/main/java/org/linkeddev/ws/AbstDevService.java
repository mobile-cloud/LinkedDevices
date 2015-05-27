package org.linkeddev.ws;

import org.linkeddev.ws.resources.AirConditionerResource;
import org.linkeddev.ws.resources.HumidifierResource;
import org.linkeddev.ws.resources.TempHumiResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class AbstDevService extends Application<Configuration> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstDevService.class);
	
	@Override
	public void initialize(Bootstrap<Configuration> b){
	}
	
	@Override
	public void run(Configuration c, Environment e) throws Exception {
		LOGGER.info("AbstDevService#run() called");
		//e.jersey().register(new TempHumiResource());
		e.jersey().register(new AirConditionerResource());
		//e.jersey().register(new HumidifierResource());
	}
	
	public static void main(String[] args) throws Exception {
		new AbstDevService().run(args);
	}
}
