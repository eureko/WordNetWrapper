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

public class CytoScapeWordNetExport 
{
	static JWIWrapper jwiwrapper;	
	static TreeMap<String, Integer> sidMap = new TreeMap<String, Integer>();
	static TreeMap<String, Integer> widMap = new TreeMap<String, Integer>();
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			// Exporting synset nodes
			FileWriter fileWriterConcept = new FileWriter("Synset.csv");
			POS[] pos_values = POS.values();
			fileWriterConcept.write ("id,POS,NodeType\n");
			int id = 0;
				
			for (POS pos:pos_values)
			{
				Iterator<ISynset> synsetIterator = jwiwrapper.dictionary.getSynsetIterator(pos);
				
					while(synsetIterator.hasNext()) {
						ISynset synset = synsetIterator.next();
						
						String line = getLine(synset, id);
						//System.out.println(line);
						fileWriterConcept.write(line + "\n");
						sidMap.put(synset.getID().toString(), id);
						id++;
					}
			}
			
			fileWriterConcept.flush();
			fileWriterConcept.close();
			
			// Exporting semantic relations
			
			FileWriter fileWriterProp = new FileWriter("SynsetNet.sif");
			fileWriterProp.write ("Src,Prop,Dest\n");
			
			for (POS pos:pos_values)
			{
			
				Iterator<ISynset> synsetIterator = jwiwrapper.dictionary.getSynsetIterator(pos);
				
				while(synsetIterator.hasNext()) 
				{
					ISynset synset = synsetIterator.next();
					
					Map<IPointer, List<ISynsetID>> map = synset.getRelatedMap();
					
					for (Map.Entry<IPointer, List<ISynsetID>> entry : map.entrySet()) 
					{
						IPointer ipointer = entry.getKey();
						List<ISynsetID> synList = entry.getValue();
						
						Iterator<ISynsetID> iterator = synList.iterator();
						while (iterator.hasNext()) {
							ISynsetID synsetID = iterator.next();
							//System.out.println(ipointer.getName() + "," + sidMap.get(synset.getID().toString()).intValue() + "," + sidMap.get(synsetID.toString()).intValue());
							fileWriterProp.write (sidMap.get(synset.getID().toString()).intValue() + " " + ipointer.getName().replace(" ", "") + " " + sidMap.get(synsetID.toString()).intValue() +"\n");
						}
					}  
					
				}
			}
			
					 
			fileWriterProp.flush();
			fileWriterProp.close();
			
			
			// Exporting terms (or words)
			
			FileWriter fileWriterWord = new FileWriter("Words.csv");
			fileWriterWord.write ("id,POS,lemma,NodeType\n");
			
			int i = 0;
			
			for (POS pos:pos_values)
			{
				Iterator<IIndexWord> wordIterator = jwiwrapper.dictionary.getIndexWordIterator(pos);
				
				while(wordIterator.hasNext()) {

					IIndexWord indexWord = wordIterator.next();
					
					List<IWordID> wordIDs = indexWord.getWordIDs();
					
					for (IWordID wid : wordIDs)
					{
						IWord word = jwiwrapper.dictionary.getWord(wid);
						
						//System.out.println(i);
						//System.out.println(word.getID());
						//System.out.println(word.getPOS());
						//System.out.println(word.getLemma());
						////System.out.println(word.getLexicalID());
						//System.out.println(sidMap.get(word.getSynset().getID().toString()).intValue()); // Legame con livello semantico
						////System.out.println(word.getAdjectiveMarker());
						
						fileWriterWord.write("" + i + ","  
								+ word.getPOS() + "," 
								+ word.getLemma() + ",word" 
								+ "\n");
						
						widMap.put(word.getID().toString(), i);
						i++;
					}
				}
			}
			
			fileWriterWord.flush();
			fileWriterWord.close();
			
			// Exporting lexical properties
			
			FileWriter fileWriterLexProp = new FileWriter("WordsNet.sif");
			fileWriterLexProp.write("Src Prop Dest\n");
			
			for (POS pos:pos_values)
			{
				Iterator<IIndexWord> wordIterator = jwiwrapper.dictionary.getIndexWordIterator(pos);
				
				while(wordIterator.hasNext()) {

					IIndexWord indexWord = wordIterator.next();
					
					List<IWordID> wordIDs = indexWord.getWordIDs();
					
					for (IWordID wid : wordIDs)
					{
						IWord word = jwiwrapper.dictionary.getWord(wid);
						
						List<IWord> synonyms = word.getSynset().getWords();
						
						/*for(IWord s_word : synonyms)
						{
							Integer secondWordInteger = widMap.get(s_word.getID().toString());
							
							if (word.hashCode() != s_word.hashCode())
							{
								if (secondWordInteger != null)
								{	//System.out.println("Synomym" + "," + widMap.get(word.getID().toString()).intValue() + "," + secondWordInteger.intValue());
									fileWriterLexProp.write ("Synomym" + "," + widMap.get(word.getID().toString()).intValue() + "," + secondWordInteger.intValue() +"\n");
								}
								else
								{
									//System.out.println("Synomym" + "," + widMap.get(word.getID().toString()).intValue() + "," + -1);
									fileWriterLexProp.write ("Synomym" + "," + widMap.get(word.getID().toString()).intValue() + "," + -1 +"\n");
								}
							}
						}*/
						
						Map<IPointer, List<IWordID>> wmap = word.getRelatedMap();
						
						
						for (Map.Entry<IPointer, List<IWordID>> entry : wmap.entrySet()) 
						{
							IPointer ipointer = entry.getKey();
							List<IWordID> wList = entry.getValue();
							
							Iterator<IWordID> iterator = wList.iterator();
							while (iterator.hasNext()) {
								
								IWordID wID = iterator.next();
								
								IWord secondWord = jwiwrapper.dictionary.getWord(wID);
								Integer secondWordInteger = widMap.get(secondWord.getID().toString());
								
								if (secondWordInteger != null)
									fileWriterLexProp.write (widMap.get(word.getID().toString()).intValue() + " " + ipointer.getName().replace(" ", "") + " " + secondWordInteger.intValue() +"\n");
								else
									fileWriterLexProp.write (widMap.get(word.getID().toString()).intValue() + ipointer.getName().replace(" ", "") + " " + -1 +"\n");
							}
						}  
					}
				}
			}
			
			
			fileWriterLexProp.flush();
			fileWriterLexProp.close();
			
			// Exporting link between the two worlds
			
			FileWriter fileWriterSemSynLinks = new FileWriter("WordNet_SemSyn.sif");
			fileWriterSemSynLinks.write("Word Synset\n");
			
			for (POS pos:pos_values)
			{
				Iterator<IIndexWord> wordIterator = jwiwrapper.dictionary.getIndexWordIterator(pos);
				
				while(wordIterator.hasNext()) {

					IIndexWord indexWord = wordIterator.next();
					
					List<IWordID> wordIDs = indexWord.getWordIDs();
									
					for (IWordID wid : wordIDs)
					{
						IWord word = jwiwrapper.dictionary.getWord(wid);
						
						fileWriterSemSynLinks.write(widMap.get(word.getID().toString()).intValue() + " hasConcept " + 
								sidMap.get(word.getSynset().getID().toString()).intValue()+ "\n");
						
					}
				}
			}
			
			fileWriterSemSynLinks.flush();
			fileWriterSemSynLinks.close();
			
			System.out.println("Index: " + i);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	static String getLine(ISynset synset, int id)
	{
		String str = "" +  id + ",";
		str += synset.getPOS().name() + ",";
		str += "synset";
		
		return str;
	}
	
	/*static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}*/
}
