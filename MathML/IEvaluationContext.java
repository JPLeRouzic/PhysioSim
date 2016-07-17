package MathML;

import java.util.Arrays;

/**
 * Interface for providing variable resolution in MathML expressions 
 * @author radams
 *
 */
public interface IEvaluationContext {
	
	/**
	 * Gets a value for a given identifier, or null if can't be found.
	 * @param identifier
	 * @return The <code>Iterable<Double></code> value or <code>null</code> if not found.
	 */
	public Iterable<Double> getValueFor(String identifier);
	
	/**
	 * Boolean test for whether a variable (identified by the argument)
	 *  can be resolved to a value.
	 * @param identifier A String identifer
	 * @return <code>true</code> if this value can be resolved, <code>false</code>
	 *  otherwise.
	 */
	public boolean hasValueFor(String identifier);
	
	/**
	 * Default null object  implementation
	 */
	public final static IEvaluationContext NULL_CONTEXT = new IEvaluationContext() {
		
		/**
		 * Returns an empty list
		 */
		public Iterable<Double> getValueFor(String identifier) {
			
			return Arrays.asList(0d);
		}

		/**
		 * Always returns <code>false</code>.
		 */
		public boolean hasValueFor(String identifier) {
			// TODO Auto-generated method stub
			return false;
		}
	};

}
