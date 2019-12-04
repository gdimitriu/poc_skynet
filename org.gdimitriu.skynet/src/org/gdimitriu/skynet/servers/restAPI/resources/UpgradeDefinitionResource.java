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
package org.gdimitriu.skynet.servers.restAPI.resources;

import java.net.MalformedURLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.gdimitriu.skynet.jaxbutils.SingletonJaxbContextFactory;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("rest/upgradeDefinition")
public class UpgradeDefinitionResource {

	@Context
	UriInfo uriInfo;
	public UpgradeDefinitionResource() {
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void postUpgradeDefinitions(@FormDataParam("Name") String name,
			@FormDataParam("xsd") String xsd, @FormDataParam("bindings") String bindings,
			@FormDataParam("force") Boolean force) {
		try {
			JAXBContext context = SingletonJaxbContextFactory.getSingletonFactory().getContextFactory().getContext(name, xsd, bindings, force);
		} catch (MalformedURLException | JAXBException e) {
			e.printStackTrace();
		}
	}
}
