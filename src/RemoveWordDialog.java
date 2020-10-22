import BreezySwing.GBDialog;

import javax.swing.*;

public class RemoveWordDialog extends GBDialog {

    private final Main main;
    private final String word;
    private final JLabel confirmLabel;
    private final JButton yesButton;
    private final JButton noButton;
    private final String type;

    public RemoveWordDialog(JFrame jFrame, Main main, String word, String type) {
        super(jFrame);
        this.main = main;
        this.word = word;
        this.type = type;
        confirmLabel = addLabel("Would you really like to remove " + word, 1, 1, 2, 1 );
        yesButton = addButton("Yes", 2, 1, 1, 1);
        noButton = addButton("No", 2, 2, 1, 1);
        setName("Remove " + word);
        setSize(500, 500);
        setVisible(true);
    }

    @Override
    public void buttonClicked(JButton jButton) {
        if(jButton.equals(yesButton)) {
            if(type.equalsIgnoreCase("noun")) {
                main.getSentenceGenerator().removeNoun(word);
            } else {
                main.getSentenceGenerator().removeVerb(word);
            }
            main.updateList();
        }
        setVisible(false);
    }


}
