package com.epccrm.view.principal;

import com.epccrm.app.CommonService;
import com.epccrm.entity.Address;
import com.epccrm.entity.Contact;
import com.epccrm.entity.Document;
import com.epccrm.entity.Principal;
import com.epccrm.view.address.AddressDetailView;
import com.epccrm.view.contact.ContactDetailView;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.component.tabsheet.JmixTabSheet;
import io.jmix.flowui.kit.action.ActionPerformedEvent;
import io.jmix.flowui.model.DataContext;
import io.jmix.flowui.model.InstanceContainer;
import io.jmix.flowui.util.RemoveOperation;
import io.jmix.flowui.view.*;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Route(value = "principals/:id", layout = MainView.class)
@ViewController("Principal.detail")
@ViewDescriptor("principal-detail-view.xml")
@EditedEntityContainer("principalDc")
public class PrincipalDetailView extends StandardDetailView<Principal> {

    private static final Logger log = LoggerFactory.getLogger(PrincipalDetailView.class);

    protected Address address = null;
    protected Contact contact = null;


    @ViewComponent("tabs.addressTab")
    private Tab tabsAddressTab;
    @Autowired
    private EntityStates entityStates;
    @ViewComponent("tabs.contactTab")
    private Tab tabsContactTab;
    @Autowired
    private MessageBundle messageBundle;
    @ViewComponent
    private InstanceContainer<Principal> principalDc;
    @Autowired
    private CommonService commonService;
    @ViewComponent
    private JmixSelect<Object> selectedAddress;
    @Autowired
    private DataManager dataManager;
    @ViewComponent
    private JmixSelect<Object> selectedContact;
    @ViewComponent("tabs.marketingNotesTab")
    private Tab tabsMarketingNotesTab;
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    FileStorage fileStorage;
    @ViewComponent("tabs.documentTab")
    private Tab tabsDocumentTab;
    @ViewComponent
    private DataGrid<Address> addressesDataGrid;
    @ViewComponent
    private JmixDetails addressDetails;
    @ViewComponent
    private JmixTabSheet tabs;
    @ViewComponent
    private JmixSelect<Object> tabSelectedAddress;

    @ViewComponent
    private DataGrid<Document> documentsDataGrid;
    @Autowired
    private Notifications notifications;
    @ViewComponent
    private DataGrid<Address> addressesDataGrid2;
    @ViewComponent
    private DataContext dataContext;

    @Subscribe
    public void onInit(final InitEvent event) {
        setDocumentsDataGrid();
    }

    public void setDocumentsDataGrid() {
        documentsDataGrid.addComponentColumn(document -> {
            Button button = uiComponents.create(Button.class);
            button.setText("Download");
            button.addThemeName("primary");
            button.addClickListener(clickEvent -> {
                commonService.downloadFromFileStorage(document);
            });
            return button;
        });
    }


    @Subscribe
    public void onValidation(final ValidationEvent event) {
        Principal principal = getEditedEntity();
        if (entityStates.isNew(getEditedEntity())) {
            if (principal.getAddress() == null || principal.getAddress().isEmpty()) {
                event.getErrors().add(messageBundle.getMessage("address should not be empty"));
            }
        }

    }

    @Subscribe
    public void onBeforeShow(final BeforeShowEvent event) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
                updateAddressSelectOptions();
                if (!entityStates.isNew(getEditedEntity())) {
                    updateTabAddressSelectOptions();
                }
            }
            if (ObjectUtils.allNotNull(principalDc.getItem().getContact())) {
                updateContactSelectOptions();
            }

            //updateTabAddressSelectOptions();
        }
    }

    public void updateAddressSelectOptions() {
        commonService.updateSelectOptions(
                principalDc.getItem().getAddress(),
                Address::getAddress1,
                Address::getIsDefaultTrue,
                selectedAddress::setPlaceholder,
                "Select Default"
        );
    }

    public void updateTabAddressSelectOptions() {
        commonService.updateSelectOptions(
                principalDc.getItem().getAddress(),
                Address::getAddress1,
                Address::getIsDefaultTrue,
                tabSelectedAddress::setPlaceholder,
                "Select Default"
        );
    }

    @Subscribe
    public void onAfterSave(final AfterSaveEvent event) {
        if (ObjectUtils.allNotNull(address)) {
            Principal principal = getEditedEntity();
            commonService.getAndUpdateAddressPrincipal(address, principal.getId());
        }

        if (ObjectUtils.allNotNull(contact)) {
            commonService.getAndUpdateContact(contact);
        }

    }


    @Subscribe("selectedAddress")
    public void onSelectedAddressComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<Address>, Address> event) {
        Address changedAddress = event.getValue();
        if (ObjectUtils.allNotNull(changedAddress)) {
            address = changedAddress;
            //log.info("Default Address name is {}", changedAddress.getAddress1());
        }
    }

    @Install(to = "addressesDataGrid.create", subject = "viewConfigurer")
    private void addressesDataGridCreateViewConfigurer(final AddressDetailView addressDetailView) {

        Principal principal = getEditedEntity();
        addressDetailView.onCreateSetDefaultTrue(principal);
    }

    @Subscribe("selectedContact")
    public void onSelectedContactComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<Contact>, Contact> event) {
        Contact changedContact = event.getValue();
        if (ObjectUtils.allNotNull(changedContact)) {
            contact = changedContact;
            log.info("Default contact name is {}", changedContact);
        }
    }

    public void updateContactSelectOptions() {

        commonService.updateSelectOptions(
                principalDc.getItem().getContact(),
                Contact::getFirstName,
                Contact::getIsDefaultTrue,
                selectedContact::setPlaceholder,
                "Select Default"
        );
    }

    @Install(to = "contactsDataGrid.create", subject = "viewConfigurer")
    private void contactsDataGridCreateViewConfigurer(final ContactDetailView contactDetailView) {
        Principal principal = getEditedEntity();
        contactDetailView.onCreateSetDefaultTrue(principal);


    }

    @Supply(to = "documentsDataGrid.file", subject = "renderer")
    private Renderer<Document> documentsDataGridFileRenderer() {
        return new ComponentRenderer<>(document -> {
            FileRef fileRef = document.getFile();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("file");

                return image;
            } else {
                return null;
            }
        });
    }

    @Subscribe("tabs.mainTab")
    public void onTabsMainTabAttach(final AttachEvent event) {
        if (entityStates.isNew(getEditedEntity())) {
            tabsAddressTab.setVisible(false);
            tabsContactTab.setVisible(false);
            tabsDocumentTab.setVisible(false);
            tabsMarketingNotesTab.setVisible(false);
        } else {
            addressDetails.setVisible(false);

        }
    }

    @Subscribe("tabSelectedAddress")
    public void onTabSelectedAddressComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<Address>, Address> event) {
        Address changedAddress = event.getValue();
        if (ObjectUtils.allNotNull(changedAddress)) {
            address = changedAddress;
            //log.info("Default Address name is {}", changedAddress.getAddress1());

        }
    }

    @Install(to = "addressesDataGrid2.create", subject = "afterSaveHandler")
    private void addressesDataGrid2CreateAfterSaveHandler(final Address address) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
                updateTabAddressSelectOptions();
            }


        }

    }

    @Install(to = "addressesDataGrid.create", subject = "afterSaveHandler")
    private void addressesDataGridCreateAfterSaveHandler(final Address address) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
                updateAddressSelectOptions();
            }


        }
    }

    @Install(to = "addressesDataGrid.remove", subject = "afterActionPerformedHandler")
    private void addressesDataGridRemoveAfterActionPerformedHandler(final RemoveOperation.AfterActionPerformedEvent<Address> afterActionPerformedEvent) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
                updateAddressSelectOptions();
            }


        }
    }

    @Install(to = "addressesDataGrid2.create", subject = "viewConfigurer")
    private void addressesDataGrid2CreateViewConfigurer(final AddressDetailView addressDetailView) {
        Principal principal = getEditedEntity();
        addressDetailView.onCreateSetDefaultTrue(principal);
    }

    @Install(to = "contactsDataGrid.create", subject = "afterSaveHandler")
    private void contactsDataGridCreateAfterSaveHandler(final Contact contact) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getContact())) {
                updateContactSelectOptions();
            }
        }
    }

    @Install(to = "contactsDataGrid.remove", subject = "afterActionPerformedHandler")
    private void contactsDataGridRemoveAfterActionPerformedHandler(final RemoveOperation.AfterActionPerformedEvent<Contact> afterActionPerformedEvent) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getContact())) {
                updateContactSelectOptions();
            }


        }
    }

    @Install(to = "addressesDataGrid2.remove", subject = "afterActionPerformedHandler")
    private void addressesDataGrid2RemoveAfterActionPerformedHandler(final RemoveOperation.AfterActionPerformedEvent<Address> afterActionPerformedEvent) {
        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
        ) {
            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
                updateTabAddressSelectOptions();
            }


        }
    }

//    @Subscribe("addressesDataGrid.remove")
//    public void onAddressesDataGridRemove(ActionPerformedEvent event) {
//        Set<Address> selectedAddresses = addressesDataGrid.getSelectedItems();
//
//        if (selectedAddresses.stream().anyMatch(Address::getIsDefaultTrue)) {
//            // If any of the selected addresses is marked as default, show a notification
//            notifications.create("Address which is right now default would not be remove now first remove from default then only you can remove this")
//                    .withType(Notifications.Type.WARNING)
//                    .show();
//
//            // Stop the remove action
//            return;
//        }
//
//        // Proceed with the removal
//        for (Address address : selectedAddresses) {
//            dataContext.remove(address);
//        }
//        addressesDataGrid.getItems();
//    }
//}



//    @Subscribe("addressesDataGrid2.remove")
//    public void onAddressesDataGrid2Remove(final ActionPerformedEvent event) {
//        Component component = event.getComponent();
//        log.info("what is comming in component",component.getId());


        // Retrieve the selected item from the data grid
       // Address selectedAddress = dataGrid.getSingleSelectedItem();

        // Check if the selected address is not null
//        if (selectedAddress != null) {
//            // Check if the address is marked as default
//            if (selectedAddress.getIsDefaultTrue()) {
//                // Prevent deletion and show a message
//                setShowSaveNotification("Cannot delete the default address.", NotificationType.TRAY);
//                return; // Exit the method to prevent further processing
//            }
//
//            // Proceed with the removal of the address
//            // This assumes you have a standard method for handling the actual removal
//            // For example: removeAddress(selectedAddress);
//        } else {
//            // Handle the case where no address is selected
//           // setShowValidationErrors("No address selected for removal.", notifications.);
//        }


//    @Subscribe("addressesDataGrid2.remove")
//    public void onAddressesDataGrid2Remove(final ActionPerformedEvent event) {
//        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
//        ) {
//            if (ObjectUtils.allNotNull(principalDc.getItem().getAddress())) {
//                updateTabAddressSelectOptions();
//            }
//
//
//        }
//    }

//    @Subscribe("contactsDataGrid.remove")
//    public void onContactsDataGridRemove(final ActionPerformedEvent event) {
//        if (ObjectUtils.allNotNull(principalDc) && ObjectUtils.allNotNull(principalDc.getItem())
//        ) {
//            if (ObjectUtils.allNotNull(principalDc.getItem().getContact())) {
//                updateContactSelectOptions();
//            }
//
//
//        }
//    }

//    @Subscribe("tabs.addressTab")
//    public void onTabsAddressTabAttach(final AttachEvent event) {
//        if(!entityStates.isNew(getEditedEntity())){
//            addressDetails.setVisible(true);
//            log.info("heyy we we are in address tab");
//        }
//    }

//    @Autowired
//    private ViewNavigators viewNavigators;
//
//    private void navigateToView() {
//        viewNavigators.view(this, PrincipalDetailView.class).navigate();
//    }
//
//    private void navigateToViewThenBack() {
//        viewNavigators.view(this, PrincipalDetailView.class)
//                .withBackwardNavigation(true)
//                .navigate();
//    }


}
   







