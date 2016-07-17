// 
//  
// 

package datamodel.type;

public enum FlowEquationType 
{
    SURFACE_FLOW("", ""), 
    PIPE_FLOW("", ""), 
    SURFACE_FILTERED_PIPE_FLOW("", ""), 
    RECEPTOR_BINDING("", ""), 
    RECEPTOR_SATURABLE_BINDING("", ""), 
    ACTIVE_TRANSPORT("", ""), 
    MICHAELIS_MENTEN_ACTIVE_TRANSPORT("", ""), 
    FACILITATED_DIFFUSION("", ""), 
    MICHAELIS_MENTEN_FACILITATED_DIFFUSION("", ""), 
    BIOTRANSFORMATION("", ""), 
    MICHAELIS_MENTEN_BIOTRANSFORMATION("", ""), 
    ELEMENT_CREATION("", ""), 
    ELEMENT_DESTRUCTION("", ""), 
    PATHOGEN_GROWTH("", ""), 
    PATHOGEN_KILL("", ""), 
    INJECTION("", ""), 
    UNDEFINED("", "");
    
    private final String displayName;
    private final String description;
    
    private FlowEquationType(final String displayName, final String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    @Override
    public String toString() {
        if (this.displayName != null && !this.displayName.equals("")) {
            return this.displayName;
        }
        String s = super.toString();
        s = s.replace('_', ' ');
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
    
    public String getDescription() {
        if (this.description != null && !this.description.equals("")) {
            return this.description;
        }
        return this.toString();
    }
}
/*
public enum FlowEquationType 
{
    public static final FlowEquationType SURFACE_FLOW;
    public static final FlowEquationType PIPE_FLOW;
    public static final FlowEquationType SURFACE_FILTERED_PIPE_FLOW;
    public static final FlowEquationType RECEPTOR_BINDING;
    public static final FlowEquationType RECEPTOR_SATURABLE_BINDING;
    public static final FlowEquationType ACTIVE_TRANSPORT;
    public static final FlowEquationType MICHAELIS_MENTEN_ACTIVE_TRANSPORT;
    public static final FlowEquationType FACILITATED_DIFFUSION;
    public static final FlowEquationType MICHAELIS_MENTEN_FACILITATED_DIFFUSION;
    public static final FlowEquationType BIOTRANSFORMATION;
    public static final FlowEquationType MICHAELIS_MENTEN_BIOTRANSFORMATION;
    public static final FlowEquationType ELEMENT_CREATION;
    public static final FlowEquationType ELEMENT_DESTRUCTION;
    public static final FlowEquationType PATHOGEN_GROWTH;
    public static final FlowEquationType PATHOGEN_KILL;
    public static final FlowEquationType INJECTION;
    public static final FlowEquationType UNDEFINED;
    private final String displayName;
    private final String description;
    
    public static FlowEquationType valueOf(final String name) {
        return Enum.valueOf(FlowEquationType.class, name);
    }
    
    private FlowEquationType(final String displayName, final String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    @Override
    public String toString() {
        if (this.displayName != null && !this.displayName.equals("")) {
            return this.displayName;
        }
        String s = super.toString();
        s = s.replace('_', ' ');
        return s.substring(0, 1) + s.substring(1).toLowerCase();
    }
    
    public String getDescription() {
        if (this.description != null && !this.description.equals("")) {
            return this.description;
        }
        return this.toString();
    }
    
    static {
        FlowEquationType.SURFACE_FLOW = new FlowEquationType("SURFACE_FLOW", 0, "", "");
        FlowEquationType.PIPE_FLOW = new FlowEquationType("PIPE_FLOW", 1, "", "");
        FlowEquationType.SURFACE_FILTERED_PIPE_FLOW = new FlowEquationType("SURFACE_FILTERED_PIPE_FLOW", 2, "", "");
        FlowEquationType.RECEPTOR_BINDING = new FlowEquationType("RECEPTOR_BINDING", 3, "", "");
        FlowEquationType.RECEPTOR_SATURABLE_BINDING = new FlowEquationType("RECEPTOR_SATURABLE_BINDING", 4, "", "");
        FlowEquationType.ACTIVE_TRANSPORT = new FlowEquationType("ACTIVE_TRANSPORT", 5, "", "");
        FlowEquationType.MICHAELIS_MENTEN_ACTIVE_TRANSPORT = new FlowEquationType("MICHAELIS_MENTEN_ACTIVE_TRANSPORT", 6, "", "");
        FlowEquationType.FACILITATED_DIFFUSION = new FlowEquationType("FACILITATED_DIFFUSION", 7, "", "");
        FlowEquationType.MICHAELIS_MENTEN_FACILITATED_DIFFUSION = new FlowEquationType("MICHAELIS_MENTEN_FACILITATED_DIFFUSION", 8, "", "");
        FlowEquationType.BIOTRANSFORMATION = new FlowEquationType("BIOTRANSFORMATION", 9, "", "");
        FlowEquationType.MICHAELIS_MENTEN_BIOTRANSFORMATION = new FlowEquationType("MICHAELIS_MENTEN_BIOTRANSFORMATION", 10, "", "");
        FlowEquationType.ELEMENT_CREATION = new FlowEquationType("ELEMENT_CREATION", 11, "", "");
        FlowEquationType.ELEMENT_DESTRUCTION = new FlowEquationType("ELEMENT_DESTRUCTION", 12, "", "");
        FlowEquationType.PATHOGEN_GROWTH = new FlowEquationType("PATHOGEN_GROWTH", 13, "", "");
        FlowEquationType.PATHOGEN_KILL = new FlowEquationType("PATHOGEN_KILL", 14, "", "");
        FlowEquationType.INJECTION = new FlowEquationType("INJECTION", 15, "", "");
        FlowEquationType.UNDEFINED = new FlowEquationType("UNDEFINED", 16, "", "");
        FlowEquationType.$VALUES = new FlowEquationType[] { FlowEquationType.SURFACE_FLOW, FlowEquationType.PIPE_FLOW, FlowEquationType.SURFACE_FILTERED_PIPE_FLOW, FlowEquationType.RECEPTOR_BINDING, FlowEquationType.RECEPTOR_SATURABLE_BINDING, FlowEquationType.ACTIVE_TRANSPORT, FlowEquationType.MICHAELIS_MENTEN_ACTIVE_TRANSPORT, FlowEquationType.FACILITATED_DIFFUSION, FlowEquationType.MICHAELIS_MENTEN_FACILITATED_DIFFUSION, FlowEquationType.BIOTRANSFORMATION, FlowEquationType.MICHAELIS_MENTEN_BIOTRANSFORMATION, FlowEquationType.ELEMENT_CREATION, FlowEquationType.ELEMENT_DESTRUCTION, FlowEquationType.PATHOGEN_GROWTH, FlowEquationType.PATHOGEN_KILL, FlowEquationType.INJECTION, FlowEquationType.UNDEFINED };
    }
}
*/
