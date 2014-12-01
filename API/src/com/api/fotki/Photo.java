package com.api.fotki;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Photo
{
	private static Map<String , String>types;//<Format , MIME-type>
	private int[] widthes;
	private String name;
	private int id;
	private Type place;
	private String[] urls;
	private String[] contentTypes;
	private byte[] data;
	private PhotoManager parent;
	
	static
	{
		types = new HashMap<String, String>();
		
		types.put("jpeg", "image/jpeg");		
		types.put("jpg", "image/jpseg");		
		types.put("png", "image/png");		
		types.put("bmp", "image/bmp");
	}
	
	public enum Type{LOCAL,LINK};
	
	public String getName() {
		return name;
	}
	public String[] getUrls() {
		return urls;
	}
	
	public InputStream openInputStream(int size) throws IOException 
	{
		return parent.openInputStream(this, size);
	}
	public int[] getWidthes() {
		return widthes;
	}
	public int getId() {
		return id;
	}
	public Type getPlace() {
		return place;
	}
	public String[] getContentTypes() {
		return contentTypes;
	}
	public byte[] getData() {
		return data;
	}
	public PhotoManager getParent() {
		return parent;
	}
	public void setWidthes(int[] widthes) {
		this.widthes = widthes;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setPlace(Type place) {
		this.place = place;
	}
	public void setUrls(String[] urls) {
		this.urls = urls;
	}
	public void setContentTypes(String[] contentTypes) {
		this.contentTypes = contentTypes;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	public void setParent(PhotoManager parent) {
		this.parent = parent;
	}
	public boolean loadOnServer(String dir) throws IOException
	{
		if(this.place == Type.LOCAL)
			return parent.loadOnServer(this , dir);
		return false;
	}
	
	private Photo(){}
	public static Photo createLink(String name ,int id ,int[] widthes , String[] urls , PhotoManager pm)
	{
		Photo p = new Photo();
		p.id = id;
		p.name = name;
		p.widthes = widthes;
		p.urls = urls;
		p.parent = pm;
		p.place = Type.LINK;
		return p;
	}
	public static Photo createPhoto(String name,String contentType ,byte[] data, PhotoManager pm)
	{
		Photo p = new Photo();
		p.name = name;
		p.parent = pm;
		p.data = data;
		p.place = Type.LOCAL;
		p.contentTypes = new String[]{contentType};
		return p;
	}
	
}
