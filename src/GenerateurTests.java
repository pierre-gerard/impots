import java.util.Random;

/*
 * Cette classe ne peut fonctionner qu'à partir d'une classe Declaration correcte.
 * Elle est uniquement destinée à un usage utilitaire, pour rédiger le sujet de TD/TP
 */
public class GenerateurTests {

	private String[] tabNoms = new String[256];
	private int[] tabRevenus = new int[256];
	private int[] tabDemiParts = new int[256];
	private int[] tabExpImpots = new int[256];
	private float[] tabExpMarginal = new float[256];
	private float[] tabExpGlobal = new float[256];

	int nbExemples;

	public void init() {
		nbExemples = 0;
	}

	public void ajoutCasLimites() throws Exception {
		for (int i = 0; i < Declaration.seuils.length; i++) {

			tabDemiParts[nbExemples] = (new Random()).nextInt(3) + 2;
			tabRevenus[nbExemples] = Declaration.seuils[i] * tabDemiParts[nbExemples] / 2;

			Declaration d = new Declaration(tabRevenus[nbExemples], tabDemiParts[nbExemples]);

			tabExpImpots[nbExemples] = d.impots();
			tabExpMarginal[nbExemples] = d.tauxMarginal();
			tabExpGlobal[nbExemples] = d.tauxGlobal();

			tabNoms[nbExemples] = "Limite" + Declaration.seuils[i];

			nbExemples++;
		}
	}

	public void ajoutCasMedians() throws Exception {
		for (int i = 1; i <= Declaration.seuils.length; i++) {

			tabDemiParts[nbExemples] = (new Random()).nextInt(3) + 2;

			tabRevenus[nbExemples] = 200000 * tabDemiParts[nbExemples];
			if (i < Declaration.seuils.length)
				tabRevenus[nbExemples] = (Declaration.seuils[i] + Declaration.seuils[i - 1]) / 2
				* tabDemiParts[nbExemples] / 2;

			Declaration d = new Declaration(tabRevenus[nbExemples], tabDemiParts[nbExemples]);

			tabExpImpots[nbExemples] = d.impots();
			tabExpMarginal[nbExemples] = d.tauxMarginal();
			tabExpGlobal[nbExemples] = d.tauxGlobal();

			tabNoms[nbExemples] = "Tranche" + i;

			nbExemples++;
		}
	}

	public void ajoutCasErreurParts() throws Exception {
		tabExpImpots[nbExemples] = -1;
		tabExpMarginal[nbExemples] = -1;
		tabExpGlobal[nbExemples] = -1;
		tabNoms[nbExemples] = "ErreurParts-";
		tabDemiParts[nbExemples] = -1;
		tabRevenus[nbExemples] = (new Random()).nextInt(Declaration.seuils[Declaration.seuils.length - 1]);
		nbExemples++;

		tabExpImpots[nbExemples] = -1;
		tabExpMarginal[nbExemples] = -1;
		tabExpGlobal[nbExemples] = -1;
		tabNoms[nbExemples] = "ErreurParts1";
		tabDemiParts[nbExemples] = 1;
		tabRevenus[nbExemples] = (new Random()).nextInt(Declaration.seuils[Declaration.seuils.length - 1]);
		nbExemples++;
	}

	public void ajoutCasErreurRevenus() throws Exception {
		tabExpImpots[nbExemples] = -2;
		tabExpMarginal[nbExemples] = -2;
		tabExpGlobal[nbExemples] = -2;

		tabNoms[nbExemples] = "ErreurRevenus";
		tabDemiParts[nbExemples] = (new Random()).nextInt(3) + 2;
		tabRevenus[nbExemples] = -1000;

		nbExemples++;
	}

	public String toString() {
		String str = "";
		for (int i = 0; i < this.nbExemples; i++)
			str += tabNoms[i] + " : (" + tabRevenus[i] + "," + tabDemiParts[i] + ")->(" + tabExpImpots[i] + ","
					+ tabExpMarginal[i] + "," + tabExpGlobal[i] + ")\n";
		return str;
	}

	public String toDokuWiki() {
		String str = "^ Nom du test ^ Revenus ^ Demi parts ^ Impôts attendus ^ Taux marginal attendu ^ Taux global attendu |\n";
		for (int i = 0; i < this.nbExemples; i++)
			str += "| test" + tabNoms[i] + " | " + tabRevenus[i] + " | " + tabDemiParts[i] + " | " + tabExpImpots[i]
					+ " | " + tabExpMarginal[i] + " | " + tabExpGlobal[i] + " |\n";
		return str;
	}

	public static void main(String[] args) throws Exception {
		GenerateurTests gen = new GenerateurTests();
		gen.init();
		gen.ajoutCasLimites();
		gen.ajoutCasMedians();
		gen.ajoutCasErreurParts();
		gen.ajoutCasErreurRevenus();
		System.out.println(gen);
		System.out.println(gen.toDokuWiki());
	}

}
