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
package restupgrade;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.gdimitriu.skynet.servers.restAPI.resources.IUpgradeServiceConstants;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.internal.MultiPartWriter;

public class UpgradeRestTest {

	private int port = 8099;

	private URI baseUri = UriBuilder.fromUri("{arg}").build(new String[]{"http://localhost:"+ port + "/"},false);
	
	private String xsd = null;
	private String bindings = null;
	private String data = null;

	public UpgradeRestTest() {
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
	
	public void sendRestUpgradeData() {
		Client client = ClientBuilder.newClient().register(MultiPartFeature.class).register(MultiPartWriter.class);
		WebTarget target = client.target(baseUri);
		FormDataMultiPart multipartEntity = new FormDataMultiPart()
	    .field(IUpgradeServiceConstants.UPGRADE_REST_NAME,"upgrade", MediaType.TEXT_PLAIN_TYPE)
	    .field(IUpgradeServiceConstants.UPGRADE_REST_XSD_SCHEMA, xsd, MediaType.TEXT_PLAIN_TYPE)
	    .field(IUpgradeServiceConstants.UPGRADE_REST_BINDINGS, bindings ,MediaType.TEXT_PLAIN_TYPE)
	    .field(IUpgradeServiceConstants.UPGRADE_REST_XML_DATA, data,MediaType.TEXT_PLAIN_TYPE)
	    .field(IUpgradeServiceConstants.UPGRADE_REST_FORCE, true ,MediaType.TEXT_PLAIN_TYPE);

		target.path(IUpgradeServiceConstants.UPGRADE_REST_PATH).path(IUpgradeServiceConstants.UPGRADE_REST_FILE_PATH)
		.request(new String[]{MediaType.MULTIPART_FORM_DATA}).post(Entity.entity(multipartEntity, multipartEntity.getMediaType()));

		try {
			multipartEntity.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		UpgradeRestTest test = new UpgradeRestTest();
		test.readUpgradeData(args[0], args[1], args[2]);
		test.sendRestUpgradeData();
	}

}
