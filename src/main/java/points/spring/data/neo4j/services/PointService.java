package points.spring.data.neo4j.services;

import java.util.*;

import points.spring.data.neo4j.domain.Point;
import points.spring.data.neo4j.domain.Arc;
import points.spring.data.neo4j.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {

	@Autowired PointRepository pointRepository;

	private Map<String, Object> toD3Format(Collection<Point> points) {
		List<Map<String, Object>> nodes = new ArrayList<>();
		List<Map<String, Object>> rels = new ArrayList<>();
		int i = 0;
		Iterator<Point> result = points.iterator();
		while (result.hasNext()) {
			Point point = result.next();
			nodes.add(map("weight", point.getWeight(), "label", "source"));
			int target = i;
			i++;
			for (Arc arc : point.getArcs()) {
				Map<String, Object> end = map("weight", arc.getEnd().getWeight(), "label", "destination");
				int source = nodes.indexOf(end);
				if (source == -1) {
					nodes.add(end);
					source = i++;
				}
				rels.add(map("source", source, "target", target));
			}
		}
		return map("nodes", nodes, "links", rels);
	}

	private Map<String, Object> map(String key1, Object value1, String key2, Object value2) {
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put(key1, value1);
		result.put(key2, value2);
		return result;
	}

	
	@Transactional(readOnly = true)
	public String findByCoord(double lat, double lon){
		Point result = pointRepository.findByCoord(lat, lon);
		if (result != null)
			return "Latitude = " + result.getLat() + " & Longitude = " + result.getLon();
		else
			return "No matches !";
	}
	
	@Transactional(readOnly = true)
	public String findById(long id){
		Point result = pointRepository.findById(id);
		if (result != null)
			return "Latitude = " + result.getLat() + " & Longitude = " + result.getLon();
		else
			return "No matches !";
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object>  graph(int limit) {
		Collection<Point> result = pointRepository.graph(limit);
		return toD3Format(result);
	}

	@Transactional(readOnly = true)
	public Map<String, Object> aStar(long id1, long id2) {
		Collection<Point> result = pointRepository.aStar(id1,id2);
		return toD3Format(result);
	}
	
	@Transactional(readOnly = true)
	public String aStarString(long id1, long id2) {
		Collection<Point> aux = pointRepository.aStar(id1,id2);
		String result = "";
		for (Point p : aux) {
			result += "--> " + p.getId() + " : " + p.getLat() + "," + p.getLon() + '\n';
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> dijkstra(long id1, long id2) {
		Collection<Point> result = pointRepository.aStar(id1,id2);
		return toD3Format(result);
	}
	
	@Transactional(readOnly = true)
	public void  createGraph() {
		pointRepository.createNodes();
	}
	
	@Transactional(readOnly = true)
	public void  createDiag1() {
		pointRepository.createDiag1();
	}

	@Transactional(readOnly = true)
	public void  createNodes() {
		pointRepository.createNodes();
	}
}
