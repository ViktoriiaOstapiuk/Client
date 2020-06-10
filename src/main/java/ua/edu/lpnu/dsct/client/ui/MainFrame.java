package ua.edu.lpnu.dsct.client.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import ua.edu.lpnu.dsct.client.CommandParser;

public class MainFrame extends JFrame {
    private CommandParser parser;

    public MainFrame() {
        initComponents();
    }

    public void setParser(CommandParser parser) {
        this.parser = parser;
    }

    private void executeButtonMouseClicked(MouseEvent e) {
        String text = commandTextField.getText();
        this.parser.callSafe(text);
    }

    private void initComponents() {
        logScrollPane = new JScrollPane();
        logTextArea = new JTextArea();
        commandTextField = new JTextField();
        executeButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(720, 500));
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 5, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

        //======== logScrollPane ========
        {
            logScrollPane.setMinimumSize(null);
            logScrollPane.setPreferredSize(new Dimension(660, 400));
            logScrollPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            //---- logTextArea ----
            logTextArea.setRows(12);
            logTextArea.setEditable(false);
            logTextArea.setMinimumSize(new Dimension(660, 500));
            logScrollPane.setViewportView(logTextArea);
        }
        contentPane.add(logScrollPane, new GridBagConstraints(0, 1, 31, 4, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- commandTextField ----
        commandTextField.setToolTipText("Enter your command and click 'Execute'");
        commandTextField.setPreferredSize(new Dimension(600, 30));
        commandTextField.setMinimumSize(null);
        contentPane.add(commandTextField, new GridBagConstraints(0, 5, 30, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //---- executeButton ----
        executeButton.setText("Execute");
        executeButton.setMaximumSize(null);
        executeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                executeButtonMouseClicked(e);
            }
        });
        contentPane.add(executeButton, new GridBagConstraints(30, 5, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JScrollPane logScrollPane;
    public JTextArea logTextArea;
    private JTextField commandTextField;
    private JButton executeButton;
}
