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
package org.skynet.services;

import java.util.HashMap;
import java.util.Map;

import org.skynet.services.interfaces.IProcessingService;
import org.skynet.services.interfaces.IService;
import org.skynet.services.interfaces.IServicesType;

/**
 * NOTE : all methods are synchronized.
 */
public class ProcessingServices implements IServicesType {

	private Map<String, IProcessingService> servicesMap = new HashMap<String, IProcessingService>(); 
	
	public ProcessingServices() {
		
	}
	
	@Override
	synchronized public void putService(final String name, final IService service) {
		servicesMap.put(name, (IProcessingService) service);
	}

	@Override
	synchronized public IProcessingService getService(final String name) {
		if (servicesMap.containsKey(name)) {
			return servicesMap.get(name);
		}
		return null;
	}
}
