/*
 Copyright (c) 2017 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of skynet project.

 skynet is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 skynet is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with skynet.  If not, see <http://www.gnu.org/licenses/>.
 */
package servers;

import org.skynet.servers.SingletonServersProvider;
import org.skynet.servers.configuration.ParameterHolder;
import org.skynet.servers.configuration.ServerBasicConfiguration;
import org.skynet.servers.configuration.SingletonConfigurationServersProvider;
import org.skynet.servers.exceptions.StartStopAllServersException;
import org.skynet.servers.interfaces.IServerConstants;
import org.skynet.services.SingletonServices;
import org.skynet.services.interfaces.IRunnableService;
import org.skynet.services.interfaces.IServicesType;

import xml.Skynet;

public class MainServersProviders {

	public MainServersProviders() {
	}
	
	private void configureServers() {
		ServerBasicConfiguration config = SingletonConfigurationServersProvider.getSingleton()
				.createConfiguration(IServerConstants.JERSEY_SERVER_KEY);
		config.setPort(8099);
		config.addParameter(new ParameterHolder("jersey.config.server.provider.packages","org.skynet.upgrade.resources"));
		config.addParameter(new ParameterHolder("jersey.config.server.provider.packages","org.skynet.services.processing.resources"));
		config.addParameter(new ParameterHolder("com.sun.jersey.config.property.packages", "rest"));
		//application configuration for multipart
		config.addParameter(new ParameterHolder("javax.ws.rs.Application","org.skynet.servers.restAPI.AppConfig"));
//        jettyServer.addInitParameter("javax.ws.rs.Application","gaby.restAPI.json.AppConfig");
//        jettyServer.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "false");
		config = SingletonConfigurationServersProvider.getSingleton()
				.createConfiguration(IServerConstants.RPC_SERVER_KEY);
		config.setPort(9000);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainServersProviders main = new MainServersProviders();
		main.configureServers();
		try {
			SingletonServersProvider.getSingleton().startAllServers();
		} catch (StartStopAllServersException e) {
			e.printStackTrace();
		}
		IServicesType service = SingletonServices.getSingleton().getServiceType("org.skynet.services.interfaces.IRunnableService");
		service.putService("Cyberdine", new Skynet());
		((IRunnableService) service.getService("Cyberdine")).start();
	}

}
