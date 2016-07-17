// 
//  
// 

package datamodel;

import java.util.Locale;

public enum KeyWord
{
    PHYSIOSIMMODEL("PhysioSim Model", "PhysioSim Model", KeyWordDefinitionType.OTHER), 
    ALL("", "", KeyWordDefinitionType.OTHER), 
    UNDEFINED("", "", KeyWordDefinitionType.OTHER), 
    WATER_SOLUBLE_PROTEIN("Water Soluble Protein", "Water Soluble Protein", KeyWordDefinitionType.OTHER), 
    HEPATIC_CLEARANCE("Hepatic Clearance", "Hepatic Clearance", KeyWordDefinitionType.OTHER), 
    TOXIN("", "", KeyWordDefinitionType.OTHER), 
    INFLAMED("", "", KeyWordDefinitionType.OTHER), 
    HEALTHY("", "", KeyWordDefinitionType.OTHER), 
    HUMAN("", "", KeyWordDefinitionType.ANIMAL), 
    MOUSE("", "", KeyWordDefinitionType.ANIMAL), 
    DEVICE("", "", KeyWordDefinitionType.ANIMAL), 
    ANIMAL("", "", KeyWordDefinitionType.ANIMAL), 
    CARDIOVASCULAR("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    RESPIRATORY("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    MUSCULAR("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    SKELETAL("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    NERVOUS("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    URINARY("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    DIGESTIVE("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    ENDOCRINE("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    LYMPHATIC("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    REPRODUCTIVE("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    ORGAN_SYSTEM("", "", KeyWordDefinitionType.ORGANSYSTEM), 
    MAJOR_VEINS("", "", KeyWordDefinitionType.TISSUE), 
    MAJOR_ARTERIES("", "", KeyWordDefinitionType.TISSUE), 
    HEART("", "", KeyWordDefinitionType.TISSUE), 
    LUNG("", "", KeyWordDefinitionType.TISSUE), 
    MUSCLE("", "", KeyWordDefinitionType.TISSUE), 
    SKIN("", "", KeyWordDefinitionType.TISSUE), 
    ADIPOSE("", "", KeyWordDefinitionType.TISSUE), 
    BONE("", "", KeyWordDefinitionType.TISSUE), 
    BRAIN_GREY_MATTER("", "", KeyWordDefinitionType.TISSUE), 
    BRAIN_WHITE_MATTER("", "", KeyWordDefinitionType.TISSUE), 
    VENTRICLES("", "", KeyWordDefinitionType.TISSUE), 
    KIDNEY("", "", KeyWordDefinitionType.TISSUE), 
    KIDNEY_LEFT("", "", KeyWordDefinitionType.TISSUE), 
    KIDNEY_RIGHT("", "", KeyWordDefinitionType.TISSUE), 
    BLADDER("", "", KeyWordDefinitionType.TISSUE), 
    LIVER("", "", KeyWordDefinitionType.TISSUE), 
    GALLBLADDER("", "", KeyWordDefinitionType.TISSUE), 
    PANCREAS_EXOCRINE("", "", KeyWordDefinitionType.TISSUE), 
    STOMACH("", "", KeyWordDefinitionType.TISSUE), 
    DUODENUM("", "", KeyWordDefinitionType.TISSUE), 
    JEJUNUM_1("", "", KeyWordDefinitionType.TISSUE), 
    JEJUNUM_2("", "", KeyWordDefinitionType.TISSUE), 
    ILEUM_1("", "", KeyWordDefinitionType.TISSUE), 
    ILEUM_2("", "", KeyWordDefinitionType.TISSUE), 
    ILEUM_3("", "", KeyWordDefinitionType.TISSUE), 
    CAECUM("", "", KeyWordDefinitionType.TISSUE), 
    COLON("", "", KeyWordDefinitionType.TISSUE), 
    ADRENALS("", "", KeyWordDefinitionType.TISSUE), 
    PANCREAS_ENDOCRINE("", "", KeyWordDefinitionType.TISSUE), 
    THYMUS("", "", KeyWordDefinitionType.TISSUE), 
    THYROID("", "", KeyWordDefinitionType.TISSUE), 
    SPLEEN("", "", KeyWordDefinitionType.TISSUE), 
    LYMPH_NODE("", "", KeyWordDefinitionType.TISSUE), 
    TESTES("", "", KeyWordDefinitionType.TISSUE), 
    OVARIES("", "", KeyWordDefinitionType.TISSUE), 
    TUMOR("", "", KeyWordDefinitionType.TISSUE), 
    BLOOD("", "", KeyWordDefinitionType.TISSUE), 
    PLASMA("", "", KeyWordDefinitionType.TISSUE), 
    BONE_MARROW("", "", KeyWordDefinitionType.TISSUE), 
    BRAIN("", "", KeyWordDefinitionType.TISSUE), 
    LIVER_AND_GALLBLADDER("", "", KeyWordDefinitionType.TISSUE), 
    SMALL_INTESTINES("", "", KeyWordDefinitionType.TISSUE), 
    JEJUNUM("", "", KeyWordDefinitionType.TISSUE), 
    ILEUM("", "", KeyWordDefinitionType.TISSUE), 
    LARGE_INTESTINES("", "", KeyWordDefinitionType.TISSUE), 
    INTESTINES("", "", KeyWordDefinitionType.TISSUE), 
    PANCREAS("", "", KeyWordDefinitionType.TISSUE), 
    TISSUE("", "", KeyWordDefinitionType.TISSUE), 
    VASCULATURE("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    INTERSTITIAL("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    APICAL("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    ALVEOULUS_AIR("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    ALVEOULUS_SURFACTANT("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    CORTICAL_BONE("", "Compact Bone", KeyWordDefinitionType.TISSUE_SPACE), 
    CSF("CSF", "Cerebrospinal Fluid", KeyWordDefinitionType.TISSUE_SPACE), 
    URINE("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    BILE("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    PANCREATIC_JUICE("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    CHYME("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    FECES("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    LYMPH("", "", KeyWordDefinitionType.TISSUE_SPACE), 
    ENDOTHELIAL_CELL("Endothelial Cell", "Capillary Wall Cell", KeyWordDefinitionType.CELLTYPE), 
    HEPATOCYTE_CELL("Hepatocyte Cell", "Liver Cell", KeyWordDefinitionType.CELLTYPE), 
    KUPFFER_CELL("Kupffer Cell", "Liver Macrophage Cell", KeyWordDefinitionType.CELLTYPE), 
    HEPATIC_STELLATE_CELL("Hepatic Stellate Cell", "Hepatic Stellate Cell", KeyWordDefinitionType.CELLTYPE), 
    EPITHELIAL_CELL("Epithelial Cell", "Epithelial Cell", KeyWordDefinitionType.CELLTYPE), 
    ADIPOCYTE_CELL("Adipocyte Cell", "Fat Cell", KeyWordDefinitionType.CELLTYPE), 
    OSTEOCLAST_CELL("Osteoclast Cell", "Bone Macrophage Cell", KeyWordDefinitionType.CELLTYPE), 
    OSTEOCYTE_CELL("Osteocyte Cell", "Bone Cell", KeyWordDefinitionType.CELLTYPE), 
    ENTEROCYTE_CELL("Enterocyte Cell", "Absorptive Cell", KeyWordDefinitionType.CELLTYPE), 
    GOBLET_CELL("Goblet Cell", "Mucus Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    MUCOUS_CELL("Mucous Cell", "Mucus Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    ALPHA_CELL("Alpha Cell", "Glucagon Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    BETA_CELL("Beta Cell", "Insulin Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    DELTA_CELL("Delta Cell", "Somatostatin Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    PP_CELL("PP Cell", "Pancreatic Polypeptide Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    ACINAR_CELL("Acinar Cell", "Pancreatic Enzyme Secreting Cell", KeyWordDefinitionType.CELLTYPE), 
    ALVEOLAR_MACROPHAGE_CELL("Alveolar Macrophage Cell", "Lung Macrophage Cell", KeyWordDefinitionType.CELLTYPE), 
    PNEUMOCYTE_CELL("Pneumocyte Cell", "", KeyWordDefinitionType.CELLTYPE), 
    CARDIAC_MYOCYTE_CELL("Cardiac Myocyte Cell", "Heart Muscle Cell", KeyWordDefinitionType.CELLTYPE), 
    SKELETAL_MYOCYTE_CELL("Skeletal Myocyte Cell", "Skeletal Muscle Cell", KeyWordDefinitionType.CELLTYPE), 
    SMOOTH_MYOCYTE_CELL("Smooth Myocyte Cell", "Smooth Muscle Cell", KeyWordDefinitionType.CELLTYPE), 
    NEURON_CELL("Neuron Cell", "Neuron Cell", KeyWordDefinitionType.CELLTYPE), 
    ASTROCYTE_CELL("Astrocyte Cell", "Astrocyte Cell", KeyWordDefinitionType.CELLTYPE), 
    OLIGODENDROCYTE_CELL("Oligodendrocyte Cell", "Oligodendrocyte Cell", KeyWordDefinitionType.CELLTYPE), 
    MICROGLIA_CELL("Microglia Cell", "Microglia Cell", KeyWordDefinitionType.CELLTYPE), 
    RENAL_TUBULAR_EPITHELIAL_CELL("Renal Tubular Epithelial Cell", "Renal Tubular Epithelial Cell", KeyWordDefinitionType.CELLTYPE), 
    MESANGIAL_CELL("Mesangial Cell", "Mesangial Cell", KeyWordDefinitionType.CELLTYPE), 
    HISTIOCYTE_CELL("Histiocyte Cell", "Tissue Macrophage Cell", KeyWordDefinitionType.CELLTYPE), 
    FIBROBLAST_CELL("Fibroblast Cell", "Fibroblast Cell", KeyWordDefinitionType.CELLTYPE), 
    ERYTHROCYTE_CELL("Erythrocyte Cell", "Red Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    LEUKOCYTE_CELL("Leukocyte Cell", "White Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    GRANULOCYTE_CELL("Granulocyte Cell", "Granuloycyte Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    NEUTROPHIL_CELL("Neutrophil Cell", "Granuloycyte Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    EOSINOPHIL_CELL("Eosinophil Cell", "Granuloycyte Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    BASOPHIL_CELL("Basophil Cell", "Granuloycyte Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    MONOCYTE_CELL("Monocyte Cell", "Monocyte Blood Cell", KeyWordDefinitionType.CELLTYPE), 
    LYMPHOCYTE_CELL("Lymphocyte Cell", "T and B Lymphocyte Cell", KeyWordDefinitionType.CELLTYPE), 
    MACROPHAGE_CELL("Macrophage Cell", "Macrophage Cell", KeyWordDefinitionType.CELLTYPE), 
    TUMOR_CELL("", "", KeyWordDefinitionType.CELLTYPE), 
    BACTERIA("", "", KeyWordDefinitionType.CELLTYPE), 
    VIRUS("", "", KeyWordDefinitionType.CELLTYPE), 
    TISSUE_CELL("", "", KeyWordDefinitionType.CELLTYPE), 
    CELL("", "", KeyWordDefinitionType.CELLTYPE), 
    CYTOSOL("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    MITOCHONDRIA("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    ROUGH_ER("Rough ER", "Rough endoplasmic reticulum", KeyWordDefinitionType.CELLTYPE_SPACE), 
    SMOOTH_ER("Smooth ER", "Smooth endoplasmic reticulum", KeyWordDefinitionType.CELLTYPE_SPACE), 
    GOLGI("", "Golgi apparatus", KeyWordDefinitionType.CELLTYPE_SPACE), 
    NUCLEUS("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    PEROXISOME("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    LYSOSOME("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    ENDOSOME("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    APICAL_ENDOSOME("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    SECRETORY_VESICLE("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    ORGANELLE("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    ER("ER", "endoplasmic reticulum", KeyWordDefinitionType.CELLTYPE_SPACE), 
    LIPID_DROPLET("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    MYELIN("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    CAPSID("", "", KeyWordDefinitionType.CELLTYPE_SPACE), 
    VESICULAR("", "", KeyWordDefinitionType.PIPE), 
    CIRCULATORY("", "", KeyWordDefinitionType.PIPE), 
    PIPE("", "", KeyWordDefinitionType.PIPE), 
    PLASMA_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    BASOLATERAL_PLASMA_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    APICAL_PLASMA_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    MITOCHONDRIA_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    ROUGH_ER_MEMBRANE("Rough ER membrane", "Rough endoplasmic reticulum membrane", KeyWordDefinitionType.SURFACE), 
    SMOOTH_ER_MEMBRANE("Smooth ER membrane", "Smooth endoplasmic reticulum membrane", KeyWordDefinitionType.SURFACE), 
    GOLGI_MEMBRANE("", "Golgi apparatus membrane", KeyWordDefinitionType.SURFACE), 
    NUCLEUS_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    PEROXISOME_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    LYSOSOME_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    ENDOSOME_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    APICAL_ENDOSOME_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    SECRETORY_VESICLE_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    ORGANELLE_MEMBRANE("", "", KeyWordDefinitionType.SURFACE), 
    LIPID_DROPLET_SURFACE("", "", KeyWordDefinitionType.SURFACE), 
    MYELIN_SURFACE("", "", KeyWordDefinitionType.SURFACE), 
    SURFACE("", "", KeyWordDefinitionType.SURFACE), 
    VASCULATURE_PORES("", "", KeyWordDefinitionType.SURFACEPORE), 
    GLOMERULAR_PORES("", "", KeyWordDefinitionType.SURFACEPORE), 
    NEPHRONS("", "", KeyWordDefinitionType.TISSUE),
    CORTICAL_NEPHRONS("", "", KeyWordDefinitionType.TISSUE),
    JUXTA_MEDULLAR_NEPHRONS("", "", KeyWordDefinitionType.TISSUE),
    GLOMERULAR("", "", KeyWordDefinitionType.TISSUE),
    BOWMAN_CAPSULE("", "", KeyWordDefinitionType.TISSUE),
    PROXIMAL_TUBULE("", "", KeyWordDefinitionType.TISSUE),
    DESCENDING_LIMB_HENLE("", "", KeyWordDefinitionType.TISSUE),
    THIN_LIMB_HENLE("", "", KeyWordDefinitionType.TISSUE),
    THICK_LIMB_HENLE("", "", KeyWordDefinitionType.TISSUE),
    DISTAL_TUBULE("", "", KeyWordDefinitionType.TISSUE),
    MEDULLA("", "", KeyWordDefinitionType.TISSUE),
    MINOR_CALYX("", "", KeyWordDefinitionType.TISSUE),
    MAJOR_CALYX("", "", KeyWordDefinitionType.TISSUE),
    RENAL_PELVIS("", "", KeyWordDefinitionType.TISSUE), 
    Pericyte_CELL("", "", KeyWordDefinitionType.CELLTYPE), 
    Podocyte_CELL("", "", KeyWordDefinitionType.CELLTYPE),
    BOWMAN_CAPSULE_PODOCYTE_CELL("", "", KeyWordDefinitionType.CELLTYPE), 
    BC_LAYER_A("", "", KeyWordDefinitionType.CELLTYPE), 
    BC_URINE_DUCT("", "", KeyWordDefinitionType.CELLTYPE), 
    BC_VISCERAL("", "", KeyWordDefinitionType.CELLTYPE), 
    BC_FILTRATION("", "", KeyWordDefinitionType.CELLTYPE)
    ;
    
    private final String displayName;
    private final String description;
    private KeyWordDefinitionType keywordType;
    
    private KeyWord(final String displayName, final String description, final KeyWordDefinitionType keywordType) {
        this.keywordType = KeyWordDefinitionType.OTHER;
        this.displayName = displayName;
        this.description = description;
        this.keywordType = keywordType;
    }
    
    public String getName() {
        return super.toString();
    }
    
    @Override
    public String toString() {
        if (this.displayName != null && !this.displayName.equals("")) {
            return this.displayName;
        }
        String s = super.toString();
        s = s.replace('_', ' ');
        return s.substring(0, 1) + s.substring(1).toLowerCase(Locale.ENGLISH);
    }
    
    public String getDescription() {
        if (this.description != null && !this.description.equals("")) {
            return this.description;
        }
        return this.toString();
    }
    
    public KeyWordDefinitionType getKeywordType() {
        return this.keywordType;
    }
}
