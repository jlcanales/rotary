/*
Copyright (c) 2013 J. L. Canales Gasco
 
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA}]
*/
package org.rotarysource.signals;

/**
 * Interface to add capabilities to be managed by the internal 
 * signal system. 
 * Each method implements the component behavior to process the
 * received signal.
 * 
 * @author J.L. Canales
 */
public interface SignalCapable {
	
	/**
	 * Process Shutdown signal.
	 */
	public void shutdown();

}
