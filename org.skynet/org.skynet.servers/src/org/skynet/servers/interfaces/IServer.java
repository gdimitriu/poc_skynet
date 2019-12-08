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
package org.skynet.servers.interfaces;

import org.skynet.servers.configuration.ParameterHolder;
import org.skynet.servers.configuration.ServerBasicConfiguration;

public interface IServer {
	/**
	 * set the server port.
	 * @param port 
	 */
	void setPort(final int port);
	
	/**
	 * get the server port.
	 * @return port
	 */
	int getPort();
	
	/**
	 * start the server.
	 */
	void startServer() throws Exception;

	/**
	 * stop the server.
	 */
	void stopServer() throws Exception;

	/** 
	 * add initial parmeters.
	 * @param key of the parameter
	 * @param value of the parameter
	 */
	default void setParameter(final String key, final String value) {
		//left blank for other servers except jetty/jersey.
	}
	
	/** 
	 * add initial parmeters.
	 * @param parameter holder
	 */
	default void setParameter(final ParameterHolder parameter) {
		//left blank for other servers except jetty/jersey.
	}
	
	/**
	 * join the main thread.
	 * this should be override by the servers which are not thread safe.
	 * @throws InterruptedException
	 */
	void join() throws InterruptedException;
	
	/**
	 * get the server registration key.
	 * @return key as string
	 */
	String getServerKey();

	default void configure(final ServerBasicConfiguration config) {
		setPort(config.getPort());
		for (ParameterHolder param : config.getAllParameters()) {
			setParameter(param);
		}
	}
}
