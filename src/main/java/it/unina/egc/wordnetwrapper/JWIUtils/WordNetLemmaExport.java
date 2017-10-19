package it.unina.egc.wordnetwrapper.JWIUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class WordNetLemmaExport 
{
	static JWIWrapper jwiwrapper;	
	
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			// Exporting terms (or words)
			
			//POS[] pos_values = POS.values();	
			FileWriter fileWriterWord = new FileWriter("export/WordNet_WordLemma.txt");
			fileWriterWord.write ("#WordNet Words' Lemma export\n");
						
						
			int i = 0;
			
			//for (POS pos:pos_values)
			//{
				Iterator<IIndexWord> wordIterator = jwiwrapper.dictionary.getIndexWordIterator(POS.NOUN);
				
				while(wordIterator.hasNext()) {
		
					IIndexWord indexWord = wordIterator.next();
					
					List<IWordID> wordIDs = indexWord.getWordIDs();
					
					for (IWordID wid : wordIDs)
					{
						IWord word = jwiwrapper.dictionary.getWord(wid);
						
						//System.out.println(i);
						System.out.println(word.getID());
						//System.out.println(word.getPOS());
						//System.out.println(word.getLemma());
						////System.out.println(word.getLexicalID());
						//System.out.println(sidMap.get(word.getSynset().getID().toString()).intValue()); // Legame con livello semantico
						////System.out.println(word.getAdjectiveMarker());
						
						//fileWriterWord.write(word.getLemma() + ", " + word.getID() + "\n");
						fileWriterWord.write(word.getID() + "\n");
						
					}
				}
			//}
			
			fileWriterWord.flush();
			fileWriterWord.close();
		
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	static String getLine(ISynset synset, int id)
	{
		String str = "" +  id + ",";
		str += synset.getID().toString() + ",";
		str += synset.getPOS().name() + ",";
		
		List<IWord> words = synset.getWords();
		str += "{";
		for( Iterator <IWord > w = words . iterator (); w. hasNext () ;){
			str += w. next ().getLemma ();
			if(w. hasNext ())
				str += "; ";
		}
		str += "}";
		str += ",";
		
		str += "\"" + synset.getGloss().replaceAll("\"", "\"\"") + "\"";
		
		return str;
	}
	
	/*static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}*/
}
