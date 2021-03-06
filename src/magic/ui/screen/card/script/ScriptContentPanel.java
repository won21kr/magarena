package magic.ui.screen.card.script;

import java.io.File;
import java.nio.file.Path;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import magic.data.CardDefinitions;
import magic.model.MagicCardDefinition;
import magic.ui.widget.CardSideBar;
import magic.ui.widget.M.MTextFileViewer;
import magic.utility.MagicFileSystem;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ScriptContentPanel extends JPanel {

    // UI components
    private final MigLayout migLayout = new MigLayout();
    private final MTextFileViewer scriptViewer = new MTextFileViewer();
    private final MTextFileViewer groovyViewer = new MTextFileViewer();
    private final JSplitPane splitter = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    private final CardSideBar sidebar;

    private final File scriptFile;
    private final File groovyFile;
    private final boolean isGroovy;

    public ScriptContentPanel(final MagicCardDefinition card) {

        sidebar = new CardSideBar();
        sidebar.setCard(card);

        final Path scriptsPath = card.isInvalid()
                ? MagicFileSystem.getDataPath(MagicFileSystem.DataPath.SCRIPTS_MISSING)
                : MagicFileSystem.getDataPath(MagicFileSystem.DataPath.SCRIPTS);

        scriptFile = scriptsPath.resolve(CardDefinitions.getScriptFilename(card)).toFile();
        groovyFile = scriptsPath.resolve(CardDefinitions.getGroovyFilename(card)).toFile();
        isGroovy = groovyFile.exists();

        setLookAndFeel();
        setLayout();
        refreshContent();

    }

    public void refreshContent() {
        scriptViewer.setTextFile(scriptFile);
        if (isGroovy) {
            groovyViewer.setTextFile(groovyFile);
        }
    }

    private void setLayout() {
        removeAll();
        migLayout.setLayoutConstraints("insets 0, gap 0");
        add(sidebar, "h 100%");
        add(isGroovy ? splitter : scriptViewer.component(), "w 100%, h 100%");
    }

    private void setLookAndFeel() {
        setLayout(migLayout);
        setOpaque(false);
        if (isGroovy) {
            splitter.setOneTouchExpandable(true);
            splitter.setLeftComponent(scriptViewer.component());
            splitter.setRightComponent(groovyViewer.component());
            splitter.setResizeWeight(0.5);
            splitter.setDividerSize(14);
            splitter.setBorder(null);
            splitter.setOpaque(false);
        }
    }
}
