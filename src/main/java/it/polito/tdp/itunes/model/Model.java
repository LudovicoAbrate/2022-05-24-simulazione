package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private Graph<Track,DefaultWeightedEdge> grafo;
	ItunesDAO dao;
	Map<Integer,Track> idMap = new HashMap<>();
	List<Track> vertici = new LinkedList<Track>();
	
	public Model() {
		this.dao = new ItunesDAO();
	}
	
	
	public void creaGrafo(Genre genere) {
		

		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		vertici.clear() ;
		for(Track t: dao.getVertici(genere)) {
			idMap.put(t.getTrackId(),t);
			vertici.add(t);
			
		}
		
		
		Graphs.addAllVertices(this.grafo,vertici );
		
		for(Adiacenza a: dao.getAdiacenze(genere, idMap)) {
			
			Graphs.addEdgeWithVertices(this.grafo, a.t1, a.t2,a.peso);
		}
		
		
	}
	
	
	public List<Adiacenza> migliore(){
		
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		int max = 0;
		
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			int peso = (int) this.grafo.getEdgeWeight(e);
			if( peso > max) {
				max = peso;
				result.clear();
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),max));
		}else if (peso == max) {
			result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),max));
		}
			
		}
		return result;
	}
	
	
	public List<Genre> getGeneri() {
		// TODO Auto-generated method stub
		return dao.getAllGenres();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	
}
