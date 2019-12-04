/*
 Copyright (c) 2016 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of poc_skynet project.

 poc_skynet is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 poc_skynet is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with poc_skynet.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gdimitriu.skynet.servers.restAPI;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import java.util.Vector;


public class ServerJetty {
	private class ParameterHolder {
		private String parameter;
		private String value;
		public ParameterHolder(final String parameter, final String value) {
			this.parameter = parameter;
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public String getParameter() {
			return parameter;
		}
	};
	private int serverPort = 8099;
	private Vector<ParameterHolder> initParameters = new Vector<ParameterHolder>();

	public ServerJetty(final int port) {
		serverPort = port;
		initParameters.add(new ParameterHolder(
				"jersey.config.server.provider.packages","org.gdimitriu.skynet"));
		initParameters.add(new ParameterHolder("com.sun.jersey.config.property.packages", "rest"));
		//application configuration for multipart
		initParameters.add(new ParameterHolder("javax.ws.rs.Application",
				"org.gdimitriu.skynet.servers.restAPI.AppConfig"));
	}
	
	/**
	 * set the server port.
	 * @param port 
	 */
	public void setServerPort(final int port) {
		serverPort = port;
	}
	
	/**
	 * get the server port.
	 * @return port
	 */
	public int getServerPort() {
		return serverPort;
	}
	
	public void addInitParameter(final String key, final String value) {
		initParameters.add(new ParameterHolder(key, value));
	}
	
	public void start() {
		try {
			
			ServletHolder sh = new ServletHolder(ServletContainer.class);
			
			for (ParameterHolder param : initParameters) {
				sh.setInitParameter(param.getParameter(), param.getValue());
			}

	        final Server server = new Server(serverPort);	       
	         
	        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);

	        context.addServlet(sh, "/*");
			
			server.start();
//			server.join();
			System.out.println(String.format("Application started.%nHit enter to stop it..."));
			System.in.read();
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getLocalizedMessage());
		}
	}
}