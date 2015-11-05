package it.unina.egc.wordnetwrapper.Gremlin;

import java.io.FileOutputStream;
import java.io.OutputStream;

import org.neo4j.kernel.Traversal;

import com.tinkerpop.gremlin.neo4j.structure.Neo4jGraph;
import com.tinkerpop.gremlin.structure.Graph;
import com.tinkerpop.gremlin.structure.Graph.Features;
import com.tinkerpop.gremlin.structure.Graph.Features.VertexFeatures;
import com.tinkerpop.gremlin.structure.io.graphml.GraphMLReader;
import com.tinkerpop.gremlin.structure.io.graphml.GraphMLWriter;
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerFactory;
import com.tinkerpop.gremlin.tinkergraph.structure.TinkerGraph;

public class GremlinTester 
{

	public static void main(String[] args) {
		
		
		
		//TinkerGraph graph = TinkerGraph.open();
		Graph graph = TinkerFactory.createModern();
		
		System.out.println(graph);
		System.out.println(graph.features());
		
		System.out.println(graph.V().next().label());
		
		try
		{
			GraphMLWriter writer = GraphMLWriter.build().create();
			OutputStream os = new FileOutputStream("tinkerpop-modern.graphml");
			
			writer.writeGraph(os, graph);
		}
		catch(Exception arg0)
		{
			arg0.printStackTrace();
		}
		
		Graph graph1 = Neo4jGraph.open("C:\\Users\\caldarola\\Documents\\Neo4j2.1.8\\default.graphdbWordNet");
				
		 
		 System.out.println(graph1);
		
		
		try
		{
			//GraphMLReader reader = new GraphMLReader();
			
			GraphMLWriter writer = GraphMLWriter.build().create();
			OutputStream os = new FileOutputStream("movies.graphml");
			
			writer.writeGraph(os, graph1);
		}
		catch(Exception arg0)
		{
			arg0.printStackTrace();
		}
		
		
		System.exit(0);
		
		
	}
}
