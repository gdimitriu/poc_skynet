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

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.skynet.services.interfaces.IService;
import org.skynet.services.interfaces.IServicesType;

public class SingletonServices {

	private static SingletonServices singleton = new SingletonServices();
	
	private ServicesTypesProvider serviceTypeProvider = null;
		
	private SingletonServices() {
		serviceTypeProvider = new ServicesTypesProvider();
	}
	
	/**
	 * get the singleton
	 * @return singleton for services.
	 */
	public static SingletonServices getSingleton() {
		return singleton;
	}
		
	public void upgrade(final Unmarshaller unMarshaller, final InputStream inputData) throws JAXBException {
		Object update = (Object) unMarshaller.unmarshal(inputData);
		Method[] methods = update.getClass().getDeclaredMethods();
		for (int i = 0 ; i < methods.length; i++) {
			if (methods[i].getName().contains("getUpgrade")) {
				try {
					Object upgradeContainer = methods[i].invoke(update,
							new Object[0]);
					upgradeAllPackages(upgradeContainer);

				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}

	private void upgradeAllPackages(final Object upgradeContainer) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = upgradeContainer.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			if (fields[i].get(upgradeContainer) instanceof List) {
				List<?> rez = (List<?>) fields[i].get(upgradeContainer);
				for (int j =0 ; j < rez.size(); j++) {
					upgradeService(rez.get(j));
				}
				break;
			}
		}
	}
	
	private void upgradeService(final Object container) {
		try {
			Method[] methods = container.getClass().getDeclaredMethods();
			for (int i = 0; i < methods.length; i ++) {
				if (methods[i].getName().contains("getServiceType")) {
					String type = (String) methods[i].invoke(container, new Object[0]);
					IServicesType serviceType = serviceTypeProvider.getServicesTypeByName(type);
					//get the service to upgrade
					IService service = serviceType.getService((String) container.getClass()
							.getDeclaredMethod("getServiceName").invoke(container, new Object[0]));
					service.upgrade(container);
					break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	public IServicesType getServiceType(final String type) {
		return serviceTypeProvider.getServicesTypeByName(type);
	}
}
