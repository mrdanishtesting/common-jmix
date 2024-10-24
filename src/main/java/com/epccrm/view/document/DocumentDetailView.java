package com.epccrm.view.document;

import com.epccrm.entity.Document;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.component.upload.Receiver;
import com.vaadin.flow.router.Route;
import io.jmix.core.FileRef;
import io.jmix.flowui.component.upload.FileStorageUploadField;
import io.jmix.flowui.component.upload.receiver.FileTemporaryStorageBuffer;
import io.jmix.flowui.kit.component.upload.event.FileUploadSucceededEvent;
import io.jmix.flowui.upload.TemporaryStorage;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.UUID;

@Route(value = "documents/:id", layout = MainView.class)
@ViewController("Document.detail")
@ViewDescriptor("document-detail-view.xml")
@EditedEntityContainer("documentDc")
public class DocumentDetailView extends StandardDetailView<Document> {

    @ViewComponent
    private FileStorageUploadField manuallyControlledField;


    @Autowired
    private TemporaryStorage temporaryStorage;


    @Subscribe("manuallyControlledField")
    public void onManuallyControlledFieldFileUploadSucceeded(
            final FileUploadSucceededEvent<FileStorageUploadField> event) {
        Receiver receiver = event.getReceiver();
        if (receiver instanceof FileTemporaryStorageBuffer) {
            UUID fileId = ((FileTemporaryStorageBuffer) receiver)
                    .getFileData().getFileInfo().getId();
            File file = temporaryStorage.getFile(fileId);

            if (file != null) {
//                notifications.create("File is uploaded to local storage device at "
//                                + file.getAbsolutePath())
//                        .show();
            }
            FileRef fileRef = temporaryStorage.putFileIntoStorage(fileId, event.getFileName());
            manuallyControlledField.setValue(fileRef);
//            notifications.create("Uploaded file: "
//                            + ((FileTemporaryStorageBuffer) receiver).getFileData().getFileName())
//                    .show();
        }


    }
}