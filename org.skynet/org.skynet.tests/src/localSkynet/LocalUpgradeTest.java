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
package localSkynet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.skynet.servers.rpc.RpcRequest;
import org.skynet.services.SingletonServices;
import org.skynet.services.interfaces.IRunnableService;
import org.skynet.services.interfaces.IServicesType;

import rpcupgrade.UpgradeRpcTest;
import xml.Skynet;

public class LocalUpgradeTest extends UpgradeRpcTest {

	public LocalUpgradeTest() {
	}

	public static void main(String[] args) {
		IServicesType service = SingletonServices.getSingleton().getServiceType("org.skynet.services.interfaces.IRunnableService");
		service.putService("Cyberdine", new Skynet());
		((IRunnableService) service.getService("Cyberdine")).start();
		LocalUpgradeTest test = new LocalUpgradeTest();
		test.readUpgradeData(args[0], args[1], args[2]);
		test.Upgrade();
		((IRunnableService) service.getService("Cyberdine")).start();
	}
	
	public void Upgrade() {
		
		try {
			PrintWriter writer1 = writeData(new FileOutputStream(new File("tmp.data")));
			writer1.close();
			InputStreamReader reader1 = new InputStreamReader(new FileInputStream(new File("tmp.data")));
			BufferedReader reader = new BufferedReader(reader1);
			RpcRequest request = new RpcRequest();
			request.process(reader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
