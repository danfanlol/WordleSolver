import java.io.File;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class WordleDriver{
	public ArrayList<String> guesses = new ArrayList();
	public static void main(String[] args) throws FileNotFoundException {
		WordleSolverAssistant assistant = new WordleSolverAssistant();
//		ArrayList<Character> s = new ArrayList();
//		s.add('N');
//		s.add('N');
//		s.add('N');
//		s.add('N');
//		s.add('Y');
//		assistant.updatePossibleWords("adieu",s);
//		s = new ArrayList();
//		s.add('G');
//		s.add('N');
//		s.add('Y');
//		s.add('N');
//		s.add('N');
//		assistant.updatePossibleWords("rough",s);
//		s = new ArrayList();
//		s.add('G');
//		s.add('G');
//		s.add('N');
//		s.add('N');
//		s.add('G');
//		assistant.updatePossibleWords("runny",s);
//		assistant.displayPossibles();

	}





}

class WordleSolverAssistant{
	public 	ArrayList<String> possibleWords = new ArrayList();
	public WordleSolverAssistant() throws FileNotFoundException{
		Scanner sc = new Scanner(new File("dictionary.txt"));
		while (sc.hasNext()){
			String word = sc.next();
			if (word.length() == 5)
				possibleWords.add(word.toLowerCase());
		}
	}
	public void updatePossibleWords(String guess, ArrayList<Character> characters){
		ArrayList<Character> impossibles = new ArrayList();
		ArrayList<position> notinPosition = new ArrayList();
		ArrayList<position> mustbeinPosition = new ArrayList();
		ArrayList<Character> mustbeinWord = new ArrayList();
		for (int i =0; i < 5;i++){
			if (characters.get(i) == 'N'){
				boolean trulynotthere = true;
				for (int j =0 ; j < notinPosition.size();j++){
					if (guess.charAt(i) == notinPosition.get(j).y){
						trulynotthere = false;
						break;
					}
				}
				for (int j =0 ; j < mustbeinPosition.size();j++){
					if (guess.charAt(i) == mustbeinPosition.get(j).y){
						trulynotthere = false;
						break;
					}
				}
				if (trulynotthere)
					impossibles.add(guess.charAt(i));
			}
			if (characters.get(i) == 'Y'){
				notinPosition.add(new position(i,guess.charAt(i)));
				mustbeinWord.add(guess.charAt(i));
			}
			if (characters.get(i) == 'G'){
				mustbeinPosition.add(new position(i,guess.charAt(i)));
				for (int j =0; j < impossibles.size();j++){
					if (impossibles.get(j) == guess.charAt(i)){
						impossibles.remove(j);
						break;
					}
				}
			}
		}
		int i =0;
		while (i != possibleWords.size()){
			boolean isimpossible = false;
			for (int j = 0; j < impossibles.size();j++){
				if (possibleWords.get(i).indexOf(impossibles.get(j)) != -1){
					isimpossible = true;
					break;
				}
			}
			if (isimpossible){
				possibleWords.remove(i);
				continue;
			}
			for (int j = 0; j < notinPosition.size();j++){
				int index= notinPosition.get(j).x;
				char character = notinPosition.get(j).y;
				if (possibleWords.get(i).indexOf(character) == index){
					isimpossible = true;
					break;
				}
			}
			if (isimpossible){
				possibleWords.remove(i);
				continue;
			}
			for (int j = 0; j < mustbeinWord.size();j++){
				Character ch = mustbeinWord.get(j);
				if (possibleWords.get(i).indexOf(ch) == -1){
					isimpossible = true;
					break;
				}
			}
			if (isimpossible){
				possibleWords.remove(i);
				continue;
			}
			for (int j = 0; j < mustbeinPosition.size();j++){
				int index= mustbeinPosition.get(j).x;
				char character = mustbeinPosition.get(j).y;
				if (possibleWords.get(i).charAt(index) != character){
					isimpossible = true;
					break;
				}
			}
			if (isimpossible){
				possibleWords.remove(i);
				i -=1;
			}
			i += 1;
		}
	}
	public void displayPossibles(){
		for (int i =0 ;i < possibleWords.size();i++){
			System.out.println(possibleWords.get(i));
		}
	}
}
class position{
	public int x;
	public char y;
	public position(int index, char character){
		x= index;
		y=  character;
	}
}
