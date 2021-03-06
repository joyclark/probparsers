package de.be4.classicalb.core.parser.analysis.prolog;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import de.be4.classicalb.core.parser.Utils;
import de.be4.classicalb.core.parser.analysis.DepthFirstAdapter;
import de.be4.classicalb.core.parser.node.*;

/**
 * This class finds all references to external machines in a machine definition.
 * Use this class by calling the static method
 * {@link #getReferencedMachines(Node)}.
 * 
 * @author plagge
 */
public class ReferencedMachines extends DepthFirstAdapter {
	private SortedSet<String> machines = new TreeSet<String>();
	private String name;

	/**
	 * Searches the syntax tree of a machine for references to external
	 * machines, like in SEES, INCLUDES, etc.
	 * 
	 * @param node
	 *            the root node of the machine's syntax tree, never
	 *            <code>null</code>
	 */
	public ReferencedMachines(Node node) {
		node.apply(this);
	}

	/**
	 * Returns all referenced machine names in the given machine
	 * 
	 * @return a set of machine names, never <code>null</code>
	 */
	public SortedSet<String> getReferencedMachines() {
		return machines;
	}

	/**
	 * 
	 * @return the name of the machine, <code>null</code> if no name was found
	 */
	public String getName() {
		return name;
	}

	@Override
	public void caseAMachineHeader(AMachineHeader node) {
		name = Utils.getIdentifierAsString(node.getName());
	}

	@Override
	public void caseAMachineReference(AMachineReference node) {
		machines.add(getIdentifier(node.getMachineName()));
	}

	private String getIdentifier(LinkedList<TIdentifierLiteral> list) {
		return list.getLast().getText();
	}

	// SEES and USES

	@Override
	public void caseASeesMachineClause(ASeesMachineClause node) {
		registerMachineNames(node.getMachineNames());
	}

	@Override
	public void caseAUsesMachineClause(AUsesMachineClause node) {
		registerMachineNames(node.getMachineNames());
	}

	// REFINES
	@Override
	public void caseARefinementMachineParseUnit(ARefinementMachineParseUnit node) {
		node.getHeader().apply(this);
		machines.add(node.getRefMachine().getText());
		for (Node mclause : node.getMachineClauses()) {
			mclause.apply(this);
		}
	}

	// IMPLEMENTS
	@Override
	public void caseAImplementationMachineParseUnit(
			AImplementationMachineParseUnit node) {
		node.getHeader().apply(this);
		machines.add(node.getRefMachine().getText());
		for (Node mclause : node.getMachineClauses()) {
			mclause.apply(this);
		}
	}

	private void registerMachineNames(List<PExpression> machineNames) {
		for (PExpression machineName : machineNames) {
			if (machineName instanceof AIdentifierExpression) {
				AIdentifierExpression identifier = (AIdentifierExpression) machineName;
				machines.add(getIdentifier(identifier.getIdentifier()));
			}
		}
	}

	/***************************************************************************
	 * exclude large sections of a machine without machine references by doing
	 * nothing
	 */

	@Override
	public void caseAConstraintsMachineClause(AConstraintsMachineClause node) {
	}

	@Override
	public void caseAInvariantMachineClause(AInvariantMachineClause node) {
	}

	@Override
	public void caseAOperationsMachineClause(AOperationsMachineClause node) {
	}

	@Override
	public void caseAPropertiesMachineClause(APropertiesMachineClause node) {
	}

	@Override
	public void caseADefinitionsMachineClause(ADefinitionsMachineClause node) {
	}

	@Override
	public void caseAInitialisationMachineClause(
			AInitialisationMachineClause node) {
	}

	@Override
	public void caseALocalOperationsMachineClause(
			ALocalOperationsMachineClause node) {
	}
}
