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
package org.gdimitriu.skynet.servers;

import org.gdimitriu.skynet.servers.restAPI.ServerJetty;

public class MainServer {

	public MainServer() {
	}
	
	public void startRestService() {
		ServerJetty jetty = new ServerJetty(8099);
//        jetty.addInitParameter("javax.ws.rs.Application","gaby.restAPI.json.AppConfig");
//        jetty.addInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "false");
		jetty.start();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainServer main = new MainServer();
		main.startRestService();

	}

}
