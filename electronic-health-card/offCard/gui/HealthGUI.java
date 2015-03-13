package gui;
import health.*;
import health.DoctorInfo;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JTable;
import javax.swing.JMenu;
import javax.swing.JTextField;
import javax.swing.JDesktopPane;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JButton;
import javax.swing.JList;


/**
 * An example that shows a JToolbar, as well as a JList, JTable, JSplitPane and JTree
 */
public class HealthGUI extends javax.swing.JFrame {
	
	public   DoctorInfo doctorInfo = null ;
	
	public PacientInfo pacientInfo  = null ; 
		
	/** The object used for accessing data stored by the <tt>CardApplet</tt>
	  on the card. */
	public HealthCard healthCard = null;
	
	private javax.swing.JMenuBar ivjJMenuBar = null;

	private javax.swing.JMenu ivjJMenu = null;

	private javax.swing.JMenu ivjJMenu1 = null;

	private javax.swing.JMenuItem ivjJMenuItem = null;

	private javax.swing.JMenuItem ivjJMenuItem1 = null;

	private javax.swing.JCheckBoxMenuItem ivjJCheckBoxMenuItem = null;

	private javax.swing.JRadioButtonMenuItem ivjJRadioButtonMenuItem = null;

	private javax.swing.JRadioButtonMenuItem ivjJRadioButtonMenuItem1 = null;

	private JDesktopPane jDesktopPane1 = null;

	private JMenuItem ivjMenuItem2 = null;

	private JInternalFrame jInternalFrame = null;

	private JInternalFrame jInternalFrame1 = null;

	private JDesktopPane jDesktopPane = null;

	private JLabel jLabel1 = null;

	private JTextField jTextDName = null;

	private JLabel jLabel2 = null;

	private JTextField jTextDType = null;

	private JLabel jLabel3 = null;

	private JTextField jTextDPhone = null;

	private JLabel jLabel4 = null;

	private JTextField jTextDMobile = null;

	private JLabel jLabel5 = null;

	private JTextField jTextDEmail = null;

	private JButton jButton = null;

	private JButton jButton1 = null;

	private JDesktopPane jDesktopPane2 = null;

	private JLabel jLabel = null;

	private JTextField jTextPName = null;

	private JLabel jLabel6 = null;

	private JLabel jLabel7 = null;

	private JLabel jLabel8 = null;

	private JLabel jLabel9 = null;

	private JLabel jLabel10 = null;

	private JLabel jLabel11 = null;

	private JLabel jLabel12 = null;

	private JLabel jLabel13 = null;

	private JLabel jLabel14 = null;

	private JLabel jLabel15 = null;

	private JLabel jLabel16 = null;

	private JLabel jLabel17 = null;

	private JLabel jLabel18 = null;

	private JLabel jLabel19 = null;

	private JLabel jLabel20 = null;

	private JLabel jLabel21 = null;

	private JLabel jLabel22 = null;

	private JLabel jLabel23 = null;

	private JLabel jLabel24 = null;

	private JTextField jTextPTitle = null;

	private JTextField jTextPPhone = null;

	private JTextField jTextPMobile = null;

	private JTextField jTextPEmail = null;

	private JTextField jTextPDateIssued = null;

	private JTextField jTextPDateUpdate = null;

	private JTextField jTextPAddress = null;

	private JTextField jTextPGender = null;

	private JTextField jTextPCNP = null;

	private JTextField jTextPDob = null;

	private JTextField jTextPBloodType = null;

	private JTextField jTextPHName = null;

	private JTextField jTextPHAdmissionDate = null;

	private JTextField jTextPHOrganDonor = null;

	private JTextField jTextPDrugMedications = null;

	private JTextField jTextPDrugAllergies = null;

	private JTextField jTextPDiagnose = null;

	private JTextField jTextPDiseaseImm = null;

	private JTextField jTextPSurgicalProc = null;

	private JLabel jLabel25 = null;

	private JTextField jTextPComments = null;

	private JButton jButton2 = null;

	private JButton jButton3 = null;

	public HealthGUI() {
		super();
		initialize();
	}

	/**
	 * Initialize the class.
	 */
	private void initialize() {
		this.setJMenuBar(getIvjJMenuBar());
		this.setContentPane(getJDesktopPane1());
		this.setName("JFrame1");
		this.setTitle("Health Card Project");
		this
				.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		this.setBounds(23, 36, 761, 697);
	}

	/**
	 * This method initializes ivjJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private javax.swing.JMenuBar getIvjJMenuBar() {
		if (ivjJMenuBar == null) {
			ivjJMenuBar = new javax.swing.JMenuBar();
			ivjJMenuBar.add(getIvjJMenu());
			ivjJMenuBar.add(getIvjJMenu1());
		}
		return ivjJMenuBar;
	}

	/**
	 * This method initializes ivjJMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private javax.swing.JMenu getIvjJMenu() {
		if (ivjJMenu == null) {
			ivjJMenu = new javax.swing.JMenu();
			ivjJMenu.add(getIvjJMenuItem());
			ivjJMenu.add(getIvjJMenuItem1());
			ivjJMenu.setText("FRAME");
			ivjJMenu.add(getIvjMenuItem2());
		}
		return ivjJMenu;
	}

	/**
	 * This method initializes ivjJMenu1
	 * 
	 * @return javax.swing.JMenu
	 */
	private javax.swing.JMenu getIvjJMenu1() {
		if (ivjJMenu1 == null) {
			ivjJMenu1 = new javax.swing.JMenu();
			ivjJMenu1.add(getIvjJCheckBoxMenuItem());
			ivjJMenu1.add(getIvjJRadioButtonMenuItem());
			ivjJMenu1.add(getIvjJRadioButtonMenuItem1());
			ivjJMenu1.setText("Window");
		}
		return ivjJMenu1;
	}

	/**
	 * This method initializes ivjJMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private javax.swing.JMenuItem getIvjJMenuItem() {
		if (ivjJMenuItem == null) {
			ivjJMenuItem = new javax.swing.JMenuItem();
			ivjJMenuItem.setText("Pacient");
		}
		return ivjJMenuItem;
	}

	/**
	 * This method initializes ivjJMenuItem1
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private javax.swing.JMenuItem getIvjJMenuItem1() {
		if (ivjJMenuItem1 == null) {
			ivjJMenuItem1 = new javax.swing.JMenuItem();
			ivjJMenuItem1.setText("Doctor");
		}
		return ivjJMenuItem1;
	}

	/**
	 * This method initializes ivjJCheckBoxMenuItem
	 * 
	 * @return javax.swing.JCheckBoxMenuItem
	 */
	private javax.swing.JCheckBoxMenuItem getIvjJCheckBoxMenuItem() {
		if (ivjJCheckBoxMenuItem == null) {
			ivjJCheckBoxMenuItem = new javax.swing.JCheckBoxMenuItem();
			ivjJCheckBoxMenuItem.setText("is Active");
		}
		return ivjJCheckBoxMenuItem;
	}

	/**
	 * This method initializes ivjJRadioButtonMenuItem
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 */
	private javax.swing.JRadioButtonMenuItem getIvjJRadioButtonMenuItem() {
		if (ivjJRadioButtonMenuItem == null) {
			ivjJRadioButtonMenuItem = new javax.swing.JRadioButtonMenuItem();
			ivjJRadioButtonMenuItem.setText("On");
		}
		return ivjJRadioButtonMenuItem;
	}

	/**
	 * This method initializes ivjJRadioButtonMenuItem1
	 * 
	 * @return javax.swing.JRadioButtonMenuItem
	 */
	private javax.swing.JRadioButtonMenuItem getIvjJRadioButtonMenuItem1() {
		if (ivjJRadioButtonMenuItem1 == null) {
			ivjJRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
			ivjJRadioButtonMenuItem1.setText("Off");
		}
		return ivjJRadioButtonMenuItem1;
	}

	/**
	 * This method initializes jDesktopPane1	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane1() {
		if (jDesktopPane1 == null) {
			jDesktopPane1 = new JDesktopPane();
			jDesktopPane1.add(getJInternalFrame(), null);
			jDesktopPane1.add(getJInternalFrame1(), null);
		}
		return jDesktopPane1;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getIvjMenuItem2() {
		if (ivjMenuItem2 == null) {
			ivjMenuItem2 = new JMenuItem();
			ivjMenuItem2.setText("EXIT");
			ivjMenuItem2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					System.exit(0);
				}
			});
		}
		return ivjMenuItem2;
	}

	/**
	 * This method initializes jInternalFrame	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame() {
		if (jInternalFrame == null) {
			jInternalFrame = new JInternalFrame("Pacient",true,true,true,true);
			jInternalFrame.setBounds(new java.awt.Rectangle(317,11,393,621));
			//jInternalFrame.setTitle("Pacient");
			jInternalFrame.setContentPane(getJDesktopPane2());
			jInternalFrame.setVisible(true);
		}
		return jInternalFrame;
	}

	/**
	 * This method initializes jInternalFrame1	
	 * 	
	 * @return javax.swing.JInternalFrame	
	 */
	private JInternalFrame getJInternalFrame1() {
		if (jInternalFrame1 == null) {
			jInternalFrame1 = new JInternalFrame("Doctor",true,true,true,true);
			jInternalFrame1.setBounds(new java.awt.Rectangle(32,18,253,297));
			//jInternalFrame1.setTitle("Doctor");			
			jInternalFrame1.setContentPane(getJDesktopPane());
			jInternalFrame1.setVisible(true);
		}
		return jInternalFrame1;
	}

	/**
	 * This method initializes jDesktopPane	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane() {
		if (jDesktopPane == null) {
			jLabel5 = new JLabel();
			jLabel5.setBounds(new java.awt.Rectangle(24,159,48,16));
			jLabel5.setText("E-MAIL");
			jLabel4 = new JLabel();
			jLabel4.setBounds(new java.awt.Rectangle(17,127,52,16));
			jLabel4.setText("MOBILE");
			jLabel3 = new JLabel();
			jLabel3.setBounds(new java.awt.Rectangle(20,91,46,16));
			jLabel3.setText("PHONE");
			jLabel2 = new JLabel();
			jLabel2.setBounds(new java.awt.Rectangle(25,52,38,16));
			jLabel2.setText("TYPE");
			jLabel1 = new JLabel();
			jLabel1.setBounds(new java.awt.Rectangle(22,16,38,16));
			jLabel1.setText("NAME");
			jDesktopPane = new JDesktopPane();
			jDesktopPane.add(jLabel1, null);
			jDesktopPane.add(getJTextDName(), null);
			jDesktopPane.add(jLabel2, null);
			jDesktopPane.add(getJTextDType(), null);
			jDesktopPane.add(jLabel3, null);
			jDesktopPane.add(getJTextDPhone(), null);
			jDesktopPane.add(jLabel4, null);
			jDesktopPane.add(getJTextDMobile(), null);
			jDesktopPane.add(jLabel5, null);
			jDesktopPane.add(getJTextDEmail(), null);
			jDesktopPane.add(getJButton(), null);
			jDesktopPane.add(getJButton1(), null);
		}
		return jDesktopPane;
	}

	/**
	 * This method initializes jTextField1	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDName() {
		if (jTextDName == null) {
			jTextDName = new JTextField();
			jTextDName.setBounds(new java.awt.Rectangle(83,14,115,20));
			jTextDName.setColumns(0);
		}
		return jTextDName;
	}

	/**
	 * This method initializes jTextField2	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDType() {
		if (jTextDType == null) {
			jTextDType = new JTextField();
			jTextDType.setBounds(new java.awt.Rectangle(79,48,122,20));
		}
		return jTextDType;
	}

	/**
	 * This method initializes jTextField3	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDPhone() {
		if (jTextDPhone == null) {
			jTextDPhone = new JTextField();
			jTextDPhone.setBounds(new java.awt.Rectangle(85,90,120,20));
		}
		return jTextDPhone;
	}

	/**
	 * This method initializes jTextField4	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDMobile() {
		if (jTextDMobile == null) {
			jTextDMobile = new JTextField();
			jTextDMobile.setBounds(new java.awt.Rectangle(84,121,122,20));
		}
		return jTextDMobile;
	}

	/**
	 * This method initializes jTextField5	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDEmail() {
		if (jTextDEmail == null) {
			jTextDEmail = new JTextField();
			jTextDEmail.setBounds(new java.awt.Rectangle(90,154,120,20));
		}
		return jTextDEmail;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new java.awt.Rectangle(38,201,75,24));
			jButton.setText("INSERT");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					System.out.println(""+jTextDName.getText());
					System.out.println(""+jTextDType.getText());
					System.out.println(""+jTextDPhone.getText());
					System.out.println(""+jTextDEmail.getText());					
					doctorInfo = new DoctorInfo(jTextDName.getText(),jTextDType.getText(),
												jTextDPhone.getText(),jTextDMobile.getText(),
												jTextDEmail.getText());														
					System.out.println (""+doctorInfo.toString());
					try {											
					healthCard = new HealthCard();
					healthCard.setDoctorInfo(0,doctorInfo);					
					}
					catch (HealthCardException hce) {
					hce.printStackTrace();
					System.exit(0);
					}
					
					
					jTextDName.setText("");
					jTextDType.setText("");
					jTextDPhone.setText("");
					jTextDMobile.setText("");
					jTextDEmail.setText("");
				}
			});
		}
		return jButton;
	}

	/**
	 * This method initializes jButton1	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton1() {
		if (jButton1 == null) {
			jButton1 = new JButton();
			jButton1.setBounds(new java.awt.Rectangle(134,199,89,26));
			jButton1.setText("GET INFO");
			jButton1.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					
						jTextDName.setText(doctorInfo.getName());
						jTextDType.setText(doctorInfo.getType());
						jTextDPhone.setText(doctorInfo.getPhone());
						jTextDMobile.setText(doctorInfo.getMobile());
						jTextDEmail.setText(doctorInfo.getEmail());
					
				}
			});
		}
		return jButton1;
	}
	/**
	 * This method initializes jDesktopPane2	
	 * 	
	 * @return javax.swing.JDesktopPane	
	 */
	private JDesktopPane getJDesktopPane2() {
		if (jDesktopPane2 == null) {
			jLabel25 = new JLabel();
			jLabel25.setBounds(new java.awt.Rectangle(24,491,70,16));
			jLabel25.setText("COMMENTS");
			jLabel24 = new JLabel();
			jLabel24.setBounds(new java.awt.Rectangle(21,464,68,16));
			jLabel24.setText("OPERATII");
			jLabel23 = new JLabel();
			jLabel23.setBounds(new java.awt.Rectangle(17,444,84,16));
			jLabel23.setText("VACCINARI");
			jLabel22 = new JLabel();
			jLabel22.setBounds(new java.awt.Rectangle(17,423,63,16));
			jLabel22.setText("DIAGNOZA");
			jLabel21 = new JLabel();
			jLabel21.setBounds(new java.awt.Rectangle(19,399,59,16));
			jLabel21.setText("ALERGII");
			jLabel20 = new JLabel();
			jLabel20.setBounds(new java.awt.Rectangle(15,374,109,16));
			jLabel20.setText("MEDICAMENTE");
			jLabel19 = new JLabel();
			jLabel19.setBounds(new java.awt.Rectangle(16,348,98,16));
			jLabel19.setText("ORGAN DONOR");
			jLabel18 = new JLabel();
			jLabel18.setBounds(new java.awt.Rectangle(11,325,102,16));
			jLabel18.setText("DATA INTERNARE");
			jLabel17 = new JLabel();
			jLabel17.setBounds(new java.awt.Rectangle(10,301,98,16));
			jLabel17.setText("NUME SPITAL");
			jLabel16 = new JLabel();
			jLabel16.setBounds(new java.awt.Rectangle(14,277,81,16));
			jLabel16.setText("TIP SINGE");
			jLabel15 = new JLabel();
			jLabel15.setBounds(new java.awt.Rectangle(17,256,96,16));
			jLabel15.setText("DATA NASTERE");
			jLabel14 = new JLabel();
			jLabel14.setBounds(new java.awt.Rectangle(16,184,56,16));
			jLabel14.setText("Address");
			jLabel13 = new JLabel();
			jLabel13.setBounds(new java.awt.Rectangle(34,232,38,16));
			jLabel13.setText("CNP");
			jLabel12 = new JLabel();
			jLabel12.setBounds(new java.awt.Rectangle(17,209,57,16));
			jLabel12.setText("GENDER");
			jLabel11 = new JLabel();
			jLabel11.setBounds(new java.awt.Rectangle(9,162,86,16));
			jLabel11.setText("DATA UPDATE");
			jLabel10 = new JLabel();
			jLabel10.setBounds(new java.awt.Rectangle(7,143,109,16));
			jLabel10.setText("DATA ELIBERARE");
			jLabel9 = new JLabel();
			jLabel9.setBounds(new java.awt.Rectangle(17,120,51,16));
			jLabel9.setText("E-MAIL");
			jLabel8 = new JLabel();
			jLabel8.setBounds(new java.awt.Rectangle(21,97,47,16));
			jLabel8.setText("MOBILE");
			jLabel7 = new JLabel();
			jLabel7.setBounds(new java.awt.Rectangle(16,68,49,16));
			jLabel7.setText("PHONE");
			jLabel6 = new JLabel();
			jLabel6.setBounds(new java.awt.Rectangle(29,43,38,16));
			jLabel6.setText("TITLE");
			jLabel = new JLabel();
			jLabel.setBounds(new java.awt.Rectangle(29,17,38,16));
			jLabel.setText("NAME");
			jDesktopPane2 = new JDesktopPane();
			jDesktopPane2.add(jLabel, null);
			jDesktopPane2.add(getJTextPName(), null);
			jDesktopPane2.add(jLabel6, null);
			jDesktopPane2.add(jLabel7, null);
			jDesktopPane2.add(jLabel8, null);
			jDesktopPane2.add(jLabel9, null);
			jDesktopPane2.add(jLabel10, null);
			jDesktopPane2.add(jLabel11, null);
			jDesktopPane2.add(jLabel12, null);
			jDesktopPane2.add(jLabel13, null);
			jDesktopPane2.add(jLabel14, null);
			jDesktopPane2.add(jLabel15, null);
			jDesktopPane2.add(jLabel16, null);
			jDesktopPane2.add(jLabel17, null);
			jDesktopPane2.add(jLabel18, null);
			jDesktopPane2.add(jLabel19, null);
			jDesktopPane2.add(jLabel20, null);
			jDesktopPane2.add(jLabel21, null);
			jDesktopPane2.add(jLabel22, null);
			jDesktopPane2.add(jLabel23, null);
			jDesktopPane2.add(jLabel24, null);
			jDesktopPane2.add(getJTextTitle(), null);
			jDesktopPane2.add(getJTextPhone(), null);
			jDesktopPane2.add(getJTextMobile(), null);
			jDesktopPane2.add(getJTextEmail(), null);
			jDesktopPane2.add(getJTextDateIssued(), null);
			jDesktopPane2.add(getJTextDateUpdate(), null);
			jDesktopPane2.add(getJTextAddress(), null);
			jDesktopPane2.add(getJTextGender(), null);
			jDesktopPane2.add(getJTextCNP(), null);
			jDesktopPane2.add(getJTextDob(), null);
			jDesktopPane2.add(getJTextBloodType(), null);
			jDesktopPane2.add(getJTextHName(), null);
			jDesktopPane2.add(getJTextHAdmissionDate(), null);
			jDesktopPane2.add(getJTextHOrganDonor(), null);
			jDesktopPane2.add(getJTextDrugMedications(), null);
			jDesktopPane2.add(getJTextDrugAllergies(), null);
			jDesktopPane2.add(getJTextDiagnose(), null);
			jDesktopPane2.add(getJTextDiseaseImm(), null);
			jDesktopPane2.add(getJTextSurgicalProc(), null);
			jDesktopPane2.add(jLabel25, null);
			jDesktopPane2.add(getJTextComments(), null);
			jDesktopPane2.add(getJButton2(), null);
			jDesktopPane2.add(getJButton3(), null);
		}
		return jDesktopPane2;
	}

	/**
	 * This method initializes jTextPName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextPName() {
		if (jTextPName == null) {
			jTextPName = new JTextField();
			jTextPName.setBounds(new java.awt.Rectangle(113,14,163,20));
		}
		return jTextPName;
	}

	/**
	 * This method initializes jTextTitle	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextTitle() {
		if (jTextPTitle == null) {
			jTextPTitle = new JTextField();
			jTextPTitle.setBounds(new java.awt.Rectangle(119,39,161,20));
		}
		return jTextPTitle;
	}

	/**
	 * This method initializes jTextPhone	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextPhone() {
		if (jTextPPhone == null) {
			jTextPPhone = new JTextField();
			jTextPPhone.setBounds(new java.awt.Rectangle(118,65,155,20));
		}
		return jTextPPhone;
	}

	/**
	 * This method initializes jTextMobile	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextMobile() {
		if (jTextPMobile == null) {
			jTextPMobile = new JTextField();
			jTextPMobile.setBounds(new java.awt.Rectangle(126,93,158,20));
		}
		return jTextPMobile;
	}

	/**
	 * This method initializes jTextEmail	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextEmail() {
		if (jTextPEmail == null) {
			jTextPEmail = new JTextField();
			jTextPEmail.setBounds(new java.awt.Rectangle(125,117,161,20));
		}
		return jTextPEmail;
	}

	/**
	 * This method initializes jTextDateIssued	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDateIssued() {
		if (jTextPDateIssued == null) {
			jTextPDateIssued = new JTextField();
			jTextPDateIssued.setBounds(new java.awt.Rectangle(125,140,168,20));
		}
		return jTextPDateIssued;
	}

	/**
	 * This method initializes jTextDateUpdate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDateUpdate() {
		if (jTextPDateUpdate == null) {
			jTextPDateUpdate = new JTextField();
			jTextPDateUpdate.setBounds(new java.awt.Rectangle(122,160,172,20));
		}
		return jTextPDateUpdate;
	}

	/**
	 * This method initializes jTextAddress	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextAddress() {
		if (jTextPAddress == null) {
			jTextPAddress = new JTextField();
			jTextPAddress.setBounds(new java.awt.Rectangle(122,184,182,20));
		}
		return jTextPAddress;
	}

	/**
	 * This method initializes jTextGender	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextGender() {
		if (jTextPGender == null) {
			jTextPGender = new JTextField();
			jTextPGender.setBounds(new java.awt.Rectangle(122,206,181,20));
		}
		return jTextPGender;
	}

	/**
	 * This method initializes jTextCNP	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextCNP() {
		if (jTextPCNP == null) {
			jTextPCNP = new JTextField();
			jTextPCNP.setBounds(new java.awt.Rectangle(121,229,189,20));
		}
		return jTextPCNP;
	}

	/**
	 * This method initializes jTextDob	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDob() {
		if (jTextPDob == null) {
			jTextPDob = new JTextField();
			jTextPDob.setBounds(new java.awt.Rectangle(123,254,198,20));
		}
		return jTextPDob;
	}

	/**
	 * This method initializes jTextBloodType	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextBloodType() {
		if (jTextPBloodType == null) {
			jTextPBloodType = new JTextField();
			jTextPBloodType.setBounds(new java.awt.Rectangle(123,275,185,20));
		}
		return jTextPBloodType;
	}

	/**
	 * This method initializes jTextHName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextHName() {
		if (jTextPHName == null) {
			jTextPHName = new JTextField();
			jTextPHName.setBounds(new java.awt.Rectangle(122,296,210,20));
		}
		return jTextPHName;
	}

	/**
	 * This method initializes jTextHAdmissionDate	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextHAdmissionDate() {
		if (jTextPHAdmissionDate == null) {
			jTextPHAdmissionDate = new JTextField();
			jTextPHAdmissionDate.setBounds(new java.awt.Rectangle(123,320,206,20));
		}
		return jTextPHAdmissionDate;
	}

	/**
	 * This method initializes jTextHOrganDonor	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextHOrganDonor() {
		if (jTextPHOrganDonor == null) {
			jTextPHOrganDonor = new JTextField();
			jTextPHOrganDonor.setBounds(new java.awt.Rectangle(124,341,104,20));
		}
		return jTextPHOrganDonor;
	}

	/**
	 * This method initializes jTextDrugMedications	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDrugMedications() {
		if (jTextPDrugMedications == null) {
			jTextPDrugMedications = new JTextField();
			jTextPDrugMedications.setBounds(new java.awt.Rectangle(131,370,225,20));
		}
		return jTextPDrugMedications;
	}

	/**
	 * This method initializes jTextDrugAllergies	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDrugAllergies() {
		if (jTextPDrugAllergies == null) {
			jTextPDrugAllergies = new JTextField();
			jTextPDrugAllergies.setBounds(new java.awt.Rectangle(131,395,239,20));
		}
		return jTextPDrugAllergies;
	}

	/**
	 * This method initializes jTextDiagnose	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDiagnose() {
		if (jTextPDiagnose == null) {
			jTextPDiagnose = new JTextField();
			jTextPDiagnose.setBounds(new java.awt.Rectangle(129,417,237,20));
		}
		return jTextPDiagnose;
	}

	/**
	 * This method initializes jTextDiseaseImm	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextDiseaseImm() {
		if (jTextPDiseaseImm == null) {
			jTextPDiseaseImm = new JTextField();
			jTextPDiseaseImm.setBounds(new java.awt.Rectangle(127,442,249,20));
		}
		return jTextPDiseaseImm;
	}

	/**
	 * This method initializes jTextSurgicalProc	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextSurgicalProc() {
		if (jTextPSurgicalProc == null) {
			jTextPSurgicalProc = new JTextField();
			jTextPSurgicalProc.setBounds(new java.awt.Rectangle(126,465,240,20));
		}
		return jTextPSurgicalProc;
	}

	/**
	 * This method initializes jTextComments	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextComments() {
		if (jTextPComments == null) {
			jTextPComments = new JTextField();
			jTextPComments.setBounds(new java.awt.Rectangle(129,488,231,20));
		}
		return jTextPComments;
	}

	/**
	 * This method initializes jButton2	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton2() {
		if (jButton2 == null) {
			jButton2 = new JButton();
			jButton2.setBounds(new java.awt.Rectangle(31,533,80,31));
			jButton2.setText("INSERT ");
			jButton2.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
					pacientInfo = new PacientInfo(jTextPName.getText(),
							jTextPTitle.getText(),jTextPPhone.getText(),jTextPMobile.getText(),
							jTextPEmail.getText(),jTextPDateIssued.getText(),jTextPDateUpdate.getText(),
							jTextPAddress.getText(),jTextPGender.getText(),jTextPCNP.getText(),
							jTextPDob.getText(),jTextPBloodType.getText(),jTextPHName.getText(),
							jTextPHAdmissionDate.getText(),jTextPHOrganDonor.getText(),jTextPDrugMedications.getText(),
							jTextPDrugAllergies.getText(),jTextPDiagnose.getText(),jTextPDiseaseImm.getText(),
							jTextPSurgicalProc.getText(),jTextPComments.getText());
					System.out.println(""+pacientInfo.toString());
					
				}
			});
		}
		return jButton2;
	}

	/**
	 * This method initializes jButton3	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton3() {
		if (jButton3 == null) {
			jButton3 = new JButton();
			jButton3.setBounds(new java.awt.Rectangle(166,532,115,34));
			jButton3.setText("GET INFO");
			jButton3.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.out.println("actionPerformed()"); // TODO Auto-generated Event stub actionPerformed()
				}
			});
		}
		return jButton3;
	}

	public static void main (String args[]){
		
	}
}  //  @jve:decl-index=0:visual-constraint="71,6"
