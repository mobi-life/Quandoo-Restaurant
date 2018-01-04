package com.quandoo.restaurant.sharedtest;

import com.quandoo.restaurant.domain.model.CustomerModel;
import com.quandoo.restaurant.domain.model.ReservationModel;
import com.quandoo.restaurant.domain.model.TableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Behzad on 12/29/2017.
 */

public class TestUtils {

    public static List<CustomerModel> mockCustomers(){
        List<CustomerModel> customers = new ArrayList<>();
        customers.add(new CustomerModel(1,"Behzad","Ghaffarnejad"));
        customers.add(new CustomerModel(2,"Bill","Gates"));
        customers.add(new CustomerModel(3,"Steve","Jobs"));
        return customers;
    }

    public static List<TableModel> mockTables(){
        List<TableModel> tables = new ArrayList<>();
        tables.add(new TableModel(1,true,-1));
        tables.add(new TableModel(2,false,2));
        tables.add(new TableModel(3,false,3));
        return tables;
    }

    public static List<ReservationModel> mockedReservations() {
        List<ReservationModel> reservations = new ArrayList<>();
        reservations.add(new ReservationModel(1,2,2));
        reservations.add(new ReservationModel(2,3,3));
        return reservations;
    }
}
