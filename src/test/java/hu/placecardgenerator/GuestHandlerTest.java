package hu.placecardgenerator;

import java.util.Queue;

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
		
		Queue<String> queue = gh.getNext(2);
		
		Assert.assertNotNull(queue);
		Assert.assertEquals(2, queue.size());
		Assert.assertEquals("Joe", queue.poll());
		Assert.assertEquals("Rebeka", queue.poll());
	}
	
	@Test
	public void testCounterGreaterThanGuest() {
		gh.add("Joe");
		gh.add("Rebeka");
		gh.add("Ben");
		gh.add("Joshua");
		
		Queue<String> queue = gh.getNext(6); 
		
		Assert.assertNotNull(queue);
		Assert.assertEquals(4, queue.size());
	}
	
}
