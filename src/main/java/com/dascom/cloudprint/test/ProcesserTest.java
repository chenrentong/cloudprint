package com.dascom.cloudprint.test;

/** 
 * @author pqcc 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;

public class ProcesserTest {
	private static Logger log = Logger.getLogger(ProcesserTest.class.getName());

	public static void main(String[] args) {
		new ProcesserTest().printSystemProcessor();
	}
	
	//名称
	//pid
	//会话名
	//会话
	//内存使用
	public void printSystemProcessor() {
		Process process = null;
		String command = getExecCommand();
		try {
			process = Runtime.getRuntime().exec(command);
			BufferedReader input = new BufferedReader(new InputStreamReader(
					process.getInputStream(),"GBK"));
			String line = "";
			while ((line = input.readLine()) != null) {
				log.info(line);
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getExecCommand() {
		// default is windows.
		String command = "tasklist";
		String osName = System.getProperty("os.name");
		if (osName != null && osName.indexOf("Windows") < 0) {
			command = "ps aux";
		}
		return command;
	}

}
