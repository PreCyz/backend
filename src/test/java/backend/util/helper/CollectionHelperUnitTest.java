package backend.util.helper;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import backend.dto.ResultBean;
import backend.util.helper.CollectionHelper;

public class CollectionHelperUnitTest {

	@Test
	public void givenEmptyCollectionWhenEmptyRteurnTrue() {
		List<String> lista = null;
		assertTrue(CollectionHelper.empty(lista));
		lista = new ArrayList<String>();
		assertTrue(CollectionHelper.empty(lista));
		lista.add("hello");
		assertTrue(!CollectionHelper.empty(lista));
	}
	
	@Test
	public void givenNotEmptyCollectionWhenEmptyRteurnFalse() {
		List<String> lista = new ArrayList<String>();
		lista.add("hello");
		assertTrue(!CollectionHelper.empty(lista));
	}
	
	@Test
	public void givenNullResultBeanListWhenGetListOfStringThenReturnEmptyArrayList() throws Exception {
		Collection<?> collection = CollectionHelper.getListOfString(null);
		assertTrue(collection instanceof ArrayList);
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void givenEmptyResultBeanListWhenGetListOfStringThenReturnEmptyArrayList() throws Exception {
		Collection<?> collection = CollectionHelper.getListOfString(Collections.emptyList());
		assertTrue(collection instanceof ArrayList);
		assertTrue(collection.isEmpty());
	}
	
	@Test
	public void givenResultBeanListWhenGetListOfStringThenReturnArrayListWithStrings() throws Exception {
		List<ResultBean> list = createListOfResultBean();
		Collection<String> collection = CollectionHelper.getListOfString(list);
		assertTrue(collection instanceof ArrayList);
		assertTrue(!collection.isEmpty());
		for(String string : collection){
			assertTrue("Starts with String", string.startsWith("String"));
		}
	}

	private List<ResultBean> createListOfResultBean() {
		List<ResultBean> list = new ArrayList<>();
		ResultBean bean = new ResultBean();
		bean.setResult("String1");
		list.add(bean);
		bean = new ResultBean();
		bean.setResult("String2");
		list.add(bean);
		bean = new ResultBean();
		bean.setResult("String3");
		list.add(bean);
		return list;
	}
}
