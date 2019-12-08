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
package org.skynet.services.processing.resources;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.skynet.services.exceptions.ProcessingStepException;
import org.skynet.services.interfaces.IProcessingStep;
import org.skynet.services.processing.wrappers.ByteArrayOutputStreamWrapper;
import org.skynet.services.providers.processingstep.SingletonProcessingStepProvider;

@Path("rest/processingStep")
public class ProcessingStepResource {

	@Context
	UriInfo uriInfo;
	public ProcessingStepResource() {
	}

	@Path("transform")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML, MediaType.TEXT_PLAIN})
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML, MediaType.TEXT_PLAIN})
	public Response processData(final String inputValue,
			@QueryParam("step") final String step,
			@QueryParam("name") final String name,
			@QueryParam("configuration") final String configuration) {
		IProcessingStep processingStep = SingletonProcessingStepProvider.getSingleton().getProcessingStep(name, step);
		if (processingStep == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		processingStep.setConfiguration(configuration);
		try {
			String rez = "";
			rez = processingStep.process(inputValue);
			return Response.ok().entity(rez).build();
		} catch (Exception e) {
			return Response.status(-100).build();
		}
		
	}
	
	@Path("transformStream")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML, MediaType.TEXT_PLAIN})
	@Produces({ MediaType.APPLICATION_XML,MediaType.APPLICATION_JSON , MediaType.TEXT_XML, MediaType.TEXT_PLAIN})
	public Response processDataStream(final InputStream inputValue,
			@QueryParam("step") final String step,
			@QueryParam("name") final String name,
			@QueryParam("configuration") final String configuration) throws ProcessingStepException {
		IProcessingStep processingStep = SingletonProcessingStepProvider.getSingleton().getProcessingStep(name, step);
		if (processingStep == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		processingStep.setConfiguration(configuration);
		processingStep.configure();
		ByteArrayOutputStreamWrapper bos = new ByteArrayOutputStreamWrapper();
		try {
			processingStep.process(inputValue, bos);
		} catch (ProcessingStepException exception) {
			try {
				bos.close();
			} catch (IOException e) {
			}
			throw exception;
		} catch (Exception e) {
			try {
				bos.close();
			} catch (IOException e1) {
			}
			return Response.status(-100).build();
		}
		StepStreamingOutput stream = new StepStreamingOutput(bos.getBuffer());
		return Response.ok().entity(stream).build();
	}
}
