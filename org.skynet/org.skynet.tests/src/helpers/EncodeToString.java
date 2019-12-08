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
package helpers;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class EncodeToString {

	public EncodeToString() {
	}
	
	public static void main(String[] args) throws IOException{
		String path= args[0];
		FileInputStream inputStream =new FileInputStream(path);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int count =0;
		while((count = inputStream.read(buffer,0,buffer.length))>0 ) {
			outputStream.write(buffer,0,count);
		}
		inputStream.close();
		outputStream.close();
		System.out.println(Base64.getEncoder().encodeToString(outputStream.toByteArray()));
	}
}
