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
package org.gdimitriu.skynet.obsolite;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
/**
 * it should be used like :
 * 
 *	CreateJarPlugin plugin = new CreateJarPlugin("D:\\workspace_skynet\\org.gdimitriu.skynet");
 *	plugin.createJar();
 * @author Gabriel
 *
 */
public class CreateJarPlugin {
	private static final String jarName = "jaxbplugin.jar";
	private static final String packageName = "org.gdimitriu.skynet.jaxbplugin";
	private static String rootDirectory = null;
	private static String jarFullPathName = null;
	private static boolean generated = false;
	
	private File classDirectory=null;
	
	static {
		rootDirectory = System.getProperty("java.io.tmpdir");
		File root = new File(rootDirectory + "/jaxb");
		if (!root.exists()) {
			root.mkdirs();
		}
		jarFullPathName = rootDirectory + "/jaxb/" + jarName;
	}

	public CreateJarPlugin(String classDirectory) throws Exception {
//		URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		this.classDirectory = new File(classDirectory + "\\bin\\"+packageName.replace('.', '\\'));
		if (!this.classDirectory.exists()) {
			throw new Exception("wrong directory");
		}
	}
	
	
	public static String getJarFullPathName(){
		return jarFullPathName;
	}
	
	public static boolean isGenerated() {
		return generated;
	}

	public boolean createJar(){

		BufferedInputStream in=null;
		FileInputStream fis=null;
		byte[] buffer =null;
		//write the manifest file
		Manifest manifest=new Manifest();
		Attributes global=manifest.getMainAttributes();
		global.put(Attributes.Name.MANIFEST_VERSION, "1.0.0");
		global.put(new Attributes.Name("Created-by"),"Gabriel Dimitriu");
		//create the jar file		
		try (FileOutputStream os=new FileOutputStream(jarFullPathName);
				JarOutputStream jos=new JarOutputStream(os,manifest);
				PrintStream ps = new PrintStream(jos);){
			
			JarEntry entry=null;
			entry = new JarEntry("META-INF/services/com.sun.tools.xjc.Plugin");
			jos.putNextEntry(entry);
			ps.println("org.gdimitriu.skynet.jaxbplugin.ExtendsPlugin");
			ps.println("org.gdimitriu.skynet.jaxbplugin.ImplementsPlugin");
			File output=new File(classDirectory.getAbsolutePath());
			File[] classes=output.listFiles();
			for(int i=0;i<classes.length;i++) {
				entry=new JarEntry(packageName+"/"+classes[i].getName());
				entry.setTime(classes[i].lastModified());
				try {
					fis=new FileInputStream(classes[i]);
					//only put here after we had open the file
					jos.putNextEntry(entry);
					in=new BufferedInputStream(fis);
					buffer = new byte[2048];
					while (true)
					{
						int count = in.read(buffer);
						if (count == -1)
							break;
						jos.write(buffer,0,count);
					}
				} catch (FileNotFoundException e) {
					continue;
				}
				in.close();
			}
			jos.flush();
			generated = true;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			generated = false;
			return false;
		}
	}
}
