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

import java.util.ArrayList;
import java.util.List;

import org.skynet.services.interfaces.IProcessingStep;

public class ProcessingFlow {
	
	private List<IProcessingStep> steps = new ArrayList<IProcessingStep>();

	public String flowName = null;
	public ProcessingFlow() {
	}

	public ProcessingFlow(final String flowName) {
		this.flowName = flowName;
	}

	/**
	 * getter for flow name.
	 * @return flow name of this processing flow.
	 */
	public String getFlowName() {
		return flowName;
	}

	/**
	 * setter for flow name.
	 * @param flowName the flow name of this processing flow.
	 */
	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}
	
	public void addStep(final IProcessingStep step) {
		steps.add(step);
	}
	
	public IProcessingStep getFirstStep() {
		return steps.get(0);
	}
	
	public IProcessingStep getStep(final int index) {
		if (index > steps.size()) {
			return null;
		}
		return steps.get(index);
	}
	
	public List<IProcessingStep> getListSteps() {
		return steps;
	}
}
