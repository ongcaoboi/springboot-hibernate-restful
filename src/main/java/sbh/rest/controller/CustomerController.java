package sbh.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbh.rest.entities.Customer;
import sbh.rest.repository.CustomerRepository;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class CustomerController {

	@Autowired
	private CustomerRepository cr;

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAll() {
		try {
			return new ResponseEntity<>(cr.getAll(), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers/filter")
	public ResponseEntity<List<Customer>> getFilter(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "sex", required = false) String sex) {
		try {
			return new ResponseEntity<>(cr.getFilter(name, sex), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/customers/{customerId}")
	public ResponseEntity<Customer> getById(@PathVariable("customerId") int customerId) {
		try {
			Customer customer = cr.getById(customerId);
			if (customer == null) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(cr.getById(customerId), HttpStatus.OK);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/customers")
	public ResponseEntity<String> create(@RequestBody Customer customer) {
		try {
			if (cr.add(customer)) {
				return new ResponseEntity<>("Created!", HttpStatus.CREATED);
			}
			return new ResponseEntity<>("Create faile!", HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customers/{customerId}")
	public ResponseEntity<String> update(@PathVariable("customerId") int customerId, @RequestBody Customer customer) {
		try {
			Customer customerFind = cr.getById(customerId);
			if (customerFind == null) {
				return new ResponseEntity<>("No customers found!", HttpStatus.BAD_GATEWAY);
			}

			customer.setId(customerId);

			if (cr.update(customer)) {
				return new ResponseEntity<>("Updated!", HttpStatus.OK);
			}
			return new ResponseEntity<>("Update faile!", HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customers/{customerId}")
	public ResponseEntity<String> delete(@PathVariable("customerId") int customerId) {
		try {
			Customer customer = cr.getById(customerId);
			if (customer == null) {
				return new ResponseEntity<>("No customers found!", HttpStatus.BAD_GATEWAY);
			}

			if (cr.delete(customer)) {
				return new ResponseEntity<>("Deleted!", HttpStatus.OK);
			}
			return new ResponseEntity<>("Delete faile!", HttpStatus.BAD_GATEWAY);
		} catch (Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
