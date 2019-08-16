package parking;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

    @Test
    public void testPark_givenAVipCarAndAFullParkingLot_thenGiveAReceiptWithCarNameAndParkingLotName() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        String parkingLotName = "Parking lot 1";
        String carName = "Car A";
        ParkingLot parkingLot = new ParkingLot(parkingLotName, 1);
        parkingLot.getParkedCars().add(new Car("Car 2"));
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);
        Car car = new Car(carName);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
//        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);
        when(vipParkingStrategy.isAllowOverPark(car)).thenReturn(true);

        Receipt receipt = vipParkingStrategy.park(parkingLots, car);

        assertEquals(carName, receipt.getCarName());
        assertEquals(parkingLotName, receipt.getParkingLotName());
    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLot_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */

        String parkingLotName = "Parking lot 1";
        String carName = "Car B";
        String msg = "No Parking Lot";
        ParkingLot parkingLot = new ParkingLot(parkingLotName, 1);
        parkingLot.getParkedCars().add(new Car("Car 2"));
        List<ParkingLot> parkingLots = Collections.singletonList(parkingLot);
        Car car = new Car(carName);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        when(vipParkingStrategy.isAllowOverPark(car)).thenReturn(false);

        Receipt receipt = vipParkingStrategy.park(parkingLots, car);

        assertEquals(carName, receipt.getCarName());
        assertEquals(msg, receipt.getParkingLotName());
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        String carName = "Car A";
        Car car = new Car(carName);

        CarDao carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(carName)).thenReturn(true);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertTrue(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse() {

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        String carName = "Car B";
        Car car = new Car(carName);

        CarDao carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(carName)).thenReturn(true);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
        String carName = "Car A";
        Car car = new Car(carName);

        CarDao carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(carName)).thenReturn(false);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        String carName = "Car B";
        Car car = new Car(carName);

        CarDao carDao = mock(CarDaoImpl.class);
        when(carDao.isVip(carName)).thenReturn(false);

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        when(vipParkingStrategy.getCarDao()).thenReturn(carDao);

        boolean allowOverPark = vipParkingStrategy.isAllowOverPark(car);
        assertFalse(allowOverPark);
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
