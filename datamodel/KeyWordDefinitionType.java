// 
// 
// 
//  
// 

package datamodel;

public enum KeyWordDefinitionType
{
    TISSUE, 
    TISSUE_SPACE, 
    CELLTYPE, 
    CELLTYPE_SPACE, 
    ANIMAL, 
    ORGANSYSTEM, 
    SURFACE, 
    PIPE, 
    SURFACEPORE, 
    MOLECULE, 
    OTHER;
}
/*

package datamodel;

public enum KeyWordDefinitionType
{
    public static final KeyWordDefinitionType TISSUE;
    public static final KeyWordDefinitionType TISSUE_SPACE;
    public static final KeyWordDefinitionType CELLTYPE;
    public static final KeyWordDefinitionType CELLTYPE_SPACE;
    public static final KeyWordDefinitionType ANIMAL;
    public static final KeyWordDefinitionType ORGANSYSTEM;
    public static final KeyWordDefinitionType SURFACE;
    public static final KeyWordDefinitionType PIPE;
    public static final KeyWordDefinitionType SURFACEPORE;
    public static final KeyWordDefinitionType MOLECULE;
    public static final KeyWordDefinitionType OTHER;
    
    public static KeyWordDefinitionType valueOf(final String name) {
        return Enum.valueOf(KeyWordDefinitionType.class, name);
    }
    
    static {
        KeyWordDefinitionType.TISSUE = new KeyWordDefinitionType("TISSUE", 0);
        KeyWordDefinitionType.TISSUE_SPACE = new KeyWordDefinitionType("TISSUE_SPACE", 1);
        KeyWordDefinitionType.CELLTYPE = new KeyWordDefinitionType("CELLTYPE", 2);
        KeyWordDefinitionType.CELLTYPE_SPACE = new KeyWordDefinitionType("CELLTYPE_SPACE", 3);
        KeyWordDefinitionType.ANIMAL = new KeyWordDefinitionType("ANIMAL", 4);
        KeyWordDefinitionType.ORGANSYSTEM = new KeyWordDefinitionType("ORGANSYSTEM", 5);
        KeyWordDefinitionType.SURFACE = new KeyWordDefinitionType("SURFACE", 6);
        KeyWordDefinitionType.PIPE = new KeyWordDefinitionType("PIPE", 7);
        KeyWordDefinitionType.SURFACEPORE = new KeyWordDefinitionType("SURFACEPORE", 8);
        KeyWordDefinitionType.MOLECULE = new KeyWordDefinitionType("MOLECULE", 9);
        KeyWordDefinitionType.OTHER = new KeyWordDefinitionType("OTHER", 10);
        KeyWordDefinitionType.$VALUES = new KeyWordDefinitionType[] { KeyWordDefinitionType.TISSUE, KeyWordDefinitionType.TISSUE_SPACE, KeyWordDefinitionType.CELLTYPE, KeyWordDefinitionType.CELLTYPE_SPACE, KeyWordDefinitionType.ANIMAL, KeyWordDefinitionType.ORGANSYSTEM, KeyWordDefinitionType.SURFACE, KeyWordDefinitionType.PIPE, KeyWordDefinitionType.SURFACEPORE, KeyWordDefinitionType.MOLECULE, KeyWordDefinitionType.OTHER };
    }
}
*/
