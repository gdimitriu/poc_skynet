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

import org.skynet.servers.interfaces.IServer;
import org.skynet.servers.restAPI.ServerJetty;
import org.skynet.servers.rpc.ServerRpc;
import org.skynet.services.SingletonServices;
import org.skynet.services.interfaces.IRunnableService;
import org.skynet.services.interfaces.IServicesType;

import xml.Skynet;

public class MainServers {

	private IServer jettyServer = null;
	private IServer rpcServer = null; 
	public MainServers() {
	}
	
	private void startRPCService() {
		rpcServer = new ServerRpc();
		rpcServer.setPort(9000);
		try {
			rpcServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void waitRPCService() {
		try {
			rpcServer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			jettyServer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void startRestService() {
		jettyServer = new ServerJetty();
		jettyServer.setPort(8099);
		jettyServer.setParameter("jersey.config.server.provider.packages","org.skynet.upgrade.resources");
		jettyServer.setParameter("com.sun.jersey.config.property.packages", "rest");
		//application configuration for multipart
		jettyServer.setParameter("javax.ws.rs.Application","org.skynet.servers.restAPI.AppConfig");
//        jettyServer.addInitParameter("javax.ws.rs.Application","gaby.restAPI.json.AppConfig");
//        jettyServer.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "false");
		try {
			jettyServer.startServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainServers main = new MainServers();
		IServicesType service = SingletonServices.getSingleton().getServiceType("org.skynet.services.interfaces.IRunnableService");
		service.putService("Cyberdine", new Skynet());
		((IRunnableService) service.getService("Cyberdine")).start();
		main.startRPCService();
		main.startRestService();
		main.waitRPCService();
	}

}
