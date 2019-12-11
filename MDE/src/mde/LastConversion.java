package mde;

import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;





public class LastConversion {
    static int  key = 1;
    static int location  = 0;
    public static DCNode theStart  ; 
    public static boolean  startvis = false;  
	public static JsonObject toJson(Map<DCNode , List<DCEdgeInfo>> distributedController) {
		JsonObjectBuilder grafcetBuilder = Json.createObjectBuilder() ;
		JsonArrayBuilder nodeDataArray = Json.createArrayBuilder();
		JsonArrayBuilder linkDataArray = Json.createArrayBuilder();
		int node = 0; String location = "300 ";
		grafcetBuilder.add("class" , "go.GraphLinksModel");
		DCNode initialNode = getInitialNode(distributedController);
		theStart = initialNode; 
		DCNode dc1 = initialNode;
		System.out.println(dc1.getName() + " ");
	    loadNode(dc1, nodeDataArray  , linkDataArray , distributedController , grafcetBuilder);
		

		JsonObject grafcet = grafcetBuilder.build();  
		return grafcet;
	}
	public static  DCNode getInitialNode(Map<DCNode , List<DCEdgeInfo>> distributedController) {
		DCNode initialNode = null;
		for (DCNode itNode : distributedController.keySet()) {
		  if(itNode.isStart()) return itNode ; 
		}
		return initialNode;
		
	}

	public static JsonObjectBuilder loadNode(DCNode dc , JsonArrayBuilder jb , JsonArrayBuilder lk  ,Map<DCNode , List<DCEdgeInfo>> distributedController ,JsonObjectBuilder grafcetBuilder  ) {
		if(dc.getName() == theStart.getName() && startvis) {
			   grafcetBuilder.add("nodeDataArray",  jb) ; 
			   grafcetBuilder.add("linkDataArray",  lk) ;
			   return grafcetBuilder ; 
		}
		DCNode  dchild = distributedController.get(dc).get(0).getEndingNode();
		if(dchild.getName() == theStart.getName()) {
              
		}
		JsonObjectBuilder dc1Obj = Json.createObjectBuilder();
		JsonObjectBuilder dc2Obj = Json.createObjectBuilder();
		JsonObjectBuilder arret1 = Json.createObjectBuilder() , arret2  = Json.createObjectBuilder(); 
		dc1Obj.add("key" , key);
		if(key == 1) {
			dc1Obj.add("category", "Start");
			startvis = true ; 
		}
		location+=50; 
		dc1Obj.add("location" , "300 " + location );
		dc1Obj.add("step" , new Integer(key++).toString());
		arret1.add("from" , key-1); 
		
		if(dchild.getName() == theStart.getName() && dc.getInhCondition() == null   && dc.getOrdCondition()==null ) {
			arret1.add("to", 1); 
			arret1.add("category" ,  "Skip") ; 
		}else {
			arret1.add("to", key); 
		}
		if(dc.getInhCondition() == null   && dc.getOrdCondition()==null ) {
			String s = ((distributedController.get(dc).get(0).getEdge().getArrow()) ? "UP " : "DOWN ") +  distributedController.get(dc).get(0).getEdge().getEvent() ; 
			arret1.add("text", s);
			dc1Obj.add("text", ""); 
			jb.add(dc1Obj); 
			lk.add(arret1);
		}
		System.out.println(dc.getInhCondition() + " "  + dc.getOrdCondition());
		if(dc.getInhCondition() != null   || dc.getOrdCondition()!=null ) {
			
			dc2Obj.add("key" , key);
			location+=50; 
			dc2Obj.add("location" , "300 " + location );
			dc2Obj.add("step" , new Integer(key++).toString());
			arret2.add("from" , key-1); 
			if(dchild.getName() == theStart.getName() ) {
				arret2.add("to", 1); 
				arret2.add("category" ,  "Skip") ; 
			}else
			   arret2.add("to", key); 
			String s = ((distributedController.get(dc).get(0).getEdge().getArrow()) ? "UP " : "DOWN ") +  distributedController.get(dc).get(0).getEdge().getEvent() ; 
			arret2.add("text", s);
			if(dc.getInhCondition()==null) {
				dc1Obj.add("text", dc.getInh().iterator().next() + ":=0");
				dc2Obj.add("text", dc.getOrd().iterator().next() + ":=1");
				arret1.add("text"  , dc.getOrdCondition()) ; 				
			}
			else if(dc.getOrdCondition() == null ) {
				dc1Obj.add("text", dc.getOrd().iterator().next()+":=0");
			    dc2Obj.add("text", dc.getInh().iterator().next() + ":=1");
			    arret1.add("text"  , dc.getInhCondition()) ; 
			}
			jb.add(dc1Obj); 
			lk.add(arret1);
			jb.add(dc2Obj); 
			lk.add(arret2);
		 }
	
		 
		return loadNode(dchild, jb ,lk , distributedController , grafcetBuilder); 
		
		
	
		
	}
}