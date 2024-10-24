package com.epccrm.view.address;

import com.epccrm.entity.Address;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "addresses", layout = MainView.class)
@ViewController("Address.list")
@ViewDescriptor("address-list-view.xml")
@LookupComponent("addressesDataGrid")
@DialogMode(width = "64em")
public class AddressListView extends StandardListView<Address> {
}