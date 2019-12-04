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
package xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gdimitriu.skynet.jaxbutils.CreateJarFromSchema;
import org.gdimitriu.skynet.upgrade.Upgrade;

public class AutoUpgrade {

	public static void main(String[] args) {
		Skynet system = new Skynet();
		system.run();
		
		CreateJarFromSchema.setDebugMode(false);
		//generate the package
		CreateJarFromSchema creator = new CreateJarFromSchema(args[0],
				args[1],
				"upgrade");
		creator.createJar("upgrade.jar");
		
		System.out.println("Here we start marshall");
		
		File file = new File(args[2]);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		try {
			File jar = new File(creator.getJarFullName());
			URLClassLoader urlClassLoader
			 = new URLClassLoader(new URL[]{ jar.toURI().toURL()},
					 ClassLoader.getSystemClassLoader());
			JAXBContext jxbContext = JAXBContext.newInstance("upgrade",urlClassLoader);
			Unmarshaller unMarshaller = jxbContext.createUnmarshaller();			
			Object update = (Object) unMarshaller.unmarshal(fis);
			Method[] methods = update.getClass().getDeclaredMethods();
			for (int i = 0 ; i < methods.length; i++) {
				if (methods[i].getName().contains("getUpgrade")) {
					Upgrade upgrade = new Upgrade();
					try {
						upgrade = (Upgrade) methods[i].invoke(update, new Object[0]);
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						e.printStackTrace();
					}
					try {
						upgrade.doUpgrade(system);
					} catch (ClassNotFoundException | IllegalArgumentException
							| IllegalAccessException
							| InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
						e.printStackTrace();
					}
					system.run();

				}
			}
		} catch (JAXBException | MalformedURLException e1) {
			e1.printStackTrace();
		}
		try {
			if(fis!=null)
				fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
