package bloomfilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

public class MinHash {
	public static double jaccard(ArrayList<String> arrayList, ArrayList<String> arrayList2) {
		ArrayList<String> conj1 = arrayList;
		ArrayList<String> conj2 = arrayList2;

		double similaridade;
		if (conj1.size()==0 && conj2.size()==0)
		{
			System.out.println("Ambos os utilizadores não têm livros associados.");
			similaridade=0;
		}else if(conj1.size()==0) {
			System.out.println("O utilizador 1 não tem livros associados.");
			similaridade=0;
		}else if(conj2.size()==0) {
			System.out.println("O utilizador 2 não tem livros associados.");
			similaridade=0;
		}else {
			int intercecao = 0;

			for (int i = 0; i < conj1.size(); i++) {
				for (int j = 0; j < conj2.size(); j++) {
					if (conj1.get(i).equals(conj2.get(j))) {
						intercecao++;
						break;
					}
				}
			}

			int uniao = conj1.size() + conj2.size() - intercecao;

			double jaccard = 1 - ((double) intercecao / uniao);
			similaridade=1-jaccard;
		}
		return similaridade;
	}
	
	public static ArrayList<Long> suggested(int user, ArrayList<String> user1, HashMap<Long, ArrayList<String> > dados, int bloomCount) {
		ArrayList<Long> temp = new ArrayList<Long>();
		double max = 0;
		double max2 = 0;
		double max3 = 0;
		double max4 = 0;
		double max5 = 0;
		Set<Entry<Long, ArrayList<String>>> set1=dados.entrySet();
		Iterator<Entry<Long, ArrayList<String>>> iterator = set1.iterator();
		while(iterator.hasNext()) {
	          Entry<Long, ArrayList<String>> mentry2 = iterator.next();
	          long comparar = (long) mentry2.getKey();
	          //System.out.println(comparar);
	          if ((jaccard(user1, dados.get(comparar)) > max) && (user != comparar)) {
					max = jaccard(user1, dados.get(comparar));
					temp.add(comparar);
				} else if (((jaccard(user1, dados.get(comparar))) < max) && (jaccard(user1, dados.get(comparar)) > max2)) {
					max2 = jaccard(user1, dados.get(comparar));
					temp.add(comparar);
				} else if ((jaccard(user1, dados.get(comparar))) < max2 && (jaccard(user1, dados.get(comparar)) > max3)) {
					max3 = jaccard(user1, dados.get(comparar));
					temp.add(comparar);
				} else if ((jaccard(user1, dados.get(comparar))) < max3 && (jaccard(user1, dados.get(comparar)) > max4)) {
					max4 = jaccard(user1, dados.get(comparar));
					temp.add(comparar);
				} else if ((jaccard(user1, dados.get(comparar))) < max4 && (jaccard(user1, dados.get(comparar)) > max5)) {
					max5 = jaccard(user1, dados.get(comparar));
					temp.add(comparar);
				}
	    }
		return temp;
	}

}