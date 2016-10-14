package Util;

import java.io.File;
import java.io.FilenameFilter;

public class FileFinder {
	
	public static String SAVEDIRECTORY = "SavedGames/";

	public static File[] FindFiles(String dirStr){
		
		File dir = new File(dirStr);
		
		return dir.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File directory, String filename) {
				// TODO Auto-generated method stub
				return filename.endsWith(".sav");
			}
			
		});
		
	}
	
	
	public static String[] CutDirectoryOut(File[] files,String directory){
		
		String[] games = new String[files.length];
		
		for(int f = 0; f < files.length; f++){
			
			games[f] = files[f].getPath().substring(directory.length());
		}
		
		return games; 
	}
	
	public static String[] RemoveExtension(String[] files){
		
		String[] removed = new String[files.length];
		
		for(int f = 0; f < files.length; f++){
			
			removed[f] = files[f].substring(0, files[f].length()-4);
		}
		
		return removed;
	}
}
