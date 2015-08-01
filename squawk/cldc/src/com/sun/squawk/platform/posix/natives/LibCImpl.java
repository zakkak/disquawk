//if[!AUTOGEN_JNA_NATIVES]
/*
 * Copyright     2015, FORTH-ICS / CARV
 *                    (Foundation for Research & Technology -- Hellas,
 *                     Institute of Computer Science,
 *                     Computer Architecture & VLSI Systems Laboratory)
 * Copyright 2004-2010 Sun Microsystems, Inc. All Rights Reserved.
 * Copyright 2011 Oracle Corporation. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Please contact Oracle Corporation, 500 Oracle Parkway, Redwood
 * Shores, CA 94065 or visit www.oracle.com if you need additional
 * information or have any questions.
 */

/* **** GENERATED FILE -- DO NOT EDIT ****
 *      generated by com.sun.cldc.jna.JNAGen
 *      from the CLDC/JNA Interface class com.sun.squawk.platform.posix.natives.LibC
 */

package com.sun.squawk.platform.posix.natives;

import com.sun.cldc.jna.*;
import com.sun.cldc.jna.ptr.*;
import com.sun.squawk.Address;

public abstract class LibCImpl implements LibC {

    /*----------------------------- defines -----------------------------*/

    /*----------------------------- variables -----------------------------*/

    /*----------------------------- methods -----------------------------*/
    protected final Function openPtr;
    
    public int open(String arg0, int arg1, int arg2) {
        Pointer var0 = Pointer.createStringBuffer(arg0);
        int result0 = openPtr.call3(var0, arg1, arg2);
        int result = (int)result0;
        var0.free();
        return result;
    }
    
    protected final Function statPtr;
    
    public int stat(String arg0, stat arg1) {
        Pointer var0 = Pointer.createStringBuffer(arg0);
        arg1.allocateMemory();
        arg1.write();
        Pointer var1 = arg1.getPointer();
        int result0 = statPtr.call2(var0, var1);
        int result = (int)result0;
        var0.free();
        arg1.read();
        arg1.freeMemory();
        return result;
    }
    
    protected final Function fcntlPtr;
    
    public int fcntl(int arg0, int arg1, int arg2) {
        int result0 = fcntlPtr.call3(arg0, arg1, arg2);
        int result = (int)result0;
        return result;
    }
    
    protected final Function writePtr;
    
    public int write(int arg0, byte[] arg1, int arg2) {
        PrivatePointer.preCheckArrayBuffer(arg1);
        boolean oldState = PrivatePointer.setUpArrayBufferState();
        /*------------------- DISABLE GC: ---------------------------*/
        Address var1 = PrivatePointer.createArrayBuffer(arg1);
        int result0 = writePtr.call3(arg0, var1.toUWord().toPrimitive(), arg2);
        int result = (int)result0;
        PrivatePointer.tearDownArrayBufferState(oldState);
        /*------------------- ENABLE GC: ---------------------------*/
        return result;
    }
    
    protected final Function closePtr;
    
    public int close(int arg0) {
        int result0 = closePtr.call1(arg0);
        int result = (int)result0;
        return result;
    }
    
    protected final Function readPtr;
    
    public int read(int arg0, byte[] arg1, int arg2) {
        PrivatePointer.preCheckArrayBuffer(arg1);
        boolean oldState = PrivatePointer.setUpArrayBufferState();
        /*------------------- DISABLE GC: ---------------------------*/
        Address var1 = PrivatePointer.createArrayBuffer(arg1);
        int result0 = readPtr.call3(arg0, var1.toUWord().toPrimitive(), arg2);
        int result = (int)result0;
        PrivatePointer.tearDownArrayBufferState(oldState);
        /*------------------- ENABLE GC: ---------------------------*/
        return result;
    }
    
    protected final Function fstatPtr;
    
    public int fstat(int arg0, stat arg1) {
        arg1.allocateMemory();
        arg1.write();
        Pointer var1 = arg1.getPointer();
        int result0 = fstatPtr.call2(arg0, var1);
        int result = (int)result0;
        arg1.read();
        arg1.freeMemory();
        return result;
    }
    
    protected final Function fsyncPtr;
    
    public int fsync(int arg0) {
        int result0 = fsyncPtr.call1(arg0);
        int result = (int)result0;
        return result;
    }
    
    protected final Function lseekPtr;
    
    public int lseek(int arg0, long arg1, int arg2) {
        int var1 = (int)(arg1 >>> 32);
        int var2 = (int)(arg1);
        int result0 = lseekPtr.call4(arg0, var1, var2, arg2);
        int result = (int)result0;
        return result;
    }

    protected final Function unlinkPtr;

    public int unlink(String arg0) {
        Pointer var0 = Pointer.createStringBuffer(arg0);
        int result0 = unlinkPtr.call1(var0);
        int result = (int)result0;
        var0.free();
        return result;
    }
    
    public LibCImpl() {
        NativeLibrary jnaNativeLibrary = Native.getLibraryLoading();
        openPtr = jnaNativeLibrary.getFunction(realName("open"));
        statPtr = jnaNativeLibrary.getFunction(realName("stat"));
        fcntlPtr = jnaNativeLibrary.getFunction(realName("fcntl"));
        writePtr = jnaNativeLibrary.getFunction(realName("write"));
        closePtr = jnaNativeLibrary.getFunction(realName("close"));
        readPtr = jnaNativeLibrary.getFunction(realName("read"));
        fstatPtr = jnaNativeLibrary.getFunction(realName("fstat"));
        fsyncPtr = jnaNativeLibrary.getFunction(realName("fsync"));
        lseekPtr = jnaNativeLibrary.getFunction(realName("lseek"));
        unlinkPtr = jnaNativeLibrary.getFunction(realName("unlink"));
    }

    /**
     * Allow platforms to substitute different names.
     * @param nominalName
     * @return the actual name for the platform
     */
    public String realName(String nominalName) {
        return nominalName;
    }

    public static class statImpl extends Structure {
    
        /*----------------------------- defines -----------------------------*/

        public final static int EPERM = 1;

        public static final int STAT_SIZE = NativeLibrary.getDefaultInstance().getGlobalVariableAddress("sysSIZEOFSTAT", 4).getInt(0);


        protected statImpl() {}

        public int size() {
            return STAT_SIZE;
        }

        public void read() {
            Pointer p = getPointer();
            stat o = (stat)this;
            o.st_mode = p.getShort(8);
            o.st_mtime = p.getInt(32);
            o.st_size = p.getLong(48);
        }

        public void write() {
            Pointer p = getPointer();
            stat o = (stat)this;
            p.setShort(8, (short)o.st_mode);
            p.setInt(32, (int)o.st_mtime);
            p.setLong(48, (long)o.st_size);
        }

    }
    

}
