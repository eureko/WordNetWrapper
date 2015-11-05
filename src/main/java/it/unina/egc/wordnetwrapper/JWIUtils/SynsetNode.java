package it.unina.egc.wordnetwrapper.JWIUtils;

import java.util.Iterator;
import java.util.List;

import javax.print.attribute.IntegerSyntax;

import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.IWord;

public class SynsetNode 
{
	ISynset synset;
	
	public ISynset getSynset() {
		return synset;
	}

	public SynsetNode(ISynset synset) {
		// TODO Auto-generated constructor stub
		this.synset = synset;
	}
	
	
	public String toString() {
		// TODO Auto-generated method stub
		
		String str = "";
		List<IWord> words = synset.getWords();
		str += synset.getID() + " {";
		for( Iterator <IWord > w = words . iterator (); w. hasNext () ;){
			str += w. next ().getLemma ();
			if(w. hasNext ())
				str += ", ";
		
		}	
		
		str += "}";
		return str;
	}
	
	/*@Override
	public String toString() {
		// TODO Auto-generated method stub
		String str = "";
		List<IWord> words = synset.getWords();
		str += " {";
		for( Iterator <IWord > w = words . iterator (); w. hasNext () ;){
			str += w. next ().getLemma ();
			if(w. hasNext ())
				str += ", ";
		
		}	
		
		str += "}";
		return str;
	}*/
}
