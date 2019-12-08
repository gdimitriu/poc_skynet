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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.skynet.services.processing.steps.transformers.configurations.ConfigurationProperties;

@XmlRootElement(name = "configuration")
public class StaxonConfigurationStep {

	@XmlElement(name = "array")
	private boolean array = false;
	
	@XmlElement(name = "virtualNode")
	private String virtualNode = "";
	
	@XmlElement(name = "autoPrimitive")
	private boolean autoPrimitive = false;
	
	@XmlElement(name = "inputProperties")
	private ConfigurationProperties[] inputProperties = null;
	
	@XmlElement(name = "outputProperties")
	private ConfigurationProperties[] outputProperties = null;
	
	public StaxonConfigurationStep() {
	}

	public boolean isArray() {
		return array;
	}

	public String getVirtualNode() {
		return virtualNode;
	}

	public boolean isAutoPrimitive() {
		return autoPrimitive;
	}

	public ConfigurationProperties[] getInputProperties() {
		return inputProperties;
	}
	
	public ConfigurationProperties[] getOutputProperties() {
		return outputProperties;
	}
}
