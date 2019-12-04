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
package org.gdimitriu.skynet.upgrade;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.gdimitriu.skynet.loaders.ClassServiceProvider;
import org.gdimitriu.skynet.loaders.JavaClassLoader;

public class Upgrade extends ClassServiceProvider {
	JavaClassLoader loader = new JavaClassLoader(this);

	public Upgrade() {
	}
	
	/**
	 * doUpgrade, this will upgrade the class
	 * @throws ClassNotFoundException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public void doUpgrade(final Object toUpgrade) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
			String[] str = getAllClassNames();
			for (int i = 0; i < str.length; i++) {
				System.out.println(getByteCode(str[i]));
				Class<?> classLoaded = loader.loadClass(str[i]);
				try {
					Object loaded = classLoaded.newInstance();
					Method executeMethod = classLoaded.getMethod("run");
					executeMethod.invoke(loaded);
				} catch (InstantiationException e) {
					e.printStackTrace();
				}
			}
	}

}
