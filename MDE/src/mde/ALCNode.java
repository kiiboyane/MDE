package mde;

import java.util.*;

public class ALCNode extends Node {
	private Set<String> ord = new HashSet<>() , inh  = new HashSet<>() ; 


	public ALCNode(Set<String> ord, Set<String> inh, String name) {
		super(name);
		this.ord = ord;
		this.inh = inh;
	}
	public Set<String> getOrd() {
		return ord;
	}
	public void setOrd(Set<String> ord) {
		this.ord = ord;
	}
	public Set<String> getInh() {
		return inh;
	}
	public void setInh(Set<String> inh) {
		this.inh = inh;
	}
	public ALCNode() {
		 ord = new HashSet<>()  ; 
		 inh  = new HashSet<>() ;
	}

	 public static ALCNode unite(ALCNode node1 , ALCNode node2) {
	 	   Set<String>  inh1  = node1.getInh(); 
	 	   Set<String>  ord1  = node1.getOrd(); 
	 	   for(String  inh : node2.getInh()) {
	 		     inh1.add(inh); 
	 	   }
	 	   for(String  ord : node2.getOrd()) {
			     ord1.add(ord); 
		      }
	 	   node1.setName(node1.getName() +"-"+ node2.getName());
	 	   node1.setOrd(ord1); 
	 	   node1.setInh(inh1); 
	 	   return node1 ; 
	 }


}
