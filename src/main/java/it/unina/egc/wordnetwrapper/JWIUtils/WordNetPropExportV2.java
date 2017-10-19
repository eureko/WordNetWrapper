package it.unina.egc.wordnetwrapper.JWIUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import edu.mit.jwi.item.IPointer;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.POS;

public class WordNetPropExportV2 
{
	static FileWriter fileWriter;
	static JWIWrapper jwiwrapper;
	
	public static void main(String[] args) 
	{
		try 
		{
			jwiwrapper = new JWIWrapper();
			
			fileWriter = new FileWriter("WordNet_PropALL_exp1.csv");
			
			POS[] pos_values = POS.values();
			
			
			
			
			for (POS pos:pos_values)
			{
			
				Iterator<ISynset> synsetIterator = jwiwrapper.dictionary.getSynsetIterator(pos);
				
				
				fileWriter.write ("Prop,Src,Dest\n");
				while(synsetIterator.hasNext()) {
					ISynset synset = synsetIterator.next();
					
					addLine(synset);
					
				}
			}
			 
			 fileWriter.flush();
			 fileWriter.close();
			
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void addLine(ISynset synset) throws IOException
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
				 //fileWriter.write (ipointer.getName() + "," + synset.getID().toString() + "," + synsetID.toString() +"\n");
			}
		}  
		
	}
	
	static String getIDNumber(String SID)
	{
		return SID.substring(SID.indexOf('-')+1, SID.lastIndexOf('-'));
	}
}
