import BreezySwing.GBFrame;

import javax.swing.*;

public class Main extends GBFrame {

    private static JFrame frame = new Main();

    private final JButton add = addButton("New Word", 1, 1, 1, 1);
    private final JButton generate = addButton("Generate Sentence", 2, 1, 1, 1);
    private final JList<String> nounList = addList(1, 2, 1, 4);
    private final JList<String> verbList = addList(1, 3, 1, 4);
    private final JList<String> sentenceList = addList(1, 4, 1, 4);

    private final SentenceGenerator sentenceGenerator = new SentenceGenerator();


    public Main() {
        test();
        //Thread thread = new Thread(new MyRunnable(sentenceGenerator, this));
       //thread.start();
        updateList();
    }
    public class MyRunnable implements Runnable {

        private SentenceGenerator generator;
        private Main m;

        public MyRunnable(SentenceGenerator generator, Main m) {
            this.generator = generator;
            this.m = m;
        }

        public void run() {
            try {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    return;
                }
                while (true) {
                    generator.generate();
                }
            } catch (Exception e) {
                m.messageBox("Done");
                return;
            }
        }
    }

    public void test() {
        sentenceGenerator.addNoun("cat");
        sentenceGenerator.addNoun("dog");
        sentenceGenerator.addNoun("cow");
        sentenceGenerator.addNoun("bat");
        sentenceGenerator.addNoun("desk");
        sentenceGenerator.addNoun("chair");
        sentenceGenerator.addNoun("pencil");
        sentenceGenerator.addNoun("book");
        sentenceGenerator.addVerb("hit");
        sentenceGenerator.addVerb("clapped");
        sentenceGenerator.addVerb("ran");
        sentenceGenerator.addVerb("watched");
        sentenceGenerator.addVerb("reamed");
        sentenceGenerator.addVerb("eviscerated");
    }

    public static void main(String[] args) {
        frame.setSize(1200, 600);
        frame.setTitle("Sentence Generator");
        frame.setVisible(true);
    }


    public void updateList() {
        DefaultListModel<String> nounModel = (DefaultListModel<String>) nounList.getModel();
        DefaultListModel<String> verbModel = (DefaultListModel<String>) verbList.getModel();
        DefaultListModel<String> sentenceModel = (DefaultListModel<String>) sentenceList.getModel();
        nounModel.clear();
        verbModel.clear();
        sentenceModel.clear();
        nounModel.addElement("Nouns - Double Click to Remove");
        verbModel.addElement("Verbs - Double Click to Remove");
        sentenceModel.addElement("Sentences - Generated Sentences Appear Here");
        sentenceGenerator.getNouns().forEach(it -> nounModel.addElement(it));
        sentenceGenerator.getVerbs().forEach(it -> verbModel.addElement(it));
        sentenceGenerator.getSentences().forEach(it -> sentenceModel.addElement(it));
    }

    @Override
    public void buttonClicked(JButton jButton) {
        if(jButton.equals(add))
            new AddWordDialog(this, this);
        if(jButton.equals(generate)) {
            try {
                sentenceGenerator.generate();
            } catch (NotEnoughWordsException e) {
                messageBox("You have not met the minimum requirement for nouns (3) or verbs (1)");
            } catch (OutOfWordsException e) {
                messageBox("Out of words, please add more words");
            }
        }
        updateList();
    }

    public void listDoubleClicked(JList<String> listObj, String itemClicked) {
        if(itemClicked.contains("Double Click to Remove"))
            return;
        if (listObj.equals(nounList)) {
            new RemoveWordDialog(this, this, itemClicked, "noun");
        }
        if (listObj.equals(verbList)) {
            new RemoveWordDialog(this, this, itemClicked, "verb");
        }
    }

    public SentenceGenerator getSentenceGenerator() {
        return sentenceGenerator;
    }
}