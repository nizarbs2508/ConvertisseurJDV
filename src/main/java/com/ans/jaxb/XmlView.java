package com.ans.jaxb;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import impl.org.controlsfx.autocompletion.AutoCompletionTextFieldBinding;
import impl.org.controlsfx.autocompletion.SuggestionProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.skin.TextAreaSkin;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * XMLView
 * 
 * @author bensalem Nizar
 */
public class XmlView extends Application {

	/**
	 * treeTableView
	 */
	private TreeTableView<Node> treeTableView;
	/**
	 * fileG
	 */
	private String fileG = "";
	/**
	 * collapseAll
	 */
	private final Button collapseAll = INUtility.buttonForKey("button.redo.text");
	/**
	 * refresh
	 */
	private final Button refresh = new Button();
	/**
	 * labelRefresh
	 */
	private final Label labelRefresh = INUtility.labelForValue(() -> INUtility.get("textfield.tooltip.xml"));

	/**
	 * labelCount
	 */
	private final Label labelCount = new Label();
	/**
	 * count
	 */
	private Integer count = 0;

	/**
	 * tagNames
	 */
	private final String tagNames[] = { Constant.NOTCONSIDERED, Constant.ELEMENT, Constant.ATTRIBUTE3, Constant.TEXT,
			Constant.CDATASECTION, Constant.PROCESSING, Constant.COMMENT, Constant.DOCUMENT, Constant.DOCUMENTTYPE };
	/**
	 * map jdv by line
	 */
	private Map<Integer, String> map = new ConcurrentHashMap<>();

	/**
	 * root
	 */
	private TreeItem<Node> root;
	/**
	 * textArea
	 */
	private final TextArea textArea = new TextArea();
	/**
	 * total
	 */
	private int total;
	/**
	 * newLabel
	 */
	private final Label newLabel = new Label();
	/**
	 * listString
	 */
	private SortedSet<String> listString = new TreeSet<>();
	/**
	 * listValue
	 */
	private SortedSet<Integer> listValue = new TreeSet<>();
	/**
	 * Logger
	 */
	private static final Logger LOG = Logger.getLogger(XmlView.class);

	/**
	 * start
	 * 
	 * @param file
	 */
	public Stage start(final String file) {
		fileG = file;
		final Stage stage = new Stage();
		start(stage);
		return stage;
	}

	/**
	 * start
	 * 
	 * @param file
	 */
	@Override
	public void start(final Stage stage) {
		final TextField autoComplete = new TextField();
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
		newObsList(updatePane).addAll(progress);
		updatePane.setStyle(Constant.STYLE4);
		final Stage taskUpdateStage = new Stage(StageStyle.UNDECORATED);
		taskUpdateStage.setScene(new Scene(updatePane, 170, 170));
		// End progressBar
		final File file;
		file = new File(fileG);
		if (!file.exists()) {
			Utility.createDemo(file);
		}
		treeTableView = createTreeTableView(file);
		treeTableView.setStyle(Constant.STYLE11);
		treeTableView.setPrefWidth(755);
		final BorderPane layout = new BorderPane();
		final Region spacer2 = new Region();
		spacer2.setPrefHeight(10);
		VBox.setVgrow(spacer2, Priority.ALWAYS);
		final Region spacer3 = new Region();
		spacer3.setPrefHeight(10);
		VBox.setVgrow(spacer3, Priority.ALWAYS);
		layout.setLeft(treeTableView);
		textArea.setText(FileUtility.readFileContents(file));
		textArea.setEditable(false);
		XmlViewUtility.newObsListTArea(textArea).add(getClass().getResource(Constant.STYLE).toExternalForm());
		XmlViewUtility.newObsClassArea(textArea).add("text-area");
		textArea.setPrefWidth(755);
		layout.setRight(textArea);
		final HBox hbox = new HBox();
		final Image img = new Image(Constant.IMAGE15);
		final ImageView view = new ImageView(img);
		view.setPreserveRatio(true);
		final ObservableList<javafx.scene.Node> listHint = XmlViewUtility.newHObsList(hbox);
		collapseAll.setGraphic(view);
		collapseAll.setStyle(Constant.STYLE16);
		collapseAll.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				if (INUtility.get("button.redo.text").equals(collapseAll.getText())) {
					collapseAll.textProperty().bind(INUtility.createStringBinding("button.outils.text"));
					collapseTreeView(root);
				} else if (INUtility.get("button.outils.text").equals(collapseAll.getText())) {
					Utility.runTask(taskUpdateStage, progress);
					Platform.runLater(() -> {
						collapseAll.textProperty().bind(INUtility.createStringBinding("button.redo.text"));
						expandTreeView(root);
					});
				}
			}
		});

		final HBox hbox1 = new HBox();
		final ObservableList<javafx.scene.Node> lhbox1 = XmlViewUtility.newHObsList(hbox1);
		lhbox1.add(collapseAll);
		hbox1.setAlignment(Pos.CENTER_RIGHT);
		final Image img1 = new Image(Constant.IMAGE16);
		final ImageView view1 = new ImageView(img1);
		view1.setPreserveRatio(true);
		refresh.setGraphic(view1);
		refresh.setAlignment(Pos.CENTER);
		refresh.setPrefHeight(40);
		refresh.setStyle(Constant.STYLE16);
		final Region spacer14 = new Region();
		spacer14.setMaxWidth(20);
		HBox.setHgrow(spacer14, Priority.ALWAYS);
		final HBox hb1 = new HBox();
		final ObservableList<javafx.scene.Node> lhb1 = XmlViewUtility.newHObsList(hb1);
		lhb1.add(labelCount);
		hb1.setAlignment(Pos.CENTER_LEFT);
		final HBox hb2 = new HBox();
		final ObservableList<javafx.scene.Node> lhb2 = XmlViewUtility.newHObsList(hb2);
		listString = new TreeSet<>();
		listValue = new TreeSet<>();
		for (final Map.Entry<Integer, String> map : map.entrySet()) {
			listString.add(map.getValue());
			listValue.add(map.getKey());
		}
		final Region spacer13 = new Region();
		spacer13.setMaxWidth(10);
		HBox.setHgrow(spacer13, Priority.ALWAYS);
		newLabel.setPadding(new Insets(10, 0, 0, 0));
		final Label lbl = INUtility.labelForValue(() -> INUtility.get("button.search.text"));
		lbl.setPadding(new Insets(10, 0, 0, 0));
		final SuggestionProvider<String> provider = SuggestionProvider.create(listString);
		new AutoCompletionTextFieldBinding<>(autoComplete, provider);
		autoComplete.setPrefWidth(250);
		autoComplete.setPrefHeight(40);
		autoComplete.setStyle(Constant.STYLE8);
		autoComplete.textProperty().addListener((observable, oldValue, newValue) -> {
			total = 0;
			textArea.setScrollTop(0);
			for (final Map.Entry<Integer, String> mapentry : map.entrySet()) {
				if (mapentry.getValue().trim().equals(newValue.trim())) {
					newLabel.setText("Ligne " + mapentry.getKey());
					final String[] lines = textArea.getText().split("\n");
					for (Integer i = 0; i < mapentry.getKey(); i++) {
						final int numChars = lines[i].length();
						total += numChars;
					}
					final Rectangle2D lineBounds = ((TextAreaSkin) XmlViewUtility.getSkin(textArea))
							.getCharacterBounds(total);
					textArea.setScrollTop(lineBounds.getMinY());
				}
			}
			if (newValue != null && (newValue.isEmpty() || newValue.isBlank())) {
				newLabel.setText("");
			}
		});

		lhb2.addAll(lbl, autoComplete);

		refresh.setOnAction(new EventHandler<>() {
			@Override
			public void handle(ActionEvent event) {
				Utility.runTask(taskUpdateStage, progress);
				Platform.runLater(() -> {
					textArea.setText(FileUtility.readFileContents(file));
					count = 0;
					map = new HashMap<>();
					treeTableView = createTreeTableView(file);
					treeTableView.setStyle(Constant.STYLE11);
					treeTableView.setPrefWidth(755);
					layout.setLeft(treeTableView);
					listString = new TreeSet<>();
					listValue = new TreeSet<>();
					for (final Map.Entry<Integer, String> map1 : map.entrySet()) {
						listString.add(map1.getValue());
						listValue.add(map1.getKey());
					}
					provider.clearSuggestions();
					provider.addPossibleSuggestions(listString);
				});
			}
		});

		final Region spacer1 = new Region();
		spacer1.setMaxWidth(800);
		HBox.setHgrow(spacer1, Priority.ALWAYS);
		final Region spacer12 = new Region();
		spacer12.setMaxWidth(50);
		HBox.setHgrow(spacer12, Priority.ALWAYS);
		labelRefresh.setPadding(new Insets(10, 0, 0, 0));
		listHint.addAll(hbox1, spacer12, hb2, spacer13, newLabel, spacer14, labelRefresh, refresh, spacer1, hb1);
		final VBox box = new VBox();
		final ObservableList<javafx.scene.Node> listVint = newObsList(box);
		final HBox hbox2 = new HBox();
		final ObservableList<javafx.scene.Node> listHint2 = XmlViewUtility.newHObsList(hbox2);
		listHint2.add(spacer2);
		final HBox hbox3 = new HBox();
		final ObservableList<javafx.scene.Node> listHint3 = XmlViewUtility.newHObsList(hbox3);
		listHint3.add(spacer3);
		listVint.addAll(hbox3, hbox, hbox2);
		layout.setTop(box);
		final Scene scene = new Scene(layout);
		XmlViewUtility.getStyleScene(scene).add(getClass().getResource(Constant.STYLE).toExternalForm());
		stage.setScene(scene);

		stage.titleProperty().bind(INUtility.createStringBinding("popup.arborescence"));
		stage.setMaximized(true);
		scene.widthProperty().addListener(new ChangeListener<>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					final Number newSceneWidth) {
				if (newSceneWidth.doubleValue() >= 1509 && newSceneWidth.doubleValue() < 1632) { // ecran 16 pouces
					treeTableView.setStyle(Constant.STYLE11);
					collapseAll.setStyle(Constant.STYLE16);
					refresh.setStyle(Constant.STYLE16);
					labelCount.setStyle(Constant.STYLE11);
					textArea.setPrefWidth(755);
					spacer1.setMaxWidth(700);
					newLabel.setStyle(Constant.STYLE11);
					lbl.setStyle(Constant.STYLE11);
					labelRefresh.setStyle(Constant.STYLE11);
				} else if (newSceneWidth.doubleValue() >= 1632 && newSceneWidth.doubleValue() <= 1728) { // ecran 17
					treeTableView.setStyle(Constant.STYLE17);
					collapseAll.setStyle(Constant.STYLE18);
					labelCount.setStyle(Constant.STYLE17);
					refresh.setStyle(Constant.STYLE18);
					textArea.setPrefWidth(855);
					spacer1.setMaxWidth(800);
					newLabel.setStyle(Constant.STYLE17);
					lbl.setStyle(Constant.STYLE17);
					labelRefresh.setStyle(Constant.STYLE17);
				} else if (newSceneWidth.doubleValue() < 1509 && newSceneWidth.doubleValue() > 0) {
					treeTableView.setStyle(Constant.STYLE14);
					collapseAll.setStyle(Constant.STYLE19);
					refresh.setStyle(Constant.STYLE19);
					labelCount.setStyle(Constant.STYLE14);
					textArea.setPrefWidth(555);
					spacer1.setMaxWidth(400);
					newLabel.setStyle(Constant.STYLE14);
					lbl.setStyle(Constant.STYLE14);
					labelRefresh.setStyle(Constant.STYLE14);
				}
			}
		});
		stage.show();
	}

	/**
	 * createTreeTableView
	 * 
	 * @param file
	 * @return
	 */
	public TreeTableView<Node> createTreeTableView(final File file) {
		final TreeTableView<Node> treeTableView = new TreeTableView<>(createTreeItems(file));
		treeTableView.setShowRoot(true);
		final TreeTableColumn<Node, TreeItem<Node>> nameColumn = new TreeTableColumn<>(Constant.NOEUD);
		nameColumn.setCellValueFactory(
				(TreeTableColumn.CellDataFeatures<Node, TreeItem<Node>> cellData) -> new ReadOnlyObjectWrapper<>(
						cellData.getValue()));
		final Image[] images = new Image[tagNames.length];
		final Image image = new Image(getClass().getResourceAsStream(Constant.IMAGEPNG));
		for (int i = 0; i < images.length; i++) {
			images[i] = new WritableImage(image.getPixelReader(), i * 16, 0, 16, 16);
		}

		nameColumn.setCellFactory(column -> new TreeTableCell<>() {
			/**
			 * imageView
			 */
			public final ImageView[] imageView = new ImageView[images.length];
			{
				for (int i = 0; i < imageView.length; i++) {
					imageView[i] = new ImageView(images[i]);
				}
			}

			@Override
			protected void updateItem(final TreeItem<Node> item, final boolean empty) {
				super.updateItem(item, empty);
				if (item != null && !empty) {
					final Node node = XmlViewUtility.getItemValue(item);
					final Object obj = XmlViewUtility.getUserDataT(node);
					if (item.getValue() != null) {
						final String name = node.getNodeName();
						final String result = XmlViewUtility.textValue(name, obj);
						setText(result);
						setGraphic(imageView[Utility.typeIndex(node.getNodeType())]);
						return;
					}
				}
				setText(null);
				setGraphic(null);
			}
		});

		final String lab = INUtility.labelForValue(() -> INUtility.get("button.tree.text")).getText();
		labelCount.setText(count + " " + lab);
		labelCount.setStyle(Constant.STYLE11);
		nameColumn.setPrefWidth(300);
		nameColumn.setSortable(false);
		XmlViewUtility.getTreeTableView(treeTableView).add(nameColumn);

		final TreeTableColumn<Node, String> valueColumn = new TreeTableColumn<>(INUtility.get("button.undo.text"));
		valueColumn.setCellValueFactory(cellData -> {
			final TreeItem<Node> item = cellData.getValue();
			final Node childNode = XmlViewUtility.getItemValue(item);
			if (childNode != null) {
				final String name = childNode.getNodeType() == Node.DOCUMENT_TYPE_NODE
						? ((DocumentType) childNode).getInternalSubset()
						: childNode.getNodeValue();
				if (name != null) {
					return new ReadOnlyObjectWrapper<>(name.trim());
				}
			}
			return null;
		});

		valueColumn.setPrefWidth(400);
		valueColumn.setSortable(false);
		XmlViewUtility.getTreeTableView(treeTableView).add(valueColumn);
		XmlViewUtility.newTreeTableView(treeTableView).selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						final Node node = newValue.getValue();
						if (node != null) {
							return;
						}
					}
				});
		XmlViewUtility.newTreeTableView(treeTableView).selectFirst();
		return treeTableView;
	}

	/**
	 * createTreeItems
	 * 
	 * @param file
	 * @return
	 */
	public TreeItem<Node> createTreeItems(final File file) {
		try (InputStream targetStream = Files.newInputStream(Paths.get(file.toURI()))) {
			final PositionalXMLReader positionalReader = new PositionalXMLReader();
			final Document doc = positionalReader.readXML(targetStream);
			doc.getDocumentElement().normalize();
			root = new TreeItem<>(doc);
			root.setExpanded(true);
			addChildrenItem(root);
		} catch (final IOException | SAXException | ParserConfigurationException e) {
			if (LOG.isInfoEnabled()) {
				final String error = e.getMessage();
				LOG.error(error);
			}
		}
		return root;
	}

	/**
	 * newTreeItem
	 * 
	 * @param childNode
	 */
	private static TreeItem<Node> newTreeItem(final Node childNode) {
		return new TreeItem<>(childNode);
	}

	/**
	 * addChildrenItem
	 * 
	 * @param root
	 */
	public void addChildrenItem(final TreeItem<Node> root) {
		final Node node = XmlViewUtility.getValue(root);
		if (node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				XmlViewUtility.getChildrenRoot(root).add(new TreeItem<>(attrs.item(i)));
			}
		}
		final NodeList list = node.getChildNodes();
		final Object obj = node.getUserData(Constant.LINENUMBER);
		if (Constant.VALUESET2.equals(node.getNodeName()) && node.hasAttributes()) {
			final NamedNodeMap attrs = node.getAttributes();
			for (int i = 0; i < attrs.getLength(); i++) {
				if (Constant.DISPLAYNAME.equals(attrs.item(i).getNodeName())) {
					map.put((Integer) obj, attrs.item(i).getTextContent());
				}
			}
		}
		for (int i = 0; i < list.getLength(); i++) {
			final Node childNode = list.item(i);
			if (childNode != null && (childNode.getNodeType() != Node.TEXT_NODE
					|| (childNode.getNodeValue() != null && !childNode.getNodeValue().isEmpty()))) {
				final TreeItem<Node> treeItem = newTreeItem(childNode);
				treeItem.setExpanded(true);
				XmlViewUtility.getChildrenRoot(root).add(treeItem);
				addChildrenItem(treeItem);
				if (Constant.VALUESET2.equals(childNode.getNodeName())) {
					count++;
				}
			}
		}
	}

	/**
	 * collapseTreeView
	 * 
	 * @param item
	 */
	private void collapseTreeView(final TreeItem<?> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(false);
			for (final TreeItem<?> child : item.getChildren()) {
				collapseTreeView(child);
			}
		}
	}

	/**
	 * expandTreeView
	 * 
	 * @param item
	 */
	private void expandTreeView(final TreeItem<?> item) {
		if (item != null && !item.isLeaf()) {
			item.setExpanded(true);
			for (final TreeItem<?> child : item.getChildren()) {
				expandTreeView(child);
			}
		}
	}

	/**
	 * newObsList
	 * 
	 * @param vBox
	 */
	private static ObservableList<javafx.scene.Node> newObsList(final VBox vBox) {
		return vBox.getChildren();
	}

}