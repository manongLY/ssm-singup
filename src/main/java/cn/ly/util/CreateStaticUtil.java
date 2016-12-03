package cn.ly.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.List;

public class CreateStaticUtil<T> {
	public void create(File file,List<T> all){
		PrintStream out = null ;
		try {
			out = new PrintStream(new FileOutputStream(file));
			Iterator<T> iter = all.iterator();
			while(iter.hasNext()){
				out.print(iter.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
	
	public void createJSON(File file,Object obj){
		PrintStream out = null ;
		try {
			out = new PrintStream(file);
			out.print(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}
}
