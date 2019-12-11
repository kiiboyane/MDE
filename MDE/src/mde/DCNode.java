package mde;

import java.util.HashSet;
import java.util.Set;

public class DCNode  extends ALCNode{
    private String ordCondition;
    private String inhCondition;

    public DCNode(Set<String> ord, Set<String> inh, String name, String ordCondition, String inhCondition) {
		super(ord , inh , name);
		this.ordCondition = ordCondition;
		this.inhCondition = inhCondition;
	}
    public DCNode() {
	    
    }

	public String getOrdCondition() {
		return ordCondition;
	}

	public void setOrdCondition(String ordCondition) {
		this.ordCondition = ordCondition;
	}

	public String getInhCondition() {
		return inhCondition;
	}

	public void setInhCondition(String inhCondition) {
		this.inhCondition = inhCondition;
	}
    
    
}
