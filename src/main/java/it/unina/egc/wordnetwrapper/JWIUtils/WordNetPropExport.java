package it.unina.egc.wordnetwrapper.JWIUtils;

import it.unina.egc.wordnetwrapper.core.WordNetWrapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNetPropExport 
{
	static FileWriter fileWriter;
	static JWIWrapper jwiwrapper;
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			fileWriter = new FileWriter("WordNet_Prop_exp1.csv");
			
			IIndexWord idxWord = jwiwrapper.dictionary.getIndexWord("entity", POS.NOUN);
			//int wordIDsize = idxWord.getWordIDs().size();
			IWordID wordID = idxWord . getWordIDs().get(0);
			 			 
			 IWord word = jwiwrapper.dictionary.getWord( wordID );
			 
			 ISynset synset = word.getSynset();
				
			
			 
			 fileWriter.write ("Prop,Src,Dest\n");
			 addLevel(synset);
			 
			 fileWriter.flush();
			 fileWriter.close();
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void addLevel(ISynset synset) throws IOException
	{
		
		Map<IPointer, List<ISynsetID>> map = synset.getRelatedMap();
		
		for (Map.Entry<IPointer, List<ISynsetID>> entry : map.entrySet()) 
		{
			IPointer ipointer = entry.getKey();
			List<ISynsetID> synList = entry.getValue();
			
			Iterator<ISynsetID> iterator = synList.iterator();
			while (iterator.hasNext()) {
				ISynsetID synsetID = iterator.next();
				System.out.println(ipointer.getName() + "," + getIDNumber(synset.getID().toString()) + "," + getIDNumber(synsetID.toString()));
				 fileWriter.write (ipointer.getName() + "," + getIDNumber(synset.getID().toString()) + "," + getIDNumber(synsetID.toString()) +"\n");
			}
		}  
		
		List <ISynsetID> hyponyms = synset.getRelatedSynsets(Pointer.HYPONYM);
		
	
		for( ISynsetID sid : hyponyms ){
			
			ISynset hyponym = jwiwrapper.dictionary.getSynset(sid);
			
			addLevel(hyponym);
		}
		
		
	}
	
	static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}
		
		
}
	
	
