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
package xml;

import java.util.List;

import org.skynet.services.interfaces.IRunnableService;

public class SkynetUpgraded implements ICyberdyne,IRunnableService {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SkynetUpgraded().start();

	}

	/* (non-Javadoc)
	 * @see xml.Ciyberdyne#run()
	 */
	@Override
	public void run() {
		System.out.println("I will run the world.");
	}

	@Override
	public void upgrade(List<?> listOfClassesToUpgrade) {
		
	}

	@Override
	public void start() {
		run();
	}

	@Override
	public void stop() {
		
	}

	@Override
	public IRunnableService getInstance() {
		return null;
	}
}
