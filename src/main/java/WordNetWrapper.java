import java.io.File;
import java.io.IOException;
import java.net.URL;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;


public class WordNetWrapper {

	static final String WN_DICT_FOLDER = "C:\\Program Files (x86)\\WordNet\\2.1\\dict";
	
	public static void main(String[] args) {
		
		try {
			testDictionary("entity");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	 static public void testDictionary(String term) throws IOException 
	 {
		
		File wordnetDir = new File(WN_DICT_FOLDER);
		
		 // construct the dictionary object and open it
		 IDictionary dict = new Dictionary (wordnetDir);
		 dict.open();
		 
				
		 System.out.println("look up first sense of the word");
		 IIndexWord idxWord = dict.getIndexWord (term, POS.NOUN);
		 
		
		 int wordIDsize = idxWord.getWordIDs().size();
		 
		 
		 System.out.println(wordIDsize);
		 
		 IWordID wordID = idxWord . getWordIDs().get(0);
		 
		 
		 IWord word = dict . getWord( wordID );
		 
		 
		 System .out . println ("Id = " + wordID );
		 System .out . println (" Lemma = " + word . getLemma ());
		 System .out . println (" Gloss = " + word . getSynset (). getGloss ());
}

}
