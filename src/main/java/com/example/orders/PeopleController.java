package com.example.orders;

import com.example.orders.models.Agent;
import com.example.orders.models.Customer;
import com.example.orders.repository.AgentRepository;
import com.example.orders.repository.CustomerRepository;
import com.example.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = {}, produces = MediaType.APPLICATION_JSON_VALUE)
public class PeopleController {
    // In the past, we've instantiated the object, and we've created a local variable for it. It was specifically tied to this layout. We want to let spring let all the messy details of how we want this stuff is created.
    @Autowired // Spring, in our case, will take and create the field and populate the field for us.
    AgentRepository agentrepos;

    @Autowired
    CustomerRepository customerrepos;

    @Autowired
    OrderRepository orderrepos;

    // /customer/order - Returns all customers with their orders
    @GetMapping("/customer/order")
    public List<Customer> getAllCustomerOrders() {
        return customerrepos.findAll();
    }

    // /customer/name/{custname} - Returns all orders for a particular customer based on name.
    @GetMapping("/customer/name/{custname}")
    public Customer findCustByName(@PathVariable String custname) {
        return customerrepos.findByCustname(custname);
    }

    // /agents - Returns all agents with their customers
    @GetMapping("/agents")
    public List<Agent> getAllAgents() {
        return agentrepos.findAll();
    }

    // /agents/orders - Returns a list with the agents name and associated order number and order description
    @GetMapping("/agents/orders")
    public List<Object[]> getAgentOrders() {
        return agentrepos.findAgentOrders();
    }

    // /customer/{custcode} - Deletes a customer based off of their custcode and deletes all their associated order
    @DeleteMapping("/customer/{custcode}")
    public void deleteCustomerOrder(@PathVariable Long custcode) {
        customerrepos.deleteById(custcode);
    }

    // /agents/{agentcode} - Deletes an agent if they are not assigned to a customer or oder. (Stretch)
}
