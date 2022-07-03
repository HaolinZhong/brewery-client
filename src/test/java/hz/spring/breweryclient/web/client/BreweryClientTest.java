package hz.spring.breweryclient.web.client;

import hz.spring.breweryclient.web.model.BeerDTO;
import hz.spring.breweryclient.web.model.CustomerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BreweryClientTest {

    @Autowired
    BreweryClient breweryClient;

    @Test
    void getBeerById() {
        BeerDTO beerDTO = breweryClient.getBeerById(UUID.randomUUID());
        assertNotNull(beerDTO);
    }

    @Test
    void saveNewBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
        URI uri = breweryClient.saveNewBeer(beerDTO);
        assertNotNull(uri);
        System.out.println(uri.toString());
    }

    @Test
    void updateBeer() {
        BeerDTO beerDTO = BeerDTO.builder().beerName("New Beer").build();
        breweryClient.updateBeer(UUID.randomUUID(), beerDTO);
    }

    @Test
    void deleteBeer() {
        breweryClient.deleteBeer(UUID.randomUUID());
    }

    @Test
    void getCustomerById() {
        CustomerDTO customerDTO = breweryClient.getCustomerById(UUID.randomUUID());
        assertNotNull(customerDTO);
    }

    @Test
    void saveNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();
        URI uri = breweryClient.saveNewCustomer(customerDTO);
        assertNotNull(uri);
        System.out.println(uri);
    }

    @Test
    void updateCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder().name("New Customer").build();
        breweryClient.updateCustomer(UUID.randomUUID(), customerDTO);
    }

    @Test
    void deleteCustomer() {
        breweryClient.deleteCustomer(UUID.randomUUID());
    }
}