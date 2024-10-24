package com.epccrm.view.address;

import com.epccrm.entity.Address;
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

@Route(value = "addresses/:id", layout = MainView.class)
@ViewController("Address.detail")
@ViewDescriptor("address-detail-view.xml")
@EditedEntityContainer("addressDc")
public class AddressDetailView extends StandardDetailView<Address> {

  private static final Logger log = LoggerFactory.getLogger(AddressDetailView.class);

  public void onCreateSetDefaultTrue(Principal principal) {

    List<Address> address = principal.getAddress();

    if (address == null || address.isEmpty() || address.size() < 1) {
      log.info("Setting default true for principal: {}", principal);
      getEditedEntity().setIsDefaultTrue(true);
    }
  }


}






