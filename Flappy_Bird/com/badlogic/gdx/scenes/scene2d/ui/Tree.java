package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pools;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public class Tree extends WidgetGroup {
    static boolean isMac;
    private ClickListener clickListener;
    private Node foundNode;
    float iconSpacingLeft;
    float iconSpacingRight;
    float indentSpacing;
    private float leftColumnWidth;
    boolean multiSelect;
    Node overNode;
    float padding;
    private float prefHeight;
    private float prefWidth;
    final Array<Node> rootNodes;
    final Array<Node> selectedNodes;
    private boolean sizeInvalid;
    TreeStyle style;
    boolean toggleSelect;
    float ySpacing;

    public static class Node {
        Actor actor;
        final Array<Node> children;
        boolean expanded;
        float height;
        Drawable icon;
        Object object;
        Node parent;
        boolean selectable;

        public Node(Actor actor) {
            this.children = new Array(0);
            this.selectable = true;
            if (actor == null) {
                throw new IllegalArgumentException("actor cannot be null.");
            }
            this.actor = actor;
        }

        public void setExpanded(boolean expanded) {
            if (expanded != this.expanded) {
                this.expanded = expanded;
                if (this.children.size != 0) {
                    Tree tree = getTree();
                    if (tree != null) {
                        int n;
                        int i;
                        if (expanded) {
                            n = this.children.size;
                            for (i = 0; i < n; i++) {
                                ((Node) this.children.get(i)).addToTree(tree);
                            }
                        } else {
                            n = this.children.size;
                            for (i = 0; i < n; i++) {
                                ((Node) this.children.get(i)).removeFromTree(tree);
                            }
                        }
                        tree.invalidateHierarchy();
                    }
                }
            }
        }

        protected void addToTree(Tree tree) {
            tree.addActor(this.actor);
            if (this.expanded) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).addToTree(tree);
                }
            }
        }

        protected void removeFromTree(Tree tree) {
            tree.removeActor(this.actor);
            if (this.expanded) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).removeFromTree(tree);
                }
            }
        }

        public void add(Node node) {
            insert(this.children.size, node);
        }

        public void addAll(Array<Node> nodes) {
            int n = nodes.size;
            for (int i = 0; i < n; i++) {
                insert(this.children.size, (Node) nodes.get(i));
            }
        }

        public void insert(int index, Node node) {
            node.parent = this;
            this.children.insert(index, node);
            updateChildren();
        }

        public void remove() {
            Tree tree = getTree();
            if (tree != null) {
                tree.remove(this);
            } else if (this.parent != null) {
                this.parent.remove(this);
            }
        }

        public void remove(Node node) {
            this.children.removeValue(node, true);
            if (this.expanded) {
                Tree tree = getTree();
                if (tree != null) {
                    node.removeFromTree(tree);
                    if (this.children.size == 0) {
                        this.expanded = false;
                    }
                }
            }
        }

        public void removeAll() {
            Tree tree = getTree();
            if (tree != null) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).removeFromTree(tree);
                }
            }
            this.children.clear();
        }

        public Tree getTree() {
            Group parent = this.actor.getParent();
            if (parent instanceof Tree) {
                return (Tree) parent;
            }
            return null;
        }

        public Actor getActor() {
            return this.actor;
        }

        public boolean isExpanded() {
            return this.expanded;
        }

        public Array<Node> getChildren() {
            return this.children;
        }

        public void updateChildren() {
            if (this.expanded) {
                Tree tree = getTree();
                if (tree != null) {
                    int n = this.children.size;
                    for (int i = 0; i < n; i++) {
                        ((Node) this.children.get(i)).addToTree(tree);
                    }
                }
            }
        }

        public Node getParent() {
            return this.parent;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public Object getObject() {
            return this.object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public Node findNode(Object object) {
            if (object != null) {
                return object.equals(this.object) ? this : Tree.findNode(this.children, object);
            } else {
                throw new IllegalArgumentException("object cannot be null.");
            }
        }

        public void collapseAll() {
            setExpanded(false);
            Tree.collapseAll(this.children);
        }

        public void expandAll() {
            setExpanded(true);
            if (this.children.size > 0) {
                Tree.expandAll(this.children);
            }
        }

        public void expandTo() {
            for (Node node = this.parent; node != null; node = node.parent) {
                node.setExpanded(true);
            }
        }

        public boolean isSelectable() {
            return this.selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        public void findExpandedObjects(Array objects) {
            if (this.expanded && !Tree.findExpandedObjects(this.children, objects)) {
                objects.add(this.object);
            }
        }

        public void restoreExpandedObjects(Array objects) {
            int n = objects.size;
            for (int i = 0; i < n; i++) {
                Node node = findNode(objects.get(i));
                if (node != null) {
                    node.setExpanded(true);
                    node.expandTo();
                }
            }
        }
    }

    public static class TreeStyle {
        public Drawable background;
        public Drawable minus;
        public Drawable over;
        public Drawable plus;
        public Drawable selection;

        public TreeStyle(Drawable plus, Drawable minus, Drawable selection) {
            this.plus = plus;
            this.minus = minus;
            this.selection = selection;
        }

        public TreeStyle(TreeStyle style) {
            this.plus = style.plus;
            this.minus = style.minus;
            this.selection = style.selection;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Tree.1 */
    class C08431 extends ClickListener {
        C08431() {
        }

        private boolean isCtrlPressed() {
            if (Tree.isMac) {
                return Gdx.input.isKeyPressed(63);
            }
            return Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT);
        }

        public void clicked(InputEvent event, float x, float y) {
            boolean z = true;
            Node node = Tree.this.getNodeAt(y);
            if (node == null || node != Tree.this.getNodeAt(getTouchDownY())) {
                return;
            }
            if (Tree.this.multiSelect && Tree.this.selectedNodes.size > 0 && (Gdx.input.isKeyPressed(59) || Gdx.input.isKeyPressed(60))) {
                float low = ((Node) Tree.this.selectedNodes.first()).actor.getY();
                float high = node.actor.getY();
                if (!isCtrlPressed()) {
                    Tree.this.selectedNodes.clear();
                }
                if (low > high) {
                    Tree.this.selectNodes(Tree.this.rootNodes, high, low);
                } else {
                    Tree.this.selectNodes(Tree.this.rootNodes, low, high);
                }
                Tree.this.fireChangeEvent();
                return;
            }
            if (!Tree.this.multiSelect || !isCtrlPressed()) {
                if (node.children.size > 0) {
                    float rowX = node.actor.getX();
                    if (node.icon != null) {
                        rowX -= Tree.this.iconSpacingRight + node.icon.getMinWidth();
                    }
                    if (x < rowX) {
                        if (node.expanded) {
                            z = false;
                        }
                        node.setExpanded(z);
                        return;
                    }
                }
                if (node.isSelectable()) {
                    boolean unselect;
                    if (Tree.this.toggleSelect && Tree.this.selectedNodes.size == 1 && Tree.this.selectedNodes.contains(node, true)) {
                        unselect = true;
                    } else {
                        unselect = false;
                    }
                    Tree.this.selectedNodes.clear();
                    if (unselect) {
                        Tree.this.fireChangeEvent();
                        return;
                    }
                }
                return;
            } else if (!node.isSelectable()) {
                return;
            }
            if (!Tree.this.selectedNodes.removeValue(node, true)) {
                Tree.this.selectedNodes.add(node);
            }
            Tree.this.fireChangeEvent();
        }

        public boolean mouseMoved(InputEvent event, float x, float y) {
            Tree.this.setOverNode(Tree.this.getNodeAt(y));
            return false;
        }

        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            super.exit(event, x, y, pointer, toActor);
            if (toActor == null || !toActor.isDescendantOf(Tree.this)) {
                Tree.this.setOverNode(null);
            }
        }
    }

    static {
        isMac = System.getProperty("os.name").contains("Mac");
    }

    public Tree(Skin skin) {
        this((TreeStyle) skin.get(TreeStyle.class));
    }

    public Tree(Skin skin, String styleName) {
        this((TreeStyle) skin.get(styleName, TreeStyle.class));
    }

    public Tree(TreeStyle style) {
        this.rootNodes = new Array();
        this.selectedNodes = new Array();
        this.ySpacing = 4.0f;
        this.iconSpacingLeft = 2.0f;
        this.iconSpacingRight = 2.0f;
        this.padding = 0.0f;
        this.sizeInvalid = true;
        this.multiSelect = true;
        this.toggleSelect = true;
        setStyle(style);
        initialize();
    }

    private void initialize() {
        EventListener c08431 = new C08431();
        this.clickListener = c08431;
        addListener(c08431);
    }

    public void setStyle(TreeStyle style) {
        this.style = style;
        this.indentSpacing = Math.max(style.plus.getMinWidth(), style.minus.getMinWidth()) + this.iconSpacingLeft;
    }

    public void add(Node node) {
        insert(this.rootNodes.size, node);
    }

    public void insert(int index, Node node) {
        remove(node);
        node.parent = null;
        this.rootNodes.insert(index, node);
        node.addToTree(this);
        invalidateHierarchy();
    }

    public void remove(Node node) {
        if (node.parent != null) {
            node.parent.remove(node);
            return;
        }
        this.rootNodes.removeValue(node, true);
        node.removeFromTree(this);
        invalidateHierarchy();
    }

    public void clearChildren() {
        super.clearChildren();
        this.rootNodes.clear();
        this.selectedNodes.clear();
        setOverNode(null);
        fireChangeEvent();
    }

    void fireChangeEvent() {
        ChangeEvent changeEvent = (ChangeEvent) Pools.obtain(ChangeEvent.class);
        fire(changeEvent);
        Pools.free(changeEvent);
    }

    public Array<Node> getNodes() {
        return this.rootNodes;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    private void computeSize() {
        this.sizeInvalid = false;
        this.prefWidth = this.style.plus.getMinWidth();
        this.prefWidth = Math.max(this.prefWidth, this.style.minus.getMinWidth());
        this.prefHeight = getHeight();
        this.leftColumnWidth = 0.0f;
        computeSize(this.rootNodes, this.indentSpacing);
        this.leftColumnWidth += this.iconSpacingLeft + this.padding;
        this.prefWidth += this.leftColumnWidth + this.padding;
        this.prefHeight = getHeight() - this.prefHeight;
    }

    private void computeSize(Array<Node> nodes, float indent) {
        float ySpacing = this.ySpacing;
        float spacing = this.iconSpacingLeft + this.iconSpacingRight;
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            float rowWidth = indent + this.iconSpacingRight;
            Actor actor = node.actor;
            if (actor instanceof Layout) {
                Layout layout = (Layout) actor;
                rowWidth += layout.getPrefWidth();
                node.height = layout.getPrefHeight();
                layout.pack();
            } else {
                rowWidth += actor.getWidth();
                node.height = actor.getHeight();
            }
            if (node.icon != null) {
                rowWidth += node.icon.getMinWidth() + spacing;
                node.height = Math.max(node.height, node.icon.getMinHeight());
            }
            this.prefWidth = Math.max(this.prefWidth, rowWidth);
            this.prefHeight -= node.height + ySpacing;
            if (node.expanded) {
                computeSize(node.children, this.indentSpacing + indent);
            }
        }
    }

    public void layout() {
        if (this.sizeInvalid) {
            computeSize();
        }
        layout(this.rootNodes, (this.leftColumnWidth + this.indentSpacing) + this.iconSpacingRight, getHeight() - (this.ySpacing / 2.0f));
    }

    private float layout(Array<Node> nodes, float indent, float y) {
        float ySpacing = this.ySpacing;
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            Actor actor = node.actor;
            float x = indent;
            if (node.icon != null) {
                x += node.icon.getMinWidth();
            }
            y -= node.height;
            node.actor.setPosition(x, y);
            y -= ySpacing;
            if (node.expanded) {
                y = layout(node.children, this.indentSpacing + indent, y);
            }
        }
        return y;
    }

    public void draw(SpriteBatch batch, float parentAlpha) {
        Color color = getColor();
        if (this.style.background != null) {
            batch.setColor(color.f40r, color.f39g, color.f38b, color.f37a * parentAlpha);
            this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
            batch.setColor(Color.WHITE);
        }
        draw(batch, this.rootNodes, this.leftColumnWidth);
        super.draw(batch, parentAlpha);
    }

    private void draw(SpriteBatch batch, Array<Node> nodes, float indent) {
        Drawable plus = this.style.plus;
        Drawable minus = this.style.minus;
        float x = getX();
        float y = getY();
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            Actor actor = node.actor;
            if (this.selectedNodes.contains(node, true) && this.style.selection != null) {
                this.style.selection.draw(batch, x, (actor.getY() + y) - (this.ySpacing / 2.0f), getWidth(), this.ySpacing + node.height);
            } else if (node == this.overNode && this.style.over != null) {
                this.style.over.draw(batch, x, (actor.getY() + y) - (this.ySpacing / 2.0f), getWidth(), this.ySpacing + node.height);
            }
            if (node.icon != null) {
                float iconY = actor.getY() + ((float) Math.round((node.height - node.icon.getMinHeight()) / 2.0f));
                batch.setColor(actor.getColor());
                node.icon.draw(batch, ((node.actor.getX() + x) - this.iconSpacingRight) - node.icon.getMinWidth(), y + iconY, node.icon.getMinWidth(), node.icon.getMinHeight());
                batch.setColor(Color.WHITE);
            }
            if (node.children.size != 0) {
                Drawable expandIcon;
                if (node.expanded) {
                    expandIcon = minus;
                } else {
                    expandIcon = plus;
                }
                SpriteBatch spriteBatch = batch;
                expandIcon.draw(spriteBatch, (x + indent) - this.iconSpacingLeft, y + (actor.getY() + ((float) Math.round((node.height - expandIcon.getMinHeight()) / 2.0f))), expandIcon.getMinWidth(), expandIcon.getMinHeight());
                if (node.expanded) {
                    draw(batch, node.children, this.indentSpacing + indent);
                }
            }
        }
    }

    public Node getNodeAt(float y) {
        this.foundNode = null;
        getNodeAt(this.rootNodes, y, getHeight());
        return this.foundNode;
    }

    private float getNodeAt(Array<Node> nodes, float y, float rowY) {
        int i = 0;
        int n = nodes.size;
        while (i < n) {
            Node node = (Node) nodes.get(i);
            if (y < (rowY - node.height) - this.ySpacing || y >= rowY) {
                rowY -= node.height + this.ySpacing;
                if (node.expanded) {
                    rowY = getNodeAt(node.children, y, rowY);
                    if (rowY == GroundOverlayOptions.NO_DIMENSION) {
                        return GroundOverlayOptions.NO_DIMENSION;
                    }
                }
                i++;
            } else {
                this.foundNode = node;
                return GroundOverlayOptions.NO_DIMENSION;
            }
        }
        return rowY;
    }

    void selectNodes(Array<Node> nodes, float low, float high) {
        int i = 0;
        int n = nodes.size;
        while (i < n) {
            Node node = (Node) nodes.get(i);
            if (node.actor.getY() >= low) {
                if (node.isSelectable()) {
                    if (node.actor.getY() <= high && !this.selectedNodes.contains(node, true)) {
                        this.selectedNodes.add(node);
                    }
                    if (node.expanded) {
                        selectNodes(node.children, low, high);
                    }
                }
                i++;
            } else {
                return;
            }
        }
    }

    public Array<Node> getSelection() {
        return this.selectedNodes;
    }

    public void setSelection(Node node) {
        this.selectedNodes.clear();
        if (node != null) {
            this.selectedNodes.add(node);
        }
        fireChangeEvent();
    }

    public void setSelection(Array<Node> nodes) {
        this.selectedNodes.clear();
        this.selectedNodes.addAll((Array) nodes);
        fireChangeEvent();
    }

    public void addSelection(Node node) {
        if (node != null) {
            this.selectedNodes.add(node);
            fireChangeEvent();
        }
    }

    public void clearSelection() {
        this.selectedNodes.clear();
        fireChangeEvent();
    }

    public TreeStyle getStyle() {
        return this.style;
    }

    public Array<Node> getRootNodes() {
        return this.rootNodes;
    }

    public Node getOverNode() {
        return this.overNode;
    }

    public void setOverNode(Node overNode) {
        this.overNode = overNode;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public void setYSpacing(float ySpacing) {
        this.ySpacing = ySpacing;
    }

    public void setIconSpacing(float left, float right) {
        this.iconSpacingLeft = left;
        this.iconSpacingRight = right;
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public void findExpandedObjects(Array objects) {
        findExpandedObjects(this.rootNodes, objects);
    }

    public void restoreExpandedObjects(Array objects) {
        int n = objects.size;
        for (int i = 0; i < n; i++) {
            Node node = findNode(objects.get(i));
            if (node != null) {
                node.setExpanded(true);
                node.expandTo();
            }
        }
    }

    static boolean findExpandedObjects(Array<Node> nodes, Array objects) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            if (node.expanded && !findExpandedObjects(node.children, objects)) {
                objects.add(node.object);
            }
        }
        return false;
    }

    public Node findNode(Object object) {
        if (object != null) {
            return findNode(this.rootNodes, object);
        }
        throw new IllegalArgumentException("object cannot be null.");
    }

    static Node findNode(Array<Node> nodes, Object object) {
        int i;
        int n = nodes.size;
        for (i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            if (object.equals(node.object)) {
                return node;
            }
        }
        n = nodes.size;
        for (i = 0; i < n; i++) {
            Node found = findNode(((Node) nodes.get(i)).children, object);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public void collapseAll() {
        collapseAll(this.rootNodes);
    }

    static void collapseAll(Array<Node> nodes) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            node.setExpanded(false);
            collapseAll(node.children);
        }
    }

    public void expandAll() {
        expandAll(this.rootNodes);
    }

    static void expandAll(Array<Node> nodes) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            ((Node) nodes.get(i)).expandAll();
        }
    }

    public ClickListener getClickListener() {
        return this.clickListener;
    }

    public void setMultiSelect(boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    public void setToggleSelect(boolean toggleSelect) {
        this.toggleSelect = toggleSelect;
    }
}
