package mde;

public class EdgeInfo {
    private Node  endingNode ;
    private Edge edge;
	public EdgeInfo(Node endingNode, Edge edge) {
		super();
		this.endingNode = endingNode;
		this.edge = edge;
	}
	public Node getEndingNode() {
		return endingNode;
	}
	public void setEndingNode(Node endingNode) {
		this.endingNode = endingNode;
	}
	public Edge getEdge() {
		return edge;
	}
	public void setEdge(Edge edge) {
		this.edge = edge;
	} 
  
}
