This will be a human physiology simulator that aims at creating drugs ex silico.
This will have several advantages, it will reduce both the animal experiments and clinical trials.

At the moment it does not work, only some parts are somewhat near Alpha stage.

The main inspiration for the Gui is BioDmet from General Electric, it uses the same idea of representing organs as a tree. However BioDmet uses its own proprietary model format and is quite rigid. For example there is only one lung, without any compartments. On the overall BioDmet model of human physiology is quite limited. It is probably one of the many avatars of Guyton's model and has never been updated, it neither have any mechanism for accepting bugs or contributions.

Like other simulators it has several ODE solvers but at some time we will try Probabilistic Programming in order to make it possible to start from the intended result and progress toward a model description. This will speed up hypothesis testing.

The physiology model is implemented at organ level (or below) by SBML format. But the fluid circulation between organs is described in a single XML file which is not in SBML format. The fluid circulation implement a PBPK functionality which should be as good as what other PBPK simulator provide today. This XML file will be modifiable as well, therefore users will be able to use PhysioSim to create and analyze pharmacokinetic models. 

The idea is that the user could use the tree to select a leaf and substitute their own models as needed, in place of the provided models, in order to implement their own project whithout bothering simulating other human physiology parts. The user can increase the complexity of the tree as needed (something not supported by simulators such as BioDmet).

Over the last decade, it has become clear that the responses of individuals to drugs can vary. Often, divergent responses can be attributed in part to differences in the genomic makeup of the individual patient. Variations in genomic characteristics, such as polymorphisms in drug metabolizing enzymes and drug targets, have led to the understanding that drug dosage needs to be matched to the genomic makeup of the individual patient. 

PhysioSim software will allows users to model, simulate, and analyze biochemical and system pathways for applications in drug discovery and design, target identification, and pharmacokinetic modeling. PhysioSim will provide an integrated environment for modeling human biological processes, enabling simulation of the dynamic behavior of these processes at several hierarchical levels, and analyzing the model by comparing simulation and experimental data. Biological processes will include metabolic, genetic, and signaling pathways.

Modeling, simulating, and analyzing a biological system will permit  test hypotheses for a pathway, identify side effects caused by drug interactions with a target compound, and identify biochemical pathways that lead to disease.

