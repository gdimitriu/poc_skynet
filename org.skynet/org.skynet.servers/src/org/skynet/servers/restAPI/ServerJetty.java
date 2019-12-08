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
package org.skynet.servers.restAPI;

import java.util.Vector;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import org.skynet.servers.configuration.ParameterHolder;
import org.skynet.servers.interfaces.IServer;
import org.skynet.servers.interfaces.IServerConstants;


public class ServerJetty implements IServer{
	private Server server = null;
	private int serverPort = 8099;	
	private ServletContextHandler context = null;
	private ServletHolder sh = null;
	private Vector<ParameterHolder> initParameters = new Vector<ParameterHolder>();

	public ServerJetty(final int port) {
		serverPort = port;
		initParameters.add(new ParameterHolder(
				"jersey.config.server.provider.packages","org.skynet.upgrade.resources"));
		initParameters.add(new ParameterHolder("com.sun.jersey.config.property.packages", "rest"));
		//application configuration for multipart
		initParameters.add(new ParameterHolder("javax.ws.rs.Application",
				"org.skynet.servers.restAPI.AppConfig"));
	}
	
	public ServerJetty() {
	}
	
	/**
	 * set the server port.
	 * @param port 
	 */
	@Override
	public void setPort(final int port) {
		serverPort = port;
	}
	
	/**
	 * get the server port.
	 * @return port
	 */
	@Override
	public int getPort() {
		return serverPort;
	}
	
	@Override
	public void setParameter(final String key, final String value) {
		initParameters.add(new ParameterHolder(key, value));
	}
	
	@Override
	public void setParameter(final ParameterHolder parameter) {
		initParameters.add(parameter);
	}
	
	@Override
	public void stopServer() throws Exception {
		if(server != null) {
			server.stop();
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		stopServer();
	}
	
	
	
	@Override
	public void startServer() throws Exception {

		sh = new ServletHolder(ServletContainer.class);

		for (ParameterHolder param : initParameters) {
			sh.setInitParameter(param.getParameter(), param.getValue());
		}

		server = new Server(serverPort);

		context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);

		context.addServlet(sh, "/*");

		server.start();
		System.out.println(String.format("Application started.%nHit enter to stop it..."));
	}
	
	@Override
	public void join() throws InterruptedException {
		server.join();
	}

	@Override
	public String getServerKey() {
		return IServerConstants.JERSEY_SERVER_KEY;
	}
}