package com.epccrm.view.marketingnote;

import com.epccrm.entity.MarketingNote;
import com.epccrm.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.*;


@Route(value = "marketingNotes", layout = MainView.class)
@ViewController("MarketingNote.list")
@ViewDescriptor("marketing-note-list-view.xml")
@LookupComponent("marketingNotesDataGrid")
@DialogMode(width = "64em")
public class MarketingNoteListView extends StandardListView<MarketingNote> {
}