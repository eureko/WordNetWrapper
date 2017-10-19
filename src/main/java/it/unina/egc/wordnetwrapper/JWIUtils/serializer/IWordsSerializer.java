package it.unina.egc.wordnetwrapper.JWIUtils.serializer;

import it.unina.egc.wordnetwrapper.JWIUtils.JWIWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.google.common.base.Stopwatch;

import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

public class IWordsSerializer 
{
	static FileReader fileReader = null;	
	static final String WordNet_Iwords_SerializedFile = "export/objs/IWords.obj";
	static Stopwatch watch = Stopwatch.createUnstarted();
	
	static JWIWrapper jwiwrapper;	
	
	static Vector<IWord> IWords = new Vector<IWord>();
	
	public static void main(String[] args) 
	{			
		try
		{
		   
			jwiwrapper = new JWIWrapper();
			
			// Exporting terms (or words)
			
			//POS[] pos_values = POS.values();	
						
			//int i = 0;
			
			//for (POS pos:pos_values)
			//{
				Iterator<IIndexWord> wordIterator = jwiwrapper.dictionary.getIndexWordIterator(POS.NOUN);
				
				while(wordIterator.hasNext()) {
		
					IIndexWord indexWord = wordIterator.next();
					
					List<IWordID> wordIDs = indexWord.getWordIDs();
					
					for (IWordID wid : wordIDs)
					{
						IWord word = jwiwrapper.dictionary.getWord(wid);
						IWords.addElement(word);
						
						//System.out.println(i);
						//System.out.println(word.getID());
						//System.out.println(word.getPOS());
						//System.out.println(word.getLemma());
						////System.out.println(word.getLexicalID());
						//System.out.println(sidMap.get(word.getSynset().getID().toString()).intValue()); // Legame con livello semantico
						////System.out.println(word.getAdjectiveMarker());
						
						
						
					}
				}
		    
		    //Serialize classVector to file
	        ObjectOutputStream oos = new ObjectOutputStream( 
	                               new FileOutputStream(new File(WordNet_Iwords_SerializedFile)));

	        oos.writeObject(IWords);
	        // close the writing.
	        oos.flush();
	        oos.close();
	        
	        
	        //Read from serialized lemmaVector
	        
	        ObjectInputStream ois = new ObjectInputStream(                                 
                    new FileInputStream(new File(WordNet_Iwords_SerializedFile))) ;
	       
			Vector<IWord> IwordVectorSerialized = (Vector<IWord>)ois.readObject();
			    
			Iterator<IWord> IWordIter = IwordVectorSerialized.iterator();
				   
			while (IWordIter.hasNext())
				System.out.println(IWordIter.next());
			   
			   ois.close(); 
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
