package points.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.Collection;
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

	private double lat;
	private double lon;
	private double weight;

	@Relationship(type = "near")
	private List<Arc> arcs = new ArrayList<>();

	public Point() {
	}

	public Point(double lat, double lon, double weight) {

		this.lat = lat;
		this.lon = lon;
		this.weight = weight;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public double getLat() {
		return lat;
	}

	public double getLon() {
		return lon;
	}

	public double getWeight() {
		return weight;
	}
	
	public Collection<Arc> getArcs() {
		return arcs;
	}

	public void addArc(Arc arc) {
		this.arcs.add(arc);
	}
}
