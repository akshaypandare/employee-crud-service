package net.texala.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Utility {
	
	/**
	 * Implementation of pattern for searching specific text
	 * @param searchTerm
	 * @return
	 */
	public static String getContainsLikePattern(String searchTerm) {
		if (StringUtils.isBlank(searchTerm)) {
			return "%";
		}else {
			return "%"+searchTerm.toLowerCase()+"%";
		}
	}
	
	/**
	 * Implementation for return string for filter data
	 * @param filterBy
	 * @return
	 */
	public static Map<String, String> prepareFilterByMap(String filterBy){
		Map<String, String> filterByMaps = new HashMap<>();
		String [] arr = filterBy.split(";");
		for(String key : arr) {
			String []tempArr = key.split(":", 2);
			filterByMaps.put(tempArr[0], tempArr[1]);
		}
		return filterByMaps;
	}
	
	/**
	 * Implementation of sort record by ASC or DESC
	 * @param sortBy
	 * @return
	 */
	public static List<Order> sortByValues(String sortBy){
		List<Order> filterByMaps = new ArrayList<>();
		String [] arr = sortBy.split(";");
		for(String key : arr) {
			System.out.println(key);
			String []tempArr = key.split(":", 2);
			final Direction direction = tempArr[1].equalsIgnoreCase("desc") ? Direction.DESC : Direction.ASC;
			filterByMaps.add(new Order(direction, tempArr[0]));
		}
		return filterByMaps;
	}
}