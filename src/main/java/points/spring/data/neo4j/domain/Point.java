package points.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@NodeEntity
public class Point {

	@GraphId
	private Long id;

	//AJOUT COST, DISTANCE
	private double x;
	private double y;

	//AJOUT differents types de relations
	@Relationship(type = "NORTH,SOUTH,EAST,WEST,NORTHEAST,NORTHWEST,SOUTHEAST,SOUTHWEST")
	private List<Arc> arcs = new ArrayList<>();	

	public Point() {
	}

	public Point(double lat, double lon) {

		this.x = lat;
		this.y = lon;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public List<Arc> getArcs() {
		return arcs;
	}

	public void setArcs(List<Arc> arcs) {
		this.arcs = arcs;
	}

	
}
