package mde;

public class DCEdgeInfo {
	private DCNode  endingNode ;
    private Edge edge;
 
    
    public DCEdgeInfo(DCNode endingNode, Edge edge) {
		super();
		this.endingNode = endingNode;
		this.edge = edge;
	}

	public DCNode getEndingNode() {
		return endingNode;
	}
	
    public void setEndingNode(DCNode endingNode) {
		this.endingNode = endingNode;
	}
	
    public Edge getEdge() {
		return edge;
	}
	
    public void setEdge(Edge edge) {
		this.edge = edge;
	}
	
}
