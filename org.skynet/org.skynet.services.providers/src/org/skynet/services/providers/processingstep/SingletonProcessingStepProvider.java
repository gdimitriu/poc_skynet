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

import org.skynet.services.interfaces.IProcessingStep;
import org.skynet.services.providers.processingstep.SingletonProcessingStepTypeProvider;

public class SingletonProcessingStepProvider {
	
	private static SingletonProcessingStepProvider singleton = new SingletonProcessingStepProvider();
	/**
	 * cache of steps that was already executed:
	 * String is compose from flow:stepName from query (name:step)
	 * IProcessingStep is the initialized step
	 */
	private Map<String, IProcessingStep> cacheOfSteps = new HashMap<String, IProcessingStep>();
	
	
	
	/**
	 * 
	 */
	private SingletonProcessingStepProvider() {

	}
	
	public static SingletonProcessingStepProvider getSingleton() {
		return singleton;
	}
	
	/**
	 * create the cache string query
	 * @param flow from query name
	 * @param step from query step
	 * @return formated string for cache
	 */
	private String createCacheQuery(final String flow, final String step) {
		return flow + ":" + step;
	}
	
	/**
	 * return the initialized step.
	 * @param flow from query name
	 * @param step from query step
	 * @return Initialized flow
	 */
	public IProcessingStep getProcessingStep(final String flow, final String step) {
		if (cacheOfSteps.containsKey(createCacheQuery(flow, step))) {
			return cacheOfSteps.get(createCacheQuery(flow, step));
		}
		Class<?> stepDefinition = SingletonProcessingStepTypeProvider.getSingleton().getStepDefinition(step);
		if (stepDefinition == null) {
			return null;
		}
		try {
			IProcessingStep processingStep = (IProcessingStep) stepDefinition.newInstance();
			putStepInCache(flow, step, processingStep);
			return processingStep;
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * put into the cache the initialized step.
	 * @param flow from query name
	 * @param step from query step
	 * @param processingStep the created processing step
	 */
	public void putStepInCache(final String flow, final String step, final IProcessingStep processingStep) {
		cacheOfSteps.put(createCacheQuery(flow, step), processingStep);
	}
}
