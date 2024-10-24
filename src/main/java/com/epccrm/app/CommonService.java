package com.epccrm.app;

import com.epccrm.entity.Address;
import com.epccrm.entity.Contact;
import com.epccrm.entity.Document;
import com.epccrm.entity.Principal;
import io.jmix.core.DataManager;
import io.jmix.core.FetchPlan;
import io.jmix.core.FileRef;
import io.jmix.flowui.download.DownloadFormat;
import io.jmix.flowui.download.Downloader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
public class CommonService {

    private static final Logger log = LoggerFactory.getLogger(CommonService.class);


    @Autowired
    private DataManager dataManager;

    @Autowired
    private Downloader downloader;


    public void downloadFromFileStorage(Document attachment) {
        FileRef fileRef = attachment.getFile();
        log.info("downloadfile in storage{}", fileRef);
        downloader.download(fileRef, DownloadFormat.OCTET_STREAM);
    }


    public void getAndUpdateAddressPrincipal(Address address, UUID uuid) {
        Address saveAddress = dataManager.load(Address.class).id(address.getId()).one();
        log.info("get and update Address :{}", saveAddress);
        setDefaultAddressForPrincipal(address, uuid);

    }

    private void setDefaultAddressForPrincipal(Address address, UUID principalId) {
        try {
            Principal principal = dataManager.load(Principal.class)
                    .id(principalId)
                    .fetchPlan(fetchPlanBuilder -> fetchPlanBuilder
                            .addFetchPlan(FetchPlan.LOCAL)
                            .add("address", fetchPlan -> fetchPlan
                                    .add("isDefaultTrue")
                                    .add("address1")))
                    .one();

            updateDefaultAddress(principal, Principal::getAddress);

            // Log before saving the new default address
            log.info("Setting new default address: {}", address.getAddress1());
          //  address.setIsDefaultTrue(true);

            // Save the new default address
           // dataManager.save(address);
            updateAddress( address.getId(),address.getAddress1());

        } catch (Exception e) {
            log.error("Failed to update default address for principal", e);
        }
    }
    @Transactional
    public void updateAddress(UUID addressId, String newAddress1) {
        Address address = dataManager.load(Address.class).id(addressId).one();
        address.setAddress1(newAddress1);
        address.setIsDefaultTrue(true);
        dataManager.save(address);
    }



    private <T> void updateDefaultAddress(T entity, Function<T, List<Address>> getAddressListFunction) {
        List<Address> addresses = getAddressListFunction.apply(entity);
        for (Address currentDefaultAddress : addresses) {
            if (Boolean.TRUE.equals(currentDefaultAddress.getIsDefaultTrue())) {
                log.info("Old default address found: {}", currentDefaultAddress.getAddress1());
                currentDefaultAddress.setIsDefaultTrue(false);

                // Save the updated address (previously default, now not default)
                dataManager.save(currentDefaultAddress);
                log.info("Updated old default address (isDefaultTrue set to false): {}", currentDefaultAddress.getAddress1());
            }
        }
    }


    public <T> void updateSelectOptions(
            List<T> items,
            Function<T, String> getDefaultNameFunction,
            Function<T, Boolean> isDefaultFunction,
            Consumer<String> setPlaceholderFunction,
            String defaultPlaceholder) {

        String placeholder = defaultPlaceholder;

        for (T item : items) {
            if (Boolean.TRUE.equals(isDefaultFunction.apply(item))) {
                placeholder = "Default :" + getDefaultNameFunction.apply(item);
                break;
            }
        }
        setPlaceholderFunction.accept(placeholder);
        log.info("Placeholder set to: {}", placeholder);
    }

    public void getAndUpdateContact(Contact contact) {
        Contact saveContact = dataManager.load(Contact.class).id(contact.getId()).one();
        log.info("get and update contact :{}", saveContact);
        updateContact(saveContact);
    }

    public void updateContact(Contact contact) {
        contact.setIsDefaultTrue(true);
        List<Contact> currentDefaultContacts = dataManager.load(Contact.class)
                .query("select c from Contact c where c.principal = :principal and c.isDefaultTrue = :defaultValue")
                .parameter("principal", contact.getPrincipal())
                .parameter("defaultValue", true).list();
        log.info("current default contact list get and update contact :{}", currentDefaultContacts);
        currentDefaultContacts.forEach(currentDefaultContact -> {
            log.info("old default contact name is {}", currentDefaultContact.getFirstName());
            currentDefaultContact.setIsDefaultTrue(false);
            dataManager.save(currentDefaultContact);
        });
        log.info("new default contact name is {}", contact.getFirstName());
        dataManager.save(contact);
    }
}
