package parking;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class InOrderParkingStrategyTest {

    @Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
         * With using Mockito to mock the input parameter */

        String parkingLotName = "Parking lot 1";
        String carName = "Car 1";
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();

        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = new Car(carName);
        when(parkingLot.getName()).thenReturn(parkingLotName);
        Receipt receipt = inOrderParkingStrategy.createReceipt(parkingLot, car);

        assertEquals(parkingLotName, receipt.getParkingLotName());
        assertEquals(carName, receipt.getCarName());
    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */

        String carName = "Car 1";
        Car mCar = mock(Car.class);
        InOrderParkingStrategy inOrderParkingStrategy = new InOrderParkingStrategy();
        when(mCar.getName()).thenReturn(carName);
        Receipt receipt = inOrderParkingStrategy.createNoSpaceReceipt(mCar);

        verify(mCar, times(1)).getName();
        assertEquals(carName, receipt.getCarName());
    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

        String carName = "Car 1";
        String msg = "No Parking Lot";
        Car car = new Car(carName);
        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(null, car);

        verify(inOrderParkingStrategy, times(1)).park(null, car);
        assertEquals(carName, receipt.getCarName());
        assertEquals(msg, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

        String parkingLotName = "Parking lot 1";
        String carName = "Car 1";
        ParkingLot parkingLot = new ParkingLot(parkingLotName, 1);
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);
        Car car = new Car(carName);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(1)).createReceipt(parkingLot, car);
        assertEquals(car, parkingLot.getParkedCars().get(0));
    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt() {

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

        String parkingLotName = "Parking lot 1";
        String carName = "Car 1";
        String msg = "No Parking Lot";
        ParkingLot parkingLot = new ParkingLot(parkingLotName, 1);
        parkingLot.getParkedCars().add(new Car("Car 2"));
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);
        Car car = new Car(carName);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy, times(0)).createReceipt(parkingLot, car);
        assertEquals(msg, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot() {

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

        String parkingLotName_1 = "Parking lot 1";
        String parkingLotName_2 = "Parking lot 2";

        String carName = "Car 1";
        ParkingLot parkingLot_1 = new ParkingLot(parkingLotName_1, 1);
        parkingLot_1.getParkedCars().add(new Car("Car 2"));
        ParkingLot parkingLot_2 = new ParkingLot(parkingLotName_2, 1);
        List<ParkingLot> parkingLots = Arrays.asList(parkingLot_1, parkingLot_2);
        Car car = new Car(carName);

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        Receipt receipt = inOrderParkingStrategy.park(parkingLots, car);

        verify(inOrderParkingStrategy).createReceipt(parkingLot_2, car);    // default times(1)
        assertEquals(parkingLotName_2, receipt.getParkingLotName());
    }


}
