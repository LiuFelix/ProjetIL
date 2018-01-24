package points.spring.data.neo4j.domain;

import java.util.ArrayList;
import java.util.Collection;

import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@RelationshipEntity(type = "NORTH,SOUTH,EAST,WEST,NORTHEAST,NORTHWEST,SOUTHEAST,SOUTHWEST")
public class Arc {

	@GraphId
	private Long id;

	private Collection<String> arcs = new ArrayList<>();

	@StartNode
	private Point start;

	@EndNode
	private Point end;
	
	private double cost;
	private double distance;
	
	public Arc() {
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Arc(Point start, Point end) {
		this.start = start;
		this.end = end;
	}

	public Long getId() {
		return id;
	}

	public Collection<String> getArcs() {
		return arcs;
	}

	public Point getStart() {
		return start;
	}

	public Point getEnd() {
		return end;
	}

	public void addArcDistance(String type) {
		this.arcs.add(type);
	}
}