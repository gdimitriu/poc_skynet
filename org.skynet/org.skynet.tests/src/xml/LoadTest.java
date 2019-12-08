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


import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.skynet.services.interfaces.IRunnableService;
import org.skynet.upgrade.loaders.Loader;


public class LoadTest{
	
	protected String data = null;


	public LoadTest() {
		data = new String();
	}

	public static void main(String[] args) {
		LoadTest test = new LoadTest();
		test.readUpgradeData(args[0]);
		test.Upgrade();
	}
	
	public void Upgrade() {		
		Loader loader = new Loader();
		IRunnableService service = (IRunnableService) loader.loadObject("xml.SkynetUpgraded", data);
		service.start();
	}

	public void readUpgradeData(final String upgradeXML) {
		File readFile = null;
		readFile = new File(upgradeXML);
		try (FileReader fr = new FileReader(readFile);) {
			StringBuilder sb = new StringBuilder();
			char[] buffer = new char[256];
			int position = 0;
			do {
				position = fr.read(buffer);
				if (position > 0) {
					sb.append(buffer, 0, position);
				}
			} while(position > 0);
			data = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
