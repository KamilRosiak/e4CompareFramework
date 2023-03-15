/**
 * Copyright (c) 2019 JEP AUTHORS.
 *
 * This file is licensed under the the zlib/libpng License.
 *
 * This software is provided 'as-is', without any express or implied
 * warranty. In no event will the authors be held liable for any
 * damages arising from the use of this software.
 * 
 * Permission is granted to anyone to use this software for any
 * purpose, including commercial applications, and to alter it and
 * redistribute it freely, subject to the following restrictions:
 * 
 *     1. The origin of this software must not be misrepresented; you
 *     must not claim that you wrote the original software. If you use
 *     this software in a product, an acknowledgment in the product
 *     documentation would be appreciated but is not required.
 * 
 *     2. Altered source versions must be plainly marked as such, and
 *     must not be misrepresented as being the original software.
 * 
 *     3. This notice may not be removed or altered from any source
 *     distribution.
 */
package de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader.jep;

import de.tu_bs.cs.isf.e4cf.core.io.reader.python_reader.jep.python.MemoryManager;

/**
 * <p>
 * Allow access to Jep internal structure in sub-packages. This class should not
 * be used outside of the Jep project.
 * </p>
 * 
 * @author Ben Steffensmeier
 * 
 * @since 3.9
 */
public abstract class JepAccess {

    protected JepAccess() {
    }

    protected long getThreadState(Jep jep) {
        return jep.getThreadState();
    }

    protected ClassLoader getClassLoader(Jep jep) {
        return jep.getClassLoader();
    }

    protected MemoryManager getMemoryManager(Jep jep) {
        return jep.getMemoryManager();
    }
}
