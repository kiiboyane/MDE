package mde;

public class Edge {
     
	private String  event ; 
    private Boolean arrow ; // true if up and false if down 
	
    public Edge(String event, Boolean arrow) {
		super();
		this.event = event;
		this.arrow = arrow;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public Boolean getArrow() {
		return arrow;
	}
	public void setArrow(Boolean arrow) {
		this.arrow = arrow;
	}
	public Edge () {}
      
}
