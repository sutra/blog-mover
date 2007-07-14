/**
 * Created on 2006-12-29 下午11:39:30
 */
package com.redv.blogmover.web;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 * @author shutra
 * 
 */
public class WritersControllerTest extends AbstractControllerTest {
	protected MockHttpServletRequest request = new MockHttpServletRequest(
			"GET", "");

	protected MockHttpServletResponse response = new MockHttpServletResponse();

	protected Controller controller = null;

	protected ModelAndView mv = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		controller = (WritersController) ctx.getBean("writersController");
	}

	public void testReaders() throws Exception {
		mv = controller.handleRequest(request, response);
		Object o = mv.getModel().get("writers");
		assertEquals(String[][].class, o.getClass());

		String[][] writers = (String[][]) o;
		for (int i = 0; i < writers.length; i++) {
			assertEquals(2, writers[i].length);
			assertEquals(String.class, writers[i][0].getClass());
			assertEquals(String.class, writers[i][1].getClass());
		}
	}
}
