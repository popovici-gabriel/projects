package health;
import opencard.opt.applet.AppletState;

	/**
	 * HealthCardState represents the state of an arbitrary <tt>HealthCardApplet</tt>
	 * on a JavaCard. 
	 * The state indicates whether card holder verification must be performed 
	 * before the applet may ba accessed.
	 * 
	 */
	public class HealthCardState extends AppletState {
		/** Remembers whether a successful card holder verification has been performed. */
		protected boolean chvPerformed_ = false;
	  /**
	   * This method is expected to be called by the select methods of proxies
	   * which are not assiciated to the same applet as the state object
	   * to indicate when the applet associated with this state is deselected.
	   */
	  public void appletDeselected() {
		chvPerformed_ = false;
	  }    
	  /**
	   * Checks whether a successful card holder verification has already
	   * been performed.
	   *
	   * @return true if a successful card holder verification has been
	   *         performed, false otherwise.
	   */
	  public boolean isCHVPerformed() {
		return chvPerformed_;
	  }    
	  /**
	   * Sets the card holder verification flag to the given value.
	   *
	   * @param chvPerformed Indicates whether a successful
	   *                     card holder verification has been performed;
	   *                     true indicates yes, false indicates no.
	   */
	  public void setCHVPerformed(boolean chvPerformed) {
		chvPerformed_ = chvPerformed;
	  }    
}
	


