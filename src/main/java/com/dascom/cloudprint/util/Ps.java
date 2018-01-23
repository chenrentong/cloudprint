/*
 * Copyright (c) 2006-2007 Hyperic, Inc.
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

package com.dascom.cloudprint.util;

import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarProxy;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcTime;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.ProcUtil;

import com.dascom.cloudprint.test.SigarUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Show process status.显示进程专题
 */
public class Ps extends SigarCommandBase {

    public void output() throws SigarException {
        long[] pids = this.proxy.getProcList();

        for (int i=0; i<pids.length; i++) {
            long pid = pids[i];
            try {
                try {
					output(pid);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
            } catch (SigarException e) {
                this.err.println("Exception getting process info for " +
                                 pid + ": " + e.getMessage());
            }
        }
    }

    public static String join(List<String> info) {
        StringBuffer buf = new StringBuffer();
        Iterator<String> i = info.iterator();
        boolean hasNext = i.hasNext();
        while (hasNext) {
            buf.append(i.next());
            hasNext = i.hasNext();
            if (hasNext)
                buf.append("\t");
        }

        return buf.toString();
    }

    public static List<String> getInfo(SigarProxy sigar, long pid)
        throws SigarException, UnsupportedEncodingException {

        ProcState state = sigar.getProcState(pid);
        ProcTime time = null;
        String unknown = "???";

        List<String> info = new ArrayList<String>();
        info.add(String.valueOf(pid));	//pid

        try {
            ProcCredName cred = sigar.getProcCredName(pid);
            info.add(cred.getUser());	//用户
            //System.out.println(cred.getGroup());
           // System.out.println(state.getPriority());
        } catch (SigarException e) {
            info.add(unknown);
        }

        try {
            time = sigar.getProcTime(pid);
            info.add(getStartTime(time.getStartTime()));	//启动时间
        } catch (SigarException e) {
            info.add(unknown);
        }

        try {
            ProcMem mem = sigar.getProcMem(pid);
            info.add(Sigar.formatSize(mem.getSize()));	//虚拟内存大小
            info.add(Sigar.formatSize(mem.getResident()));	//驻留内存大小
            info.add(Sigar.formatSize(mem.getShare()));	//共享内存
        } catch (SigarException e) {
            info.add(unknown);
        }
        //System.out.println(state.getName());
        info.add(String.valueOf(state.getState()));	//状态
        //ProcExe exe = sigar.getProcExe(pid);
        //System.out.println(exe.getCwd());
        //System.out.println(exe.getCwd());
        if (time != null) {
            info.add(getCpuTime(time));	//CPU时间
        }
        else {
            info.add(unknown);
        }

        String name = ProcUtil.getDescription(sigar, pid);	//进程描述
        info.add(name);

        return info;
    }

    public void output(long pid) throws SigarException, UnsupportedEncodingException {
        println(join(getInfo(this.proxy, pid)));
    }

    public static String getCpuTime(long total) {
        long t = total / 1000;
        return t/60 + ":" + t%60;
    }

    public static String getCpuTime(ProcTime time) {
        return getCpuTime(time.getTotal());
    }

    private static String getStartTime(long time) {
        if (time == 0) {
            return "00:00";
        }
        long timeNow = System.currentTimeMillis();
        String fmt = "MMdd";

        if ((timeNow - time) < ((60*60*24) * 1000)) {	//今天启动时间
            fmt = "HH:mm";
        }

        return new SimpleDateFormat(fmt).format(new Date(time));
    }

    public static void main(String[] args) throws Exception {
    	Sigar sigar = SigarUtil.getInstance();
        new Ps().processCommand();
    }
}

            
