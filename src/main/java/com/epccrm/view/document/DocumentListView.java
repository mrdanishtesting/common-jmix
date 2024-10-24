package com.epccrm.view.document;

import com.epccrm.entity.Document;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "documents", layout = MainView.class)
@ViewController("Document.list")
@ViewDescriptor("document-list-view.xml")
@LookupComponent("documentsDataGrid")
@DialogMode(width = "64em")
public class DocumentListView extends StandardListView<Document> {
    @Autowired
    private UiComponents uiComponents;
    @Autowired
    private FileStorage fileStorage;

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
            }
            else {
                return null;
            }
        });
    }
    }
