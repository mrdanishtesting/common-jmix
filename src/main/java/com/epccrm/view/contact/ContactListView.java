package com.epccrm.view.contact;

import com.epccrm.entity.Contact;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "contacts", layout = MainView.class)
@ViewController("Contact.list")
@ViewDescriptor("contact-list-view.xml")
@LookupComponent("contactsDataGrid")
@DialogMode(width = "64em")
public class ContactListView extends StandardListView<Contact> {
}