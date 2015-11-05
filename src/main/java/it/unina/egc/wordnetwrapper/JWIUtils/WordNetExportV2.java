package it.unina.egc.wordnetwrapper.JWIUtils;

import it.unina.egc.wordnetwrapper.core.WordNetWrapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;
import edu.mit.jwi.item.Pointer;

public class WordNetExportV2 
{
	
	static JWIWrapper jwiwrapper;
	
	static FileWriter fileWriter;
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			//fileWriter = new FileWriter("WordNet_Concept_exp1.csv");
			
			//
			
			
				
			POS[] pos_values = POS.values();
			
			
			
			
			for (POS pos:pos_values)
			{
				fileWriter = new FileWriter("WordNet_POS" + pos.getNumber() + ".csv");
				fileWriter.write ("SID,POS,words,gloss\n");
				
				Iterator<ISynset> synsetIterator = jwiwrapper.dictionary.getSynsetIterator(pos);
				
				
					while(synsetIterator.hasNext()) {
						ISynset synset = synsetIterator.next();
						
						String line = getLine(synset);
						System.out.println(line);
						fileWriter.write(line + "\n");
					}
					
					fileWriter.flush();
					fileWriter.close();
			}
			
			
			System.out.println(pos_values.length);
			 
			
			
				
			 
			
			
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
		
		str += "\"" + synset.getGloss().replaceAll("\"", "\"\"") + "\"";
		
		
		
		
		return str;
	}
	
	static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}
}
