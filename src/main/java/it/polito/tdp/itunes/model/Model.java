package it.polito.tdp.itunes.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	private ItunesDAO dao;
	private Graph<Track,DefaultWeightedEdge> grafo;
	private Map<Integer,Track> idMap;
	
	public Model() {
		
		this.dao = new ItunesDAO();
		this.idMap= new HashMap<>();
		
		this.dao.getAllTracks(idMap);
	}
	
	public void CreaGrafo(Genre genere) {
		
		this.grafo = new SimpleWeightedGraph<Track,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		
		// aggiungo i vertici
		Graphs.addAllVertices(genere.getGenreId(), idMap);
	}
	
	
	public List<Genre> getGeneri(){
		return dao.getAllGenres();
		
	}
}
