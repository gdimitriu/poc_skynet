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
package org.skynet.services.providers.processingstep;

import java.util.HashMap;
import java.util.Map;

import org.skynet.services.processing.steps.DummyProcessingStep;
import org.skynet.services.processing.steps.transformers.json.Json2XmlStep;
import org.skynet.services.processing.steps.transformers.json.Xml2JsonStep;

public class SingletonProcessingStepTypeProvider {

	private static SingletonProcessingStepTypeProvider singleton = new SingletonProcessingStepTypeProvider();
	
	/**
	 * registered Maps contains all available type of maps.
	 * String is stepName (full qualified name) from query step
	 * Class is the class definition of the step.
	 */
	private Map<String, Class<?>> registeredMaps = new HashMap<String, Class<?>>();
	/**
	 * 
	 */
	private SingletonProcessingStepTypeProvider() {
		registeredMaps.put(DummyProcessingStep.class.getCanonicalName(), DummyProcessingStep.class);
		registeredMaps.put(Json2XmlStep.class.getCanonicalName(), Json2XmlStep.class);
		registeredMaps.put(Xml2JsonStep.class.getCanonicalName(), Xml2JsonStep.class);
	}
	
	public static SingletonProcessingStepTypeProvider getSingleton() {
		return singleton;
	}
	
	/**
	 * get the step definition of the map.
	 * @param step definition name
	 * @return step definition
	 */
	public Class<?> getStepDefinition(final String step) {
		if (registeredMaps.containsKey(step)) {
			return registeredMaps.get(step);
		}
		return null;
	}

}
