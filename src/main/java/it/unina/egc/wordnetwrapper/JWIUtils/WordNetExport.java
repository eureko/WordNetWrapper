package it.unina.egc.wordnetwrapper.JWIUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNetExport 
{
	static FileWriter fileWriter;
	static JWIWrapper jwiwrapper;
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			fileWriter = new FileWriter("WordNet_Nouns_exp1.csv");
			
			IIndexWord idxWord = jwiwrapper.dictionary.getIndexWord("entity", POS.NOUN);
			//int wordIDsize = idxWord.getWordIDs().size();
			IWordID wordID = idxWord . getWordIDs().get(0);
			 			 
			 IWord word = jwiwrapper.dictionary.getWord( wordID );
			 
			 ISynset synset = word.getSynset();
				
			 
			 fileWriter.write ("SID,POS,words,gloss\n");
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
		
		String line = getLine(synset);
		System.out.println(line);
		fileWriter.write(line + "\n");
		List <ISynsetID> hyponyms = synset.getRelatedSynsets(Pointer.HYPONYM);
		
	
		for( ISynsetID sid : hyponyms ){
			
			ISynset hyponym = jwiwrapper.dictionary.getSynset(sid);
			
			addLevel(hyponym);
		}
		
		
		
	}
	
	static String getLine(ISynset synset)
	{
		String str = getIDNumber(synset.getID().toString()) + ",";
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
		
		str += synset.getGloss();
		
		
		
		
		return str;
	}
	
	static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}
}
