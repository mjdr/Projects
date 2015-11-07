package bot.ui;

import java.awt.TextField;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class SearchBar extends TextField implements TextListener
{
	
	
	private static final long serialVersionUID = 1L;
	private MainFrame parent;
	public SearchBar(MainFrame parent) 
	{
		this.parent = parent;
	}
	
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		addTextListener(this);
	}

	@Override
	public void textValueChanged(TextEvent e) 
	{
		parent.search(getText());
	}
}
