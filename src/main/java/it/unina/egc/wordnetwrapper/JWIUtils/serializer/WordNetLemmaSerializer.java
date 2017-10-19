package it.unina.egc.wordnetwrapper.JWIUtils.serializer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.Vector;

import com.google.common.base.Stopwatch;

public class WordNetLemmaSerializer 
{
	static FileReader fileReader = null;	
	static final String WordNet_Lemmas_export = "export/WordNet_WordLemma.txt";
	static final String WordNet_Lemma_SerializedFile = "export/objs/WordNetOrderedLemmas.obj";
	static Stopwatch watch = Stopwatch.createUnstarted();
	
	static Vector<String> lemmasVector = new Vector<String>();
	
	public static void main(String[] args) 
	{			
		try (BufferedReader br = new BufferedReader(new FileReader(WordNet_Lemmas_export))) 
		{
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		        if (!line.startsWith("#"))
		    		lemmasVector.add(line);
		    }
		    
		    lemmasVector.trimToSize();
		    Collections.sort(lemmasVector);
		    
		    
		    //Serialize classVector to file
	        ObjectOutputStream oos = new ObjectOutputStream( 
	                               new FileOutputStream(new File(WordNet_Lemma_SerializedFile)));

	        oos.writeObject(lemmasVector);
	        // close the writing.
	        oos.flush();
	        oos.close();
	        
	        
	        //Read from serialized lemmaVector
	        
	        ObjectInputStream ois = new ObjectInputStream(                                 
                    new FileInputStream(new File(WordNet_Lemma_SerializedFile))) ;
	       
			Vector<String> classesVectorSerialized = (Vector<String>)ois.readObject();
			    
			Iterator<String> classesIter = classesVectorSerialized.iterator();
				   
			while (classesIter.hasNext())
				System.out.println(classesIter.next());
			   
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
