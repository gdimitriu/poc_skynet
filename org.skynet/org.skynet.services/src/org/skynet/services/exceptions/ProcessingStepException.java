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
package org.skynet.services.exceptions;

public class ProcessingStepException extends Exception {

	private static final long serialVersionUID = -688531409430355229L;

	public ProcessingStepException() {
	}

	/**
	 * @param s
	 */
	public ProcessingStepException(String s) {
		super(s);
	}

	/**
	 * @param throwable
	 */
	public ProcessingStepException(Throwable throwable) {
		super(throwable);
	}

	/**
	 * @param s
	 * @param throwable
	 */
	public ProcessingStepException(String s, Throwable throwable) {
		super(s, throwable);
	}

	/**
	 * @param s
	 * @param throwable
	 * @param flag
	 * @param flag1
	 */
	public ProcessingStepException(String s, Throwable throwable, boolean flag,
			boolean flag1) {
		super(s, throwable, flag, flag1);
	}

}
