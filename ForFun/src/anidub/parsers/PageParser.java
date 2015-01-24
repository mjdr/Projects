package anidub.parsers;

import java.io.IOException;

import anidub.Page;

public interface PageParser 
{
	Page getPage(int page) throws IOException;
	Page getPage(String url) throws IOException;
}
