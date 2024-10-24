package com.epccrm.view.contact;

import com.epccrm.entity.Contact;
import com.epccrm.entity.Principal;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Route(value = "contacts/:id", layout = MainView.class)
@ViewController("Contact.detail")
@ViewDescriptor("contact-detail-view.xml")
@EditedEntityContainer("contactDc")
public class ContactDetailView extends StandardDetailView<Contact> {

    private static final Logger log = LoggerFactory.getLogger(ContactDetailView.class);

    public void onCreateSetDefaultTrue(Principal principal) {
        List<Contact> contact = principal.getContact();

        if (contact == null || contact.isEmpty() || contact.size() < 1) {
            log.info("Setting default true contact for principal: {}", principal);
            getEditedEntity().setIsDefaultTrue(true);
        }
    }
}