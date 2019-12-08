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
package rpcupgrade;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class UpgradeRpcTest {

	private int port = 9000;
	
	protected String xsd = null;
	protected String bindings = null;
	protected String data = null;

	public UpgradeRpcTest() {
		xsd = new String();
		bindings = new String();
		data = new String();
	}
	
	public void readUpgradeData(final String upgradeXSD, final String upgradeBindings, final String upgradeXML) {
		File readFile = null;
		readFile = new File(upgradeXSD);
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
			xsd = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		readFile = new File(upgradeBindings);
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
			bindings = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	public PrintWriter writeData(OutputStream os) {
		PrintWriter writer = new PrintWriter(os);
		writer.println("XsdUpgradeService");
		writer.println("upgrade");
		writer.println(nrOfOccurences(xsd, '\n'));
		writer.print(xsd);
		writer.println(nrOfOccurences(bindings, '\n'));
		writer.print(bindings);
		writer.println(nrOfOccurences(data, '\n'));
		writer.print(data);
		writer.println(true);
		writer.flush();
		return writer;
	}
	
	public void sendRpcUpgradeData() {
		Socket socket = null;
		try {
			socket = new Socket("localhost", port);
			writeData(socket.getOutputStream());
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		UpgradeRpcTest test = new UpgradeRpcTest();
		test.readUpgradeData(args[0], args[1], args[2]);
		test.sendRpcUpgradeData();
	}

	int nrOfOccurences(final String str, final char ch) {
		int count = 0;
		for (int i = 0 ; i < str.length(); i++) {
			if (str.charAt(i) == ch) {
				count ++;
			}
		}
		return count;
	}
}
