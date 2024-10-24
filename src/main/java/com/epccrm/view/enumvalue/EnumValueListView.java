package com.epccrm.view.enumvalue;

import com.epccrm.entity.EnumValue;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "enumValues", layout = MainView.class)
@ViewController("EnumValue.list")
@ViewDescriptor("enum-value-list-view.xml")
@LookupComponent("enumValuesDataGrid")
@DialogMode(width = "64em")
public class EnumValueListView extends StandardListView<EnumValue> {
}