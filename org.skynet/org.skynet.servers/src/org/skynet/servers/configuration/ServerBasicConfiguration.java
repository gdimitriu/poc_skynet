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
package org.skynet.servers.configuration;

import java.util.ArrayList;
import java.util.List;

public class ServerBasicConfiguration {

	private int port = 0;
	private String serverKey = null;
	private List<ParameterHolder> parameters = new ArrayList<ParameterHolder>();
	/**
	 * 
	 */
	public ServerBasicConfiguration(final String serverKey) {
		this.serverKey = serverKey;
	}
	
	public int getPort() {
		return port;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void addParameter(final ParameterHolder parameter) {
		parameters.add(parameter);
//		if (!parameters.contains(parameter)) {
//			parameters.add(parameter);
//			return;
//		}
//		parameters.remove(parameter);
//		parameters.add(parameters.indexOf(parameter), parameter);
	}
	
	public ParameterHolder[] getAllParameters() {
		return (ParameterHolder[]) parameters.toArray(new ParameterHolder[parameters.size()]);
	}

	public String getServerKey() {
		return serverKey;
	}
}
