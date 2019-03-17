package com.example.orders;

import com.example.orders.models.Agent;
import com.example.orders.models.Customer;
import com.example.orders.models.Order;
import com.example.orders.repository.AgentRepository;
import com.example.orders.repository.CustomerRepository;
import com.example.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // Day 2 Stuff:
    // GET /customers - returns all the customer
    @GetMapping("/customers")
    public List<Customer> findCustomers() {
        return customerrepos.findAll();
    }

    // GET /orders - return all the orders
    @GetMapping("/orders")
    public List<Order> findOrders() {
        return orderrepos.findAll();
    }

    // GET /agents - Returns all agents with their customers
    @GetMapping("/agents")
    public List<Agent> getAllAgents() {
        return agentrepos.findAll();
    }

    // GET /customers/custcode/{custcode}
    @GetMapping("/customers/custcode/{custcode}")
    public Customer findCustId(@PathVariable long custcode) {
        var foundCust = customerrepos.findById(custcode);
        if (foundCust.isPresent()) {
            return foundCust.get();
        }
        return null;
    }

    // GET /orders/ordnum/{ordnum}
    @GetMapping("/orders/ordnum/{ordnum}")
    public Order getOrder(@PathVariable long ordnum) {
        var foundOrd = orderrepos.findById(ordnum);
        if (foundOrd.isPresent()) {
            return foundOrd.get();
        }
        return null;
    }

    // GET /agents/agentcode/{agentcode}
    @GetMapping("/agents/agentcode/{agentcode}")
    public Agent getAgent(@PathVariable long agentcode) {
        var foundAgent = agentrepos.findById(agentcode);
        if (foundAgent.isPresent()) {
            return foundAgent.get();
        }
        return null;
    }

    // POST /customers - adds a customer
    @PostMapping("/customers")
    public Customer addCustomer(@RequestBody Customer newCustomer) throws URISyntaxException {
        return customerrepos.save(newCustomer);
    }

    // POST /orders - adds an order
    @PostMapping("/orders")
    public Order addOrder(@RequestBody Order newOrder) throws URISyntaxException {
        return orderrepos.save(newOrder);
    }

    // POST /agents - adds an agent
    @PostMapping("/agents")
    public Agent addAgent(@RequestBody Agent newAgent) throws URISyntaxException {
        return agentrepos.save(newAgent);
    }

    // PUT /customers/custocode/{custcode} - updates a customer based on custcode
    @PutMapping("/customers/custcode/{custcode}")
    public List<Customer> changeCustomer(@RequestBody Customer editCustomer, @PathVariable long custcode) throws URISyntaxException {
        Optional<Customer> updatedCustomer = customerrepos.findById(custcode);
        if (updatedCustomer.isPresent()) {
            editCustomer.setCustcode(custcode);
            customerrepos.save(editCustomer);
            return java.util.Arrays.asList(editCustomer);
        }
        return updatedCustomer.stream().collect(Collectors.toList());
    }

    // PUT /orders/ordnum/{ordnum} - updates an order based on ordnum
    @PutMapping("/orders/ordnum/{ordnum}")
    public Order changeOrder(@RequestBody Order editOrder, @PathVariable long ordnum) throws URISyntaxException {
        Optional<Order> updatedOrder = orderrepos.findById(ordnum);
        if (updatedOrder.isPresent()) {
            editOrder.setOrdnum(ordnum);
            orderrepos.save(editOrder);
            return editOrder;
        }
        return null;
    }

    // PUT /agents/agentcode/{agentcode} - updates an agent based on ordnum
    @PutMapping("/agents/agentcode/{agentcode}")
    public List<Agent> changeAgent(@RequestBody Agent editAgent, @PathVariable long agentcode) throws URISyntaxException {
        Optional<Agent> updatedAgent = agentrepos.findById(agentcode);
        if (updatedAgent.isPresent()) {
            editAgent.setAgentcode(agentcode);
            agentrepos.save(editAgent);
            return java.util.Arrays.asList(editAgent);
        }
        return updatedAgent.stream().collect(Collectors.toList());
    }

    // DELETE /customers/custcode/{custcode} - Deletes a customer based off of their custcode and deletes all their associated orders
    @DeleteMapping("/customers/custcode/{custcode}")
    public List<Customer> deleteCustomer(@PathVariable long custcode) {
        List<Customer> rmCustomer = customerrepos.findById(custcode).stream().collect(Collectors.toList());
        customerrepos.deleteById(custcode);
        return rmCustomer;
    }

    // DELETE /orders/ordnum/{ordnum} - deletes an order based off its ordnum
    @DeleteMapping("/orders/ordnum/{ordnum}")
    public Order deleteOrder(@PathVariable long ordnum) {
        Order rmOrder = orderrepos.findById(ordnum).get();
        orderrepos.deleteById(ordnum);
        return rmOrder;
    }

    // Day 1 Stuff:

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
