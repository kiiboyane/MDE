package mde;

public class Node {
	
	private String  name ; 
	private boolean isStart;
	public boolean isStart() {
		return isStart;
	}
	public void setIsStart(boolean start) {
		this.isStart = start;
	}
	public Node() {
		 
	}
	public Node( String name ) {
		  this.name = name; 
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	// the equals  and hashCode functions are for the HashMap  comparaison 
	@Override
	public boolean equals (Object node1) {
		   if(node1  == this ) 
			   return true;
		   if(node1 == null || node1.getClass() != getClass())
			   return false; 
		   Node node = (Node) node1; 
		   return name.equals(node.getName()) ;
	}
	@Override
	public int hashCode() {
		 return (int) name.charAt(0); 
	}


	

}

