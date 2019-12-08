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
package org.skynet.services.interfaces;

import java.io.InputStream;
import java.io.OutputStream;

public interface IProcessingStep {
	default String process(final String input) throws Exception {
		return "processed:" + input;
	}
	
	default void process(final InputStream input, final OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int len;
		len = input.read(buffer);
		output.write("processed:".getBytes());
		while (len != -1) {
			output.write(buffer, 0, len);
		    len = input.read(buffer);
		}
	}
	
	default void setConfiguration(final String configuration) {
		
	}
	
	default void configure() {
		
	}
}