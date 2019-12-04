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
package org.gdimitriu.skynet.services;

import java.util.HashMap;
import java.util.Map;

import org.gdimitriu.skynet.services.interfaces.IRunnableService;
import org.gdimitriu.skynet.services.interfaces.IServicesType;

public class ServicesTypesProvider {

	private Map<String,IServicesType> serviceTypeProvider = null;

	public ServicesTypesProvider() {
		serviceTypeProvider = new HashMap<String, IServicesType>();
		serviceTypeProvider.put(IRunnableService.class.getName(), new RunnableServices());
	}
	
	public IServicesType getServicesTypeByName(final String type) {
		if (serviceTypeProvider.containsKey(type)) {
			return serviceTypeProvider.get(type);
		}
		return null;
	}

}
