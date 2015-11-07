package bot.ui;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import bot.Post;

public class TorrentsList extends JList<String> implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	

	DefaultListModel<String>model = new DefaultListModel<String>();
	MainFrame parent;
	
	public TorrentsList(MainFrame parent) 
	{
		this.parent = parent;
		setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setModel(model);
		setPreferredSize(new Dimension(100,600));
	}
	public void set(Post p)
	{
		model.removeAllElements();
		if(p == null) return;
		for(int i = 0;i < p.torrentLinks.length;i++)
			model.addElement(""+(i+1));
	}
	
	@Override
	public void addNotify() 
	{
		super.addNotify();
		addListSelectionListener(this);
	}
	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		if(!e.getValueIsAdjusting() && getSelectedIndex() != -1)
			parent.loadTorrent(Integer.parseInt(model.getElementAt(getSelectedIndex())));
	}
}
