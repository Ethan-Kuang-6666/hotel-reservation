package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private static CustomerService customerService = null;
    private static Map<String, Customer> customers = new HashMap<String, Customer>();

    // private constructor
    private CustomerService() {
    }

    // get the instance
    public static CustomerService getCustomerService() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    // note: it may throw exception.
    public void addCustomer(String email, String firstName, String lastName) {
        Customer c;
        while (true) {
            try {
                c = new Customer(firstName, lastName, email);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter valid email address!");
            }
        }
        customers.put(email, c);
    }

    public Customer getCustomer(String customerEmail) {
        Customer result = customers.get(customerEmail);
        if (result == null) {
            System.out.println("Customer not found!");
        }
        return result;
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
