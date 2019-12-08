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
package processing;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

public class ProcessingRestTest {

	private int port = 8099;

	private URI baseUri = UriBuilder.fromUri("{arg}").build(new String[]{"http://localhost:"+ port + "/"},false);

	public ProcessingRestTest() {

	}

	public void sendProcessingDummyValue() {
		System.out.println("string IO");
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(baseUri);
		Response  response = target.path("rest").path("processingStep").path("transform")
				.queryParam("step", "org.skynet.services.processing.steps.DummyProcessingStep")
				.queryParam("name", "dummystep").request(MediaType.TEXT_PLAIN).put(Entity.entity("blabla", MediaType.TEXT_PLAIN));
		if (response.getStatus() >= 0) {
			String rez =response.readEntity(String.class);
			System.out.println(rez);
		}
		System.out.println("stream IO");
		client = ClientBuilder.newClient();
		target = client.target(baseUri).register(MultiPartFeature.class);
		response = target.path("rest").path("processingStep").path("transformStream")
				.queryParam("step", "org.skynet.services.processing.steps.DummyProcessingStep")
				.queryParam("name", "dummystep").request(MediaType.TEXT_PLAIN).put(Entity.entity("blabla", MediaType.TEXT_PLAIN));
		if (response.getStatus() >= 0) {
			InputStream inputStream = response.readEntity(InputStream.class);
			System.out.println(new BufferedReader(new InputStreamReader(inputStream))
			  .lines().collect(Collectors.joining("\n")));
		}
	}
	
	public void sendProcessingXML2JSONValue() {
		System.out.println("XML2JSON stream IO");
		Client client = ClientBuilder.newClient();
		Response  response = null;
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		response = target.path("rest").path("processingStep").path("transformStream")
				.queryParam("step", "org.skynet.services.processing.steps.transformers.json.Xml2JsonStep")
				.queryParam("name", "xml2json")
				.queryParam("configuration", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><configuration><array>false</array><virtualNode></virtualNode><autoPrimitive>true</autoPrimitive></configuration>")
				.request(MediaType.TEXT_PLAIN)
				.put(Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?><mydata><ala>myData</ala><bala>100.0</bala></mydata>", MediaType.APPLICATION_XML));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			InputStream inputStream = response.readEntity(InputStream.class);
			System.out.println(new BufferedReader(new InputStreamReader(inputStream))
			  .lines().collect(Collectors.joining("\n")));
		}
	}
	
	public void sendProcessingJSON2XMLValue() {
		System.out.println("JSON2XML stream IO");
		Client client = ClientBuilder.newClient();
		Response  response = null;
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		response = target.path("rest").path("processingStep").path("transformStream")
				.queryParam("step", "org.skynet.services.processing.steps.transformers.json.Json2XmlStep")
				.queryParam("name", "xml2json")
				.queryParam("configuration", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><configuration><array>true</array><virtualNode></virtualNode><autoPrimitive>true</autoPrimitive></configuration>")
				.request(MediaType.TEXT_PLAIN)
				.put(Entity.entity("{\"mydata\":{\"ala\":\"myData\",\"bala\":\"100.0\"}}", MediaType.APPLICATION_JSON));
		if (response.getStatus() == Status.OK.getStatusCode()) {
			InputStream inputStream = response.readEntity(InputStream.class);
			System.out.println(new BufferedReader(new InputStreamReader(inputStream))
			  .lines().collect(Collectors.joining("\n")));
		}
	}
	
	public void sendProcessingXML2JSONValueException() {
		System.out.println("XML2JSON with exception stream IO");
		Client client = ClientBuilder.newClient();
		Response  response = null;
		WebTarget target = client.target(baseUri).register(MultiPartFeature.class);
		response = target.path("rest").path("processingStep").path("transformStream")
				.queryParam("step", "org.skynet.services.processing.steps.transformers.json.Xml2JsonStep")
				.queryParam("name", "xml2json")
				.queryParam("configuration", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><configuration><array>false</array><virtualNode>aaa</virtualNode><autoPrimitive>true</autoPrimitive></configuration>")
				.request(MediaType.TEXT_PLAIN)
				.put(Entity.entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?><mydata><ala>myData</ala><bala>100.0</bala></mydata>", MediaType.APPLICATION_XML));
		if (response.getStatus() == Status.EXPECTATION_FAILED.getStatusCode()) {
			if (response.hasEntity()) {
				String exception = response.readEntity(String.class);
				System.out.println(exception);
			} else {
				System.out.println(response.toString());
			}
		} else if (response.getStatus() == Status.OK.getStatusCode()) {
			InputStream inputStream = response.readEntity(InputStream.class);
			System.out.println(new BufferedReader(new InputStreamReader(inputStream))
			  .lines().collect(Collectors.joining("\n")));
		} else {
			System.out.println("Exception ocurred in server " + response.toString());
		}
	}
	
	public static void main(String[] args) {
		ProcessingRestTest test = new ProcessingRestTest();
		test.sendProcessingDummyValue();
		test.sendProcessingXML2JSONValue();
		test.sendProcessingJSON2XMLValue();
		test.sendProcessingXML2JSONValueException();
	}

}
