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
package org.gdimitriu.skynet.jaxbutils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.gdimitriu.skynet.utils.IOUtils;

public class JaxbContextFactory {

	private Map<String, JAXBContext> jxbContextMap = new HashMap<String, JAXBContext>();
	/**
	 * 
	 */
	public JaxbContextFactory() {
		// TODO Auto-generated constructor stub
	}

	public JAXBContext getContext(final String name) {
		if( jxbContextMap.containsKey(name) ) {
			return jxbContextMap.get(name);
		}
		return null;
	}

	/**
	 * this is used to get the creator in REST operation.
	 * @param name of the jar
	 * @param xsd definition
	 * @param bindings bindings of classes
	 * @param force to force the update of jar
	 * @return jarCreator
	 * @throws MalformedURLException 
	 * @throws JAXBException 
	 */
	public JAXBContext getContext(final String name, final String xsd,
			final String bindings, final boolean force) throws MalformedURLException, JAXBException {

		if( force == false && jxbContextMap.containsKey(name) ) {
			return jxbContextMap.get(name);
		}
		CreateJarFromSchema.setDebugMode(false);
		CreateJarFromSchema creator = new CreateJarFromSchema(IOUtils.transformStringToInputStream(xsd),
				IOUtils.transformStringToInputStream(bindings), name);
		creator.createJar(name + ".jar");
		File jar = new File(creator.getJarFullName());
		URLClassLoader urlClassLoader;
			urlClassLoader = new URLClassLoader(new URL[]{ jar.toURI().toURL()},
					 ClassLoader.getSystemClassLoader());
		JAXBContext jxbContext = JAXBContext.newInstance(name ,urlClassLoader);
		jxbContextMap.put(name, jxbContext);
		creator.clean();
		return jxbContext;
	}
}
