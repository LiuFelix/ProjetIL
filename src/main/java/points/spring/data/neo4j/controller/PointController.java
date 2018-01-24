package points.spring.data.neo4j.controller;

import java.util.Map;

import points.spring.data.neo4j.domain.Point;
import points.spring.data.neo4j.services.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class PointController {

	final PointService pointService;

	@Autowired
	public PointController(PointService pointService) {
		this.pointService = pointService;
	}
	
	@RequestMapping("/getFromCoord")
	public String findByCoord(@RequestParam(value = "lat",required = true) double lat, @RequestParam(value = "lon",required = true) double lon) {
		return pointService.findByCoord(lat,lon);
	}
	
	@RequestMapping("/get")
	public String findById(@RequestParam(value = "id",required = true) long id) {
		return pointService.findById(id);
	}

	@RequestMapping("/graph")
	public Map<String, Object> graph(@RequestParam(value = "limit",required = false) Integer limit) {
		return pointService.graph(limit == null ? 100 : limit);
	}

	@RequestMapping("/aStar")
	public Map<String, Object> aStar(@RequestParam(value = "start",required = true) long id1, @RequestParam(value = "end",required = true) long id2) {
		return pointService.aStar(id1,id2);
	}
	
	@RequestMapping("/aStarString")
	public String aStarString(@RequestParam(value = "start",required = true) long id1, @RequestParam(value = "end",required = true) long id2) {
		return pointService.aStarString(id1,id2);
	}
	
	@RequestMapping("/dijkstra")
	public Map<String, Object> dijkstra(@RequestParam(value = "start",required = true) long id1, @RequestParam(value = "end",required = true) long id2) {
		return pointService.dijkstra(id1,id2);
	}
	
	@RequestMapping("/dijkstraS")
	public String dijkstraString(@RequestParam(value = "start",required = true) long id1, @RequestParam(value = "end",required = true) long id2) {
		return pointService.dijkstraString(id1,id2);
	}
	
	@RequestMapping("/dijkstraCoord")
	public Map<String, Object> dijkstraCoord(@RequestParam(value = "slat",required = true) double lat1, @RequestParam(value = "slon",required = true) double lon1,
			@RequestParam(value = "elat",required = true) double lat2, @RequestParam(value = "elon",required = true) double lon2) {
		return pointService.dijkstraCoord(lat1,lon1,lat2,lon2);
	}
}