/*
 Copyright (c) 2016 Gabriel Dimitriu All rights reserved.
 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

 This file is part of poc_skynet project.

 poc_skynet is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 poc_skynet is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with poc_skynet.  If not, see <http://www.gnu.org/licenses/>.
*/
package org.gdimitriu.skynet.jaxbutils;

public class SingletonJaxbContextFactory {
	private static  SingletonJaxbContextFactory singletonFactory = new SingletonJaxbContextFactory();
	
	private volatile JaxbContextFactory factory = null;
	/**
	 * 
	 */
	private SingletonJaxbContextFactory() {
	}
	
	/**
	 * get the singleton creation jar.
	 * @return singleton
	 */
	public static SingletonJaxbContextFactory getSingletonFactory() {
		return singletonFactory;
	}
	
	/**
	 * get the factory.
	 * @return jar creators factory
	 */
	public JaxbContextFactory getContextFactory() {
		if (this.factory == null) {
			synchronized (this) {
				if (this.factory == null) {
					this.factory = new JaxbContextFactory();
				}
			}
		}
		return this.factory;
	}

}
