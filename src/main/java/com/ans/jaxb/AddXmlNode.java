package com.ans.jaxb;

import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckListView;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.spire.xls.CellRange;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.XPathCompiler;
import net.sf.saxon.s9api.XPathSelector;
import net.sf.saxon.s9api.XdmNode;

/**
 * convert JDV files
 * 
 * @author bensalem Nizar
 */
public class AddXmlNode extends Application {
	/**
	 * files list
	 */
	private static List<File> files;
	/**
	 * files list
	 */
	private static List<File> filesB;
	/**
	 * files list to inster
	 */
	private static List<File> finalFiles;
	/**
	 * isOkGenerate xml file
	 */
	private boolean isOkGenerate;
	/**
	 * secondStage
	 */
	private Stage secondStage;
	/**
	 * list view
	 */
	private static CheckListView<File> list;
	/**
	 * list view B
	 */
	private static CheckListView<File> listB;
	/**
	 * isOk
	 */
	private boolean isOk;
	/**
	 * selectAll
	 */
	private final CheckBox selectAll = new CheckBox();
	/**
	 * labelSelectAll
	 */
	private final Label labelSelectAll = INUtility.labelForValue(() -> INUtility.get("button.paste.text"));
	/**
	 * hboxVisibility
	 */
	private static HBox hboxVisibility = new HBox();
	/**
	 * list file local
	 */
	public List<File> listF = new ArrayList<>();
	/**
	 * list file local
	 */
	public List<File> listFR = new ArrayList<>();
	/**
	 * size
	 */
	public Toolkit size = Toolkit.getDefaultToolkit();
	/**
	 * primaryStage
	 */
	public Stage primaryStage = new Stage();
	/**
	 * thirdStage
	 */
	public Stage thirdStage;
	/**
	 * tokenurl
	 */
	public String tokenurl;
	/**
	 * downloadurl
	 */
	public String downloadurl;
	/**
	 * tokenopen
	 */
	public String tokenopen;
	/**
	 * fileContent
	 */
	public String fileContent;
	/**
	 * mapSnomed
	 */
	public Map<String, String> mapSnomed = new ConcurrentHashMap<>();
	/**
	 * count
	 */
	public int count;
	/**
	 * passed
	 */
	public boolean passed;
	/**
	 * selectedList
	 */
	public List<String> selectedList = new ArrayList<>();
	/**
	 * accord
	 */
	public Accordion accord = new Accordion();
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(AddXmlNode.class);

	/**
	 * main method
	 * 
	 * @param args
	 */
	public static void main(final String args[]) {
		launch(args);
	}

	/**
	 * start javafx App
	 */
	@Override
	public void start(final Stage stage) {
		final TextField textFieldPS = RegionUtility2.getField1();
		final String home1 = new File(textFieldPS.getText()).getParent();
		final File directory = new File(home1 + Constant.JSONFOLDER);
		final File directorySys = new File(home1 + Constant.CODEFOLDER);
		try {
			final File[] liste = directory.listFiles();
			if (liste != null && liste.length > 0) {
				for (final File file : liste) {
					FileUtils.forceDelete(file);
				}
			}
			if (directory.exists()) {
				FileUtils.forceDelete(directory);
			}
			final File[] listSys = directorySys.listFiles();
			if (listSys != null && listSys.length > 0) {
				for (final File file : listSys) {
					FileUtils.forceDelete(file);
				}
			}
			if (directorySys.exists()) {
				FileUtils.forceDelete(directorySys);
			}
		} catch (final IOException exp) {
			if (LOG.isInfoEnabled()) {
				final String error = exp.getMessage();
				LOG.error(error);
			}
		}
		final Button buttonTermino1 = INUtility.buttonForKey("button.updateTermino");
		final TextField textLogin = new TextField();
		final PasswordField textPwd = new PasswordField();
		final Label labelPwd = INUtility.labelForValue(() -> INUtility.get("button.searchreplace.text"));
		final Label labelLog = INUtility.labelForValue(() -> INUtility.get("button.view"));
		hboxVisibility.setVisible(false);
		hboxVisibility.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
		selectAll.setSelected(false);
		list = new CheckListView<>();
		listB = new CheckListView<>();
		finalFiles = new ArrayList<>();

		// Start ProgressBar creation
		final double wndwWidth = 150.0d;
		final double wndhHeigth = 150.0d;
		final ProgressIndicator progress = new ProgressIndicator();
		progress.setMinWidth(wndwWidth);
		progress.setMinHeight(wndhHeigth);
		progress.setProgress(0.25F);
		final VBox updatePane = new VBox();
		updatePane.setPadding(new Insets(10));
		updatePane.setSpacing(5.0d);
		updatePane.setAlignment(Pos.CENTER);
		NodeUtility2.newObsList(updatePane).addAll(progress);
		updatePane.setStyle(Constant.STYLE4);
		final Stage taskUpdateStage = new Stage(StageStyle.UNDECORATED);
		taskUpdateStage.setScene(new Scene(updatePane, 170, 170));
		// End progressBar

		final ImageView imgView = ImageUtility.imageView();

		final ImageView imgViewM = ImageUtility.imageViewM();

		final ImageView imgValid = ImageUtility.imgValid();

		final ImageView imgRead = ImageUtility.imgRead();

		final ImageView imgExit = ImageUtility.imgExit();

		final Menu file = INUtility.menuForKey("button.file");
		file.setStyle(Constant.STYLE2);

		final MenuItem item = INUtility.menuBarForKey("button.openFile");
		item.setGraphic(imgView);
		item.setStyle(Constant.STYLE2);

		final MenuItem item1 = INUtility.menuBarForKey("label.wellformed");
		item1.setGraphic(imgValid);
		item1.setStyle(Constant.STYLE2);

		final MenuItem item3 = INUtility.menuBarForKey("label.exit");
		item3.setGraphic(imgExit);
		item3.setStyle(Constant.STYLE2);

		JavaFxUtility.newObsMenu(file).addAll(item, item1, item3);
		// Creating separator menu items
		final SeparatorMenuItem sep = new SeparatorMenuItem();
		// Adding separator objects to menu
		JavaFxUtility.newObsMenu(file).add(2, sep);

		final Menu apropos = INUtility.menuForKey("button.apropos");
		apropos.setStyle(Constant.STYLE2);

		final MenuItem item2 = INUtility.menuBarForKey("label.readme");
		item2.setGraphic(imgRead);
		item2.setStyle(Constant.STYLE2);

		JavaFxUtility.newObsMenu(apropos).addAll(item2);

		final Menu tools = INUtility.menuForKey("button.outils");
		tools.setStyle(Constant.STYLE2);

		final MenuItem itemtools1 = INUtility.menuBarForKey("popup.arborescence");
		final ImageView imgViewT = ImageUtility.imgViewT();
		itemtools1.setGraphic(imgViewT);
		itemtools1.setStyle(Constant.STYLE2);

		final MenuItem itemtools2 = INUtility.menuBarForKey("button.openAD");
		final ImageView imgViewO = ImageUtility.imgViewO();
		itemtools2.setGraphic(imgViewO);
		itemtools2.setStyle(Constant.STYLE2);

		JavaFxUtility.newObsMenu(tools).addAll(itemtools1, itemtools2);

		// Creating a File chooser
		final FileChooser fileChooser = new FileChooser();
		fileChooser.titleProperty().bind(INUtility.createStringBinding(Constant.CHOOSEFILE));
		JavaFxUtility.newExtFilter(fileChooser).addAll(new ExtensionFilter(INUtility.get("button.closetxt.text"),
				Constant.ALLXML, Constant.ALLXLSX, Constant.ALLXLSM));
		files = new ArrayList<>();
		filesB = new ArrayList<>();
		listF = new ArrayList<>();
		listFR = new ArrayList<>();
		JavaFxUtility.newObsListStr(list).add(getClass().getResource(Constant.STYLE).toExternalForm());
		JavaFxUtility.newObsListStr(listB).add(getClass().getResource(Constant.STYLE).toExternalForm());
		final Label labelEmpty = INUtility.labelForValue(() -> INUtility.get("button.cut.text"));
		labelEmpty.setStyle(Constant.STYLE5);
		list.setPlaceholder(labelEmpty);
		list.setStyle(Constant.STYLE5);
		list.setPrefHeight(620);
		list.setMaxHeight(620);
		list.setMinHeight(620);
		list.setPrefWidth((NodeUtility2.newDimension(size).getWidth() - 10) / 2);
		JavaFxUtility.newSelectionModel(list).setSelectionMode(SelectionMode.MULTIPLE);
		final Label labelEmpty1 = INUtility.labelForValue(() -> INUtility.get("button.copy.text"));
		labelEmpty1.setStyle(Constant.STYLE5);
		listB.setPlaceholder(labelEmpty1);
		listB.setStyle(Constant.STYLE5);
		listB.setPrefHeight(620);
		listB.setMaxHeight(620);
		listB.setMinHeight(620);
		listB.setPrefWidth((NodeUtility2.newDimension(size).getWidth() - 10) / 2);
		JavaFxUtility.newSelectionModel(listB).setSelectionMode(SelectionMode.MULTIPLE);
		// Adding action on the menu item
		item.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				// Opening a dialog box
				final List<File> file = fileChooser.showOpenMultipleDialog(stage);
				if (file != null) {
					files.addAll(file);
					final ObservableList<File> oblist = FXCollections.observableArrayList();
					for (final File file2 : files) {
						oblist.add(file2);
					}
					list.setItems(oblist);
					JavaFxUtility2.newIndexModel(list).checkAll();
					if (NodeUtility2.isEmptyOrNull(finalFiles)) {
						finalFiles = new ArrayList<>();
					}
					finalFiles.addAll(FileUtility.getItems(list));

					list.setCellFactory(lv -> new CheckBoxListCell<>(list::getItemBooleanProperty) {
						/**
						 * tooltip
						 */
						private final Tooltip tooltip = new Tooltip();

						@Override
						public void updateItem(final File employee, final boolean empty) {
							super.updateItem(employee, empty);
							setText(employee == null ? "" : String.format(employee.getAbsolutePath()));
							if (employee != null) {
								tooltip.setText(employee.getName() + '\n' + (double) employee.length() / 1024
										+ Constant.KBYTES);
								setTooltip(tooltip);
							}
						}
					});
					accord.setExpandedPane(null);
				}
			}
		});

		// Adding action on the menu item3
		item3.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				if (secondStage != null && secondStage.isShowing()) {
					secondStage.close();
				}
				if (primaryStage != null && primaryStage.isShowing()) {
					primaryStage.close();
				}
				if (thirdStage != null && thirdStage.isShowing()) {
					thirdStage.close();
					textLogin.setText("");
					textPwd.setText("");
				}
				stage.close();
			}
		});

		// Adding action on the menu item1
		item1.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				// Opening a dialog box
				final List<File> file = fileChooser.showOpenMultipleDialog(stage);
				final List<File> fileMalFormed = new ArrayList<>();
				if (file != null) {
					for (final File file2 : file) {
						try (InputStream iStrream = Files.newInputStream(Paths.get(file2.toURI()))) {
							final DocumentBuilderFactory dBF = DocumentBuilderFactory.newInstance();
							DocumentBuilder builder;
							builder = dBF.newDocumentBuilder();
							builder.parse(iStrream);
						} catch (final SAXException | IOException | ParserConfigurationException e) {
							if (file2 != null) {
								fileMalFormed.add(file2);
							}
						}
					}
					if (fileMalFormed.isEmpty()) {
						final Alert alert = new Alert(AlertType.INFORMATION);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(130);
						dialogPane.setMaxHeight(130);
						dialogPane.setPrefHeight(130);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.setContentText(INUtility.get("popup.error11"));
						alert.showAndWait();
					} else {
						String name;
						final StringBuilder sBuilder = new StringBuilder();
						for (final File file2 : fileMalFormed) {
							sBuilder.append(Constant.RETOURCHARIOT).append(file2.getName());
						}
						name = sBuilder.toString();
						final Alert alert = new Alert(AlertType.ERROR);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(120 * fileMalFormed.size());
						dialogPane.setMaxHeight(120 * fileMalFormed.size());
						dialogPane.setPrefHeight(120 * fileMalFormed.size());
						alert.setContentText(INUtility.get("popup.error10") + Constant.RETOURCHARIOT + name);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.showAndWait();
					}
				}
			}
		});
		// Adding action on the menu item2
		item2.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				Platform.runLater(() -> {
					final ClassLoader classloader = NodeUtility2.newClassLoader();
					final InputStream iStream;
					if (INUtility.getLocale().equals(Locale.FRENCH)) {
						iStream = classloader.getResourceAsStream(Constant.LISEZMOIFILE);
					} else {
						iStream = classloader.getResourceAsStream(Constant.LISEZMOIEN);
					}
					final VBox root = new VBox();
					root.setPadding(new Insets(10));
					root.setSpacing(5);
					final TextArea textArea = new TextArea();
					textArea.setEditable(false);
					textArea.setPrefHeight(Integer.MAX_VALUE);
					textArea.setPrefWidth(Integer.MAX_VALUE);
					textArea.setStyle(Constant.STYLE6);
					JavaFxUtility.newObsListTArea(textArea)
							.add(getClass().getResource(Constant.STYLE).toExternalForm());
					try {
						textArea.setText(FileUtility.readFileContents(iStream));
						NodeUtility2.newObsList(root).add(textArea);
						final Scene scene = new Scene(root);
						primaryStage.titleProperty().bind(INUtility.createStringBinding("label.readme"));
						primaryStage.setScene(scene);
						primaryStage.setMaximized(true);
						primaryStage.show();
					} catch (final IOException e) {
						if (LOG.isInfoEnabled()) {
							final String error = e.getMessage();
							LOG.error(error);
						}
					}
				});
			}
		});

		final Label lContent = INUtility.labelForValue(() -> INUtility.get("button.yes.text"));

		final TextField field = new TextField();

		final Label lContentOther = INUtility.labelForValue(() -> INUtility.get("button.no.text"));

		final TextField fieldOther = new TextField();

		final ImageView imgViewA = ImageUtility.imgViewA();

		final ImageView imgViewR = ImageUtility.imgViewR();

		final ImageView imgViewI = ImageUtility.imgViewI();

		// Creating a menu bar and adding menu to it.
		final MenuBar menuBar = new MenuBar(file, tools, apropos);

		final Button button1 = INUtility.buttonForKey("button.macrojdvad");
		button1.setGraphic(imgViewA);
		final Button button10 = INUtility.buttonForKey("button.macro");
		button10.setGraphic(imgViewM);
		final ImageView imgViewD = ImageUtility2.imgViewD();

		final Button buttonDownload = INUtility.buttonForKey("button.download");

		final Button buttonTermino = INUtility.buttonForKey("button.updateTermino");
		buttonTermino.setGraphic(imgViewR);
		buttonDownload.setGraphic(imgViewD);
		// creating ImageView for adding image
		final ImageView imageView = ImageUtility2.getImageView();

		// creating HBox to add imageview
		final HBox hBoxImg = new HBox();
		NodeUtility2.newHObsList(hBoxImg).addAll(imageView);
		hBoxImg.setStyle(Constant.STYLE7);
		hBoxImg.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);

		final Button button3 = INUtility.buttonForKey("button.initinterface");
		button3.setGraphic(imgViewI);

		// Creating a Grid Pane
		final GridPane gridPane4 = new GridPane();
		// Setting the vertical and horizontal gaps between the columns
		gridPane4.setVgap(1);
		gridPane4.setHgap(5);
		// Setting the Grid alignment
		gridPane4.setAlignment(Pos.BASELINE_LEFT);
		// Arranging all the nodes in the grid
		gridPane4.add(buttonDownload, 0, 4);
		gridPane4.add(button10, 1, 4);
		gridPane4.add(button1, 2, 4);
		gridPane4.add(buttonTermino, 3, 4);
		gridPane4.add(button3, 4, 4);
		gridPane4.setMinSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);

		final Label label = RegionUtility.getLabel();

		final DateTimePicker picker = RegionUtility2.getPicker();

		final Label label1 = RegionUtility.getLabel1();

		final ComboBox<String> comboBox = new ComboBox<>();
		final ObservableList<String> options = FXCollections.observableArrayList(Constant.FINALE, Constant.PARTIEL,
				Constant.FINALETRONQUE, Constant.COMPLETED, Constant.ACTIVE, Constant.ABORTED);
		comboBox.setItems(options);
		JavaFxUtility2.newSingleModel(comboBox).select(0);
		comboBox.setPadding(new Insets(5, 5, 5, 5));
		comboBox.setPrefWidth(140);
		comboBox.setStyle(Constant.STYLE8);
		JavaFxUtility2.newStyleCombo(comboBox).add(getClass().getResource(Constant.STYLE).toExternalForm());

		final Label label2 = RegionUtility.getLabel2();

		final TextField textField2 = RegionUtility2.getTextField();

		final Label labelL = RegionUtility.getLabel3();

		final TextField textFieldL = RegionUtility2.getTextField2();

		final Label labelT = RegionUtility.getLabel4();

		final TextField textFieldT = RegionUtility2.getTextField3();

		final Label labelP = INUtility.labelForValue(() -> INUtility.get("popup.filePath"));
		labelP.setPadding(new Insets(5, 5, 5, 5));
		labelP.setStyle(Constant.STYLE5);

		final TextField textFieldP = RegionUtility2.getField();

		final Label labelPS = INUtility.labelForValue(() -> INUtility.get("popup.filepathjdv"));
		labelPS.setPadding(new Insets(5, 5, 5, 5));
		labelPS.setStyle(Constant.STYLE5);

		final Label labelUrl = INUtility.labelForValue(() -> INUtility.get("button.url"));
		labelUrl.setPadding(new Insets(5, 5, 5, 5));
		labelUrl.setStyle(Constant.STYLE5);

		final TextField textFieldUrl = RegionUtility2.getField2();

		// Creating a Grid Pane
		final GridPane gridPane = new GridPane();
		// Setting the padding
		gridPane.setPadding(new Insets(5, 10, 5, 10));
		// Setting the vertical and horizontal gaps between the columns
		gridPane.setVgap(1);
		gridPane.setHgap(15);
		// Setting the Grid alignment
		gridPane.setAlignment(Pos.BASELINE_LEFT);
		// Arranging all the nodes in the grid
		gridPane.add(label, 0, 0);
		gridPane.add(picker, 1, 0);
		gridPane.add(label1, 2, 0);
		gridPane.add(comboBox, 3, 0);
		gridPane.add(label2, 4, 0);
		gridPane.add(textField2, 5, 0);
		gridPane.add(labelL, 6, 0);
		gridPane.add(textFieldL, 7, 0);
		gridPane.add(labelT, 8, 0);
		gridPane.add(textFieldT, 9, 0);
		gridPane.setMinSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);
		// Creating a Grid Pane
		final GridPane gridPane2 = new GridPane();
		// Setting size for the pane
		gridPane2.setMinSize(50, 50);
		// Setting the padding
		gridPane2.setPadding(new Insets(5, 10, 5, 10));
		// Setting the vertical and horizontal gaps between the columns
		gridPane2.setVgap(1);
		gridPane2.setHgap(7);
		// Setting the Grid alignment
		gridPane2.setAlignment(Pos.BASELINE_LEFT);
		// Arranging all the nodes in the grid
		gridPane2.add(labelP, 0, 1);
		gridPane2.add(textFieldP, 1, 1);
		// Arranging all the nodes in the grid
		gridPane2.add(labelPS, 2, 1);
		gridPane2.add(textFieldPS, 3, 1);
		gridPane2.setMinSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);

		final ToggleGroup group = new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Toggle> over, Toggle oldToggle, Toggle newToggle) {
				// Has selection.
				if (group.getSelectedToggle() != null) {
					final RadioButton button = (RadioButton) RegionUtility.getToggle(group);
					if (Constant.FHIR.equals(button.getText())) {
						textFieldUrl.setText(Constant.TEXTFIELDURL2);
					} else {
						textFieldUrl.setText(Constant.TEXTFIELDURL);
					}
				}
			}
		});
		final RadioButton radio1 = new RadioButton(Constant.ESANTE);
		radio1.setToggleGroup(group);
		radio1.setSelected(true);
		final RadioButton radio2 = new RadioButton(Constant.FHIR);
		radio2.setToggleGroup(group);

		// Creating a Grid Pane
		final GridPane gridPane3 = new GridPane();
		// Setting the padding
		gridPane3.setPadding(new Insets(5, 10, 5, 10));
		// Setting the vertical and horizontal gaps between the columns
		gridPane3.setVgap(1);
		gridPane3.setHgap(10);
		// Setting the Grid alignment
		gridPane3.setAlignment(Pos.BASELINE_LEFT);
		// Arranging all the nodes in the grid
		gridPane3.add(labelUrl, 0, 2);
		gridPane3.add(textFieldUrl, 1, 2);
		gridPane3.add(radio1, 2, 2);
		gridPane3.add(radio2, 3, 2);
		gridPane3.setMinSize(GridPane.USE_PREF_SIZE, GridPane.USE_PREF_SIZE);

		final TitledPane titledPane = new TitledPane();
		final TitledPane titledPaneOther = new TitledPane();
		itemtools2.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				if (new File(textFieldP.getText()).exists()) {
					FileUtility.openFile(new File(textFieldP.getText()));
				} else {
					final Alert alert = new Alert(AlertType.ERROR);
					final DialogPane dialogPane = alert.getDialogPane();
					JavaFxUtility.newObsListDPane(dialogPane)
							.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
					JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
					dialogPane.setMinHeight(130);
					dialogPane.setMaxHeight(130);
					dialogPane.setPrefHeight(130);
					alert.setHeaderText(null);
					alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
					alert.setContentText(INUtility.get("popup.error4"));
					alert.showAndWait();
				}
				accord.setExpandedPane(null);
			}
		});

		final Button chooseFile = INUtility.buttonForKey(Constant.CHOOSEFILE);
		final Button chooseFileOther = INUtility.buttonForKey(Constant.CHOOSEFILE);
		final TitledPane firstTitledPane = new TitledPane();
		final Label labelContent = INUtility.labelForValue(() -> INUtility.get("popup.information"));

		buttonTermino.setStyle(Constant.STYLE1);
		buttonTermino.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				thirdStage = new Stage();
				final VBox root = new VBox();
				root.setPadding(new Insets(10));
				root.setSpacing(5);
				labelLog.setPadding(new Insets(5, 5, 5, 5));
				labelLog.setStyle(Constant.STYLE5);
				labelPwd.setPadding(new Insets(5, 5, 5, 5));
				labelPwd.setStyle(Constant.STYLE5);
				textLogin.setPadding(new Insets(5, 5, 5, 5));
				textLogin.setStyle(Constant.STYLE8);
				textPwd.setPadding(new Insets(5, 5, 5, 5));
				textPwd.setStyle(Constant.STYLE8);
				// create a tile pane
				final HBox rbox = new HBox();
				rbox.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
				final List<String> matchingKey = new ArrayList<>();
				final Map<String, String> map = new ConcurrentHashMap<>();
				final Map<String, String> map1 = new ConcurrentHashMap<>();
				try (InputStream input = NodeUtility2.newClassLoader().getResourceAsStream(Constant.RDFFILENAME)) {
					final Properties prop = new Properties();
					if (input == null && LOG.isInfoEnabled()) {
						final String error = INUtility.get(Constant.SELECT);
						LOG.error(error);
					}
					// load a properties file from class path, inside static method
					prop.load(input);
					// get the property value and print it out
					final Pattern patt = Pattern.compile(Constant.SAMPLEPATTERN);
					for (final Entry<Object, Object> each : prop.entrySet()) {
						final Matcher matcher = patt.matcher((String) each.getKey());
						if (matcher.find()) {
							final String[] words = ((String) each.getKey()).split(Constant.SAMPLEPATTERN2);
							matchingKey.add(words[1]);
							map.put(words[1], (String) each.getValue());
						}
					}
					tokenurl = prop.getProperty(Constant.TOKENURL).trim();
					downloadurl = prop.getProperty(Constant.DOWNLOADURL).trim();
					tokenopen = prop.getProperty(Constant.TOKENOPEN).trim();

				} catch (final IOException ex) {
					if (LOG.isInfoEnabled()) {
						final String error = ex.getMessage();
						LOG.error(error);
					}
				}
				titledPane.setText(Constant.TAASIP);
				titledPane.setPadding(new Insets(5, 5, 5, 5));
				titledPane.setStyle(Constant.STYLE5);

				titledPaneOther.setText(INUtility.get("button.closeevent.text"));
				titledPaneOther.setPadding(new Insets(5, 5, 5, 5));
				titledPaneOther.setStyle(Constant.STYLE5);

				final VBox content = new VBox();
				lContent.setPadding(new Insets(5, 5, 5, 5));
				lContent.setStyle(Constant.STYLE5);
				lContentOther.setPadding(new Insets(5, 5, 5, 5));
				lContentOther.setStyle(Constant.STYLE5);

				final HBox box = new HBox();
				box.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
				field.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
				fieldOther.setMinSize(TextField.USE_PREF_SIZE, TextField.USE_PREF_SIZE);
				fieldOther.setStyle(Constant.STYLE5);
				fieldOther.setDisable(true);

				final Accordion accordioneOther = new Accordion();
				final FileChooser fileChooserOther = new FileChooser();
				fileChooserOther.titleProperty().bind(INUtility.createStringBinding(Constant.CHOOSEFILE));
				JavaFxUtility.newExtFilter(fileChooserOther)
						.addAll(new ExtensionFilter(INUtility.get("button.closetxt.text"), Constant.ALLRDF));
				chooseFileOther.setPrefWidth(150);
				chooseFileOther.setPrefHeight(30);
				chooseFileOther.setMinHeight(30);
				chooseFileOther.setMaxHeight(30);
				chooseFileOther.setStyle(Constant.STYLE1);

				chooseFileOther.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event) {
						// Opening a dialog box
						final List<File> file = fileChooserOther.showOpenMultipleDialog(thirdStage);
						if (file != null) {
							fieldOther.setText(file.get(0).getAbsolutePath());
							titledPaneOther.setExpanded(false);
						}
					}
				});

				final Accordion accordione = new Accordion();
				chooseFile.setPrefWidth(150);
				chooseFile.setPrefHeight(30);
				chooseFile.setMinHeight(30);
				chooseFile.setMaxHeight(30);
				chooseFile.setStyle(Constant.STYLE1);
				chooseFile.setOnAction(new EventHandler<>() {
					@Override
					public void handle(ActionEvent event) {
						// Opening a dialog box
						final List<File> file = fileChooser.showOpenMultipleDialog(thirdStage);
						if (file != null) {
							field.setText(file.get(0).getAbsolutePath());
							titledPane.setExpanded(false);
							accordione.setDisable(true);
						}
					}
				});

				final Region spacere = RegionUtility.getRegion1();
				NodeUtility2.newHObsList(box).addAll(chooseFile, spacere, field);
				final FileChooser fileChooser = new FileChooser();
				fileChooser.titleProperty().bind(INUtility.createStringBinding(Constant.CHOOSEFILE));
				JavaFxUtility.newExtFilter(fileChooser).addAll(
						new ExtensionFilter(INUtility.get("button.closetxt.text"), Constant.ALLXLSX, Constant.ALLXLSM));
				NodeUtility2.newObsList(content).addAll(lContent, box);
				titledPane.setContent(content);
				NodeUtility2.newTitledPane(accordione).addAll(titledPane);
				accordione.setDisable(true);
				NodeUtility2.newTitledPane(accordioneOther).addAll(titledPaneOther);
				for (final String name : matchingKey) {
					final CheckBox check = NodeUtility.newCheck(name);
					check.setStyle(Constant.STYLE20);
					final Region spacer = RegionUtility.getRegion1();
					NodeUtility2.newHObsList(rbox).addAll(check, spacer);
					check.setOnAction(e -> {
						if (check.isSelected()) {
							selectedList.add(name);
							if (name.contains(Constant.ASIP)) {
								accordione.setDisable(false);
								titledPane.setExpanded(true);
							}
						} else {
							selectedList.remove(name);
							if (name.contains(Constant.ASIP)) {
								accordione.setDisable(true);
								titledPane.setExpanded(false);
								field.setText("");
							}
						}
					});
				}
				buttonTermino1.setMinSize(Button.USE_PREF_SIZE, Button.USE_PREF_SIZE);
				buttonTermino1.setStyle(Constant.STYLE1);

				final HBox resultButton = new HBox();
				resultButton.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
				NodeUtility2.newHObsList(resultButton).addAll(buttonTermino1);
				resultButton.setAlignment(Pos.CENTER);
				final Region spacer4 = ImageUtility2.getRegion1();
				firstTitledPane.setText(INUtility.get("button.quit.text"));
				firstTitledPane.setPadding(new Insets(5, 5, 5, 5));
				firstTitledPane.setStyle(Constant.STYLE5);

				final VBox content1 = new VBox();
				NodeUtility2.newObsList(content1).add(labelContent);
				labelContent.setPadding(new Insets(5, 5, 5, 5));
				labelContent.setStyle(Constant.STYLE5);

				firstTitledPane.setContent(content1);

				final VBox contentOther = new VBox();
				lContentOther.setPadding(new Insets(5, 5, 5, 5));
				lContentOther.setStyle(Constant.STYLE5);

				final HBox boxOther = new HBox();
				boxOther.setMinSize(HBox.USE_PREF_SIZE, HBox.USE_PREF_SIZE);
				final Region spaceree = RegionUtility.getRegion1();
				NodeUtility2.newHObsList(boxOther).addAll(chooseFileOther, spaceree, fieldOther);
				NodeUtility2.newObsList(contentOther).addAll(lContentOther, boxOther);
				titledPaneOther.setContent(contentOther);

				final Accordion accordion = new Accordion();
				NodeUtility2.newTitledPane(accordion).addAll(firstTitledPane);

				final Region spacer5 = ImageUtility2.getRegion2();

				final Region spacer6 = ImageUtility2.getRegion2();

				final Region spacer7 = ImageUtility2.getRegion2();

				NodeUtility2.newObsList(root).addAll(labelLog, textLogin, labelPwd, textPwd, rbox, spacer4,
						resultButton, spacer5, accordione, spacer6, accordioneOther, spacer7, accordion);
				final Scene scene = new Scene(root, 900, 650);
				thirdStage.titleProperty().bind(INUtility.createStringBinding("button.terminologies"));
				thirdStage.setScene(scene);
				thirdStage.setMaximized(false);
				thirdStage.show();
				buttonTermino1.setOnAction(new EventHandler<>() {
					@Override
					public void handle(final ActionEvent event) {
						Utility.runTask(taskUpdateStage, progress);
						Platform.runLater(() -> {
							final List<String> listTermino = new ArrayList<>();
							String text = "";
							final String pathToGenS = textFieldPS.getText();
							final Path pathsS = Paths.get(pathToGenS);
							if (!pathsS.toFile().exists()) {
								try {
									Files.createDirectories(pathsS.toFile().toPath());
								} catch (final IOException e) {
									if (LOG.isInfoEnabled()) {
										final String error = e.getMessage();
										LOG.error(error);
									}
								}
							}
							final String outputDi = textFieldPS.getText() + Constant.TERMINOLOGIE;
							final Path path = Paths.get(outputDi);
							if (!path.toFile().exists()) {
								try {
									Files.createDirectories(path.toFile().toPath());
								} catch (final IOException e) {
									if (LOG.isInfoEnabled()) {
										final String error = e.getMessage();
										LOG.error(error);
									}
								}
							}

							if (textLogin.getText().isEmpty() && textPwd.getText().isEmpty()) {
								count = 1;
							} else {
								final String str = Utility.getFirstToken(tokenopen, textLogin.getText(),
										textPwd.getText());
								if (str == null) {
									count = 0;
								} else {
									count = 1;
								}
							}
							if (NumberUtils.compare(count, 1) == 0) {
								// TA-ASIP
								if (selectedList.size() == 1 && !field.getText().isEmpty()) {
									passed = true;
									final String ext = FileUtility.getExtension(field.getText());
									if (!field.getText().isEmpty()
											&& (Constant.XLSM.equals(ext) || Constant.XLSX.equals(ext))) {
										// Create a Workbook instance
										final Workbook workbookRdf = new Workbook();
										// Load an Excel file
										workbookRdf.loadFromFile(field.getText());
										Worksheet worksheet = null;
										for (final Object sheet : workbookRdf.getWorksheets()) {
											final String sheetName = ((Worksheet) sheet).getName();
											if (sheetName.equalsIgnoreCase(Constant.VOCANS)) {
												worksheet = ((Worksheet) sheet);
											}
										}
										final List<RetrieveValueSet> listR = new ArrayList<>();
										// Get the row count
										final int maxRow = FileUtility.getLastRow(worksheet);
										// Loop through the rows
										for (int row = 2; row <= maxRow; row++) {
											final RetrieveValueSet response = NodeUtility.newRetrieveValueSet();
											// Loop through the columns
											// Get the current cell
											final CellRange cell = NodeUtility2.newCellRange1(worksheet, row);
											final CellRange cell1 = NodeUtility.newCellRange(worksheet, row);
											response.setValueSetOID(cell.getValue());
											response.setObsolete(cell1.getValue());
											if (response.getValueSetOID() != null
													&& !Constant.OUI.equalsIgnoreCase(response.getObsolete())) {
												listR.add(response);
											}
										}
										if (listR != null && !listR.isEmpty()) {
											final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
											DocumentBuilder builder;
											try {
												builder = dbf.newDocumentBuilder();
												final Document doc = builder.newDocument();
												String fileDestName = null;
												final Element newNode = doc.createElement(Constant.RDF_RDF);
												doc.appendChild(newNode);
												newNode.setAttribute("xmlns:rdf",
														"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
												newNode.setAttribute("xmlns:schema", "https://schema.org/");
												newNode.setAttribute("xmlns:voaf", "http://purl.org/vocommons/voaf#");
												newNode.setAttribute("xmlns:rdfs",
														"http://www.w3.org/2000/01/rdf-schema#");
												newNode.setAttribute("xmlns:metadata", "http://topbraid.org/metadata#");
												newNode.setAttribute("xmlns:icd", "http://id.who.int/icd/schema/");
												newNode.setAttribute("xmlns:terms", "http://purl.org/dc/terms/");
												newNode.setAttribute("xmlns:adms", "http://www.w3.org/ns/adms#");
												newNode.setAttribute("xmlns:core", "http://open-services.net/ns/core#");
												newNode.setAttribute("xmlns:j.0", "http://topbraid.org/swa#");
												newNode.setAttribute("xmlns:owl", "http://www.w3.org/2002/07/owl#");
												newNode.setAttribute("xmlns:skos",
														"http://www.w3.org/2004/02/skos/core#");
												newNode.setAttribute("xmlns:teamwork", "http://topbraid.org/teamwork#");
												newNode.setAttribute("xmlns:j.1",
														"http://teamwork.topbraidlive.org/ontologyprojects#");
												newNode.setAttribute("xmlns:dcat", "http://www.w3.org/ns/dcat#");
												newNode.setAttribute("xmlns:ns",
														"https://data.esante.gouv.fr/profile/ns#");
												newNode.setAttribute("xmlns:foaf", "http://xmlns.com/foaf/0.1/");
												newNode.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");

												try (InputStream input = NodeUtility2.newClassLoader()
														.getResourceAsStream(Constant.RDFFILENAME)) {

													final Properties prop = new Properties();
													if (input == null && LOG.isInfoEnabled()) {
														final String error = INUtility.get(Constant.SELECT);
														LOG.error(error);
													}
													// load a properties file from class path, inside static method
													prop.load(input);
													// get the property value and print it out
													final Pattern patt = Pattern.compile(Constant.NAMEPATTERN);
													for (final Entry<Object, Object> each : prop.entrySet()) {
														final Matcher match = patt.matcher((String) each.getKey());
														if (match.find()) {
															final String[] words = ((String) each.getKey())
																	.split(Constant.NAMEPATTERN2);
															matchingKey.add(words[1]);
															map1.put(words[1], (String) each.getValue());
														}
													}
												} catch (final IOException ex) {
													if (LOG.isInfoEnabled()) {
														final String error = ex.getMessage();
														LOG.error(error);
													}
												}

												for (final RetrieveValueSet str : listR) {
													Element thirdNode;
													thirdNode = doc.createElement(Constant.DESCRIPTION);
													newNode.appendChild(thirdNode);
													Element secondNode;
													secondNode = doc.createElement(Constant.NOTATION);
													secondNode.setTextContent(str.getValueSetOID());
													thirdNode.appendChild(secondNode);
													doc.normalize();
													for (final Map.Entry<String, String> mapentry : map1.entrySet()) {
														if (Constant.NAMETAASIP
																.contains((CharSequence) mapentry.getKey())) {
															fileDestName = mapentry.getValue();
															break;
														}
													}
												}
												final File xml = NodeUtility.prettyPrint(doc,
														FileUtility.newFileName(textFieldPS.getText()),
														fileDestName.trim());
												listTermino.add(xml.getAbsolutePath());
												workbookRdf.dispose();
											} catch (final ParserConfigurationException e) {
												if (LOG.isInfoEnabled()) {
													final String error = e.getMessage();
													LOG.error(error);
												}
											}
										}
									}
								} else if (!textLogin.getText().isEmpty() && !textPwd.getText().isEmpty()
										&& !selectedList.isEmpty() && field.getText().isEmpty()) {
									passed = true;
									List<File> fileRdf;
									final TerminologyDownloader downloader = new TerminologyDownloader();
									final String outputD = textFieldPS.getText() + Constant.TERMINOLOGIE;
									final Terminology terminology = new Terminology();
									terminology.setTextLogin(textLogin.getText());
									terminology.setTextPwd(textPwd.getText());
									terminology.setListTerminology(selectedList);
									terminology.setMap(map);
									terminology.setTokenurl(tokenurl);
									terminology.setDownloadurl(downloadurl);
									terminology.setTokenopen(tokenopen);
									terminology.setOutputD(outputD);
									fileRdf = downloader.main(terminology);
									try (InputStream input = NodeUtility2.newClassLoader()
											.getResourceAsStream(Constant.RDFFILENAME)) {
										final Properties prop = new Properties();
										if (input == null && LOG.isInfoEnabled()) {
											final String error = INUtility.get(Constant.SELECT);
											LOG.error(error);
										}
										// load a properties file from class path, inside static method
										prop.load(input);
										// get the property value and print it out
										final Pattern patt = Pattern.compile(Constant.NAMEPATTERN);
										for (final Entry<Object, Object> each : prop.entrySet()) {
											final Matcher matcher = patt.matcher((String) each.getKey());
											if (matcher.find()) {
												final String[] words = ((String) each.getKey())
														.split(Constant.NAMEPATTERN2);
												matchingKey.add(words[1]);
												map1.put(words[1], (String) each.getValue());
											}
										}
									} catch (final IOException ex) {
										if (LOG.isInfoEnabled()) {
											final String error = ex.getMessage();
											LOG.error(error);
										}
									}

									if (fileRdf != null) {
										if (fileRdf.isEmpty()) {
											textLogin.setText("");
											textPwd.setText("");
										} else {
											for (final File file : fileRdf) {
												if (Constant.RDF
														.equals(FileUtility.getExtension(file.getAbsolutePath()))) {
													List<String> listStr;
													final String fileName = FileUtility.getFileName(file).trim();
													String fileDestName = null;
													for (final Map.Entry<String, String> mapentry : map1.entrySet()) {
														if (fileName.toUpperCase(Locale.FRENCH)
																.contains((CharSequence) mapentry.getKey())) {
															fileDestName = mapentry.getValue();
															break;
														}
													}
													try {
														listStr = parse(file.getAbsolutePath());
														final DocumentBuilderFactory documentFactory = DocumentBuilderFactory
																.newInstance();
														final DocumentBuilder documentBuilder = documentFactory
																.newDocumentBuilder();
														final Document document = documentBuilder.newDocument();
														// root element
														final Element root = document.createElement(Constant.RDF_RDF);
														document.appendChild(root);
														if (!mapSnomed.isEmpty()) {
															for (final Map.Entry<String, String> mapentry : mapSnomed
																	.entrySet()) {
																final String key = mapentry.getKey();
																final String value = mapentry.getValue();
																final Attr attr = document.createAttribute(key);
																attr.setValue(value);
																root.setAttributeNode(attr);
															}
														}
														// notation element
														if (listStr != null && !listStr.isEmpty()) {
															for (final String str : listStr) {
																// description element
																final Element description = document
																		.createElement(Constant.DESCRIPTION);
																root.appendChild(description);
																final Element notation = document
																		.createElement(Constant.NOTATION);
																notation.appendChild(document.createTextNode(str));
																description.appendChild(notation);
															}
															document.normalize();
															if (fileDestName != null) {
																final File xml = NodeUtility.prettyPrint(document,
																		FileUtility.newFileName(textFieldPS.getText()),
																		fileDestName.trim());
																listTermino.add(xml.getAbsolutePath());
															}

														}

													} catch (final IOException | XMLStreamException
															| ParserConfigurationException e) {
														if (LOG.isInfoEnabled()) {
															final String error = e.getMessage();
															LOG.error(error);
														}
													}
												}
											}
										}
									} else {
										textLogin.setText("");
										textPwd.setText("");
									}
								} else if (!textLogin.getText().isEmpty() && !textPwd.getText().isEmpty()
										&& !selectedList.isEmpty() && !field.getText().isEmpty()) {
									passed = true;
									try (InputStream input = NodeUtility2.newClassLoader()
											.getResourceAsStream(Constant.RDFFILENAME)) {
										final Properties prop = new Properties();
										if (input == null && LOG.isInfoEnabled()) {
											final String error = INUtility.get(Constant.SELECT);
											LOG.error(error);
										}
										// load a properties file from class path, inside static method
										prop.load(input);
										// get the property value and print it out
										final Pattern patt = Pattern.compile(Constant.NAMEPATTERN);
										for (final Entry<Object, Object> each : prop.entrySet()) {
											final Matcher match = patt.matcher((String) each.getKey());
											if (match.find()) {
												final String[] words = ((String) each.getKey())
														.split(Constant.NAMEPATTERN2);
												matchingKey.add(words[1]);
												map1.put(words[1], (String) each.getValue());
											}
										}
									} catch (final IOException ex) {
										if (LOG.isInfoEnabled()) {
											final String error = ex.getMessage();
											LOG.error(error);
										}
									}
									final String ext = FileUtility.getExtension(field.getText());
									if (!field.getText().isEmpty()
											&& (Constant.XLSM.equals(ext) || Constant.XLSX.equals(ext))) {
										// Create a Workbook instance
										final Workbook workbookRdf = new Workbook();
										// Load an Excel file
										workbookRdf.loadFromFile(field.getText());
										Worksheet worksheet = null;
										for (final Object sheet : workbookRdf.getWorksheets()) {
											final String sheetName = ((Worksheet) sheet).getName();
											if (sheetName.equalsIgnoreCase(Constant.VOCANS)) {
												worksheet = ((Worksheet) sheet);
											}
										}
										final List<RetrieveValueSet> listR = NodeUtility.newArrayListValue();
										// Get the row count
										final int maxRow = worksheet.getLastRow();
										// Loop through the rows
										for (int row = 2; row <= maxRow; row++) {
											final RetrieveValueSet response = NodeUtility.newRetrieveValueSet();
											// Loop through the columns
											final CellRange cell = NodeUtility2.newCellRange1(worksheet, row);
											final CellRange cell1 = NodeUtility.newCellRange(worksheet, row);
											response.setValueSetOID(cell.getValue());
											response.setObsolete(cell1.getValue());
											if (response.getValueSetOID() != null
													&& !Constant.OUI.equalsIgnoreCase(response.getObsolete())) {
												listR.add(response);
											}
										}
										if (listR != null && !listR.isEmpty()) {
											final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
											DocumentBuilder builder;
											try {
												builder = dbf.newDocumentBuilder();
												final Document doc = builder.newDocument();
												String fileDestName = null;
												final Element newNode = doc.createElement(Constant.RDF_RDF);
												doc.appendChild(newNode);
												newNode.setAttribute("xmlns:rdf",
														"http://www.w3.org/1999/02/22-rdf-syntax-ns#");
												newNode.setAttribute("xmlns:schema", "https://schema.org/");
												newNode.setAttribute("xmlns:voaf", "http://purl.org/vocommons/voaf#");
												newNode.setAttribute("xmlns:rdfs",
														"http://www.w3.org/2000/01/rdf-schema#");
												newNode.setAttribute("xmlns:metadata", "http://topbraid.org/metadata#");
												newNode.setAttribute("xmlns:icd", "http://id.who.int/icd/schema/");
												newNode.setAttribute("xmlns:terms", "http://purl.org/dc/terms/");
												newNode.setAttribute("xmlns:adms", "http://www.w3.org/ns/adms#");
												newNode.setAttribute("xmlns:core", "http://open-services.net/ns/core#");
												newNode.setAttribute("xmlns:j.0", "http://topbraid.org/swa#");
												newNode.setAttribute("xmlns:owl", "http://www.w3.org/2002/07/owl#");
												newNode.setAttribute("xmlns:skos",
														"http://www.w3.org/2004/02/skos/core#");
												newNode.setAttribute("xmlns:teamwork", "http://topbraid.org/teamwork#");
												newNode.setAttribute("xmlns:j.1",
														"http://teamwork.topbraidlive.org/ontologyprojects#");
												newNode.setAttribute("xmlns:dcat", "http://www.w3.org/ns/dcat#");
												newNode.setAttribute("xmlns:ns",
														"https://data.esante.gouv.fr/profile/ns#");
												newNode.setAttribute("xmlns:foaf", "http://xmlns.com/foaf/0.1/");
												newNode.setAttribute("xmlns:xsd", "http://www.w3.org/2001/XMLSchema#");
												for (final RetrieveValueSet str : listR) {
													Element thirdNode;
													thirdNode = doc.createElement(Constant.DESCRIPTION);
													newNode.appendChild(thirdNode);
													Element secondNode;
													secondNode = doc.createElement(Constant.NOTATION);
													secondNode.setTextContent(str.getValueSetOID());
													thirdNode.appendChild(secondNode);
													doc.normalize();
													for (final Map.Entry<String, String> mapentry : map1.entrySet()) {
														if (Constant.NAMETAASIP
																.contains((CharSequence) mapentry.getKey())) {
															fileDestName = mapentry.getValue();
															break;
														}
													}
												}
												final File xml = NodeUtility.prettyPrint(doc,
														FileUtility.newFileName(textFieldPS.getText()),
														fileDestName.trim());
												listTermino.add(xml.getAbsolutePath());

											} catch (final ParserConfigurationException e) {
												if (LOG.isInfoEnabled()) {
													final String error = e.getMessage();
													LOG.error(error);
												}
											}
										}
										workbookRdf.dispose();
									}

									List<File> fileRdf;
									final TerminologyDownloader downloader = new TerminologyDownloader();
									final String outputD = textFieldPS.getText() + Constant.TERMINOLOGIE;
									final Terminology terminology = new Terminology();
									terminology.setTextLogin(textLogin.getText());
									terminology.setTextPwd(textPwd.getText());
									terminology.setListTerminology(selectedList);
									terminology.setMap(map);
									terminology.setTokenurl(tokenurl);
									terminology.setDownloadurl(downloadurl);
									terminology.setTokenopen(tokenopen);
									terminology.setOutputD(outputD);
									fileRdf = downloader.main(terminology);

									try (InputStream input = NodeUtility2.newClassLoader()
											.getResourceAsStream(Constant.RDFFILENAME)) {
										final Properties prop = new Properties();
										if (input == null && LOG.isInfoEnabled()) {
											final String error = INUtility.get(Constant.SELECT);
											LOG.error(error);
										}
										// load a properties file from class path, inside static method
										prop.load(input);
										// get the property value and print it out
										final Pattern patt = Pattern.compile(Constant.NAMEPATTERN);
										for (final Entry<Object, Object> each : prop.entrySet()) {
											final Matcher match = patt.matcher((String) each.getKey());
											if (match.find()) {
												final String[] words = ((String) each.getKey())
														.split(Constant.NAMEPATTERN2);
												matchingKey.add(words[1]);
												map1.put(words[1], (String) each.getValue());
											}
										}
									} catch (final IOException ex) {
										if (LOG.isInfoEnabled()) {
											final String error = ex.getMessage();
											LOG.error(error);
										}
									}
									if (fileRdf != null) {
										if (fileRdf.isEmpty()) {
											textLogin.setText("");
											textPwd.setText("");

										} else {
											for (final File file : fileRdf) {
												if (Constant.RDF
														.equals(FileUtility.getExtension(file.getAbsolutePath()))) {
													List<String> listStr;
													final String fileName = FileUtility.getFileName(file).trim();
													String fileDestName = null;
													for (final Map.Entry<String, String> mapentry : map1.entrySet()) {
														if (fileName.toUpperCase(Locale.FRENCH)
																.contains((CharSequence) mapentry.getKey())) {
															fileDestName = mapentry.getValue();
															break;
														}
													}
													try {
														listStr = parse(file.getAbsolutePath());
														final DocumentBuilderFactory documentFactory = DocumentBuilderFactory
																.newInstance();
														final DocumentBuilder documentBuilder = documentFactory
																.newDocumentBuilder();
														final Document document = documentBuilder.newDocument();
														// root element
														final Element root = document.createElement(Constant.RDF_RDF);
														document.appendChild(root);
														if (!mapSnomed.isEmpty()) {
															for (final Map.Entry<String, String> mapentry : mapSnomed
																	.entrySet()) {
																final String key = mapentry.getKey();
																final String value = mapentry.getValue();
																final Attr attr = document.createAttribute(key);
																attr.setValue(value);
																root.setAttributeNode(attr);
															}
														}
														// notation element
														if (listStr != null && !listStr.isEmpty()) {
															for (final String str : listStr) {
																// description element
																final Element description = document
																		.createElement(Constant.DESCRIPTION);
																root.appendChild(description);
																final Element notation = document
																		.createElement(Constant.NOTATION);
																notation.appendChild(document.createTextNode(str));
																description.appendChild(notation);
															}
															document.normalize();
															final File xml = NodeUtility.prettyPrint(document,
																	FileUtility.newFileName(textFieldPS.getText()),
																	fileDestName.trim());
															listTermino.add(xml.getAbsolutePath());
														}

													} catch (final ParserConfigurationException | IOException
															| XMLStreamException e) {
														if (LOG.isInfoEnabled()) {
															final String error = e.getMessage();
															LOG.error(error);
														}
													}
												}
											}
										}
									} else {
										textLogin.setText("");
										textPwd.setText("");
									}
								}
								// Importer terminologie
								if (!fieldOther.getText().isEmpty()) {
									passed = true;
									final List<File> fileRdf = new ArrayList<>();
									final File rdfFile = new File(fieldOther.getText());
									fileRdf.add(rdfFile);
									try (InputStream input = NodeUtility2.newClassLoader()
											.getResourceAsStream(Constant.RDFFILENAME)) {
										final Properties prop = new Properties();
										if (input == null && LOG.isInfoEnabled()) {
											final String error = INUtility.get(Constant.SELECT);
											LOG.error(error);
										}
										// load a properties file from class path, inside static method
										prop.load(input);
										// get the property value and print it out
										final Pattern patt = Pattern.compile(Constant.NAMEPATTERN);
										for (final Entry<Object, Object> each : prop.entrySet()) {
											final Matcher match = patt.matcher((String) each.getKey());
											if (match.find()) {
												final String[] words = ((String) each.getKey())
														.split(Constant.NAMEPATTERN2);
												matchingKey.add(words[1]);
												map1.put(words[1], (String) each.getValue());
											}
										}
									} catch (final IOException ex) {
										if (LOG.isInfoEnabled()) {
											final String error = ex.getMessage();
											LOG.error(error);
										}
									}
									if (fileRdf != null && !fileRdf.isEmpty()) {
										for (final File file : fileRdf) {
											if (Constant.RDF.equals(FileUtility.getExtension(file.getAbsolutePath()))) {
												List<String> listStr;
												final String fileName = FileUtility.getFileName(file).trim();
												String fileDestName = null;
												for (final Map.Entry<String, String> mapentry : map1.entrySet()) {
													if (fileName.toUpperCase(Locale.FRENCH)
															.contains((CharSequence) mapentry.getKey())) {
														fileDestName = mapentry.getValue();
														break;
													}
												}
												try {
													listStr = parse(file.getAbsolutePath());
													final DocumentBuilderFactory documentFactory = DocumentBuilderFactory
															.newInstance();
													final DocumentBuilder documentBuilder = documentFactory
															.newDocumentBuilder();
													final Document document = documentBuilder.newDocument();
													// root element
													final Element root = document.createElement(Constant.RDF_RDF);
													document.appendChild(root);
													if (!mapSnomed.isEmpty()) {
														for (final Map.Entry<String, String> mapentry : mapSnomed
																.entrySet()) {
															final String key = mapentry.getKey();
															final String value = mapentry.getValue();
															final Attr attr = document.createAttribute(key);
															attr.setValue(value);
															root.setAttributeNode(attr);
														}
													}
													// notation element
													if (listStr != null && !listStr.isEmpty()) {
														for (final String str : listStr) {
															// description element
															final Element description = document
																	.createElement(Constant.DESCRIPTION);
															root.appendChild(description);
															final Element notation = document
																	.createElement(Constant.NOTATION);
															notation.appendChild(document.createTextNode(str));
															description.appendChild(notation);
														}
														document.normalize();
														final File xml = NodeUtility.prettyPrint(document,
																FileUtility.newFileName(textFieldPS.getText()),
																fileDestName.trim());
														listTermino.add(xml.getAbsolutePath());

													}
												} catch (final ParserConfigurationException | IOException
														| XMLStreamException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}
										}

									}
								}
								if (passed) {
									final Alert alert = new Alert(AlertType.INFORMATION);
									final DialogPane dialogPane = alert.getDialogPane();
									JavaFxUtility.newObsListDPane(dialogPane).add(NodeUtility2.newClassLoader()
											.getResource(Constant.STYLE0).toExternalForm());
									JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
									dialogPane.setMinHeight(300);
									dialogPane.setMaxHeight(300);
									dialogPane.setPrefHeight(300);
									dialogPane.setMinWidth(500);
									dialogPane.setMaxWidth(500);
									dialogPane.setPrefWidth(500);
									alert.setHeaderText(null);
									alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
									if (listTermino != null && !listTermino.isEmpty()) {
										final StringBuffer sbuffer = new StringBuffer();
										for (final String termino : listTermino) {
											sbuffer.append(termino).append(Constant.RETOURCHARIOT);
										}
										text = sbuffer.toString();

									}
									alert.setContentText(INUtility.get("button.cancel.text") + Constant.RETOURCHARIOT
											+ Constant.RETOURCHARIOT + text);
									alert.showAndWait();
									textLogin.setText("");
									textPwd.setText("");
									final String outputDir = textFieldPS.getText() + Constant.TERMINOLOGIE;
									fieldOther.setText("");
									field.setText("");
									selectedList = new ArrayList<>();
									count = 0;
									passed = false;
									thirdStage.close();
									FileUtility.deleteDirectory(new File(outputDir));
								} else {
									final Alert alert = new Alert(AlertType.ERROR);
									final DialogPane dialogPane = alert.getDialogPane();
									JavaFxUtility.newObsListDPane(dialogPane).add(NodeUtility2.newClassLoader()
											.getResource(Constant.STYLE0).toExternalForm());
									JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
									dialogPane.setMinHeight(150);
									dialogPane.setMaxHeight(150);
									dialogPane.setPrefHeight(150);
									alert.setHeaderText(null);
									alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
									alert.setContentText(INUtility.get("popup.error8"));
									alert.showAndWait();
									count = 0;
									passed = false;
								}
							} else {
								final Alert alert = new Alert(AlertType.ERROR);
								final DialogPane dialogPane = alert.getDialogPane();
								JavaFxUtility.newObsListDPane(dialogPane).add(
										NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
								JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
								dialogPane.setMinHeight(150);
								dialogPane.setMaxHeight(150);
								dialogPane.setPrefHeight(150);
								alert.setHeaderText(null);
								alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
								alert.setContentText(INUtility.get("popup.error8"));
								alert.showAndWait();
								count = 0;
								passed = false;
							}
						});
					}
				});
				accord.setExpandedPane(null);
			}
		});

		buttonDownload.setStyle(Constant.STYLE1);
		buttonDownload.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				final List<File> listFile = new ArrayList<>();
				final String url = textFieldUrl.getText();
				Utility.runTask(taskUpdateStage, progress);
				Platform.runLater(() -> {
					final String pathToGen = textFieldP.getText();
					final Path paths = Paths.get(pathToGen);
					if (!paths.toFile().getParentFile().exists()) {
						try {
							Files.createDirectories(paths.toFile().getParentFile().toPath());
						} catch (final IOException e) {
							if (LOG.isInfoEnabled()) {
								final String error = e.getMessage();
								LOG.error(error);
							}
						}
					}
					final String pathToGenS = textFieldPS.getText();
					final Path pathsS = Paths.get(pathToGenS);
					if (!pathsS.toFile().exists()) {
						try {
							Files.createDirectories(pathsS.toFile().toPath());
						} catch (final IOException e) {
							if (LOG.isInfoEnabled()) {
								final String error = e.getMessage();
								LOG.error(error);
							}
						}
					}
					final String home = new File(textFieldPS.getText()).getParent();
					final File folder = new File(home + Constant.IMAGE9);
					if (!folder.exists()) {
						final Path path = Paths.get(folder.getPath());
						try {
							Files.createDirectory(path);
						} catch (final IOException e) {
							if (LOG.isInfoEnabled()) {
								final String error = e.getMessage();
								LOG.error(error);
							}
						}
					}
					File file;
					if (url.endsWith(Constant.EXTENSIONZIP)) {
						file = new File(home + Constant.IMAGE9 + Constant.URLFILE);
					} else {
						file = new File(home + Constant.IMAGE9 + Constant.URLFILE2);
					}
					try {
						FileUtility.downloadUsingNIO(url, file.getAbsolutePath());
						isOk = true;
					} catch (final IOException e) {
						if (LOG.isInfoEnabled()) {
							final String error = e.getMessage();
							LOG.error(error);
						}
						final Alert alert = new Alert(AlertType.ERROR);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(150);
						dialogPane.setMaxHeight(150);
						dialogPane.setPrefHeight(150);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.setContentText(INUtility.get("button.error.download"));
						alert.showAndWait();
						isOk = false;
					}
					if (isOk) {
						final String zipFilePath = file.getAbsolutePath();
						final String destFilePath = file.getParent();
						List<File> lfiles;
						List<File> lFiles = new ArrayList<>();
						try {
							if (url.endsWith(Constant.EXTENSIONZIP)) {
								unzip(zipFilePath, destFilePath);
							} else {
								final JSONParser jsonP = new JSONParser();
								try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.toURI()))) {
									final Object obj = jsonP.parse(reader);
									final JSONObject jObject = (JSONObject) obj;
									final JSONArray jArray = (JSONArray) jObject.get("entry");
									lfiles = new ArrayList<>();
									if (jArray != null) {
										for (final Object jObj : jArray) {
											final JSONObject object = (JSONObject) jObj;
											final String fullUrl = (String) object.get("fullUrl");
											final JSONObject resources = (JSONObject) object.get("resource");
											final String name = (String) resources.get("name");
											final File file2 = new File(
													home + Constant.JSONFOLDER + name.concat(Constant.JSON));
											FileUtility.downloadUsingNIO(fullUrl, file2.getAbsolutePath());
											if (file2.getName().startsWith(Constant.JDVFIRST)) {
												lfiles.add(file2);
											}
										}
									}
									if (!lfiles.isEmpty()) {
										for (final File file3 : lfiles) {
											final String str = ConvertJsonToXML.convert(file3.getAbsolutePath(), home);
											listFile.add(new File(str));
										}
										final File[] files = new File[listFile.size()];
										lFiles = sortByNumber(listFile.toArray(files));
									}
								} catch (final IOException | ParseException e) {
									if (LOG.isInfoEnabled()) {
										final String error = e.getMessage();
										LOG.error(error);
									}
								}
								for (final File pathname : lFiles) {
									filesB.add(pathname);
								}
								constructZip();
							}
							hboxVisibility.setVisible(true);
						} catch (final IOException e) {
							if (LOG.isInfoEnabled()) {
								final String error = e.getMessage();
								LOG.error(error);
							}
						} finally {
							file.delete();
						}
					}
				});
				accord.setExpandedPane(null);

			}
		});

		itemtools1.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				Utility.runTask(taskUpdateStage, progress);
				Platform.runLater(() -> {
					if (new File(textFieldP.getText()).exists()) {
						final XmlView view = new XmlView();
						secondStage = view.start(textFieldP.getText());
					} else {
						final Alert alert = new Alert(AlertType.ERROR);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(130);
						dialogPane.setMaxHeight(130);
						dialogPane.setPrefHeight(130);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.setContentText(INUtility.get("popup.error4"));
						alert.showAndWait();
					}
					accord.setExpandedPane(null);
				});
			}
		});

		button3.setStyle(Constant.STYLE1);
		button3.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				isOkGenerate = false;
				files = new ArrayList<>();
				filesB = new ArrayList<>();
				listF = new ArrayList<>();
				listFR = new ArrayList<>();
				finalFiles = new ArrayList<>();
				FileUtility.getItems(list).clear();
				FileUtility.getItems(listB).clear();
				JavaFxUtility2.newIndexModel(list).clearChecks();
				JavaFxUtility2.newIndexModel(listB).clearChecks();
				selectAll.setSelected(false);
				hboxVisibility.setVisible(false);
				accord.setExpandedPane(null);
			}
		});

		button1.setStyle(Constant.STYLE1);
		button1.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				if (finalFiles.isEmpty()) {
					final Alert alert = new Alert(AlertType.ERROR);
					final DialogPane dialogPane = alert.getDialogPane();
					JavaFxUtility.newObsListDPane(dialogPane)
							.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
					JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
					dialogPane.setMinHeight(130);
					dialogPane.setMaxHeight(130);
					dialogPane.setPrefHeight(130);
					alert.setHeaderText(null);
					alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
					alert.setContentText(INUtility.get("popup.error5"));
					alert.showAndWait();
				} else if (!finalFiles.isEmpty() && finalFiles.size() == 1
						&& (Constant.XLSX.equals(FileUtility.getExtension(finalFiles.get(0).getAbsolutePath()))
								|| Constant.XLSM
										.equals(FileUtility.getExtension(finalFiles.get(0).getAbsolutePath())))) {
					final Alert alert = new Alert(AlertType.ERROR);
					final DialogPane dialogPane = alert.getDialogPane();
					JavaFxUtility.newObsListDPane(dialogPane)
							.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
					JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
					dialogPane.setMinHeight(130);
					dialogPane.setMaxHeight(130);
					dialogPane.setPrefHeight(130);
					alert.setHeaderText(null);
					alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
					alert.setContentText(INUtility.get("popup.error5"));
					alert.showAndWait();
				} else {
					Utility.runTask(taskUpdateStage, progress);
					Platform.runLater(() -> {
						final File generated = new File(textFieldP.getText());
						if (generated.exists()) {
							generated.delete();
						}
						Document doc;
						final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
						dbf.setIgnoringElementContentWhitespace(true);
						DocumentBuilder dbuilder = null;
						try {
							dbuilder = dbf.newDocumentBuilder();
						} catch (final ParserConfigurationException e) {
							if (LOG.isInfoEnabled()) {
								final String error = e.getMessage();
								LOG.error(error);
							}
						}
						final Document document = dbuilder.newDocument();
						// add elements to Document
						final Element rootElement = document.createElement(Constant.TERMINOLOGIES);
						// append root element to document
						document.appendChild(rootElement);
						for (final File file : finalFiles) {
							if (Constant.XML.equals(FileUtility.getExtension(file.getAbsolutePath()))) {
								try (InputStream input = Files.newInputStream(Paths.get(file.toURI()))) {
									doc = dbuilder.parse(input);
									final NodeList nodes = doc.getElementsByTagName(Constant.CONCEPTLIST);
									if (nodes != null) {
										for (int h = 0; h < nodes.getLength(); h++) {
											if (nodes.item(h) instanceof Element) {
												final Element elem = (Element) nodes.item(h);
												doc.renameNode(elem, elem.getNamespaceURI(), Constant.CONCEPTLIST2);
											}
										}
									}
									final NodeList nodesC = doc.getElementsByTagName(Constant.CONCEPT);
									if (nodesC != null) {
										for (int n = 0; n < nodesC.getLength(); n++) {
											if (nodesC.item(n) instanceof Element) {
												final Element elem = (Element) nodesC.item(n);
												doc.renameNode(elem, elem.getNamespaceURI(), Constant.CONCEPT2);
											}
										}
									}
									final NodeList listOfStaff = doc.getElementsByTagName(Constant.VALUESET);
									if (listOfStaff != null) {
										for (int i = 0; i < listOfStaff.getLength(); i++) {
											if (listOfStaff.item(i) instanceof Element) {
												final Element elem = (Element) listOfStaff.item(i);
												doc.renameNode(elem, elem.getNamespaceURI(), Constant.VALUESET2);
											}
											final org.w3c.dom.Node staff = listOfStaff.item(i);
											final org.w3c.dom.Node importedNode = document.importNode(staff, true);
											String key = importedNode.getAttributes().getNamedItem(Constant.DISPLAYNAME)
													.getNodeValue();
											if (key.contains(Constant.TAB)) {
												key = key.replace(Constant.TAB, "");
											}
											((Element) importedNode).setAttribute(Constant.NAMEATTRIBUTE, key);
											((Element) importedNode).setAttribute(Constant.DISPLAYNAME, key);

											final DateTimeFormatter format = DateTimeFormatter
													.ofPattern("yyyy-MM-dd HH:mm:ss");
											if (picker != null) {
												final String formatDateTime = FileUtility.getPicker(picker)
														.format(format);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												((Element) importedNode).setAttribute(Constant.EFFECTIVEDATE,
														dateFinal);
											}

											if (comboBox != null) {
												((Element) importedNode).setAttribute(Constant.STATUSCODE,
														JavaFxUtility2.newSingleModel(comboBox).getSelectedItem());
											}
											if (textField2 != null) {
												if (textField2.getText() != null) {
													((Element) importedNode).setAttribute(Constant.VERSIONLABEL,
															textField2.getText());
												} else {
													((Element) importedNode).setAttribute(Constant.VERSIONLABEL, "");
												}
											} else {
												((Element) importedNode).setAttribute(Constant.VERSIONLABEL, "");
											}

											final org.w3c.dom.Node att = importedNode.getAttributes()
													.getNamedItem(Constant.VERSION);
											if (att != null) {
												importedNode.getAttributes().removeNamedItem(att.getNodeName());
											}
											final org.w3c.dom.Node att1 = importedNode.getAttributes()
													.getNamedItem(Constant.DATEFIN);
											if (att1 != null) {
												importedNode.getAttributes().removeNamedItem(att1.getNodeName());
											}
											final org.w3c.dom.Node att2 = importedNode.getAttributes()
													.getNamedItem(Constant.DATEMAJ);
											if (att2 != null) {
												importedNode.getAttributes().removeNamedItem(att2.getNodeName());
											}
											final org.w3c.dom.Node att3 = importedNode.getAttributes()
													.getNamedItem(Constant.DATEVALID);
											if (att3 != null) {
												importedNode.getAttributes().removeNamedItem(att3.getNodeName());
											}
											final org.w3c.dom.Node att4 = importedNode.getAttributes()
													.getNamedItem(Constant.DESC);
											if (att4 != null) {
												importedNode.getAttributes().removeNamedItem(att4.getNodeName());
											}
											final org.w3c.dom.Node att5 = importedNode.getAttributes()
													.getNamedItem(Constant.TYPEFICHIER);
											if (att5 != null) {
												importedNode.getAttributes().removeNamedItem(att5.getNodeName());
											}
											final org.w3c.dom.Node att6 = importedNode.getAttributes()
													.getNamedItem(Constant.URLFICHIER);
											if (att6 != null) {
												importedNode.getAttributes().removeNamedItem(att6.getNodeName());
											}

											final NodeList list = importedNode.getChildNodes();
											if (list != null) {
												for (int j = 0; j < list.getLength(); j++) {
													final org.w3c.dom.Node staf = list.item(j);
													final NodeList listk = staf.getChildNodes();
													if (listk != null) {
														List<Element> elements = new ArrayList<>();
														for (int v = 0; v < listk.getLength(); v++) {
															org.w3c.dom.Node node = listk.item(v);
															if (node.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
																elements.add((Element) node);
																staf.removeChild(node);
															}
														}
														elements.sort(Comparator
																.comparing(e -> e.getAttribute("codeSystem")));
														Map<String, List<Element>> groupedElements = elements.stream()
																.collect(Collectors.groupingBy(e -> {
																	String attributeValue = e
																			.getAttribute("codeSystem");
																	return attributeValue.isEmpty() ? "no-type"
																			: attributeValue;
																}));
														for (final Map.Entry<String, List<Element>> entry : groupedElements
																.entrySet()) {
															for (int k = 0; k < entry.getValue().size(); k++) {//
																final org.w3c.dom.Node item = entry.getValue().get(k);
																if (item.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
																	staf.appendChild(item);
																	final Element elem = (Element) item;
																	if (!elem.getAttribute(Constant.DATEFIN)
																			.isEmpty()) {
																		elem.getParentNode().removeChild(elem);
																	}
																	final org.w3c.dom.Node att7 = item.getAttributes()
																			.getNamedItem(Constant.DATEFIN);
																	if (textFieldL != null) {
																		if (textFieldL.getText() != null) {
																			((Element) item).setAttribute(
																					Constant.LEVEL,
																					textFieldL.getText());
																		} else {
																			((Element) item)
																					.setAttribute(Constant.LEVEL, "");
																		}
																	} else {
																		((Element) item).setAttribute(Constant.LEVEL,
																				"");
																	}
																	if (textFieldT != null) {
																		if (textFieldT.getText() != null) {
																			((Element) item).setAttribute(Constant.TYPE,
																					textFieldT.getText());
																		} else {
																			((Element) item).setAttribute(Constant.TYPE,
																					"");
																		}
																	} else {
																		((Element) item).setAttribute(Constant.TYPE,
																				"");
																	}

																	if (att7 != null) {
																		item.getAttributes()
																				.removeNamedItem(att7.getNodeName());
																	}
																	final org.w3c.dom.Node att8 = item.getAttributes()
																			.getNamedItem(Constant.DATEVALID);
																	if (att8 != null) {
																		item.getAttributes()
																				.removeNamedItem(att8.getNodeName());
																	}

																}
															}
														}
													}
												}
											}
											rootElement.appendChild(importedNode);
										}
									}

								} catch (final IOException | SAXException e) {
									if (LOG.isInfoEnabled()) {
										final String error = e.getMessage();
										LOG.error(error);
									}
									init(file);
								}
							}
						}

						if (!finalFiles.isEmpty()) {
							try (OutputStream output = Files.newOutputStream(Paths.get(textFieldP.getText()))) {
								writeXmlGenerate(document, output);
								if (isOkGenerate) {
									final Alert alert = new Alert(AlertType.INFORMATION);
									final DialogPane dialogPane = alert.getDialogPane();
									JavaFxUtility.newObsListDPane(dialogPane).add(NodeUtility2.newClassLoader()
											.getResource(Constant.STYLE0).toExternalForm());
									JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
									dialogPane.setMinHeight(130);
									dialogPane.setMaxHeight(130);
									dialogPane.setPrefHeight(130);
									alert.setHeaderText(null);
									alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
									alert.setContentText(INUtility.get("button.succes"));
									alert.showAndWait();
									isOkGenerate = false;
									files = new ArrayList<>();
									filesB = new ArrayList<>();
									listF = new ArrayList<>();
									listFR = new ArrayList<>();
									finalFiles = new ArrayList<>();
									JavaFxUtility2.newIndexModel(list).clearChecks();
									JavaFxUtility2.newIndexModel(listB).clearChecks();
									FileUtility.getItems(list).clear();
									FileUtility.getItems(listB).clear();
									selectAll.setSelected(false);
									hboxVisibility.setVisible(false);
								}
							} catch (final IOException | TransformerException e) {
								if (LOG.isInfoEnabled()) {
									final String error = e.getMessage();
									LOG.error(error);
								}
							}
						}
					});
				}
				accord.setExpandedPane(null);
			}
		});

		final Region spacer16 = ImageUtility2.getRegion3();
		hboxVisibility.setPadding(new Insets(0, 0, 0, 8));
		final ObservableList<Node> listHb = NodeUtility2.newHObsList(hboxVisibility);
		listHb.addAll(selectAll, spacer16, labelSelectAll);
		Platform.runLater(() -> {
			selectAll.selectedProperty().addListener(new ChangeListener<>() {
				@Override
				public void changed(final ObservableValue<? extends Boolean> observable, final Boolean oldValue,
						final Boolean newValue) {
					if (NodeUtility2.isEmptyOrNull(finalFiles)) {
						finalFiles = new ArrayList<>();
					}
					if (newValue != null) {
						if (selectAll.isSelected()) {
							JavaFxUtility2.newIndexModel(listB).checkAll();
						} else {
							JavaFxUtility2.newIndexModel(listB).clearChecks();
						}
					}
				}
			});
		});

		final BorderPane pane = new BorderPane();
		pane.setPrefWidth(NodeUtility2.newDimension(size).getWidth());
		pane.setLeft(listB);
		pane.setRight(list);

		listB.setStyle(Constant.STYLE9);
		list.setStyle(Constant.STYLE9);

		final SplitPane sPane = new SplitPane();
		sPane.setStyle(Constant.STYLE10);
		sPane.setOrientation(Orientation.HORIZONTAL);
		sPane.setDividerPositions(0f, 0.9f);

		// Create First TitledPane.
		final TitledPane firstPane = new TitledPane();
		JavaFxUtility.newObsTitledPane(firstPane).add(Constant.STYLE);
		firstPane.textProperty().bind(INUtility.createStringBinding("button.addzone.text"));
		firstPane.setPadding(new Insets(5, 5, 5, 5));
		firstPane.setStyle(Constant.STYLE5);
		firstPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);

		final VBox content1 = new VBox();
		NodeUtility2.newObsList(content1).add(gridPane);
		firstPane.setContent(content1);

		// Create Second TitledPane.
		final TitledPane secondPane = new TitledPane();
		JavaFxUtility.newObsTitledPane(secondPane).add(Constant.STYLE);
		secondPane.textProperty().bind(INUtility.createStringBinding("window.title"));
		secondPane.setPadding(new Insets(5, 5, 5, 5));
		secondPane.setStyle(Constant.STYLE5);
		secondPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);

		final VBox content2 = new VBox();
		content2.setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);

		NodeUtility2.newObsList(content2).addAll(gridPane2, gridPane3);
		secondPane.setContent(content2);
		NodeUtility2.newTitledPane(accord).addAll(firstPane, secondPane);
		accord.setMinSize(Accordion.USE_PREF_SIZE, Accordion.USE_PREF_SIZE);
		final VBox vPane = new VBox(accord, gridPane4);
		vPane.setMinSize(VBox.USE_PREF_SIZE, VBox.USE_PREF_SIZE);

		NodeUtility2.newSpane(sPane).addAll(hBoxImg, vPane);

		final ImageView imageViewVI = this.getIcon("/UIControls/fr.png");
		final ImageView imageViewEN = this.getIcon("/UIControls/en.png");

		// Create MenuItems
		final MenuItem menuItemVI = INUtility.menuBarForKey("button.tooltip.frensh");
		menuItemVI.setGraphic(imageViewVI);
		final MenuItem menuItemEN = INUtility.menuBarForKey("button.tooltip.english");
		menuItemEN.setGraphic(imageViewEN);

		final ImageView imageViewLang = this.getIcon("/UIControls/languages.png");

		// Create a MenuButton with 3 Items
		final SplitMenuButton splitMenu = new SplitMenuButton(menuItemVI, menuItemEN);
		splitMenu.textProperty().bind(INUtility.createStringBinding("textfield.tooltip.xsl"));
		splitMenu.setGraphic(imageViewLang);
		// Handling when the user clicks on the left side of SplitMenuButton.
		menuItemVI.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				INUtility.switchLanguage(Locale.FRANCE);
			}
		});
		menuItemEN.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				INUtility.switchLanguage(Locale.ENGLISH);
			}
		});
		final FlowPane rootF = new FlowPane();
		rootF.setVgap(5);
		rootF.setHgap(1);
		NodeUtility2.newFlowList(rootF).add(splitMenu);
		rootF.setAlignment(Pos.CENTER_RIGHT);
		rootF.setPadding(new Insets(0, 10, 0, 0));
		final SplitPane splitPane = new SplitPane();
		splitPane.setStyle("-fx-box-border: 0px;");
		splitPane.setOrientation(Orientation.HORIZONTAL);
		splitPane.setDividerPositions(0.91f, 0.09f);
		splitPane.setPadding(new Insets(5, 0, 0, 0));
		RegionUtility.getItemsSplit(splitPane).addAll(menuBar, rootF);
		menuBar.setMinSize(MenuBar.USE_PREF_SIZE, MenuBar.USE_PREF_SIZE);
		splitPane.setMinSize(SplitPane.USE_PREF_SIZE, SplitPane.USE_PREF_SIZE);
		rootF.setMinSize(FlowPane.USE_PREF_SIZE, FlowPane.USE_PREF_SIZE);

		final VBox root = new VBox(splitPane, sPane, hboxVisibility, pane);
		button10.setStyle(Constant.STYLE1);
		button10.setOnAction(new EventHandler<>() {
			@Override
			public void handle(final ActionEvent event) {
				Utility.runTask(taskUpdateStage, progress);
				Platform.runLater(() -> {
					if (files.isEmpty() && JavaFxUtility2.newIndexModel(listB).isEmpty()) {
						final Alert alert = new Alert(AlertType.ERROR);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(130);
						dialogPane.setMaxHeight(130);
						dialogPane.setPrefHeight(130);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.setContentText(INUtility.get("popup.error6"));
						alert.showAndWait();
					} else {
						final List<File> lfileFinal = new ArrayList<>();
						listFR = new ArrayList<>();
						final ObservableList<File> lfile = FileUtility.checkModel(JavaFxUtility2.newIndexModel(listB));
						final String pathToGenS = textFieldPS.getText();
						final Path pathS = Paths.get(pathToGenS);
						if (!pathS.toFile().exists()) {
							try {
								Files.createDirectories(pathS.toFile().toPath());
							} catch (final IOException e) {
								if (LOG.isInfoEnabled()) {
									final String error = e.getMessage();
									LOG.error(error);
								}
							}
						}
						if (lfile != null && !lfile.isEmpty()) {
							for (final File f : lfile) {
								try {
									Files.copy(FileUtility.toPath(f),
											Path.of(textFieldPS.getText() + FileUtility.getFileName(f)),
											StandardCopyOption.REPLACE_EXISTING);
									// add revisionDate attribute
									final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
									final DocumentBuilder builder = dbFactory.newDocumentBuilder();
									final Document doc = builder.parse(
											Path.of(textFieldPS.getText() + FileUtility.getFileName(f)).toFile());
									final String majDate = getXpathSingleValue(f,
											"//*:RetrieveValueSetResponse/*:ValueSet/@dateMaj/string()");
									if (majDate != null && !majDate.isEmpty()) {
										final String year = majDate.substring(0, 4);
										final String month = majDate.substring(4, 6);
										final String day = majDate.substring(6, 8);
										final String hh = majDate.substring(8, 10);
										final String min = majDate.substring(10, 12);
										final String ss = majDate.substring(12, 14);
										final String dateFinal = year + "-" + month + "-" + day + "T" + hh + ":" + min
												+ ":" + ss;
										final NodeList nList = doc.getElementsByTagName("ValueSet");
										for (int temp = 0; temp < nList.getLength(); temp++) {
											final org.w3c.dom.Node nNode = nList.item(temp);
											if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
												Element eElement = (Element) nNode;
												((Element) eElement).setAttribute("revisionDate", dateFinal);
												boolean hasAttribute = eElement.hasAttribute("xmlns");
												if (hasAttribute) {
													eElement.removeAttribute("xmlns");
												}
											}
										}
										final TransformerFactory transformerFac = TransformerFactory.newInstance();
										Transformer transformer = transformerFac.newTransformer();
										transformer.setOutputProperty(OutputKeys.INDENT, Constant.YES);
										transformer.setOutputProperty(OutputKeys.ENCODING, Constant.UTF8);
										transformer.setOutputProperty(OutputKeys.METHOD, Constant.XML);
										transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, Constant.YES);
										final ClassLoader classloader = NodeUtility2.newClassLoader();
										try (InputStream iStream = classloader.getResourceAsStream(Constant.PRETTY)) {
											transformer = transformerFac.newTransformer(new StreamSource(iStream));
											final DOMSource source = new DOMSource(doc);
											final StreamResult result = new StreamResult(Path
													.of(textFieldPS.getText() + FileUtility.getFileName(f)).toFile());
											transformer.transform(source, result);
										} catch (final IOException e) {
											if (LOG.isInfoEnabled()) {
												final String error = e.getMessage();
												LOG.error(error);
											}
										}
									}
									lfileFinal.add(f);
								} catch (final IOException | ParserConfigurationException | SAXException
										| TransformerFactoryConfigurationError | TransformerException e) {
									if (LOG.isInfoEnabled()) {
										final String error = e.getMessage();
										LOG.error(error);
									}
								}
							}
							final File[] list = new File(textFieldPS.getText()).listFiles();
							for (final File file : list) {
								if (!file.isDirectory()) {
									for (final File f : lfileFinal) {
										if (FileUtility.getFileName(file).equals(FileUtility.getFileName(f))
												&& !listFR.contains(f)) {
											listFR.add(file);
										}
									}
								}
							}
							filesB = new ArrayList<>();
							FileUtility.getItems(listB).clear();

						}
						for (final File file : files) {
							final String ext = FileUtility.getExtension(file.getAbsolutePath());
							if (Constant.XLSM.equals(ext) || Constant.XLSX.equals(ext)) {
								// Create a Workbook instance
								final Workbook workbook = NodeUtility.newWorkbook();
								// Load an Excel file
								workbook.loadFromFile(file.getAbsolutePath());
								Worksheet worksheet = null;
								Worksheet worksheet1 = null;
								for (final Object sheet : workbook.getWorksheets()) {
									final String sheetName = ((Worksheet) sheet).getName();
									if (sheetName.equalsIgnoreCase(Constant.JDVANS)) {
										worksheet = ((Worksheet) sheet);
									}
									if (sheetName.equalsIgnoreCase(Constant.JDVHL7)) {
										worksheet1 = ((Worksheet) sheet);
									}
								}
								final List<RetrieveValueSetResponse> listR = NodeUtility.newArrayList();
								// Get the row count
								final int maxRow = worksheet.getLastRow();
								// Get the column count
								final int maxColumn = worksheet.getLastColumn();
								// Loop through the rows
								for (int row = 2; row <= maxRow; row++) {
									final RetrieveValueSetResponse response = NodeUtility.newRetrieveSetResponse();
									// Loop through the columns
									for (int col = 1; col <= maxColumn; col++) {
										// Get the current cell
										final CellRange cell = NodeUtility.newCellRangeCol(worksheet, row, col);
										final CellRange cell8 = NodeUtility.newCellRange8(worksheet, row);
										if (cell8.getValue().isEmpty()) {
											if (NumberUtils.compare(col, 1) == 0) {
												response.setValueSetOID(cell.getValue());
											} else if (NumberUtils.compare(col, 2) == 0) {
												response.setValueSetName(cell.getValue());
											} else if (NumberUtils.compare(col, 3) == 0) {
												response.setCode(cell.getValue());
											} else if (NumberUtils.compare(col, 4) == 0) {
												response.setDisplayName(cell.getValue());
											} else if (NumberUtils.compare(col, 5) == 0) {
												response.setCodeSystemName(cell.getValue());
											} else if (NumberUtils.compare(col, 6) == 0) {
												response.setCodeSystem(cell.getValue());
											} else if (NumberUtils.compare(col, 7) == 0) {
												response.setDateDebut(cell.getValue());
											} else if (NumberUtils.compare(col, 8) == 0) {
												response.setDateFin(cell.getValue());
											}

											if (response.getDateDebut() == null) {
												response.setDateDebut("");
											}

											if (response.getDateFin() == null) {
												response.setDateFin("");
											}
											java.util.Date dateD = null;
											java.util.Date dateF = null;
											final String pattern = "yyyy-MM-dd HH:mm:ss";
											final DateFormat dformat = new SimpleDateFormat(pattern);
											if (!response.getDateDebut().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateD = formatter.parse(response.getDateDebut());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (!response.getDateFin().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateF = formatter.parse(response.getDateFin());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (dateD == null && dateF == null) {
												response.setRevisionDate("");
												response.setRevisionFormatDate(new Date(01 / 01 / 1000));
											} else if (dateD != null && dateF == null) {
												final String formatDateTime = dformat.format(dateD);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateD);
											} else if (dateD == null && dateF != null) {
												final String formatDateTime = dformat.format(dateF);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateF);
											} else if (dateD != null && dateF != null) {
												if (dateD.equals(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.after(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.before(dateF)) {
													final String formatDateTime = dformat.format(dateF);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateF);
												}
											}
										} else {
											if (NumberUtils.compare(col, 1) == 0) {
												response.setValueSetOID(cell.getValue());
											} else if (NumberUtils.compare(col, 7) == 0) {
												response.setDateDebutBarre(cell.getValue());
											} else if (NumberUtils.compare(col, 8) == 0) {
												response.setDateFinBarre(cell.getValue());
											}

											if (response.getDateDebutBarre() == null) {
												response.setDateDebutBarre("");
											}

											if (response.getDateFinBarre() == null) {
												response.setDateFinBarre("");
											}
											java.util.Date dateD = null;
											java.util.Date dateF = null;
											final String pattern = "yyyy-MM-dd HH:mm:ss";
											final DateFormat dformat = new SimpleDateFormat(pattern);
											if (!response.getDateDebutBarre().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateD = formatter.parse(response.getDateDebutBarre());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (!response.getDateFinBarre().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateF = formatter.parse(response.getDateFinBarre());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (dateD == null && dateF == null) {
												response.setRevisionDate("");
												response.setRevisionFormatDate(new Date(01 / 01 / 1000));
											} else if (dateD != null && dateF == null) {
												final String formatDateTime = dformat.format(dateD);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateD);
											} else if (dateD == null && dateF != null) {
												final String formatDateTime = dformat.format(dateF);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateF);
											} else if (dateD != null && dateF != null) {
												if (dateD.equals(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.after(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.before(dateF)) {
													final String formatDateTime = dformat.format(dateF);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateF);
												}
											}
										}
									}
									if (response.getValueSetOID() != null) {
										listR.add(response);
									}
								}
								// Get the row count
								final int maxRow1 = worksheet1.getLastRow();
								// Get the column count
								final int maxColumn1 = worksheet1.getLastColumn();
								// Loop through the rows
								for (int row = 2; row <= maxRow1; row++) {
									final RetrieveValueSetResponse response = NodeUtility.newRetrieveSetResponse();
									// Loop through the columns
									for (int col = 1; col <= maxColumn1; col++) {
										// Get the current cell
										final CellRange cell = NodeUtility.newCellRangeCol(worksheet1, row, col);
										final CellRange cell8 = NodeUtility.newCellRange8(worksheet1, row);
										if (cell8.getValue().isEmpty()) {
											if (NumberUtils.compare(col, 1) == 0) {
												response.setValueSetOID(cell.getValue());
											} else if (NumberUtils.compare(col, 2) == 0) {
												response.setValueSetName(cell.getValue());
											} else if (NumberUtils.compare(col, 3) == 0) {
												response.setCode(cell.getValue());
											} else if (NumberUtils.compare(col, 4) == 0) {
												response.setDisplayName(cell.getValue());
											} else if (NumberUtils.compare(col, 5) == 0) {
												response.setCodeSystemName(cell.getValue());
											} else if (NumberUtils.compare(col, 6) == 0) {
												response.setCodeSystem(cell.getValue());
											} else if (NumberUtils.compare(col, 7) == 0) {
												response.setDateDebut(cell.getValue());
											} else if (NumberUtils.compare(col, 8) == 0) {
												response.setDateFin(cell.getValue());
											}

											if (response.getDateDebut() == null) {
												response.setDateDebut("");
											}

											if (response.getDateFin() == null) {
												response.setDateFin("");
											}
											java.util.Date dateD = null;
											java.util.Date dateF = null;
											final String pattern = "yyyy-MM-dd HH:mm:ss";
											final DateFormat dformat = new SimpleDateFormat(pattern);
											if (!response.getDateDebut().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateD = formatter.parse(response.getDateDebut());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (!response.getDateFin().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateF = formatter.parse(response.getDateFin());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (dateD == null && dateF == null) {
												response.setRevisionDate("");
												response.setRevisionFormatDate(new Date(01 / 01 / 1000));
											} else if (dateD != null && dateF == null) {
												final String formatDateTime = dformat.format(dateD);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateD);
											} else if (dateD == null && dateF != null) {
												final String formatDateTime = dformat.format(dateF);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateF);
											} else if (dateD != null && dateF != null) {
												if (dateD.equals(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.after(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.before(dateF)) {
													final String formatDateTime = dformat.format(dateF);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateF);
												}
											}
										} else {
											if (NumberUtils.compare(col, 1) == 0) {
												response.setValueSetOID(cell.getValue());
											} else if (NumberUtils.compare(col, 7) == 0) {
												response.setDateDebutBarre(cell.getValue());
											} else if (NumberUtils.compare(col, 8) == 0) {
												response.setDateFinBarre(cell.getValue());
											}

											if (response.getDateDebutBarre() == null) {
												response.setDateDebutBarre("");
											}

											if (response.getDateFinBarre() == null) {
												response.setDateFinBarre("");
											}
											java.util.Date dateD = null;
											java.util.Date dateF = null;
											final String pattern = "yyyy-MM-dd HH:mm:ss";
											final DateFormat dformat = new SimpleDateFormat(pattern);
											if (!response.getDateDebutBarre().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateD = formatter.parse(response.getDateDebutBarre());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (!response.getDateFinBarre().isEmpty()) {
												final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy",
														Locale.FRENCH);
												try {
													dateF = formatter.parse(response.getDateFinBarre());
												} catch (final java.text.ParseException e) {
													if (LOG.isInfoEnabled()) {
														final String error = e.getMessage();
														LOG.error(error);
													}
												}
											}

											if (dateD == null && dateF == null) {
												response.setRevisionDate("");
												response.setRevisionFormatDate(new Date(01 / 01 / 1000));
											} else if (dateD != null && dateF == null) {
												final String formatDateTime = dformat.format(dateD);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateD);
											} else if (dateD == null && dateF != null) {
												final String formatDateTime = dformat.format(dateF);
												final String[] words = formatDateTime.split(" ");
												String dateFinal;
												dateFinal = words[0] + "T" + words[1];
												response.setRevisionDate(dateFinal);
												response.setRevisionFormatDate(dateF);
											} else if (dateD != null && dateF != null) {
												if (dateD.equals(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.after(dateF)) {
													final String formatDateTime = dformat.format(dateD);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateD);
												}

												if (dateD.before(dateF)) {
													final String formatDateTime = dformat.format(dateF);
													final String[] words = formatDateTime.split(" ");
													String dateFinal;
													dateFinal = words[0] + "T" + words[1];
													response.setRevisionDate(dateFinal);
													response.setRevisionFormatDate(dateF);
												}
											}
										}
									}
									if (response.getValueSetOID() != null) {
										listR.add(response);
									}
								}
								final Map<String, List<RetrieveValueSetResponse>> resultMap = listR.stream()
										.collect(Collectors.groupingBy(RetrieveValueSetResponse::getValueSetOID));

								final Iterator<?> iterator = resultMap.entrySet().iterator();
								while (iterator.hasNext()) {
									@SuppressWarnings("rawtypes")
									final Map.Entry mapentry = (Map.Entry) iterator.next();
									final List<RetrieveValueSetResponse> result2 = NodeUtility2
											.getListRetrieveResp(mapentry);

									final List<Date> twoLatestDates = result2.stream()
											.map(RetrieveValueSetResponse::getRevisionFormatDate)
											.sorted(Collections.reverseOrder(Date::compareTo)).distinct().limit(1)
											.collect(Collectors.toList());

									final String pattern = "yyyy-MM-dd HH:mm:ss";
									final DateFormat dformat = new SimpleDateFormat(pattern);
									final String formatDateTime = dformat.format(twoLatestDates.get(0));
									final String[] words = formatDateTime.split(" ");
									final String dateFinal = words[0] + "T" + words[1];

									for (final Iterator<RetrieveValueSetResponse> ite = result2.iterator(); ite
											.hasNext();) {
										RetrieveValueSetResponse retrieve = ite.next();
										retrieve.setRevisionDate(dateFinal);
										if (retrieve.getCode() == null && retrieve.getCodeSystem() == null) {
											ite.remove();
										}
									}
									CreateXMLFile.createXMLFile(result2, textFieldPS.getText());
									final File fileF = CreateXMLFile.getCreatedFile();
									listF.add(fileF);
								}

								workbook.dispose();
							}
						}
						for (final File pathname : listF) {
//							CreateXMLFile.modifyFile(pathname.getAbsolutePath(), "xmlns=\"\"", "");
							files.add(pathname);
						}
						for (final File pathname : listFR) {
							filesB.add(pathname);
						}
						if (files != null) {
							final ObservableList<File> oblist = FXCollections.observableArrayList();
							for (final File file : files) {
								if (FileUtility.getFileName(file).endsWith("xml")) {
									oblist.add(file);
								}
							}
							list.setItems(oblist);
							if (NodeUtility2.isEmptyOrNull(finalFiles)) {
								finalFiles = new ArrayList<>();
							}
							list.setCellFactory(lv -> new CheckBoxListCell<>(list::getItemBooleanProperty) {
								/**
								 * tooltip
								 */
								private final Tooltip tooltip = new Tooltip();

								@Override
								public void updateItem(final File employee, final boolean empty) {
									super.updateItem(employee, empty);
									setText(employee == null ? "" : String.format(employee.getAbsolutePath()));
									if (employee != null) {
										tooltip.setText(employee.getName() + '\n' + (double) employee.length() / 1024
												+ Constant.KBYTES);
										setTooltip(tooltip);
									}
								}
							});
							FileUtility.getCheckedIndices(JavaFxUtility2.newIndexModel(list))
									.addListener(new ListChangeListener<>() {
										@Override
										public void onChanged(final Change<? extends Integer> change) {
											while (change.next()) {
												if (change.wasAdded()) {
													for (final int i : change.getAddedSubList()) {
														if (!finalFiles.contains(FileUtility.getItems(list).get(i))) {
															finalFiles.add(FileUtility.getItems(list).get(i));
														}
													}
												}
												if (change.wasRemoved()) {
													for (final int i : change.getRemoved()) {
														finalFiles.remove(FileUtility.getItems(list).get(i));
													}
												}
											}
										}
									});
							JavaFxUtility2.newIndexModel(list).checkAll();
						}
						if (filesB != null) {
							final ObservableList<File> oblist = FXCollections.observableArrayList();
							for (final File file : filesB) {
								oblist.add(file);
							}
							listB.setItems(oblist);
							if (NodeUtility2.isEmptyOrNull(finalFiles)) {
								finalFiles = new ArrayList<>();
							}
							listB.setCellFactory(lv -> new CheckBoxListCell<>(listB::getItemBooleanProperty) {
								/**
								 * tooltip
								 */
								private final Tooltip tooltip = new Tooltip();

								@Override
								public void updateItem(final File employee, final boolean empty) {
									super.updateItem(employee, empty);
									setText(employee == null ? "" : String.format(employee.getAbsolutePath()));
									if (employee != null) {
										tooltip.setText(employee.getName() + '\n' + (double) employee.length() / 1024
												+ Constant.KBYTES);
										setTooltip(tooltip);
									}
								}
							});

							FileUtility.getCheckedIndices(JavaFxUtility2.newIndexModel(listB))
									.addListener(new ListChangeListener<>() {
										@Override
										public void onChanged(final Change<? extends Integer> change) {
											while (change.next()) {
												if (change.wasAdded()) {
													for (final int i : change.getAddedSubList()) {
														NodeUtility2.construcList(finalFiles, listB, i);
													}
												}
												if (change.wasRemoved()) {
													for (final int i : change.getRemoved()) {
														finalFiles.remove(FileUtility.getItems(listB).get(i));
													}
												}
											}
										}
									});
							selectAll.setSelected(true);
							JavaFxUtility2.newIndexModel(listB).checkAll();
						}

						final Alert alert = new Alert(AlertType.INFORMATION);
						final DialogPane dialogPane = alert.getDialogPane();
						JavaFxUtility.newObsListDPane(dialogPane)
								.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
						JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
						dialogPane.setMinHeight(130);
						dialogPane.setMaxHeight(130);
						dialogPane.setPrefHeight(130);
						alert.setHeaderText(null);
						alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
						alert.setContentText(INUtility.get("button.succes"));
						alert.showAndWait();
						isOkGenerate = false;
					}
					accord.setExpandedPane(null);
				});
			}
		});
		// scene principal du convertisseur
		final ScrollPane sp = new ScrollPane();
		sp.setContent(root);
		sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp.setFitToWidth(true);
		final Scene scene = new Scene(sp, Color.BEIGE);
		root.setMinSize(stage.getMinWidth(), stage.getMinHeight());
		final Parent parent = scene.getRoot();
		FileUtility.getStyleParent(parent).add(getClass().getResource(Constant.STYLE).toExternalForm());
		FileUtility.getIcons(stage).add(new Image(NodeUtility2.newClassLoader().getResourceAsStream(Constant.PHOTO)));
		stage.titleProperty().bind(INUtility.createStringBinding("popup.name"));
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.setOnCloseRequest(new EventHandler<>() {
			@Override
			public void handle(WindowEvent event) {
				if (secondStage != null && secondStage.isShowing()) {
					secondStage.close();
				}
				if (primaryStage != null && primaryStage.isShowing()) {
					primaryStage.close();
				}
				if (thirdStage != null && thirdStage.isShowing()) {
					thirdStage.close();
					textLogin.setText("");
					textPwd.setText("");
				}
			}
		});
		scene.widthProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(final ObservableValue<? extends Number> observableValue, final Number oldSceneWidth,
					final Number newSceneWidth) {
				ImageUtility3.constructView1(button1, button10, button3, buttonDownload, buttonTermino, buttonTermino1,
						chooseFileOther, newSceneWidth);
				ImageUtility3.constructView2(textLogin, textPwd, newSceneWidth);
				ImageUtility3.constructView3(label1, label2, textField2, labelL, textFieldL, labelT, newSceneWidth,
						firstPane, secondPane);
				ImageUtility3.constructView4(textFieldT, labelP, textFieldP, labelPS, textFieldPS, labelUrl,
						newSceneWidth);
				ImageUtility3.constructView5(labelPwd, labelLog, firstTitledPane, labelContent, labelUrl, labelContent,
						newSceneWidth);
				ImageUtility3.constructView6(textFieldUrl, textFieldUrl, labelEmpty1, comboBox, firstTitledPane, picker,
						newSceneWidth);
				ImageUtility4.constructView7(file, item3, item1, apropos, item2, newSceneWidth);
				button1.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
				button10.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
				button3.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
				buttonDownload.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
				buttonTermino.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
				buttonTermino1.setMinSize(gridPane4.getPrefWidth(), gridPane4.getPrefHeight());
			}
		});

		stage.show();
		firstPane.lookup(".titled-pane > .title > .text")
				.setStyle("-fx-font-weight: bold;-fx-font-family: Verdana, Tahoma, sans-serif;");
		secondPane.lookup(".titled-pane > .title > .text")
				.setStyle("-fx-font-weight: bold;-fx-font-family: Verdana, Tahoma, sans-serif;");
		firstPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);
		secondPane.setMinSize(TitledPane.USE_PREF_SIZE, TitledPane.USE_PREF_SIZE);
	}

	/**
	 * write Xml generate JDV final combined
	 * 
	 * @param doc
	 * @param output
	 * @throws TransformerException
	 * @throws UnsupportedEncodingException
	 */
	private void writeXmlGenerate(final Document doc, final OutputStream output)
			throws TransformerException, UnsupportedEncodingException {
		final TransformerFactory transformerFac = TransformerFactory.newInstance();
		Transformer transformer = transformerFac.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, Constant.YES);
		transformer.setOutputProperty(OutputKeys.ENCODING, Constant.UTF8);
		transformer.setOutputProperty(OutputKeys.METHOD, Constant.XML);
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, Constant.YES);
		final ClassLoader classloader = NodeUtility2.newClassLoader();
		try (InputStream iStream = classloader.getResourceAsStream(Constant.PRETTY)) {
			transformer = transformerFac.newTransformer(new StreamSource(iStream));
			final DOMSource source = new DOMSource(doc);
			final StreamResult result = new StreamResult(output);
			transformer.transform(source, result);
			isOkGenerate = true;
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
	}

	/**
	 * décompresse le fichier zip dans le répertoire donné
	 * 
	 * @param folder  le répertoire où les fichiers seront extraits
	 * @param zipfile le fichier zip à décompresser
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void unzip(final String zipFilePath, final String destFilePath) throws IOException {
		final File destination = new File(destFilePath);
		if (!destination.exists()) {
			destination.mkdir();
		}
		final Charset cp866 = Charset.forName(Constant.CHARSET);
		List<File> listFile;
		try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(Paths.get(zipFilePath)), cp866)) {
			ZipEntry zipEntry = zipInputStream.getNextEntry();
			File file;
			listFile = new ArrayList<>();
			while (zipEntry != null) {
				final String filePath = destination + File.separator + zipEntry.getName();
				if (zipEntry.isDirectory()) {
					final File directory = FileUtility.getDirectory(filePath);
					directory.mkdirs();
				} else {
					if (zipEntry.getName().startsWith(Constant.JDV)) {
						final String str = extractFile(zipInputStream, filePath);
						file = FileUtility.getDirectory(str);
						listFile.add(file);
					}
				}
				zipInputStream.closeEntry();
				zipEntry = zipInputStream.getNextEntry();
			}
		}
		for (final File pathname : listFile) {
			filesB.add(pathname);
		}
		constructZip();
	}

	/**
	 * constructZip
	 */
	private static void constructZip() {
		if (!filesB.isEmpty()) {
			final ObservableList<File> oblist = FXCollections.observableArrayList();
			for (final File file2 : filesB) {
				oblist.add(file2);
			}
			listB.setItems(oblist);
			if (NodeUtility2.isEmptyOrNull(finalFiles)) {
				finalFiles = new ArrayList<>();
			}
			listB.setCellFactory(lv -> new CheckBoxListCell<>(listB::getItemBooleanProperty) {
				/**
				 * tooltip
				 */
				private final Tooltip tooltip = new Tooltip();

				@Override
				public void updateItem(final File employee, final boolean empty) {
					super.updateItem(employee, empty);
					setText(employee == null ? "" : String.format(employee.getAbsolutePath()));
					if (employee != null) {
						tooltip.setText(
								employee.getName() + '\n' + (double) employee.length() / 1024 + Constant.KBYTES);
						setTooltip(tooltip);
					}
				}
			});
			hboxVisibility.setVisible(true);
		}
	}

	/**
	 * extractFile
	 * 
	 * @param Zip_Input_Stream
	 * @param File_Path
	 * @throws IOException
	 */
	private static String extractFile(final ZipInputStream zipInputStream, final String filePath) throws IOException {
		try (BufferedOutputStream bufferedOutput = new BufferedOutputStream(
				Files.newOutputStream(Paths.get(filePath)))) {
			final byte[] bytes = new byte[Constant.BUFFER_SIZE];
			int readByte = zipInputStream.read(bytes);
			while (readByte != -1) {
				bufferedOutput.write(bytes, 0, readByte);
				readByte = zipInputStream.read(bytes);
			}
		}
		final Path source = Paths.get(filePath);
		final String sourceReplace = source.toFile().getName().replace('-', '_');
		final Path sourceFile = source.resolveSibling(sourceReplace);
		if (sourceFile.toFile().exists()) {
			sourceFile.toFile().delete();
		}
		Files.move(source, sourceFile);
		String name = new File(filePath).getName();
		name = name.substring(0, name.indexOf('.')) + ".tabs";
		final String sourceName = sourceReplace.substring(0, sourceReplace.indexOf('.')) + ".tabs";
		new File(filePath).delete();
		final Path path = Paths.get(sourceFile.toUri());
		final Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(path), charset);
		content = content.replaceAll(name, sourceName);
		Files.write(path, content.getBytes(charset));
		return sourceFile.toString();
	}

	/**
	 * 
	 * @param filePath
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	private List<String> parse(final String filePath) throws IOException, XMLStreamException {
		final List<String> list = new ArrayList<>();
		mapSnomed = new ConcurrentHashMap<>();
		final File inputFile = new File(filePath);
		try (InputStream input = Files.newInputStream(Paths.get(inputFile.toURI()))) {
			final SAXParserFactory factory = SAXParserFactory.newInstance();
			final SAXParser saxParser = factory.newSAXParser();
			final DefaultHandler handler = new DefaultHandler() {
				/**
				 * bid
				 */
				public boolean bid;

				@Override
				public void startElement(final String uri, final String localName, final String qName,
						final Attributes attributes) throws SAXException {
					if (Constant.RDF_RDF.equalsIgnoreCase(qName)) {
						for (int i = 0; i < attributes.getLength(); i++) {
							final String name = attributes.getLocalName(i);
							final String val = attributes.getValue(i);
							mapSnomed.put(name, val);
						}
					}
					if (Constant.NOTATION.equalsIgnoreCase(qName)) {
						bid = true;
					}
				}

				@Override
				public void characters(final char character[], final int start, final int length) throws SAXException {
					if (bid) {
						final String donnees = new String(character, start, length);
						list.add(donnees);
						bid = false;
					}
				}

				@Override
				public void endElement(String uri, String localName, final String qName) throws SAXException {
					if (Constant.NOTATION.equalsIgnoreCase(qName)) {
						bid = false;
					}
				}
			};
			saxParser.parse(input, handler);
		} catch (final SAXException | IOException | ParserConfigurationException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return list;
	}

	/**
	 * getIcon
	 * 
	 * @param resourcePath
	 * @return
	 */
	private ImageView getIcon(final String resourcePath) {
		Image image = null;
		try (InputStream input = this.getClass().getResourceAsStream(resourcePath)) {
			image = new Image(input);
		} catch (final IOException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return new ImageView(image);
	}

	/**
	 * initialize screen
	 * 
	 * @param file
	 */
	public void init(final File file) {
		final Alert alert = new Alert(AlertType.ERROR);
		final DialogPane dialogPane = alert.getDialogPane();
		JavaFxUtility.newObsListDPane(dialogPane)
				.add(NodeUtility2.newClassLoader().getResource(Constant.STYLE0).toExternalForm());
		JavaFxUtility.newListDPaneClass(dialogPane).add(Constant.DIALOG);
		dialogPane.setMinHeight(150);
		dialogPane.setMaxHeight(150);
		dialogPane.setPrefHeight(150);
		alert.setHeaderText(null);
		alert.getDialogPane().lookupButton(ButtonType.OK).setVisible(true);
		alert.setContentText(INUtility.get("popup.error8") + Constant.RETOURCHARIOT + FileUtility.getFileName(file));
		alert.showAndWait();
		files = new ArrayList<>();
		filesB = new ArrayList<>();
		listF = new ArrayList<>();
		listFR = new ArrayList<>();
		finalFiles = new ArrayList<>();
		FileUtility.getItems(list).clear();
		FileUtility.getItems(listB).clear();
		JavaFxUtility2.newIndexModel(list).clearChecks();
		JavaFxUtility2.newIndexModel(listB).clearChecks();
		selectAll.setSelected(false);
	}

	/**
	 * sortByNumber file name
	 * 
	 * @param files
	 */
	public List<File> sortByNumber(final File[] files) {
		final List<File> lfiles = new ArrayList<>();
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(final File o1, final File o2) {
				final int n1 = extractNumber(o1.getName());
				final int n2 = extractNumber(o2.getName());
				return n1 - n2;
			}

			private int extractNumber(final String name) {
				int i = 0;
				try {
					final int first = name.indexOf('_') + 2;
					final int second = name.indexOf('_', name.indexOf('_') + 1);
					final String number = name.substring(first, second);
					i = Integer.parseInt(number);
				} catch (final Exception e) {
					i = 0;
					if (LOG.isInfoEnabled()) {
						final String error = e.getMessage();
						LOG.error(error);
					}
				}
				return i;
			}
		});
		for (final File f : files) {
			lfiles.add(f);
		}
		return lfiles;

	}

	/**
	 * getXpathSingleValue
	 * 
	 * @param file
	 * @return
	 */
	public static String getXpathSingleValue(final File file, final String expression) {
		String content = "";
		final Processor saxonProcessor = new Processor(false);
		final net.sf.saxon.s9api.DocumentBuilder builde = saxonProcessor.newDocumentBuilder();
		try {
			final XdmNode doc = builde.build(file);
			final XPathCompiler xpath = saxonProcessor.newXPathCompiler();
			final XPathSelector xdm = xpath.compile(expression).load();
			xdm.setContextItem(doc);
			if (xdm.evaluateSingle() != null) {
				content = xdm.evaluateSingle().toString();
			}
		} catch (final SaxonApiException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return content;
	}
}
