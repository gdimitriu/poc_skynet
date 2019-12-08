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

import org.skynet.servers.configuration.ServerBasicConfiguration;
import org.skynet.servers.configuration.SingletonConfigurationServersProvider;
import org.skynet.servers.exceptions.StartStopAllServersException;
import org.skynet.servers.interfaces.IServer;
import org.skynet.servers.providers.RegisteredServersProvider;


public class SingletonServersProvider {

	private static SingletonServersProvider singleton = new SingletonServersProvider();
	private RegisteredServersProvider registerServer = null;
	/**
	 * 
	 */
	private SingletonServersProvider() {
		registerServer = new RegisteredServersProvider();
	}

	/**
	 * get the singleton provider.
	 * @return singleton server provider
	 */
	public static SingletonServersProvider getSingleton() {
		return singleton;
	}
	
	public void startAllServers() throws StartStopAllServersException{
		for(Class<?> def : registerServer.getAllServerDefinition()) {
			try {
				startServer(def);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
	}
	
	private void startServer(Class<?> def) throws Throwable {
		IServer server = registerServer.getRegisterServer(def);
		if (server == null) {
			server = (IServer) def.newInstance();
			registerServer.putRegisterServer(def, server);
			configureServer(server);
			server.startServer();
		}
		
	}

	private void configureServer(IServer server) {
		ServerBasicConfiguration config = SingletonConfigurationServersProvider.getSingleton().getConfiguration(server.getServerKey());
		server.configure(config);
	}

	public void stopAllServers() throws StartStopAllServersException {
		for(Class<?> def : registerServer.getAllServerDefinition()) {
			try {
				stopServer(def);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void stopServer(Class<?> def) throws Exception {
		registerServer.getRegisterServer(def).stopServer();
	}	
}
