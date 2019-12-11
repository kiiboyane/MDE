package mde;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import javax.json.*;

public class Parse {
    public static void main(String[] args) {
    	
		Map<Node , List<EdgeInfo>> initialGraph = new HashMap<>();
		Map<ALCNode , List<ALCEdgeInfo>> resultingGraph = new HashMap<>() ; 

		initialGraph = Aggregation.toLC("uml.that");
		resultingGraph = Aggregation.aggrGraph(initialGraph) ; 
		  
		System.out.println("the resulting Graph");
		Aggregation.show(resultingGraph); 
		  
		System.out.println("////////////////////////////////");
		Map<DCNode , List<DCEdgeInfo>> distributedController = new HashMap<>(); 
		distributedController = ConsidOfConstraints.toDc(resultingGraph,"file.const");
		
		JsonObject grafcet = LastConversion.toJson(distributedController);
		try (PrintWriter file = new PrintWriter("file.json")) {
			file.println(grafcet.toString());
			System.out.println("Successfully Copied JSON Object to File...");
			System.out.println("\nJSON Object: " + grafcet);
		}catch(Exception e) {
			e.printStackTrace();
		}
			
		  
	}
    


}
