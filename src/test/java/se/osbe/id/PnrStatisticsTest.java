package se.osbe.id;
//package se.osbe.ssn;
//
//import java.math.BigDecimal;
//import java.util.LinkedList;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import se.osbe.ssn.Ssn;
//import se.osbe.ssn.SsnStats;
//
//public class SsnStatisticsTest {
//
//	private List<Ssn> _lista;
//	
//	@Before
//	public void setup(){
//		_lista = new LinkedList<Ssn>();
//	}
//	
//	@Test
//	public void testSsnStatisticsAverageAge() {
//		
//		_lista.add(Ssn.parse("0012121212", true));
//		_lista.add(Ssn.parse("0012121212", true));
//		_lista.add(Ssn.parse("0012121212", true));
//		_lista.add(Ssn.parse("1212121212", true));
//		_lista.add(Ssn.parse("1412121212", true));
//		
//		BigDecimal result = SsnStats.extractAverageAge(_lista);
//		Assert.assertNotNull(result);
//		Assert.assertTrue(result.intValue() > 0);
//	}
//
//	@Test
//	public void testSsnStatistics() {
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8012121212", true));
//		_lista.add(Ssn.parse("8512121212", true));
//		_lista.add(Ssn.parse("8512121212", true));
//
//		Assert.assertNotNull( SsnStats.extractAverageAgeAsList(_lista) );
//	}
//}
