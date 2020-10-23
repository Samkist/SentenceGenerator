import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SentenceGenerator {

    private final SamLinkedList<String> nouns = new SamLinkedList<>();
    private final SamLinkedList<String> verbs = new SamLinkedList<>();
    private final SamLinkedList<String> sentences = new SamLinkedList<>();

    public void addNoun(String noun) {
        nouns.add(noun);
    }

    public void removeNoun(String noun) {
        nouns.remove(noun);
    }

    public void addVerb(String verb) {
        verbs.add(verb);
    }

    public void removeVerb(String verb) {
        verbs.remove(verb);
    }

    public void generate() throws NotEnoughWordsException, OutOfWordsException {
        if(nouns.size() < 3 || verbs.size() == 0) {
            throw new NotEnoughWordsException("Not enough nouns or verbs");
        }
        Iterator<String> nounIt = nouns.iterator();
        Iterator<String> verbIt = verbs.iterator();
        //noun verb noun noun
        String format = "The %s %s the %s with a(n) %s.";
        int attempts = 0;
        while(attempts < 5) {
            ArrayList<String> sentenceNouns = new ArrayList<>();
            String verb = "";
            int nouns = 0;
            while(nounIt.hasNext())
                sentenceNouns.add(nounIt.next());
            ArrayList<String> chosenNouns = new ArrayList<>();
            int amount = sentenceNouns.size();
            Random rand = new Random();
            int att = 0;
            while(nouns < 3 && att < 50) {
                try {
                    System.out.println("Trying bound " + amount);
                    int randomNumber = rand.nextInt(amount);
                    if(!chosenNouns.contains(sentenceNouns.get(randomNumber))) {
                        chosenNouns.add(sentenceNouns.get(randomNumber));
                        nouns++;
                    }
                } catch (Exception e) {
                    att++;
                }
            }
            ArrayList<String> chosenVerbs = new ArrayList<>();
            while(verbIt.hasNext()) {
                chosenVerbs.add(verbIt.next());
            }
            verb = chosenVerbs.get(new Random().nextInt(chosenVerbs.size()));
            String sentence = String.format(format, chosenNouns.get(0), verb, chosenNouns.get(1), chosenNouns.get(2));
            if(!sentences.contains(sentence)) {
                sentences.add(sentence);
                return;
            }
            attempts++;
        }
        throw new OutOfWordsException("Ran out of words, reached five attempts");
    }

    public SamLinkedList<String> getNouns() {
        return nouns;
    }

    public SamLinkedList<String> getVerbs() {
        return verbs;
    }

    public SamLinkedList<String> getSentences() {
        return sentences;
    }
}
