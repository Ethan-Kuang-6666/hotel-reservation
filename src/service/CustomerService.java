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
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
