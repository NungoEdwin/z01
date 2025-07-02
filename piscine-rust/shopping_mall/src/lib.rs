pub mod mall;

// Re-export types for easier access
pub use mall::floor::Floor;
pub use mall::floor::store::Store;
pub use mall::floor::store::employee::Employee;
pub use mall::guard::Guard;
pub use mall::Mall;

// Re-export modules for direct access
pub use mall::floor;
pub use mall::floor::store;

// Returns the store with the biggest square_meters
pub fn biggest_store(mall: Mall) -> mall::floor::store::Store {
    let mut biggest = None;
    let mut max_size = 0;

    for floor in mall.floors.iter() {
        for store in floor.stores.iter() {
            if store.square_meters > max_size {
                max_size = store.square_meters;
                biggest = Some(store);
            }
        }
    }

    biggest.unwrap().clone()
}

// Returns a vector containing the employee(s) with the highest salary
pub fn highest_paid_employee(mall: Mall) -> Vec<Employee> {
    let mut highest_paid = Vec::new();
    let mut max_salary = 0.0;

    for floor in mall.floors.iter() {
        for store in floor.stores.iter() {
            for employee in store.employees.iter() {
                if employee.salary > max_salary {
                    max_salary = employee.salary;
                    highest_paid.clear();
                    highest_paid.push(employee.clone());
                } else if employee.salary == max_salary {
                    highest_paid.push(employee.clone());
                }
            }
        }
    }

    highest_paid
}

// Returns the number of employees and guards as a usize
pub fn nbr_of_employees(mall: Mall) -> usize {
    let mut count = mall.guards.len();

    for floor in mall.floors.iter() {
        for store in floor.stores.iter() {
            count += store.employees.len();
        }
    }

    count
}

// Adds guards if there is not at least 1 guard for every 200 square meters of floor size
pub fn check_for_securities(mall: &mut Mall, guards: Vec<Guard>) {
    let mut total_floor_size = 0;

    for floor in mall.floors.iter() {
        total_floor_size += floor.size_limit;
    }

    let required_guards = (total_floor_size as f64 / 200.0).ceil() as usize;
    let current_guards = mall.guards.len();

    if current_guards < required_guards {
        let guards_to_add = required_guards - current_guards;
        for i in 0..guards_to_add {
            if i < guards.len() {
                mall.hire_guard(guards[i].clone());
            }
        }
    }
}

// Raises or cuts employee salaries based on working hours
pub fn cut_or_raise(mall: &mut Mall) {
    for floor in mall.floors.iter_mut() {
        for store in floor.stores.iter_mut() {
            for employee in store.employees.iter_mut() {
                let working_hours = employee.working_hours.1 - employee.working_hours.0;

                if working_hours > 10 {
                    // Raise salary by 10%
                    let raise_amount = employee.salary * 0.1;
                    employee.raise(raise_amount);
                } else {
                    // Cut salary by 10%
                    let cut_amount = employee.salary * 0.1;
                    employee.cut(cut_amount);
                }
            }
        }
    }
}