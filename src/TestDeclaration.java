import org.junit.Assert;
import org.junit.Test;

public class TestDeclaration {

	@Test
	public void testLimite0() {
		try {
			Declaration d = new Declaration(0, 3);
			Assert.assertTrue("Impôts", d.impots() == 0);
			Assert.assertTrue("Marginal", d.tauxMarginal() == 0);
			Assert.assertTrue("Global", d.tauxGlobal() == 0);
		} catch (ErreurPartsException | ErreurRevenusException e) {
			Assert.fail(e.toString());
		}
	}

	/*
	 * Test en JUnit4 ; en JUnit5 on procède autrement
	 */
	@Test(expected = ErreurRevenusException.class)
	public void testErreurRevenus() throws ErreurPartsException, ErreurRevenusException {
		Declaration d = new Declaration(-1000, 2);
	}
}