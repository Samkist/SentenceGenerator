import BreezySwing.GBDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWordDialog extends GBDialog {
    private final Main main;
    private final ButtonGroup bg = new ButtonGroup();
    private final JButton addWord = addButton("Add Word", 3, 2, 1, 1);
    private final JRadioButton nounButton = addRadioButton("Add Noun",1, 1, 1, 1);
    private final JRadioButton verbButton = addRadioButton("Add Verb",2, 1, 1, 1);
    private final JLabel typeLabel = addLabel("Noun: ", 1, 2, 1, 1);
    private final JTextField textField = addTextField("", 1, 3, 1, 1);

    public AddWordDialog(JFrame jFrame, Main main) {
        super(jFrame);
        this.main = main;

        bg.add(nounButton);
        bg.add(verbButton);
        nounButton.setSelected(true);

        nounButton.addActionListener(buttonListener);
        verbButton.addActionListener(buttonListener);

        setTitle("Add Word");
        setSize(500, 500);
        setVisible(true);
    }

    ActionListener buttonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(nounButton.isSelected()) {
                typeLabel.setText("Noun: ");
            } else {
                typeLabel.setText("Verb: ");
            }
        }
    };

    @Override
    public void buttonClicked(JButton jButton) {
        if (jButton.equals(addWord)) {
            if(nounButton.isSelected()) {
                main.getSentenceGenerator().addNoun(textField.getText());
            } else {
                main.getSentenceGenerator().addVerb(textField.getText());
            }
            main.updateList();
            setVisible(false);
        }
    }


}
