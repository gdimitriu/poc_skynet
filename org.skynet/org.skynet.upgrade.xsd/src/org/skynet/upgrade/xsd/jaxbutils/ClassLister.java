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
/**
 * This is a modified version of the 
 * https://github.com/ddopson/java-class-enumerator
 * by GDimitriu
 * This is used to list the classes from a specific package/jar. 
 */

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class ClassLister {
	
	private static Class<?> loadClass(URL resource, String className) {
//		SecurityManager securityManager = new SecurityManager();
		Policy pol = Policy.getPolicy();
		CodeSource codesource = new CodeSource(resource, (Certificate[]) null);
		PermissionCollection pCollection = pol.getPermissions(codesource);
		ProtectionDomain domain = new ProtectionDomain(codesource, pCollection);
		AccessControlContext context = new AccessControlContext(new ProtectionDomain[] {domain});

		
			return AccessController.doPrivileged(new PrivilegedAction<Class<?>>() {
				@Override
				public Class<?> run() {
					try {
						ClassLoader classLoader = codesource.getClass().getClassLoader();
						return Class.forName(className,  true, classLoader);
					} catch (ClassNotFoundException e) {
							throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
					}
			}}, context);
	}

	private static void processDirectory(File directory, String pkgname, ArrayList<Class<?>> classes, URL resource) {
		// Get the list of the files contained in the package
		String[] files = directory.list();
		if (files == null) {
			return;
		}
		for (int i = 0; i < files.length; i++) {
			String fileName = files[i];
			String className = null;
			// we are only interested in .class files
			if (fileName.endsWith(".class")) {
				// removes the .class extension
				className = pkgname + '.' + fileName.substring(0, fileName.length() - 6);
			}
			if (className != null) {
				classes.add(loadClass(resource, className));
			}
			File subdir = new File(directory, fileName);
			if (subdir.isDirectory()) {
				processDirectory(subdir, pkgname + '.' + fileName, classes, resource);
			}
		}
	}

	private static void processJarfile(URL resource, String pkgname, ArrayList<Class<?>> classes) {
		String relPath = pkgname.replace('.', '/');
		String resPath = resource.getPath();
		String jarPath = resPath.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");
		JarFile jarFile;
		try {
			jarFile = new JarFile(jarPath);         
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
		}
		Enumeration<JarEntry> entries = jarFile.entries();
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			String entryName = entry.getName();
			String className = null;
			if(entryName.endsWith(".class") && entryName.startsWith(relPath) && entryName.length() > (relPath.length() + "/".length())) {
				className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
			}
			if (className != null) {
				classes.add(loadClass(resource, className));
			}
		}
		try {
			jarFile.close();
		} catch (IOException e) {
			throw new RuntimeException("Unexpected IOException closing JAR File '" + jarPath + "'", e);
		}
		
	}
	
	public static Set<Class<?>> getClassesForPackageAsSet(Package pkg, URLClassLoader urlClassLoader){
		return new HashSet<Class<?>>(getClassesForPackageAsList(pkg,urlClassLoader));
	}
	
	public static Set<Class<?>> getClassesForPackageAsSet(String pkgname, URLClassLoader urlClassLoader){
		return new HashSet<Class<?>>(getClassesForPackageAsList(pkgname,urlClassLoader));
	}
	
	public static ArrayList<Class<?>> getClassesForPackageAsList(Package pkg, URLClassLoader urlClassLoader) {		
		return getClassesForPackageAsList(pkg.getName(),urlClassLoader);
	}
	
	public static ArrayList<Class<?>> getClassesForPackageAsList(String pkgname, URLClassLoader urlClassLoader) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		
		String relPath = pkgname.replace('.', '/');
	
		// Get a File object for the package
//		URL resource = urlClassLoader.getResource(relPath);
		URL[] resources = urlClassLoader.getURLs();
		URL resource = resources[0];
		if (resource == null) {
			throw new RuntimeException("Unexpected problem: No resource for " + relPath);
		}
		
		if(resource.toString().startsWith("jar:") || resource.toString().endsWith("jar")) {
			processJarfile(resource, pkgname, classes);
		} else {
			processDirectory(new File(resource.getPath()), pkgname, classes, resource);
		}

		return classes;
	}
}