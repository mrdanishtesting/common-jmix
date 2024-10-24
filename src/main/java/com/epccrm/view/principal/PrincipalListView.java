package com.epccrm.view.principal;

import com.epccrm.entity.Principal;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import io.jmix.core.FileRef;
import io.jmix.core.FileStorage;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.grid.DataGrid;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;


@Route(value = "principals", layout = MainView.class)
@ViewController("Principal.list")
@ViewDescriptor("principal-list-view.xml")
@LookupComponent("principalsDataGrid")
@DialogMode(width = "64em")
public class PrincipalListView extends StandardListView<Principal> {
    @ViewComponent
    private DataGrid<Principal> principalsDataGrid;
    @Autowired
    private UiComponents uiComponents;

    @Autowired
    private FileStorage fileStorage;

    @Supply(to = "principalsDataGrid.picture", subject = "renderer")
    private Renderer<Principal> principalsDataGridPictureRenderer() {
        return new ComponentRenderer<>(principal -> {
            FileRef fileRef = principal.getLogo();
            if (fileRef != null) {
                Image image = uiComponents.create(Image.class);
                image.setWidth("30px");
                image.setHeight("30px");
                StreamResource streamResource = new StreamResource(
                        fileRef.getFileName(),
                        () -> fileStorage.openStream(fileRef));
                image.setSrc(streamResource);
                image.setClassName("logo-picture");

                return image;
            } else {
                return null;
            }
        });
    }
    }

