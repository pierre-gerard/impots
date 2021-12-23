import java.util.Scanner;

public class Declaration {

	protected static final boolean trace = false;

	protected static final int[] seuils = { 0, 10000, 25000, 75000, 100000 };
	protected static final int[] taux = { 0, 10, 30, 40, 45 };

	int revenus;
	int demiParts;

	public Declaration(int revenus, int demiParts) throws ErreurPartsException, ErreurRevenusException {
		super();

		if (revenus < 0)
			throw new ErreurRevenusException();
		if (demiParts < 1)
			throw new ErreurRevenusException();

		this.revenus = revenus;
		this.demiParts = demiParts;
	}

	public int impots() {
		trace("\n===IMPOTS===");

		float revenus = (float) this.revenus;
		float parts = (float) this.demiParts / 2;
		trace("Revenus = " + revenus);
		trace("Parts = " + parts);

		float quotient = revenus / parts;
		trace("QF = " + quotient);

		float impots = 0;
		int tranche = 1;

		while ((tranche < seuils.length) && (quotient > seuils[tranche])) {
			trace("\n= Tranche " + tranche);
			trace("- Max " + seuils[tranche]);
			impots += (seuils[tranche] - seuils[tranche - 1]) * (taux[tranche - 1] / 100.0);
			trace("- Impôts QF " + impots);
			tranche++;
		}

		if (quotient > seuils[tranche - 1]) {
			impots += (quotient - seuils[tranche - 1]) * (taux[tranche - 1] / 100.0);
			trace("\n= Impôts QF+ " + impots);
		}

		impots *= parts;
		trace("\n= Impôts parts " + impots);

		return (int) impots;
	}

	public float tauxMarginal() {
		trace("\n===TAUX MARGINAL===");

		float revenus = (float) this.revenus;
		float parts = (float) this.demiParts / 2;
		trace("Revenus = " + revenus);
		trace("Parts = " + parts);

		float quotient = revenus / parts;
		trace("QF = " + quotient);

		int tranche = 1;

		while ((tranche < seuils.length) && (quotient > seuils[tranche])) {
			trace("Tranche " + tranche);
			tranche++;
		}

		trace("Taux " + taux[tranche - 1]);
		return taux[tranche - 1];
	}

	public float tauxGlobal() {
		trace("\n===TAUX GLOBAL===");

		float impots = this.impots();
		float tauxGlobal = impots / (float) this.revenus;
		if (Float.isNaN(tauxGlobal))
			tauxGlobal = 0;
		trace("TauxGlobal " + tauxGlobal);

		return (tauxGlobal * 100);
	}

	private static void trace(String str) {
		if (trace)
			System.out.println(str);
	}

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Revenus (€) ? ");
		int revenus = scanner.nextInt();
		System.out.print("Demi parts ? ");
		int demiParts = scanner.nextInt();

		Declaration declaration;
		try {

			declaration = new Declaration(revenus, demiParts);

			int ir = declaration.impots();
			System.out.println("IMPOTS = " + ir + " €");

			float txM = declaration.tauxMarginal();
			System.out.println("TAUX MARGINAL = " + txM + " %");

			float txG = declaration.tauxGlobal();
			System.out.println("TAUX GLOBAL = " + txG + " %");

		} catch (ErreurPartsException | ErreurRevenusException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}

		scanner.close();
	}
}
