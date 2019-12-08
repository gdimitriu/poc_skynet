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

package org.skynet.upgrade.servers.rpc;

import java.io.BufferedReader;
import java.io.IOException;

import org.skynet.upgrade.servers.rpc.requests.IRequestService;
import org.skynet.upgrade.servers.rpc.requests.SingletonRequestTypeFactory;

public class RpcRequest {
	
	private IRequestService serviceData;
	
	public RpcRequest() {
		serviceData = null;
	}
	public void process(final BufferedReader reader) {
		try {
			String type = reader.readLine();
			String serviceName = reader.readLine();
			serviceData = SingletonRequestTypeFactory.getSingleton()
					.getFactory().getServiceData(type, serviceName);
			serviceData.readData(reader);
			serviceData.upgrade();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
