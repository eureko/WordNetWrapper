package it.unina.egc.wordnetwrapper.gui;

import it.unina.egc.wordnetwrapper.JWIUtils.SynsetNode;
import it.unina.egc.wordnetwrapper.core.WordNetWrapper;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class MainPanel extends JPanel implements TreeSelectionListener{
	
	JTree tree;
	JPanel detailPanel;
	
	JLabel glossLabel = new JLabel();
	
	
	public MainPanel() 
	{
		super(new GridLayout(1,0));
		
		//Create a tree that allows one selection at a time.
		tree = new JTree(getTreeHierarchy());
		tree.getSelectionModel().setSelectionMode
		        (TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setExpandsSelectedPaths(true);
		
		//Listen for when the selection changes.
		tree.addTreeSelectionListener(this);
		
		//Create the scroll pane and add the tree to it. 
		JScrollPane treeView = new JScrollPane(tree);
		
		//Create the HTML viewing pane.
		detailPanel = new JPanel(new BorderLayout());
		
		glossLabel.setFont(new Font(Font.SERIF, Font.BOLD, 24));
		detailPanel.add(glossLabel, BorderLayout.NORTH);
		
		
		
		
		
		JScrollPane definitionPanel = new JScrollPane(detailPanel);
		
		
		
			
		//Add the scroll panes to a split pane.
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setTopComponent(treeView);
		splitPane.setBottomComponent(definitionPanel);
		
		Dimension minimumSize = new Dimension(100, 50);
		definitionPanel.setMinimumSize(minimumSize);
		treeView.setMinimumSize(minimumSize);
		splitPane.setDividerLocation(100); 
		splitPane.setPreferredSize(new Dimension(500, 300));
		
		//Add the split pane to this panel.
		add(splitPane);

	}
	
	DefaultMutableTreeNode getTreeHierarchy()
	{
		IIndexWord idxWord = WordNetWrapper.JWIwrapper.dictionary.getIndexWord("entity", POS.NOUN);
		int wordIDsize = idxWord.getWordIDs().size();
		 				 
		 IWordID wordID = idxWord . getWordIDs().get(0);
		 
		 
		 IWord word = WordNetWrapper.JWIwrapper.dictionary.getWord( wordID );
		 
		 ISynset synset = word.getSynset();
		 String lemma = word.getLemma();
		 String gloss = word.getSynset().getGloss();
		 
		 DefaultMutableTreeNode top =
		            new DefaultMutableTreeNode(new SynsetNode(synset));
		 
		 
		 addLevel(top);
		 
		 return top;
	 
	}

	
	void addLevel(DefaultMutableTreeNode node)
	{
		ISynset synset = ((SynsetNode)node.getUserObject()).getSynset();
		List <ISynsetID> hyponyms = synset.getRelatedSynsets(Pointer.HYPONYM);
	
		for( ISynsetID sid : hyponyms ){
			
			ISynset hyponym = WordNetWrapper.JWIwrapper.dictionary.getSynset(sid);
			DefaultMutableTreeNode hyponymNode = new DefaultMutableTreeNode(new SynsetNode(hyponym));
			node.add(hyponymNode);
			addLevel(hyponymNode);
		}
		
		
		
	}
	
	
	public void valueChanged(TreeSelectionEvent event) {
		
		 DefaultMutableTreeNode selectedNode = 
			       (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		 
		 ISynset selectedSynset = ((SynsetNode)selectedNode.getUserObject()).getSynset();
		 System.out.println(selectedSynset.getGloss());
		 glossLabel.setText(selectedSynset.getGloss());
		
	}
	
	

}
