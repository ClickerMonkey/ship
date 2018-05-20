import javax.swing.DefaultListModel;


/**
 *
 * @author  Philip Diffenderfer
 */
public class Game24Applet extends javax.swing.JApplet {
    
	private static final long serialVersionUID = 1L;
	
    private Game24 game = new Game24();
  
    
    public void init() {
    	this.setSize(401, 205);
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    initComponents();
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void initComponents() {
        pnlSolve = new javax.swing.JPanel();
        btnSolve = new javax.swing.JButton();
        txtSolveA = new javax.swing.JTextField();
        txtSolveB = new javax.swing.JTextField();
        txtSolveC = new javax.swing.JTextField();
        txtSolveD = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSolutions = new javax.swing.JList();
        pnlCreate = new javax.swing.JPanel();
        cmbDigits = new javax.swing.JComboBox();
        lblDigits = new javax.swing.JLabel();
        lblDifficulty = new javax.swing.JLabel();
        cmbDifficulty = new javax.swing.JComboBox();
        btnCreate = new javax.swing.JButton();
        txtCreateA = new javax.swing.JTextField();
        txtCreateB = new javax.swing.JTextField();
        txtCreateC = new javax.swing.JTextField();
        txtCreateD = new javax.swing.JTextField();

        pnlSolve.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Solve 24 Card", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
        btnSolve.setText("Solve!");
        btnSolve.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commandSolveGame(evt);
            }
        });

        txtSolveA.setPreferredSize(new java.awt.Dimension(32, 19));

        txtSolveB.setPreferredSize(new java.awt.Dimension(32, 19));

        txtSolveC.setPreferredSize(new java.awt.Dimension(32, 19));

        txtSolveD.setPreferredSize(new java.awt.Dimension(32, 19));

        jScrollPane1.setViewportView(lstSolutions);

        org.jdesktop.layout.GroupLayout pnlSolveLayout = new org.jdesktop.layout.GroupLayout(pnlSolve);
        pnlSolve.setLayout(pnlSolveLayout);
        pnlSolveLayout.setHorizontalGroup(
            pnlSolveLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, pnlSolveLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlSolveLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .add(pnlSolveLayout.createSequentialGroup()
                        .add(txtSolveA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlSolveLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, btnSolve, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, pnlSolveLayout.createSequentialGroup()
                                .add(txtSolveB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(txtSolveC, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                        .add(10, 10, 10)
                        .add(txtSolveD, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlSolveLayout.setVerticalGroup(
            pnlSolveLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlSolveLayout.createSequentialGroup()
                .add(btnSolve)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlSolveLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtSolveA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtSolveD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtSolveB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtSolveC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCreate.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "Create 24 Card", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12)));
        cmbDigits.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Single Digits", "Double Digits", "Triple Digits" }));

        lblDigits.setText("Digit Count");

        lblDifficulty.setText("Difficulty");

        cmbDifficulty.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1 Dot", "2 Dots", "3 Dots" }));

        btnCreate.setText("Create!");
        btnCreate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commandCreateGame(evt);
            }
        });

        txtCreateA.setEditable(false);
        txtCreateA.setPreferredSize(new java.awt.Dimension(32, 19));

        txtCreateB.setEditable(false);
        txtCreateB.setPreferredSize(new java.awt.Dimension(32, 19));

        txtCreateC.setEditable(false);
        txtCreateC.setPreferredSize(new java.awt.Dimension(32, 19));

        txtCreateD.setEditable(false);
        txtCreateD.setPreferredSize(new java.awt.Dimension(32, 19));

        org.jdesktop.layout.GroupLayout pnlCreateLayout = new org.jdesktop.layout.GroupLayout(pnlCreate);
        pnlCreate.setLayout(pnlCreateLayout);
        pnlCreateLayout.setHorizontalGroup(
            pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlCreateLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlCreateLayout.createSequentialGroup()
                        .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblDifficulty)
                            .add(lblDigits))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(cmbDigits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(cmbDifficulty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 92, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(pnlCreateLayout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(txtCreateA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, btnCreate, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, pnlCreateLayout.createSequentialGroup()
                                .add(txtCreateB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(txtCreateC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(txtCreateD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 32, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(18, 18, 18))
        );
        pnlCreateLayout.setVerticalGroup(
            pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(pnlCreateLayout.createSequentialGroup()
                .addContainerGap()
                .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cmbDigits, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblDigits))
                .add(7, 7, 7)
                .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cmbDifficulty, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblDifficulty))
                .add(15, 15, 15)
                .add(btnCreate)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCreateLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(txtCreateC, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCreateB, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCreateD, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(txtCreateA, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(pnlSolve, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(pnlCreate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(pnlSolve, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(pnlCreate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }

    
    private void commandCreateGame(java.awt.event.MouseEvent evt) {
    	int difficulty  = cmbDifficulty.getSelectedIndex();
    	int digits = cmbDigits.getSelectedIndex();
    	//Create Card
    	int[] n = game.create(difficulty, digits);
    	//Set TextBoxes
    	txtCreateA.setText(String.valueOf(n[0]));
    	txtCreateB.setText(String.valueOf(n[1]));
    	txtCreateC.setText(String.valueOf(n[2]));
    	txtCreateD.setText(String.valueOf(n[3]));
    }

    private void commandSolveGame(java.awt.event.MouseEvent evt) {
    	if (txtSolveA.getText().equals("") ||
    		txtSolveB.getText().equals("") || 
    		txtSolveC.getText().equals("") || 
    		txtSolveD.getText().equals("")) {
    		return;
    	}
    	int a = Integer.parseInt(txtSolveA.getText());
    	int b = Integer.parseInt(txtSolveB.getText());
    	int c = Integer.parseInt(txtSolveC.getText());
    	int d = Integer.parseInt(txtSolveD.getText());
    	//Solve
    	game.solve(a, b, c, d);
    	//Display Results
    	if (game.solutions.size() == 0) {
    		//No Solutions Found
    		DefaultListModel listModel = new DefaultListModel();
    		listModel.addElement("No Solutions!");
    		lstSolutions.setModel(listModel);
    	} else {
    		//Cleanup Solutions
    		game.clearSimilarSolutions();
    		//Display Solutions
    		DefaultListModel listModel = new DefaultListModel();
    		for (Game24.Node[] n : game.solutions) {
    			listModel.addElement("((" + n[0].value + 
    					   " " + n[1].operation + " " + n[1].value + ")" +
    					   " " + n[2].operation + " " + n[2].value + ")" +
    					   " " + n[3].operation + " " + n[3].value);
    		}
    		lstSolutions.setModel(listModel);
        	//Clear TextBoxes
        	txtSolveA.setText("");
        	txtSolveB.setText("");
        	txtSolveC.setText("");
        	txtSolveD.setText("");
    	}
    }
    
    
    // Variables declaration - do not modify
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnSolve;
    private javax.swing.JComboBox cmbDifficulty;
    private javax.swing.JComboBox cmbDigits;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDifficulty;
    private javax.swing.JLabel lblDigits;
    private javax.swing.JList lstSolutions;
    private javax.swing.JPanel pnlCreate;
    private javax.swing.JPanel pnlSolve;
    private javax.swing.JTextField txtCreateA;
    private javax.swing.JTextField txtCreateB;
    private javax.swing.JTextField txtCreateC;
    private javax.swing.JTextField txtCreateD;
    private javax.swing.JTextField txtSolveA;
    private javax.swing.JTextField txtSolveB;
    private javax.swing.JTextField txtSolveC;
    private javax.swing.JTextField txtSolveD;
    // End of variables declaration
    
}
