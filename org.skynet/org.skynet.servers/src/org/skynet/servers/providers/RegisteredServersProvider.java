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
package org.skynet.servers.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.skynet.servers.interfaces.IServer;
import org.skynet.servers.restAPI.ServerJetty;
import org.skynet.servers.rpc.ServerRpc;

public class RegisteredServersProvider {

	/** class definitions for servers */
	private List<Class<?>> serversClasses = null;
	/** instantiated servers */
	private Map<Class<?>, IServer> servers = null;
	/**
	 * 
	 */
	public RegisteredServersProvider() {
		serversClasses = new ArrayList<Class<?>>();
		servers = new HashMap<Class<?>, IServer>();
		defaultServerRegister();
	}
	
	/**
	 * register the default servers.
	 */
	private void defaultServerRegister() {
		serversClasses.add(ServerJetty.class);
		serversClasses.add(ServerRpc.class);
	}

	public Class<?>[] getAllServerDefinition() {
		return (Class<?>[]) serversClasses.toArray(new Class<?>[serversClasses.size()]);
	}
	
	public void putRegisterServer(final Class<?> classDefinition, final IServer startedServer) {
		servers.put(classDefinition, startedServer);
	}
	
	public IServer getRegisterServer(final Class<?> classDefinition) {
		return servers.get(classDefinition);
	}
}
