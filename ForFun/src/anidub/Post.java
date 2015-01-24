package anidub;


public class Post 
{
	protected String title;
	protected String link;
	protected String author;
	protected String imageLink;
	protected int viewNumber;
	protected String description;
	public Post(
			String title, String link, 
			String author, String imageLink,
			int viewNumber, String description) 
	{
		this.title = title;
		this.link = link;
		this.author = author;
		this.imageLink = imageLink;
		this.viewNumber = viewNumber;
		this.description = description;
	}
	
	@Override
	public String toString() 
	{
		StringBuilder sb = new StringBuilder();
		sb.append("Title : ").append(title).append('\n');
		sb.append("Link  : ").append(link).append('\n');
		sb.append("Author: ").append(author).append('\n');
		sb.append("Views: ").append(viewNumber).append('\n');
		return sb.toString();
	}

	public String getTitle() 
	{
		return title;
	}

	public String getLink() 
	{
		return link;
	}

	public String getAuthor() 
	{
		return author;
	}

	public String getImageLink() 
	{
		return imageLink;
	}

	public int getViewNumber() 
	{
		return viewNumber;
	}

	public String getDescription() 
	{
		return description;
	}
	
	
	
}
