package com.reactive;

import lombok.Data;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HandleOperator {

    public static void main(String[] args) {

        final Flux<Integer> range = Flux.range(1, 5);
        final Flux<Integer> even = range.handle((integer, synchronousSink) -> {
            if (integer % 2 == 0) {
                synchronousSink.next(integer);
            }
        }).cast(Integer.class);
         even.subscribe(System.out::println);

        final Flux<String> stringFlux = range.handle((integer, synchronousSink) -> {
            if (integer % 2 == 0) {
                synchronousSink.next(integer + "SUM");
            }
        }).cast(String.class);
        stringFlux.subscribe(System.out::println);

        final List<Employee.Department> departments = IntStream.range(1, 100).mapToObj(Employee.Department::new).collect(Collectors.toList());
        final Employee employee = new Employee("sagir");
        employee.setDepartments(departments);
        final Flux<Employee> employeeFlux = Flux.just(employee);
        final Flux<Employee> employeeFlux1 = employeeFlux.handle((employee1, synchronousSink) -> {
            final List<Employee.Department> departments1 = employee1.getDepartments();
            departments1.forEach(department -> {
                department.setDepartmentName(UUID.randomUUID().toString().substring(1, 5));
            });
            synchronousSink.next(employee1);
        }).cast(Employee.class);
        employeeFlux1.subscribe(System.out::println);
    }


    static class Employee {
        private String name;

        public Employee(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Department> getDepartments() {
            return departments;
        }

        public void setDepartments(List<Department> departments) {
            this.departments = departments;
        }

        private List<Department> departments;


        static class Department {

            private Integer id;
            public String departmentName;

            public Department(Integer id) {
                this.id = id;
            }

            public Integer getId() {
                return id;
            }

            public void setId(Integer id) {
                this.id = id;
            }

            public String getDepartmentName() {
                return departmentName;
            }

            public void setDepartmentName(String departmentName) {
                this.departmentName = departmentName;
            }

            @Override
            public String toString() {
                return "Department{" +
                        "id=" + id +
                        ", departmentName='" + departmentName + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Employee{" +
                    "name='" + name + '\'' +
                    ", departments=" + departments +
                    '}';
        }
    }
}
