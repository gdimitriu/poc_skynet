/**
 * 
 */
package org.skynet.upgrade.loaders.interfaces;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Gabriel
 *
 */
public interface IClassServices {

	/**
	 * get the byteCode from input xml/json etc.
	 * @param name of the class
	 * @return data class
	 */
	String getByteCode(String name);
	
	/**
	 * get all bytecodes from input xml/json etc.
	 * @return array of data class
	 */
	String[] getAllByteCodes();
	
	/**
	 * get all class names from the input xml/json etc.
	 * @return list of class names
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 */
	String[] getAllClassNames() throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException;
}
