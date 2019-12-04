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
package org.gdimitriu.skynet.loaders;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.gdimitriu.skynet.loaders.interfaces.IClassServices;

public class ClassServiceProvider implements IClassServices {

	/**
	 * constructor.
	 */
	public ClassServiceProvider() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.gdimitriu.skynet.loaders.interfaces.IClassServices#getByteCode(java.lang.String)
	 */
	@Override
	public String getByteCode(String name) {
		Field[] fields= getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			try {
				if (fields[i].get(this) instanceof List) {
					List<?> rez = (List<?>) fields[i].get(this);
					for (int j =0 ; j < rez.size(); j++) {
						String className = (String) rez.get(j).getClass().getDeclaredMethod("getClassName").invoke(rez.get(j));
						if (className.equals(name)) {
							return Base64.getEncoder().encodeToString(
									(byte[]) rez.get(j).getClass().getDeclaredMethod("getClassData").invoke(rez.get(j)));
						}
					}
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gdimitriu.skynet.loaders.interfaces.IClassServices#getAllByteCodes()
	 */
	@Override
	public String[] getAllByteCodes() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gdimitriu.skynet.loaders.interfaces.IClassServices#getAllClassNames()
	 */
	@Override
	public String[] getAllClassNames() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException {
		List<String> classes = new ArrayList<String>();
		Field[] fields= getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			if (fields[i].get(this) instanceof List) {
				List rez = (List) fields[i].get(this);
				for (int j =0 ; j < rez.size(); j++) {
					classes.add((String) rez.get(j).getClass().getDeclaredMethod("getClassName").invoke(rez.get(j)));
				}
			}
			
			
		}
		return (String[]) classes.toArray(new String[classes.size()]);
	}
	
	protected String getStringFromEncodedOutputStream(final ByteArrayOutputStream outputStream) {
		if (outputStream == null) {
			return new String();
		}
		return Base64.getEncoder().encodeToString(outputStream.toByteArray());
	}

}
