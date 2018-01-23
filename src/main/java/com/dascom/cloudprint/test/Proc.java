package com.dascom.cloudprint.test;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetFlags;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.OperatingSystem;
import org.hyperic.sigar.ProcCpu;
import org.hyperic.sigar.ProcCred;
import org.hyperic.sigar.ProcCredName;
import org.hyperic.sigar.ProcExe;
import org.hyperic.sigar.ProcFd;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcStat;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;
import org.hyperic.sigar.Uptime;
import org.hyperic.sigar.Who;
import org.hyperic.sigar.cmd.ProcModuleInfo;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.SigarCommandBase;
  
/** 
 * @author vane 
 * @监控系统中运行的批处理文件 
 */  
public class Proc extends SigarCommandBase{  
      

      
    public static void main(String[] args) throws SigarException {
    	List<ProcessInfo> slist=getProcessInfo();
    	System.out.println(slist.size());
    	for(ProcessInfo s:slist){
    		System.out.println(s);
    	}
    	//a();
    	//b();
    /*	Sigar sigar = SigarUtil.getInstance();
    	long[] pids = sigar.getProcList();
    	  Ps ps = new Ps();*/
    	  

    }  
    
    public static void b() throws SigarException{
    	Sigar sigar = SigarUtil.getInstance();
    	// CPU数量（单位：个）  
    	int cpuLength = sigar.getCpuInfoList().length;  
    	System.out.println(cpuLength);  
    	  
    	// CPU的总量（单位：HZ）及CPU的相关信息  
    	CpuInfo infos[] = sigar.getCpuInfoList();  
    	for (int i = 0; i < infos.length; i++) {// 不管是单块CPU还是多CPU都适用  
    	    CpuInfo info = infos[i];  
    	    System.out.println("mhz=" + info.getMhz());// CPU的总量MHz  
    	    System.out.println("vendor=" + info.getVendor());// 获得CPU的卖主，如：Intel  
    	    System.out.println("model=" + info.getModel());// 获得CPU的类别，如：Celeron  
    	    System.out.println("cache size=" + info.getCacheSize());// 缓冲存储器数量  
    	}  
    	System.out.println("-----------------");
    	// 方式二，不管是单块CPU还是多CPU都适用  
    	CpuPerc cpuList[] = null;  
    	try {  
    	    cpuList = sigar.getCpuPercList();  
    	} catch (SigarException e) {  
    	    e.printStackTrace();  
    	}  
    	for (int i = 0; i < cpuList.length; i++) {  
    	     System.out.println(cpuList[i].getIdle());
    	} 
    	System.out.println("-----------------");
    	// 取到当前机器的MAC地址  
    	String[] ifaces = sigar.getNetInterfaceList();  
    	String hwaddr = null;  
    	for (int i = 0; i < ifaces.length; i++) {  
    	    NetInterfaceConfig cfg = sigar.getNetInterfaceConfig(ifaces[i]);  
    	    if (NetFlags.LOOPBACK_ADDRESS.equals(cfg.getAddress())  
    	            || (cfg.getFlags() & NetFlags.IFF_LOOPBACK) != 0  
    	            || NetFlags.NULL_HWADDR.equals(cfg.getHwaddr())) {  
    	        continue;  
    	    }  
    	    hwaddr = cfg.getHwaddr();  
    	    
    	    // break;  
    	}  
    	System.out.println(hwaddr);
    	//print(hwaddr != null ? hwaddr : null);
    	System.out.println("-----------------");
    	// 物理内存信息  
    	Mem mem = sigar.getMem();  
    	// 内存总量  
    	System.out.println("Total = " + mem.getTotal() / 1024L / 1024 + "M av");  
    	// 当前内存使用量  
    	System.out.println("Used = " + mem.getUsed() / 1024L / 1024 + "M used");  
    	// 当前内存剩余量  
    	System.out.println("Free = " + mem.getFree() / 1024L / 1024 + "M free");  
    	System.out.println("-----------------");
    	// 系统页面文件交换区信息  
    	Swap swap = sigar.getSwap();  
    	// 交换区总量  
    	System.out.println("Total = " + swap.getTotal() / 1024L + "K av");  
    	// 当前交换区使用量  
    	System.out.println("Used = " + swap.getUsed() / 1024L + "K used");  
    	// 当前交换区剩余量  
    	System.out.println("Free = " + swap.getFree() / 1024L + "K free"); 
    	System.out.println("-----------------");
    	// 取到当前操作系统的名称  
    	String hostname = "";  
    	try {  
    	    hostname = InetAddress.getLocalHost().getHostName();  
    	} catch (Exception exc) {  
    	    try {  
    	        hostname = sigar.getNetInfo().getHostName();  
    	    } catch (SigarException e) {  
    	        hostname = "localhost.unknown";  
    	    } finally {  
    	        sigar.close();  
    	    }  
    	}  
    	System.out.println(hostname);  
    	
    	// 取当前操作系统的信息  
    	OperatingSystem OS = OperatingSystem.getInstance();  
    	// 操作系统内核类型如： 386、486、586等x86  
    	System.out.println("OS.getArch() = " + OS.getArch());  
    	System.out.println("OS.getCpuEndian() = " + OS.getCpuEndian());//  
    	System.out.println("OS.getDataModel() = " + OS.getDataModel());//  
    	// 系统描述  
    	System.out.println("OS.getDescription() = " + OS.getDescription());  
    	System.out.println("OS.getMachine() = " + OS.getMachine());//  
    	// 操作系统类型  
    	System.out.println("OS.getName() = " + OS.getName());  
    	System.out.println("OS.getPatchLevel() = " + OS.getPatchLevel());//  
    	// 操作系统的卖主  
    	System.out.println("OS.getVendor() = " + OS.getVendor());  
    	// 卖主名称  
    	System.out  
    	        .println("OS.getVendorCodeName() = " + OS.getVendorCodeName());  
    	// 操作系统名称  
    	System.out.println("OS.getVendorName() = " + OS.getVendorName());  
    	// 操作系统卖主类型  
    	System.out.println("OS.getVendorVersion() = " + OS.getVendorVersion());  
    	// 操作系统的版本号  
    	System.out.println("OS.getVersion() = " + OS.getVersion());  
    	
    	// 取当前系统进程表中的用户信息  
    	Who who[] = sigar.getWhoList();  
    	if (who != null && who.length > 0) {  
    	    for (int i = 0; i < who.length; i++) {  
    	    	System.out.println("\n~~~~~~~~~" + String.valueOf(i) + "~~~~~~~~~");  
    	        Who _who = who[i];  
    	        System.out.println("getDevice() = " + _who.getDevice());  
    	        System.out.println("getHost() = " + _who.getHost());  
    	        System.out.println("getTime() = " + _who.getTime());  
    	        // 当前系统进程表中的用户名  
    	        System.out.println("getUser() = " + _who.getUser());  
    	    }  
    	} 
    	
    	
    	// 取硬盘已有的分区及其详细信息（通过sigar.getFileSystemList()来获得FileSystem列表对象，然后对其进行编历  
    	FileSystem fslist[] = sigar.getFileSystemList();  
    	String dir = System.getProperty("user.home");// 当前用户文件夹路径  
    	System.out.println(dir + "  " + fslist.length);  
    	for (int i = 0; i < fslist.length; i++) {  
    		System.out.println("\n~~~~~~~~~~" + i + "~~~~~~~~~~");  
    	FileSystem fs = fslist[i];  
    	// 分区的盘符名称  
    	System.out.println("fs.getDevName() = " + fs.getDevName());  
    	// 分区的盘符名称  
    	System.out.println("fs.getDirName() = " + fs.getDirName());  
    	System.out.println("fs.getFlags() = " + fs.getFlags());//  
    	// 文件系统类型，比如 FAT32、NTFS  
    	System.out.println("fs.getSysTypeName() = " + fs.getSysTypeName());  
    	// 文件系统类型名，比如本地硬盘、光驱、网络文件系统等  
    	System.out.println("fs.getTypeName() = " + fs.getTypeName());  
    	// 文件系统类型  
    	System.out.println("fs.getType() = " + fs.getType());  
    	FileSystemUsage usage = null;  
    	try {  
    	    usage = sigar.getFileSystemUsage(fs.getDirName());  
    	} catch (SigarException e) {  
    	    if (fs.getType() == 2)  
    	        throw e;  
    	    continue;  
    	}  
    	switch (fs.getType()) {  
    	case 0: // TYPE_UNKNOWN ：未知  
    	    break;  
    	case 1: // TYPE_NONE  
    	    break;  
    	case 2: // TYPE_LOCAL_DISK : 本地硬盘  
    	    // 文件系统总大小  
    		System.out.println(" Total = " + usage.getTotal() + "KB");  
    	    // 文件系统剩余大小  
    		System.out.println(" Free = " + usage.getFree() + "KB");  
    	    // 文件系统可用大小  
    		System.out.println(" Avail = " + usage.getAvail() + "KB");  
    	    // 文件系统已经使用量  
    		System.out.println(" Used = " + usage.getUsed() + "KB");  
    	    double usePercent = usage.getUsePercent() * 100D;  
    	    // 文件系统资源的利用率  
    	    System.out.println(" Usage = " + usePercent + "%");  
    	    break;  
    	case 3:// TYPE_NETWORK ：网络  
    	    break;  
    	case 4:// TYPE_RAM_DISK ：闪存  
    	    break;  
    	case 5:// TYPE_CDROM ：光驱  
    	    break;  
    	case 6:// TYPE_SWAP ：页面交换  
    	    break;  
    	}  
    	System.out.println(" DiskReads = " + usage.getDiskReads());  
    	System.out.println(" DiskWrites = " + usage.getDiskWrites());  
    	} 
    }
    
    //默认
    public static void testLib() {
		try {
			Sigar sigar = SigarUtil.getInstance();
			CpuPerc cpu = sigar.getCpuPerc();
			System.out.println("这是" + cpu.getUser());
			System.out.println("这是" + String.valueOf(cpu.getCombined()));
			
		} catch (SigarException e) {
			e.printStackTrace();
		}
	}
    
    public static void a(){
    	Sigar sigar = SigarUtil.getInstance();
    	try {
    		   long[] pids = sigar.getProcList();
    		   double total = 0;
    		   Map<String, Long> procName5PidMap = new HashMap<String, Long>();	//存储进程信息
    		   for (long pid : pids) {
    		    
    		    ProcState prs = sigar.getProcState(pid);	//ProcState使用
    		    ProcCpu pCpu = new ProcCpu();
    		    ProcCpu procCpu = sigar.getProcCpu(String.valueOf(pid));
    		    System.out.println("CPU占用率percent" + procCpu.getPercent()
    		    		+ " CPU总量Totala" + procCpu.getTotal());
    		    		
    		   // pCpu.gather(sigar, pid);
    		   // System.out.print(prs.getName());
    		    System.out.println(" pid: " + pid + " name:" + prs.getName());
    		    
    		    total += pCpu.getTotal();
    		    procName5PidMap.put(prs.getName(), pid);
    		   }
    		   System.out.println("total : " + total);
    		   
    		   
    		   //cpu信息
    		   Cpu cpu = sigar.getCpu();
    		   System.out.println("idle: " + cpu.getIdle());//获取整个系统cpu空闲时间
    		   System.out.println("irq: " + cpu.getIrq());
    		   System.out.println("nice: " + cpu.getNice());
    		   System.out.println("soft irq: " + cpu.getSoftIrq());
    		   System.out.println("stolen: " + cpu.getStolen());
    		   System.out.println("sys: " + cpu.getSys());
    		   System.out.println("total: " + cpu.getTotal());
    		   System.out.println("user: " + cpu.getUser());
    		   System.out.println();
    		   
    		   
    		   CpuPerc perc = sigar.getCpuPerc();
    		   System.out.println("整体cpu的占用情况:");
    		   System.out.println("system idle: " + perc.getIdle());//获取当前cpu的空闲率
    		   System.out.println("conbined: "+ perc.getCombined());//获取当前cpu的占用率
    		   
    		   
    		   CpuPerc[] cpuPercs = sigar.getCpuPercList();
    		   System.out.println("每个cpu的资源占用情况:");
    		   for (CpuPerc cpuPerc : cpuPercs) {
    		    System.out.println("system idle: " + cpuPerc.getIdle());//获取当前cpu的空闲率
    		    System.out.println("conbined: "+ cpuPerc.getCombined());//获取当前cpu的占用率
    		    System.out.println();
    		   }
    		   
    		   
    		   NetInfo netInfo = sigar.getNetInfo();
    		   System.out.println("网络信息:");
    		   System.out.println("gateway:" + netInfo.getDefaultGateway());
    		   System.out.println("domain name:" + netInfo.getDomainName());
    		   System.out.println("primary dns:" + netInfo.getPrimaryDns());
    		   while (true) {
    		    System.out.println("pid: " + sigar.getPid());//获取当前java进程的pid
    		    Map map = sigar.getProcEnv(sigar.getPid());//进程环境情况
    		    System.out.println("wait...");
    		    Thread.currentThread().sleep(10000000);
    		   }
    		  } catch (SigarException e) {
    		   // TODO Auto-generated catch block
    		   e.printStackTrace();
    		  } catch (InterruptedException e) {
    		   // TODO Auto-generated catch block
    		   e.printStackTrace();
    		  }
    }
    
    //获取进程
    public static List<ProcessInfo> getProcessInfo() throws SigarException {
    	Sigar sigar = SigarUtil.getInstance();
        Ps ps = new Ps();	//使用ps
        List<ProcessInfo> processInfos = new ArrayList<ProcessInfo>();
        try {
            long[] pids = sigar.getProcList();	//pids
            for (long pid : pids) {
            	 ProcState prs = sigar.getProcState(pid);	//ProcState使用
    
                List<String> list = ps.getInfo(sigar, pid);	//ps
                ProcessInfo info = new ProcessInfo();
                for (int i = 0; i <= list.size(); i++) {
                    switch (i) {
                    case 0:    info.setPid(list.get(0));    break;	//进程id
                    case 1: info.setUser(list.get(1));    break;	//用户
                    case 2: info.setStartTime(list.get(2));    break;	//开启时间
                    case 3:    info.setMemSize(list.get(3));    break;
                    case 4:    info.setMemUse(list.get(4));    break;
                    case 5:    info.setMemhare(list.get(5));    break;
                    case 6:    info.setState(list.get(6));    break;
                    case 7:    info.setCpuTime(list.get(7));    break;
                    case 8:    info.setName(list.get(8));    break;	//进程名字
                    }
                }
                processInfos.add(info);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }
        return processInfos;
    }
    
    
    /** 
     * 输出信息 
     */  
    public static void PrintInfo(){  
        Properties props=System.getProperties();  
        System.out.println("Java的运行环境版本："+props.getProperty("java.version"));   
        System.out.println("默认的临时文件路径："+props.getProperty("java.io.tmpdir"));   
        System.out.println("操作系统的名称："+props.getProperty("os.name"));   
        System.out.println("操作系统的构架："+props.getProperty("os.arch"));   
        System.out.println("操作系统的版本："+props.getProperty("os.version"));   
        System.out.println("文件分隔符："+props.getProperty("file.separator"));   //在 unix 系统中是＂／＂   
        System.out.println("路径分隔符："+props.getProperty("path.separator"));   //在 unix 系统中是＂:＂   
        System.out.println("行分隔符："+props.getProperty("line.separator"));   //在 unix 系统中是＂/n＂  
        System.out.println("用户的账户名称："+props.getProperty("user.name"));   
        System.out.println("用户的主目录："+props.getProperty("user.home"));   
        System.out.println("用户的当前工作目录："+props.getProperty("user.dir"));  
    }

	@Override
	public void output(String[] arg0) throws SigarException {
		// TODO Auto-generated method stub
		
	}  
}  