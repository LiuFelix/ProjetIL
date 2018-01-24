package points.spring.data.neo4j.repositories;

import java.util.Collection;

import points.spring.data.neo4j.domain.Point;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "points", path = "points")
public interface PointRepository extends PagingAndSortingRepository<Point, Long> {

	
	@Query("MATCH (s:Point) WHERE s.x<{lat}+0.1 AND s.x>{lat}-0.1 AND s.y<{lon}+0.1 AND s.y>{lon}-0.1 RETURN ID(s) LIMIT 1")
	long findByCoord(@Param("lat") double lat, @Param("lon") double lon);

	@Query("MATCH (s:Point) WHERE ID(s)={id} RETURN s")
	Point findById(@Param("id") long id);

	@Query("MATCH (s:Point)-[r]->(e:Point) RETURN s,r,e LIMIT {limit}")
	Collection<Point> graph(@Param("limit") int limit);
	
	@Query("MATCH (p1:Point) WHERE ID(p1)={start} MATCH (p2:Point) WHERE ID(p2)={end} CALL apoc.algo.aStar(p1,p2,'EAST>|WEST>|NORTH>|SOUTH>|SOUTHEAST>|SOUTHWEST>|NORTHEAST>|NORTHWEST>','cost','x','y') YIELD path RETURN path")
	Collection<Point> aStar(@Param("start") long id1,@Param("end") long id2);

	@Query("MATCH (p1:Point) WHERE ID(p1)={start} MATCH (p2:Point) WHERE ID(p2)={end} CALL apoc.algo.dijkstra(p1,p2,'EAST>|WEST>|NORTH>|SOUTH>|SOUTHEAST>|SOUTHWEST>|NORTHEAST>|NORTHWEST>','cost') YIELD path RETURN path")
	Collection<Point> dijkstra(@Param("start") long id1,@Param("end") long id2);
	
	@Query("MATCH (p1:Point) WHERE ID(p1)={start} MATCH (p2:Point) WHERE ID(p2)={end} CALL apoc.algo.dijkstra(p1,p2,'EAST>|WEST>|NORTH>|SOUTH>|SOUTHEAST>|SOUTHWEST>|NORTHEAST>|NORTHWEST>','cost') YIELD path RETURN path")
	Collection<Point> dijkstraCoord(@Param("start") long id1,@Param("end") long id2);
}
