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

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.gdimitriu.skynet.loaders.interfaces.IClassServices;

public class JavaClassLoader extends ClassLoader {
	
	private Map<String,Class<?>> types = new HashMap<String,Class<?>>();
	
	private IClassServices dataProvider;
	
	public JavaClassLoader(final IClassServices inputProvider) {
		super(JavaClassLoader.class.getClassLoader());
		dataProvider = inputProvider;
	}
	
	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		if(name==null) {
			return null;
		}
		if(types.containsKey(name)){
			return types.get(name);
		}
		//pt clase din classpath
		try {
			return findSystemClass(name);
		} catch (Exception e) {
		}
		String data = dataProvider.getByteCode(name);
		byte[] buffer = Base64.getDecoder().decode(data);
		Class<?> type = defineClass(null, buffer, 0, buffer.length);
		types.put(name,type);
		return type;
	}
}
