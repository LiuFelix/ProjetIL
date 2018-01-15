package points.spring.data.neo4j.repositories;

import java.util.Collection;

import points.spring.data.neo4j.domain.Point;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(collectionResourceRel = "points", path = "points")
public interface PointRepository extends PagingAndSortingRepository<Point, Long> {

	@Query("LOAD CSV WITH HEADERS FROM \"file:///map005_40.csv\" AS row CREATE (:Point {lat:toFloat(row.lat),lon:toFloat(row.lon),weight:toFloat(row.weight)})")
	void createNodes();
		
	@Query("match (p1:Point),(p2:Point) where p1.lon=p2.lon+0.025 AND p1.lat=p2.lat+0.025 CREATE (p1)-[:near {distance : p1.weight}]->(p2),(p2)-[:near {distance : p2.weight}]->(p1);")
	void createDiag1();
	
	@Query("MATCH (s:Point) WHERE s.lat={lat} AND s.lon={lon} RETURN s")
	Point findByCoord(@Param("lat") double lat, @Param("lon") double lon);

	@Query("MATCH (s:Point) WHERE ID(s)={id} RETURN s")
	Point findById(@Param("id") long id);

	@Query("MATCH (s:Point)-[r:near]->(e:Point) RETURN s,r,e LIMIT {limit}")
	Collection<Point> graph(@Param("limit") int limit);
	
	@Query("MATCH (p1:Point) WHERE ID(p1)={start} MATCH (p2:Point) WHERE ID(p2)={end} CALL apoc.algo.aStar(p1,p2,'near>','distance','lat','lon') YIELD path RETURN path")
	Collection<Point> aStar(@Param("start") long id1,@Param("end") long id2);

	@Query("MATCH (p1:Point) WHERE ID(p1)={start} MATCH (p2:Point) WHERE ID(p2)={end} CALL apoc.algo.dijkstra(p1,p2,'near>','distance') YIELD path RETURN path")
	Collection<Point> dijkstra(@Param("start") long id1,@Param("end") long id2);
	
}
