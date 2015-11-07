package bot.ui;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bot.Loader;
import bot.Post;

public class TitleList extends JList<Post> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	
	DefaultListModel<Post>model = new DefaultListModel<Post>();
	MainFrame parent;
	
	public TitleList(MainFrame parent) 
	{
		this.parent = parent;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setModel(model);
	}
	
	public void init()
	{
		set(Loader.Local.getAll());
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		addListSelectionListener(this);
	}
	
	public void search(String s)
	{
		if(s.length() == 0)
			set(Loader.Local.getAll());
		else
			set(Loader.Local.findPost(s));
		repaint();
	}
	
	public void set(List<Post>list)
	{
		model.removeAllElements();
		for(Post p : list)
			if(p.torrentLinks != null && p.torrentLinks.length > 0)
				model.addElement(p);
	}
	
	public Post getElementAt(int i)
	{
		if(i < 0 || i > model.size())
			return null;
		return model.getElementAt(i);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		if(!e.getValueIsAdjusting())
			parent.postChanged(getElementAt(getSelectedIndex()));
	}
	
}
