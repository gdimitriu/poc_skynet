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
package xml;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.List;

import org.skynet.services.interfaces.IRunnableService;
import org.skynet.upgrade.loaders.Loader;

public class Skynet implements ICyberdyne, IRunnableService {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

	/* (non-Javadoc)
	 * @see xml.Ciyberdyne#run()
	 */
	@Override
	public void run() {
		System.out.println("this is skynet before upgrade");
	}

	@Override
	public void start() {
		run();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public IRunnableService getInstance() {
		return this;
	}
	
	@Override
	public void upgrade(final List<?> listOfClassesToUpgrade) {
		if (listOfClassesToUpgrade.size() == 1) {
			try {
				Object upgradeData = listOfClassesToUpgrade.get(0);
				Method getClassDataMethod = upgradeData.getClass().getMethod("getClassData");
				Method getClassNameMethod = listOfClassesToUpgrade.get(0).getClass().getMethod("getClassName");
				Object data = getClassDataMethod.invoke(upgradeData);
				Object name = getClassNameMethod.invoke(upgradeData);
				Loader loader = new Loader();
				Object obj = loader.loadObject((String) name, 
						Base64.getEncoder().encodeToString((byte[]) data));
				Method executeMethod = obj.getClass().getMethod("start");
				executeMethod.invoke(obj);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}
}
