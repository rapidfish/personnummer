package se.osbe.id;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.osbe.id.exception.PersonnummerException;
import se.osbe.id.helper.StatisticsHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PnrStatisticsTest {

	private List<Optional<Personnummer>> _lista;

	@Before
	public void setup(){
		_lista = new ArrayList<>();
	}

	@Test
	public void testPersonnummerStatisticsAverageAge() throws PersonnummerException {

		_lista.add(Personnummer.parse("0012121212", true));
		_lista.add(Personnummer.parse("0012121212", true));
		_lista.add(Personnummer.parse("0012121212", true));
		_lista.add(Personnummer.parse("1212121212", true));
		_lista.add(Personnummer.parse("1412121212", true));

		Double result = new StatisticsHelper(
					_lista.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList()
				)).getAvgAge();
		Assert.assertNotNull(result);
		Assert.assertTrue(result.intValue() > 0);
	}

	@Test
	public void testSsnStatistics() throws PersonnummerException {
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8012121212", true));
		_lista.add(Personnummer.parse("8512121212", true));
		_lista.add(Personnummer.parse("8512121212", true));

		Assert.assertTrue( new StatisticsHelper(_lista.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList())).getAvgAge() > 0);
	}
}
