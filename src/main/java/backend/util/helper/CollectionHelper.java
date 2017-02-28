package backend.util.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import backend.dto.ResultBean;

public class CollectionHelper {
	
	public static <A> boolean empty(Collection<A> collection) {
		return collection == null || collection.size() == 0;
	}
	
	public static List<String> getListOfString(List<ResultBean> run) {
		List<String> results  = new ArrayList<String>();
		if (run != null) {
			for (ResultBean resultBean : run) {
				results.add(resultBean.getResult());
			}
		}
		return results;
	}

}
