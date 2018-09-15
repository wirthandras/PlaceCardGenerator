package hu.placecardgenerator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.placecardgenerator.domain.GuestHandler;

public class GuestHandlerTest {
	
	private GuestHandler gh;
	
	@Before
	public void setup() {
		gh = new GuestHandler();
	}
	
	@Test
	public void testZeroGuest() {
		Assert.assertNotNull(gh.getNext(0));
		Assert.assertEquals(0, gh.getNext(0).size());
		Assert.assertEquals(0, gh.getNext(1).size());
	}
	
	@Test
	public void testCounterLessThanGuest() {
		gh.add("Joe");
		gh.add("Rebeka");
		gh.add("Ben");
		gh.add("Joshua");
		
		Assert.assertNotNull(gh.getNext(2));
		Assert.assertEquals(2, gh.getNext(2).size());
		Assert.assertEquals("Joe", gh.getNext(2).poll());
		Assert.assertEquals("Rebeka", gh.getNext(2).poll());
	}
	
	@Test
	public void testCounterGreaterThanGuest() {
		gh.add("Joe");
		gh.add("Rebeka");
		gh.add("Ben");
		gh.add("Joshua");
		
		Assert.assertNotNull(gh.getNext(6));
		Assert.assertEquals(4, gh.getNext(6).size());
	}
	
}
