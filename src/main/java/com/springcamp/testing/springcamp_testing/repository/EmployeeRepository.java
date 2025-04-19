package com.springcamp.testing.springcamp_testing.repository;

import com.springcamp.testing.springcamp_testing.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // Find employee by a custom query with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQLIndexParams(String firstName, String lastName);

    // Find employee by a custom query with named params
    @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
    Employee findByJPQLNamedParams(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // Find employee by a custom query with native index params
    @Query(value = "select * from employees e where e.first_name=?1 and e.last_name=?2", nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);
}
