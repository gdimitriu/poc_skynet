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
package org.skynet.upgrade.xsd.jaxbutils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

public class JaxbContextFactory {

	private Map<String, JAXBContext> jxbContextMap = new HashMap<String, JAXBContext>();
	public JaxbContextFactory() {
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
		CreateJarFromSchema creator = new CreateJarFromSchema(new ByteArrayInputStream(xsd.getBytes(StandardCharsets.UTF_8)),
				new ByteArrayInputStream(bindings.getBytes(StandardCharsets.UTF_8)), name);
		creator.createJar(name + ".jar");
		File jar = new File(creator.getJarFullName());
		if (!jar.exists()) {
			return null;
		}
		URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{ jar.toURI().toURL()},
					 ClassLoader.getSystemClassLoader());
		JAXBContext jxbContext = JAXBContext.newInstance(name ,urlClassLoader);
		jxbContextMap.put(name, jxbContext);
		creator.clean();
		return jxbContext;
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
	public JAXBContext getContext(final String name, final InputStream xsd,
			final InputStream bindings, final boolean force) throws MalformedURLException, JAXBException {

		if( force == false && jxbContextMap.containsKey(name) ) {
			return jxbContextMap.get(name);
		}
		CreateJarFromSchema.setDebugMode(false);
		CreateJarFromSchema creator = new CreateJarFromSchema(xsd, bindings, name);
		creator.createJar(name + ".jar");
		File jar = new File(creator.getJarFullName());
		if (!jar.exists()) {
			return null;
		}
		URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{ jar.toURI().toURL()},
					 ClassLoader.getSystemClassLoader());
		JAXBContext jxbContext = JAXBContext.newInstance(name ,urlClassLoader);
		jxbContextMap.put(name, jxbContext);
		creator.clean();
		return jxbContext;
	}
}
