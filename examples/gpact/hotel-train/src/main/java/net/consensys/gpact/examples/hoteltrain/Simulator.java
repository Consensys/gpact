package net.consensys.gpact.examples.hoteltrain;

import java.math.BigInteger;
import net.consensys.gpact.examples.hoteltrain.soliditywrappers.Hotel;
import net.consensys.gpact.examples.hoteltrain.soliditywrappers.TravelAgency;
import org.web3j.tx.TransactionManager;

// No simulation needs to be done as the parameters from the travel agency's
// bookHotelAndTrain call get passed directly to the Hotel and Train's bookRoom call.
public class Simulator {
  private final Hotel hotelContract;
  private final TravelAgency travelAgencyContract;

  private String bookHotelAndTrainRLP;
  private String hotelBookRoomRLP;
  private String trainBookRoomRLP;

  public Simulator() {
    TransactionManager txM = null;
    this.hotelContract = Hotel.load(null, null, txM, null);
    this.travelAgencyContract = TravelAgency.load(null, null, txM, null);
  }

  public void executeSimulateBookHotelAndTrain(BigInteger date, BigInteger bookingId) {
    BigInteger maxAmountToPay =
        BigInteger.valueOf(100); // The hard coded value in the Solidity code.
    this.hotelBookRoomRLP = this.hotelContract.getABI_bookRoom(date, bookingId, maxAmountToPay);
    this.trainBookRoomRLP = this.hotelContract.getABI_bookRoom(date, bookingId, maxAmountToPay);
    this.bookHotelAndTrainRLP = this.travelAgencyContract.getABI_bookHotelAndTrain(date, bookingId);
  }

  public String getBookHotelAndTrainRLP() {
    return bookHotelAndTrainRLP;
  }

  public String getHotelBookRoomRLP() {
    return hotelBookRoomRLP;
  }

  public String getTrainBookRoomRLP() {
    return trainBookRoomRLP;
  }
}
