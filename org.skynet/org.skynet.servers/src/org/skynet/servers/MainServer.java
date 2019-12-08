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
package org.skynet.servers;

import java.io.IOException;

import org.skynet.servers.restAPI.ServerJetty;
import org.skynet.servers.rpc.ServerRpc;

public class MainServer {

	private ServerJetty jettyServer = null;
	private ServerRpc rpcServer = null; 
	/**
	 * 
	 */
	public MainServer() {
	}
	
	private void startRPCService() {
		rpcServer = new ServerRpc(9000);
		try {
			rpcServer.startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void waitRPCService() {
		try {
			rpcServer.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private void startRestService() {
		jettyServer = new ServerJetty(8099);
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
		MainServer main = new MainServer();
		main.startRPCService();
		main.startRestService();
		main.waitRPCService();
		
	}

}
