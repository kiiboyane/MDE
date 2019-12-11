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


public class ConsidOfConstraints {
	//this method convert an ALC to a DC
	public static Map<DCNode , List<DCEdgeInfo>> toDc(Map<ALCNode , List<ALCEdgeInfo>> alc , String file) {
		DCNode thestart = new DCNode(); 
		Map<String, DCNode> dcNodes = new HashMap<>();
		Map<DCNode , List<DCEdgeInfo>> dc = new HashMap<DCNode , List<DCEdgeInfo>>();
		int i =0;
		ArrayList <HashMap<String , String>> constraints = loadConstraints(file);
		for (ALCNode itNode : alc.keySet()) {
			changeNodesName(alc, itNode.getName(), "node" + i);
			itNode.setName( "node" + i++ );
			DCNode dcTemp = new DCNode( itNode.getOrd() , itNode.getInh() , itNode.getName() , null , null);
		    dcTemp.setIsStart(itNode.isStart()); 
		    if(dcTemp.isStart()) thestart = dcTemp; 
			dcNodes.put( itNode.getName() , dcTemp );
			if(dcTemp.getInh().size() > 0) {
				for(String inh:dcTemp.getInh()) {
					dcTemp.setInhCondition(constraints.get(1).get(inh));
				}	
			}
			if(dcTemp.getOrd().size() > 0) {
				for(String ord:dcTemp.getOrd()) {
					dcTemp.setOrdCondition(constraints.get(0).get(ord));
				}		
			}
		}
		//showNodesName(alc, "jfnl" ,"kcjbsj");
		for (Map.Entry<ALCNode,List<ALCEdgeInfo>> entry : alc.entrySet())  {
			//System.out.println(entry.getKey().getName());
			DCNode dcTemp  =  dcNodes.get(entry.getKey().getName());
			List<DCEdgeInfo> listTemp =new ArrayList<>();
			for(ALCEdgeInfo edjInfo : entry.getValue()) {
				Edge edge = edjInfo.getEdge();
				DCNode endingNode = dcNodes.get(edjInfo.getEndingNode().getName());
				if(thestart.getName() == endingNode.getName()) endingNode.setIsStart(true); 
				listTemp.add(new DCEdgeInfo(endingNode, edge));
			}
			dc.put(dcTemp , listTemp);
		}
		return dc;
	}
	
	
	
	//This function loads the constraints of the file and returns an arraylist of size 2 whose first one represents the list of the orders with
	// their conditions and the second case the list of inhs with their conditions
	
	public static ArrayList <HashMap<String , String>>  loadConstraints(String file){
		ArrayList <HashMap<String , String>> constraints = new ArrayList<>(2);
		constraints.add(new HashMap<String , String>());
		constraints.add(new HashMap<String , String>());
		ArrayList <String> lines =  readLines(file);
		for(String line : lines) {
			String cond  = "";
			int i =0;
			while(i<line.length()-1 &&(!(line.subSequence(i, i+2).equals("If")))) 
				i++;
			
			i+=2;
			
			while(i<line.length()&&line.charAt(i)==' ')i++;
			
			while(i<line.length()-3 &&(!(line.subSequence(i, i+4).equals("then")))) {
				cond+=line.charAt(i++);
			}
			i+=4;
			while(i<line.length()&&line.charAt(i)==' ')i++;
			if(i>line.length())continue;
			//System.out.println(cond);
			if(line.charAt(i)=='O') {
				i+=3;
				String ord = "";
				while(i<line.length()&&line.charAt(i)==' ')i++;
				i+=1;
				while(i<line.length()&&line.charAt(i)!=')') {
					ord +=line.charAt(i);
					i++;
				}
				constraints.get(0).put(ord, cond);
			}else if(line.charAt(i)=='I') {
				i+=3;
				String inh = "";
				while(i<line.length()&&line.charAt(i)==' ')i++;
				i+=1;
				while(i<line.length()&&line.charAt(i)!=')') {
					inh +=line.charAt(i);
					i++;
				}
				constraints.get(1).put(inh, cond);			
			}
			
		}
		return constraints;
	}
	
	
	public static ArrayList<String> readLines (String file) {
		ArrayList<String> lines = new ArrayList<>();
		try{
			InputStream flux=new FileInputStream(new File(file)); 
			InputStreamReader lecture=new InputStreamReader(flux);
			BufferedReader buff=new BufferedReader(lecture);
			String line;
			while ((line=buff.readLine())!=null){
				line.replaceAll("\\s+","");
				lines.add(line);
			}
			buff.close(); 
			}		
			catch (Exception e){
			System.out.println(e.toString());
			}
		return lines;
	}
	
	public static void changeNodesName(Map<ALCNode , List<ALCEdgeInfo>> alc , String oldName  , String newName)  {
		for(Map.Entry<ALCNode,List<ALCEdgeInfo>> entry : alc.entrySet()) {
			for(ALCEdgeInfo edgeInfo : entry.getValue()) {
				if(edgeInfo.getEndingNode().getName().equals(oldName))
					edgeInfo.getEndingNode().setName(newName);
			}
		}
	}

	
	public static void showNodesName(Map<ALCNode , List<ALCEdgeInfo>> alc , String oldName  , String newName)  {
		for(Map.Entry<ALCNode,List<ALCEdgeInfo>> entry : alc.entrySet()) {
			for(ALCEdgeInfo edgeInfo : entry.getValue()) {
				System.out.println(edgeInfo.getEndingNode().getName());	
			}
		}
	}
	
}
