package com.epccrm.view.enumvalue;

import com.epccrm.entity.EnumValue;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.select.JmixSelect;
import io.jmix.flowui.view.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Route(value = "enumValues/:id", layout = MainView.class)
@ViewController("EnumValue.detail")
@ViewDescriptor("enum-value-detail-view.xml")
@EditedEntityContainer("enumValueDc")
public class EnumValueDetailView extends StandardDetailView<EnumValue> {
    private static final Logger log = LoggerFactory.getLogger(EnumValueDetailView.class);

    @PersistenceContext
    EntityManager entityManager;

    @ViewComponent
    Select entityTypeSelect;

    @ViewComponent
    Select fieldTypeSelect;

    @Subscribe
    public void onInit(final InitEvent event) {
        Set<String> entityTypes = new HashSet<>();
        entityManager.getMetamodel().getEntities().forEach(entity -> {
                    entity.getAttributes().forEach(attribute -> {
                        if (attribute.getJavaType().equals(EnumValue.class)) {
                            // log.info("Entity: " + entity.getName() + ", Attribute: " + attribute.getName());
                            entityTypes.add(entity.getName());
                        }
                    });
                }
        );
        entityTypeSelect.setItems(entityTypes);

    }

    @Subscribe("entityTypeSelect")
    public void onEntityTypeSelectComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixSelect<String>, String> event) {
        List<String> fieldTypes = new ArrayList<>();
        entityManager.getMetamodel().getEntities().stream()
                .filter(entity -> entity.getName().equals(event.getValue()))
                .forEach(filteredEntity -> {
                    filteredEntity.getAttributes().forEach(attribute -> {
                        if (attribute.getJavaType().equals(EnumValue.class))
                            fieldTypes.add(attribute.getName());
                    });
                });
        fieldTypeSelect.setItems(fieldTypes);

    }
}

