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
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.gdimitriu.skynet.jaxbutils.SingletonJaxbContextFactory;
import org.gdimitriu.skynet.services.SingletonServices;
import org.gdimitriu.skynet.utils.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path(IUpgradeServiceConstants.UPGRADE_REST_PATH)
public class UpgradeServiceResource {

	public UpgradeServiceResource() {
	}
	
	@POST
	@Path(IUpgradeServiceConstants.UPGRADE_REST_FILE_PATH)
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public void postUpgradeServices(@FormDataParam(IUpgradeServiceConstants.UPGRADE_REST_NAME) String name,
			@FormDataParam(IUpgradeServiceConstants.UPGRADE_REST_XSD_SCHEMA) String upgadeDefinition, @FormDataParam(IUpgradeServiceConstants.UPGRADE_REST_BINDINGS) String upgradeBindings,
			@FormDataParam(IUpgradeServiceConstants.UPGRADE_REST_XML_DATA) String upgradeData,
			@FormDataParam(IUpgradeServiceConstants.UPGRADE_REST_FORCE) Boolean force) {
		try {
			//get the context
			JAXBContext jxbContext = SingletonJaxbContextFactory.getSingletonFactory()
					.getContextFactory().getContext(name, upgadeDefinition, upgradeBindings, force);
			
			Unmarshaller unMarshaller = jxbContext.createUnmarshaller();
			SingletonServices.getSingleton().upgrade(unMarshaller,
					IOUtils.transformStringToInputStream(upgradeData));
			
		} catch (MalformedURLException | JAXBException e) {
			e.printStackTrace();
		}
	}
}
