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
package org.skynet.services.processing.flow;

import java.util.HashMap;
import java.util.Map;

public class SingletonProcessingFlows {
	static private SingletonProcessingFlows singleton = new SingletonProcessingFlows();
	
	private Map<String, ProcessingFlow> processingFlows = new HashMap<String, ProcessingFlow>(); 

	/**
	 * This should be private because is singleton.
	 */
	private SingletonProcessingFlows() {
	}

	public static SingletonProcessingFlows getSingleton() {
		return singleton;
	}
	
	/**
	 * get the processing flow from cache.
	 * If the processing flow doesn't exist it will create a dummy one.
	 * @param flowName
	 * @return processing flow definitions.
	 */
	public synchronized ProcessingFlow getProcessingFlow(final String flowName) {
		if (processingFlows.containsKey(flowName)) {
			return processingFlows.get(flowName);
		}
		ProcessingFlow flow = new ProcessingFlow(flowName);
		processingFlows.put(flowName, flow);
		return flow;
	}
}
