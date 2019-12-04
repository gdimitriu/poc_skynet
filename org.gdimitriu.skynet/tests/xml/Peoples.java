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
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gdimitriu.skynet.jaxbutils.CreateJarFromSchema;

public class Peoples {

	public static void main(String[] args) throws Exception {
		//setdebug mode
		CreateJarFromSchema.setDebugMode(false);
		//generate the package
		CreateJarFromSchema creator = new CreateJarFromSchema("D:\\workspace_skynet\\org.gdimitriu.skynet\\testData\\xml\\peoples\\peoples.xsd",
				"D:\\workspace_skynet\\org.gdimitriu.skynet\\testData\\xml\\peoples\\bindings.xml",
				"auto_generated");
//		if(creator.generateAndCompile() == false){
//			return;
//		}
		creator.createJar("peoples.jar");

		System.out.println("Here we start marshall");
		
		File file = new File(args[0]);
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
			JAXBContext jxbContext = JAXBContext.newInstance("auto_generated",urlClassLoader);
			Unmarshaller unMarshaller = jxbContext.createUnmarshaller();			
			Object people = unMarshaller.unmarshal(fis);
			File jrr = new File("D:\\workspace_skynet\\org.gdimitriu.skynet\\skynet.jar");

//			ArrayList<Class<?>> obj = ClassLister.getClassesForPackageAsList("auto_generated" ,urlClassLoader);
//			Field flds = ClassLoader.class.getDeclaredField("classes");
//			flds.setAccessible(true);
//			Object value = flds.get(urlClassLoader);
//			Vector<Class<?>> obj = ((Vector<Class<?>>) value);
			Field[] flds = people.getClass().getDeclaredFields();
			for(int i = 0; i < flds.length; i++) {
				flds[i].setAccessible(true);
				Object value =flds[i].get(people);				
			}
/*			for(Person p:people.getPerson()) {
				System.out.println("person=" + p.getName().getFamilyName());
			}
*/		} catch (JAXBException e1) {
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
