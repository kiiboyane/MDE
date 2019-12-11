package mde;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Aggregation {
	
	
	public static List<Object>  getElements(String line){
		 List<Object> list = new ArrayList<>();
		 Node ending = new Node();
		 Edge edge = new Edge(); 
		 //"0" -right-> [<&arrow-top> Go_up] "1" 
		 boolean firstNode = false , secondNode = false , arrow = false  , still = false  ; 
		 String start = "" , end ="" , title = ""  , arrowTitle = "" ; 
		 for(int i=0; i< line.length() ; i++ ) {
			  if(line.charAt(i) == '"'  && !firstNode) {
			        i++; 
			  	    firstNode= true; 
				    while( i< line.length() && line.charAt(i)!='"') {
				    	start+=line.charAt(i); 
				    	i++; 
				    } 
				    i++; 
			  } 
			  if(i< line.length() && line.charAt(i) == '"'  && firstNode && !secondNode) {
				   i++ ; 
				   secondNode = true;
				   while( i< line.length() && line.charAt(i)!='"') {
				    	end+=line.charAt(i); 
				    	i++; 
				    }
			  }
			  if(i< line.length() && line.charAt(i) == '&' && !still) {
				   i+=7 ;still = true ; 
				   while( i< line.length() && line.charAt(i)!='>') {
				    	arrowTitle+=line.charAt(i); 
				    	i++; 
				    }
				   if(arrowTitle.equals("top")) {
					    arrow = true; 
				   }else {
					    arrow = false; 
				   }
			  }
			  if(i< line.length() && line.charAt(i) == '>' && still ) {
				  i++ ; 
				  while(i<line.length() && line.charAt(i)!=']') {
					  if(line.charAt(i)!=' ')title+=line.charAt(i); 
					  i++; 
				  }
				  // remove whitespace 
				  title.replaceAll("\\s+","");
			  }
			  
		 }
		 ending = new Node(end);
		 edge = new Edge (title , arrow ); 
		 list.add(new Node(start));
		 list.add(edge); 
		 list.add(ending); 
		 
		 return list; 
	}
	
	public static  Map<Node , List<EdgeInfo>>  toLC(String file) {
		Map<Node , List<EdgeInfo>>  initialGraph = new HashMap<>(); 
		Node theStart = new Node(); 
		try{
			InputStream flux=new FileInputStream(new File(file)); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String line;
			boolean isStart = true;
			while ((line=buff.readLine())!=null){
				line.replaceAll("\\s+","");
				if(line.length()>0 && line.charAt(0)=='"') {
					List<Object> list = getElements(line);
					Node start = (Node)list.get(0) , ending  = (Node) list.get(2);
					Edge edge = (Edge) list.get(1); 
					
					//System.out.println(start.getName() + " " + edge.getEvent()  +  " "  + edge.getArrow() + " " +ending.getName() ) ; 
					// if the graph does not contain the start  we initialise the list of the node ( this is a directed graph ) 
					if(initialGraph.containsKey(start) == false) {
						start.setIsStart(isStart);
						if(isStart) theStart = start ;
						isStart = false;
						initialGraph.put(start , new ArrayList<>()); 
					}
					if(!isStart && start.equals(theStart)) {
						   start.setIsStart(true); 
					}
					if(!isStart && ending.equals(theStart)) {
						   ending
						   .setIsStart(true); 
					}
					initialGraph.get(start).add(new
							EdgeInfo(ending , edge));
				   // visited.put(start , false); 
				   // visited.put(ending , false); 
				}
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		return initialGraph ; 
	}
    public static Map<ALCNode , List<ALCEdgeInfo>>  aggrGraph( Map<Node , List<EdgeInfo>> initialGraph){
     Map<ALCNode , List<ALCEdgeInfo>> resultingGraph = new HashMap<>() ;
     Map<Node , ALCNode> linkingGraphs = new HashMap<>() ; 
     // now Comes the aggregation 
	  for (Node  start : initialGraph.keySet()) {  // for each node , we have different nodes coming from it    
		List<EdgeInfo>  ends = initialGraph.get(start); 
		//System.out.println(ends.size());
		for(EdgeInfo end : ends) {
			Node endingNode = end.getEndingNode()  ; 
			Edge edge = end.getEdge(); 
			//System.out.println(endingNode.getName());
		     if(edge.getEvent().charAt(0) <='Z' && edge.getEvent().charAt(0) >='A' ) { //Controllable event za3ma 
			    System.out.println(start.getName()
			    		+" "+edge.getEvent()+" "+edge.getArrow()    +" "+endingNode.getName());
			    // we need to specify whether this is an authorised order  or Inhibited order 
			    // if the arrow is up (true) it is an authorised order and inh oherwise
			    ALCNode node =new ALCNode() , node1 = new ALCNode()   , node2 =  new ALCNode(); 
			    if(linkingGraphs.containsKey(start) || linkingGraphs.containsKey(endingNode) ){
			    	    //System.out.println(start.getName() + "_" + endingNode.getName());
			    	    
			    	    if(linkingGraphs.containsKey(start) && !linkingGraphs.containsKey(endingNode)) {
			    	    	 node = linkingGraphs.get(start);
			    	    	 node.setName(linkingGraphs.get(start).getName() + "-"+ endingNode.getName());
			    	    	 node1 =  node2 = linkingGraphs.get(start); 
					    	 if(edge.getArrow()) {
					    		node.getOrd().add(edge.getEvent());
					    	 }else {
					    		node.getInh().add(edge.getEvent());
					    	 }
					    	 System.out.println(start.isStart() + " or "  + endingNode.isStart() + " " +(start.isStart() || endingNode.isStart()));
					    	 node.setIsStart(start.isStart() || endingNode.isStart());
					    	 //linkingGraphs.put(endingNode ,  linkingGraphs.get(start)); 
			    	    }else if(!linkingGraphs.containsKey(start) && linkingGraphs.containsKey(endingNode)) {
			    	    	 node = linkingGraphs.get(endingNode); 
			    	    	 node.setName(start.getName() + "-"+ linkingGraphs.get(endingNode).getName());
			    	    	 node2 = node1 =  linkingGraphs.get(endingNode); 
					    	 if(edge.getArrow()) {
					    		 node.getOrd().add(edge.getEvent());
					    	 }else {
					    		 node.getInh().add(edge.getEvent());
					    	 }
					    	 System.out.println(start.isStart() + "  OR "  + endingNode.getName() + " " +(start.isStart() || endingNode.isStart()));
					    	 
					    	 node.setIsStart(start.isStart() || endingNode.isStart());
					    	 //linkingGraphs.put(start ,  linkingGraphs.get(endingNode)); 
			    	    }else if (linkingGraphs.containsKey(start) && linkingGraphs.containsKey(endingNode)) {
			    	    	   node1 = linkingGraphs.get(endingNode); 
			    	    	   node2 = linkingGraphs.get(start); 
			    	    	   node = ALCNode.unite(linkingGraphs.get(start) , linkingGraphs.get(endingNode)); 
			    	    	   if(edge.getArrow()) {
						    		 node.getOrd().add(edge.getEvent());
						    	}else {
						    		 node.getInh().add(edge.getEvent());
						    	}
			    	    	   System.out.println(start.isStart() + "  OR "  + endingNode.isStart() + " " +(start.isStart() || endingNode.isStart()));
			    	    	   node.setIsStart(start.isStart() || endingNode.isStart());
			    	    }
			    	    linkingGraphs.put(start, node) ; 
			    	    linkingGraphs.put(endingNode, node) ; 
			    	    
			    }else {
			    	 ALCNode  aggr = new ALCNode(); 
			    	 aggr.setName(start.getName() + "-" + endingNode.getName());
			    	 System.out.println(start.getName() + "-" + endingNode.getName());
			    	 if(edge.getArrow()) {
			    		 aggr.getOrd().add(edge.getEvent());
			    	 }else {
			    	  	 aggr.getInh().add(edge.getEvent());
			    	 }
			    	 System.out.println(start.isStart() + " O R "  + endingNode.isStart() + " " +(start.isStart() || endingNode.isStart()));
			    	 aggr.setIsStart(start.isStart() || endingNode.isStart());
			    	 linkingGraphs.put(start , aggr); 
			    	 linkingGraphs.put(endingNode , aggr);
			    	 
			    	 node = aggr;
			    	 node1 = node2 =aggr;
			    }
			    System.out.println(node.getName() + " time to change " + ((node.isStart())?  " is a start " : "  is not a start "));
			    System.out.println(node1.getName() + " " +  node2.getName() );
	    	    for(Map.Entry<Node, ALCNode>  element : linkingGraphs.entrySet()) {
   		          if(element.getValue().equals(node1) || element.getValue().equals(node2) ) { // here we replace  the two alcnodes with the union 
   		        	       linkingGraphs.put(element.getKey() , node); 
   		        	       
   		          }
   		          System.out.println(element.getKey().getName() + " " + element.getValue().getName());
   	       }
			    //visited.get(end.getName()) = true; 
		     }else {
		    	continue;
		    	
		     }
		 }
	  }
	  
	  for (Node  start : initialGraph.keySet()) {  // for each node , we have different nodes coming from it  
		//System.out.println("the key  "+  start.getName());  
		List<EdgeInfo>  ends = initialGraph.get(start); 
		//System.out.println(ends.size());
		for(EdgeInfo end : ends) {
			Node endingNode = end.getEndingNode()  ; 
			Edge edge = end.getEdge(); 
			//System.out.println(endingNode.getName());
		     if(edge.getEvent().charAt(0) <='Z' && edge.getEvent().charAt(0) >='A' ) { //Controllable event za3ma 
			      continue; 
		     }else {
		    	  System.out.println(start.getName()
				    		+" "+edge.getEvent()+" "+edge.getArrow()    +" "+endingNode.getName());	    	  
		    	 // this mean that event is not controllable
		    	 // in here we just have to create an ALCNode for each Node (if it does not exist and then connect them with the edge I already have )
		    	 ALCNode  alcStart = new ALCNode() , alcEnding = new ALCNode();
		    	 if(linkingGraphs.containsKey(start)) {
		    		 alcStart = linkingGraphs.get(start); 
		    	 }else {
		    		 alcStart.setName(start.getName());
		    		 alcStart.setIsStart(start.isStart()) ; 
		    	 }
		    	 if(linkingGraphs.containsKey(endingNode)) {
		    		 alcEnding = linkingGraphs.get(endingNode); 
		    	 }else {
		    		  alcEnding.setName(endingNode.getName());
		    		  alcEnding.setIsStart(endingNode.isStart()) ; 
		    	 }
		    	 System.out.println("combining both : ");
		    	 System.out.println(alcStart.getName() + " " + alcEnding.getName());			    	 
		    	 List<ALCEdgeInfo> edgelist = new ArrayList<ALCEdgeInfo>() ; 
		    	// edgelist.add(new ALCEdgeInfo(alcEnding , edge)); 
		    	 // think about if the resulting graph is not  a single connected components 
		    	 if(resultingGraph.containsKey(alcStart)) {
		    		 edgelist = resultingGraph.get(alcStart); 
		    		 edgelist.add(new ALCEdgeInfo(alcEnding,edge));
		    		 resultingGraph.put(alcStart ,  edgelist); 
		    	 }else {
		    		 edgelist.add(new ALCEdgeInfo(alcEnding,edge));
		    		 resultingGraph.put(alcStart ,  edgelist); 
		    	 }
		    	 // yarbi salama   	  
		     }
		 }
	  }
     return resultingGraph; 
    	
    }
    public static void show( Map<ALCNode , List<ALCEdgeInfo>>   resultingGraph) {
    	for(Entry<ALCNode, List<ALCEdgeInfo>> key : resultingGraph.entrySet()) {
			  System.out.println((key.getKey()).getName()); 
			  System.out.println((key.getKey()).getOrd()); 
			  System.out.println((key.getKey()).getInh()); 
			  System.out.println(((key.getKey()).isStart()== true )? "isStart" : "isNotStart"); 
			  for(ALCEdgeInfo  alc : key.getValue() ) {
				  System.out.println(alc.getEndingNode().getName()  + " " + alc.getEdge().getArrow() + " " + alc.getEdge().getEvent() 
						  );
			  }
	 }
    }
   

}
