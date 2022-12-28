package resn.logging;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	private File f;
	private FileWriter output ;
	String filename ;
	
	private Logger() {}
	
	private static Logger instance = new Logger() ;
	
	public static Logger getInstance() {return instance;}
	
	public void log(String msg) {
//		Long timeStamp = System.currentTimeMillis() ;
		try {
//			this.output.write(timeStamp.toString() + " " + msg + "\n");
			this.output.write(msg + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}
	
	public void setOutputDestination(String filename) {
		this.filename = filename ;
		
		try {
			this.f = new File(this.filename) ;
			this.output = new FileWriter(f, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	}
	
	public void logClose() {
		
		try {
			this.output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean canWrite() {
		boolean isWritable ;
		isWritable = this.f.canWrite()  ;
		return isWritable ;
	}
}
