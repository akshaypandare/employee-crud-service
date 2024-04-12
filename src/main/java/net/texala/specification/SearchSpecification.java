package net.texala.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import net.texala.constants.Utility;
import net.texala.model.Employee;

public class SearchSpecification {

	/**
	 * To get the required jpa specification
	 * 
	 * @param filterBy
	 * @param searchText
	 * @return
	 */
	public static Specification<Employee> searchEmployee(String filterBy, String searchText) {
		return (root, query, cb) -> {

			String containsLikePattern = Utility.getContainsLikePattern(searchText);
			List<Predicate> predicates = new ArrayList<>();
			if (StringUtils.isNotBlank(filterBy)) {
				Map<String, String> filterByMap = Utility.prepareFilterByMap(filterBy);
				for (Map.Entry<String, String> filterByEntry : filterByMap.entrySet()) {
					predicates.add(cb.equal(root.<String>get(filterByEntry.getKey()).as(String.class),
							filterByEntry.getValue()));
				}
			}
			predicates.add(cb.or(cb.like(cb.lower(root.<String>get("empName")), containsLikePattern),
					cb.like(cb.lower(root.<String>get("empAddress")), containsLikePattern),
					cb.like(cb.lower(root.<String>get("empSalary").as(String.class)), containsLikePattern),
					cb.like(cb.lower(root.<String>get("empStatus").as(String.class)), containsLikePattern)));

			return cb.and(predicates.toArray(new Predicate[0]));
		};
	}
}