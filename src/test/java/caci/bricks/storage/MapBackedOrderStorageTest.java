package caci.bricks.storage;

import caci.bricks.model.order.Order;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MapBackedOrderStorageTest {
    private MapBackedOrderStorage mapBackedOrderStorage;

    @Before
    public void setUp() throws Exception {
        mapBackedOrderStorage = new MapBackedOrderStorage();
    }

    @Test
    public void create_orderWithNextNumberIsReturned() throws Exception {
        assertThat(mapBackedOrderStorage.create(1).getOrderNumber()).isEqualTo(1);
    }

    @Test
    public void create_multipleOrders_orderNumberIncreases() throws Exception {
        assertThat(mapBackedOrderStorage.create(1).getOrderNumber()).isEqualTo(1);
        assertThat(mapBackedOrderStorage.create(1).getOrderNumber()).isEqualTo(2);
        assertThat(mapBackedOrderStorage.create(1).getOrderNumber()).isEqualTo(3);
    }

    @Test
    public void fetch_validOrderNumber_orderIsReturned() throws Exception {
        Order order = mapBackedOrderStorage.create(2);
        int validOrderNumber = order.getOrderNumber();

        assertThat(mapBackedOrderStorage.fetch(validOrderNumber))
                .isEqualTo(order);
    }

    @Test
    public void fetch_invalidOrderNumber_nullIsReturned() throws Exception {
        assertThat(mapBackedOrderStorage.fetch(1234))
                .isNull();
    }
}