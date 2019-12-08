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
package org.skynet.services.interfaces;

import java.lang.reflect.Field;
import java.util.List;

import javax.xml.bind.JAXBException;

public interface IService {

	/**
	 * upgrade the Service.
	 * @param unMarshaller
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws JAXBException 
	 */
	default void upgrade(Object upgradeData) throws IllegalArgumentException, IllegalAccessException {
		Field[] fields = upgradeData.getClass().getDeclaredFields();
		for(int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			if (fields[i].get(upgradeData) instanceof List) {
				List<?> rez = (List<?>) fields[i].get(upgradeData);
				upgrade(rez);
				break;
			}
		}
	}
	
	/**
	 * upgrade the list of classes
	 * @param classListUpgrade list of classes to upgrade
	 */
	void upgrade(final List<?> listOfClassesToUpgrade);
}
