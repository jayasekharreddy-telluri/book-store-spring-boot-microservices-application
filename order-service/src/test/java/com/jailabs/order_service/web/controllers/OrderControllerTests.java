package com.jailabs.order_service.web.controllers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;

import com.jailabs.order_service.AbstractIT;
import com.jailabs.order_service.testdata.TestDataFactory;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-orders.sql") // Loads test data before each test class
class OrderControllerTests extends AbstractIT {

    @Nested
    class CreateOrderTests {

        @Test
        void shouldCreateOrderSuccessfully() {
            mockGetProductByCode("P100", "Product 1", new BigDecimal("25.50"));
            var payload =
                    """
                                {
                                    "customer" : {
                                        "name": "Siva",
                                        "email": "siva@gmail.com",
                                        "phone": "999999999"
                                    },
                                    "deliveryAddress" : {
                                        "addressLine1": "HNO 123",
                                        "addressLine2": "Kukatpally",
                                        "city": "Hyderabad",
                                        "state": "Telangana",
                                        "zipCode": "500072",
                                        "country": "India"
                                    },
                                    "items": [
                                        {
                                            "code": "P100",
                                            "name": "Product 1",
                                            "price": 25.50,
                                            "quantity": 1
                                        }
                                    ]
                                }
                            """;
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("orderNumber", notNullValue());
        }

        @Test
        void shouldReturnBadRequestWhenMandatoryDataIsMissing() {
            var payload = TestDataFactory.createOrderRequestWithInvalidCustomer();
            given().contentType(ContentType.JSON)
                    .body(payload)
                    .when()
                    .post("/api/orders")
                    .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value());
        }
    }

    @Nested
    class FetchOrdersTests {

        @Test
        void shouldFetchOrdersSuccessfully() {
            // Fetch all orders for the user 'siva'
            given().when()
                    .get("/api/orders")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
                    .body("size()", is(2)) // Ensure there are 2 orders
                    .body("[0].orderNumber", is("order-123"))
                    .body("[0].customerName", is("Siva"))
                    .body("[1].orderNumber", is("order-456"))
                    .body("[1].customerName", is("Prasad"));
        }

        @Test
        void shouldFetchSpecificOrderSuccessfully() {
            // Fetch specific order by orderNumber
            given().pathParam("orderNumber", "order-123")
                    .when()
                    .get("/api/orders/{orderNumber}")
                    .then()
                    .statusCode(HttpStatus.OK.value())
                    .contentType(ContentType.JSON)
                    .body("orderNumber", is("order-123"))
                    .body("customerName", is("Siva"))
                    .body("items.size()", is(2)) // Verify number of items
                    .body("items[0].code", is("P100"))
                    .body("items[0].name", is("The Hunger Games"))
                    .body("items[0].price", is(34.0f))
                    .body("items[0].quantity", is(2));
        }
    }
}
