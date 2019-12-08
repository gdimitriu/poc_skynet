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
package org.skynet.upgrade.servers.rpc.requests;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.skynet.services.SingletonServices;
import org.skynet.upgrade.xsd.jaxbutils.SingletonJaxbContextFactory;

public class XsdUpgradeService implements IRequestService {

	/** xsd upgrade definition */
	private StringBuilder upgradeDefinition;
	/** bindings for the upgrade definition */
	private StringBuilder upgradeBindings;
	/** force the upgrade jaxb */
	private boolean force;
	/** name of the service to upgrade */
	private String serviceName;
	/** data to upgrade in xml type */
	private StringBuilder upgradeData;

	public XsdUpgradeService(final String serviceName) {
		this.serviceName = serviceName;
		this.upgradeDefinition = new StringBuilder();
		this.upgradeBindings = new StringBuilder();
		this.upgradeData = new StringBuilder();
	}

	/* (non-Javadoc)
	 * @see org.skynet.upgrade.servers.rpc.requests.IRequestService#readData(java.io.BufferedReader)
	 */
	@Override
	public void readData(BufferedReader reader) throws IOException {
		// read upgrade definition
		int lineNr = Integer.parseInt(reader.readLine());
		for (int i = 0; i < lineNr; i++) {
			this.upgradeDefinition.append(reader.readLine()).append('\n');
		}
		// read bindings
		lineNr = Integer.parseInt(reader.readLine());
		for (int i = 0; i < lineNr; i++) {
			this.upgradeBindings.append(reader.readLine()).append('\n');
		}
		// read upgrade data
		lineNr = Integer.parseInt(reader.readLine());
		for (int i = 0; i < lineNr; i++) {
			this.upgradeData.append(reader.readLine()).append('\n');
		}
		this.force = Boolean.parseBoolean(reader.readLine());
	}

	/* (non-Javadoc)
	 * @see org.skynet.upgrade.servers.rpc.requests.IRequestService#getData()
	 */
	@Override
	public Object[] getData() {
		return null;
	}

	@Override
	public void upgrade() {
		try {
			//get the context
			JAXBContext jxbContext = SingletonJaxbContextFactory.getSingletonFactory()
					.getContextFactory().getContext(serviceName, upgradeDefinition.toString(), upgradeBindings.toString(), force);
			if (jxbContext == null) {
				return;
			}
			Unmarshaller unMarshaller = jxbContext.createUnmarshaller();
			SingletonServices.getSingleton().upgrade(unMarshaller,
					new ByteArrayInputStream(upgradeData.toString().getBytes(StandardCharsets.UTF_8)));
			
		} catch (MalformedURLException | JAXBException e) {
			e.printStackTrace();
		}
	}

}
