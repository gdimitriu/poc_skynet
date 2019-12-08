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
package org.skynet.services.processing.steps.transformers.json;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

import org.skynet.services.exceptions.ProcessingStepException;
import org.skynet.services.interfaces.IProcessingStep;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;

public class StaxonStep implements IProcessingStep {
	protected JsonXMLConfig config = null;
	protected JsonXMLConfigBuilder builder = null;
	protected StaxonConfigurationStep configuration = null;
	
	public StaxonStep() {
		builder = new JsonXMLConfigBuilder().multiplePI(true);
	}
	
	@Override
	public void setConfiguration(final String config) {
		if (config == null || config.equals("")) {
			return;
		}
		try {
			JAXBContext context = JAXBContext.newInstance(StaxonConfigurationStep.class);
			configuration = (StaxonConfigurationStep) context.createUnmarshaller()
					.unmarshal(new StringReader(config));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void configure() {
		if (configuration == null) {
			builder = builder.autoArray(false).autoPrimitive(true);
		} else {
			loadConfiguration();
		}
		config = builder.build();
	}
	
	/**
	 * load the configuration and initialize the builder
	 */
	protected void loadConfiguration() {
		builder = builder.autoArray(configuration.isArray())
				.autoPrimitive(configuration.isAutoPrimitive());
		if (configuration.getVirtualNode() != null && !configuration.getVirtualNode().equals("")) {
				builder = builder.virtualRoot(configuration.getVirtualNode());
		}
	}

	@Override
	public String process(final String input) throws Exception {
		return IProcessingStep.super.process(input);
	}

	@Override
	public void process(final InputStream input, final OutputStream output) throws ProcessingStepException {

		XMLEventReader eventReader = null;
		XMLEventWriter eventWriter = null;
		
		try {
			eventReader = createEventReader(input);
			eventWriter = createEventWriter(output);
			if (!specialProcessing(eventReader,eventWriter)) {
				while (eventReader.hasNext()) {
					XMLEvent event = eventReader.nextEvent();
					eventWriter.add(event);
				}
			}
		} catch (XMLStreamException e) {
			throw new ProcessingStepException(e);
		} finally {
			//close all event reader/writer
			if (eventReader != null) {
				try {
					eventReader.close();
				} catch (XMLStreamException e) {
				}
			}
			if (eventWriter != null) {
				try {
					eventWriter.close();
				} catch (XMLStreamException e) {
				}
			}
		}
	}
	
	protected boolean specialProcessing(final XMLEventReader eventReader,
			final XMLEventWriter eventWriter) {
		return false;
	}

	protected XMLEventReader createEventReader(final InputStream input) throws XMLStreamException {
		return null;
	}
	
	protected XMLEventWriter createEventWriter(final OutputStream output) throws XMLStreamException{
		return null;
	}
}
