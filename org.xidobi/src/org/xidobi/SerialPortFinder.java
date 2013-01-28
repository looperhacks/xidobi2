/*
 * Copyright 2013 Gemtec GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xidobi;

import java.util.Set;

import javax.annotation.Nonnull;

/**
 * Interface for classes which can find informations of all serial ports that are installed on the
 * system.
 * 
 * @author Tobias Bre�ler
 * 
 * @see SerialPortInfo
 */
public interface SerialPortFinder {

	/**
	 * Returns a {@link Set} with all serial ports that are installed on the system.
	 * 
	 * @return a {@link Set} with serial ports or an empty {@link Set} if no ports are available,
	 *         but never <code>null</code>
	 */
	@Nonnull
	Set<SerialPort> getAll();

}
