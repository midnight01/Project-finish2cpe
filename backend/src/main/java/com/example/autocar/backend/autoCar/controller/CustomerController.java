package com.example.autocar.backend.autoCar.controller;

import com.example.autocar.backend.autoCar.entity.Carloan;
import com.example.autocar.backend.autoCar.entity.Customer;
import com.example.autocar.backend.autoCar.entity.Specification;
import com.example.autocar.backend.autoCar.message.ResponseMessage;
import com.example.autocar.backend.autoCar.repository.CarloanRepository;
import com.example.autocar.backend.autoCar.repository.CustomerRepository;
import com.example.autocar.backend.autoCar.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/data")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CarloanRepository carloanRepository;


    @GetMapping(path = "/Customer")
    public Collection<Customer> Customer() {
        return customerRepository.findAll().stream().collect(Collectors.toList());
    }

    @PostMapping(path = "/Customer/save/{carloanId}")
    public ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer, @PathVariable long carloanId) {
//
//        Date today = new Date();
//        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
//        String date = DATE_FORMAT.format(today);

        Carloan carloan = carloanRepository.findByCarloanId(carloanId);
        Customer customer = new Customer();

        String idcard = newCustomer.getIdCardNumber();
        String phone = newCustomer.getPhoneNumber();
        String idcard1;
        idcard1 = (idcard.charAt(0) + "-" + idcard.charAt(1) +
                idcard.charAt(2) + idcard.charAt(3) + idcard.charAt(4) + "-" +
                idcard.charAt(5) + idcard.charAt(6) + idcard.charAt(7) +
                idcard.charAt(8) + idcard.charAt(9) + "-" + idcard.charAt(10) +
                idcard.charAt(11) + "-" + idcard.charAt(12));

        String phone1;
        phone1 = (phone.charAt(0) + "" + phone.charAt(1) + "-" + phone.charAt(2) +
                phone.charAt(3) + phone.charAt(4) + phone.charAt(5) + "-" + phone.charAt(6) +
                phone.charAt(7) + phone.charAt(8) + phone.charAt(9));

        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setPhoneNumber(phone1);
        customer.setEmail(newCustomer.getEmail());
        customer.setIdCardNumber(idcard1);
        customer.setCarloan(carloan);
        customer.setJobStatu(newCustomer.getJobStatu());
        customer.setWorkExperience(newCustomer.getWorkExperience());
        customer.setSalaryBase(newCustomer.getSalaryBase());
        customer.setDate(newCustomer.getDate());
        customer.setDateEnd(newCustomer.getDate());
        customerRepository.save(customer);
        return new ResponseEntity<>(new ResponseMessage("Save Data successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/Customer/delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (!customerService.deleteCustomer(id)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new ResponseMessage("Delete Data successfully!"), HttpStatus.OK);
    }

    @PutMapping("/Customer/update/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        Optional<Customer> customer1 = customerService.updateCustomer(id, customer);
        if (!customer1.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(new ResponseMessage("Update Data successfully!"), HttpStatus.OK);
    }

    @GetMapping(path = "/Customer/{customerId}")
    private ResponseEntity<Customer> findByCustomerId(@PathVariable long customerId) {
        System.out.println(customerId);
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping(path = "/Customer/date/{date}")
    private List<Customer> findByDate(@PathVariable String date) {
        return customerRepository.findByDate(date);
    }

    @GetMapping(path = "/Customer/date/{date}/{dateEnd}")
    private List<Customer> findByDatetoDateEnd(@PathVariable String date,@PathVariable String dateEnd) {

//        Specification
    return customerRepository.findByDate(date);
    }

    @GetMapping(path = "/Customer/date/{date}/dateEnd/{dateEnd}")
    private List<Customer> findByDateAndDateEnd2(@PathVariable String date,@PathVariable String dateEnd) {

//        Specification
        return customerRepository.findByDateAndDateEnd2(date,dateEnd);
    }


}
