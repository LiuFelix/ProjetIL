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
			nodes.add(map2("id", point.getId(),"lat", point.getX(), "lon", point.getY(),  "label", "source"));
			int target = i;
			i++;
			for (Arc arc : point.getArcs()) {
				Map<String, Object> end = map("cost", arc.getCost(), "label", "destination");
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
	
	private Map<String, Object> map2(String key1, Object value1, String key2, Object value2, String key3, Object value3, String key4, Object value4) {
		Map<String, Object> result = new HashMap<String, Object>(4);
		result.put(key1, value1);
		result.put(key2, value2);
		result.put(key3, value3);
		result.put(key4, value4);
		return result;
	}

	
	@Transactional(readOnly = true)
	public String findByCoord(double lat, double lon){
		long result = -1;
		result = pointRepository.findByCoord(lat, lon);
		if (result != -1)
			return findById(result);
		else
			return "No matches !";
	}
	
	@Transactional(readOnly = true)
	public String findById(long id){
		Point result = pointRepository.findById(id);
		if (result != null)
			return "Latitude = " + result.getX() + " & Longitude = " + result.getY();
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
			result += "--> " + p.getId() + " : " + p.getX() + "," + p.getY() + '\n';
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> dijkstra(long id1, long id2) {
		Collection<Point> result = pointRepository.dijkstra(id1,id2);
		return toD3Format(result);
	}

	@Transactional(readOnly = true)
	public String dijkstraString(long id1, long id2) {
		Collection<Point> aux = pointRepository.dijkstra(id1,id2);
		String result = "";
		for (Point p : aux) {
			result += "--> " + p.getId() + " : " + p.getX() + "," + p.getY() + '\n';
		}
		return result;
	}
	
	@Transactional(readOnly = true)
	public Map<String, Object> dijkstraCoord(double lat1, double lon1, double lat2, double lon2) {
		long id1 = pointRepository.findByCoord(lat1, lon1);
		long id2 = pointRepository.findByCoord(lat2, lon2);
		Collection<Point> result = pointRepository.dijkstra(id1, id2);
		return toD3Format(result);
	}
	
}
