package com.epccrm.view.marketingnote;

import com.epccrm.entity.MarketingNote;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "marketingNotes/:id", layout = MainView.class)
@ViewController("MarketingNote.detail")
@ViewDescriptor("marketing-note-detail-view.xml")
@EditedEntityContainer("marketingNoteDc")
public class MarketingNoteDetailView extends StandardDetailView<MarketingNote> {
}