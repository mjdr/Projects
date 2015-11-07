package com.api.fotki;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface PhotoManager 
{
	InputStream openInputStream(Photo file , int size) throws IOException;
	Photo[] getList(String dir) throws IOException;
	Map<String,String> getListOfDirs() throws IOException;
	boolean loadOnServer(Photo photo) throws IOException;
	boolean createDir(String dir) throws IOException;
	boolean removeDir(String dir) throws IOException;
	boolean existsDir(String dir) throws IOException;
}
