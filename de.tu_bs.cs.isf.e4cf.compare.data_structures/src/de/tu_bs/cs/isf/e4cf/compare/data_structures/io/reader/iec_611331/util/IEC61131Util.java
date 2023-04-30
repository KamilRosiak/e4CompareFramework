package de.tu_bs.cs.isf.e4cf.compare.data_structures.io.reader.iec_611331.util;

import java.util.List;

import org.eclipse.emf.common.util.EList;

import de.tu_bs.cs.isf.e4cf.compare.data_structures.impl.NodeImpl;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.interfaces.Node;
import de.tu_bs.cs.isf.e4cf.compare.data_structures.util.NodeBuilder;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Configuration;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Resource;
import de.tu_bs.cs.isf.familymining.ppu_iec.ppuIECmetaModel.configuration.Variable;

public class IEC61131Util {
	public static String GLOBAL_VARS = "Global Variabels";

	public static Node createConfigurationRoot(Configuration config) {
		NodeImpl node = new NodeImpl("Configuration");
		node.addAttribute(NodeBuilder.createAttribute("identifier", config.getIdentifier()));
		node.getChildren().add(createGlobalVarNode(config.getGlobalVariables()));
		node.getChildren().add(createRessourceNode(config.getResources()));
		return node;
	}

	private static Node createRessourceNode(EList<Resource> resources) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Node createGlobalVarNode(List<Variable> varList) {
		NodeImpl node = new NodeImpl(GLOBAL_VARS);

		return node;

	}
}
